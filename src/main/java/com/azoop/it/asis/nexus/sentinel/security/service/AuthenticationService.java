package com.azoop.it.asis.nexus.sentinel.security.service;

import com.azoop.it.asis.nexus.sentinel.infrastucture.client.ClientRepository;
import com.azoop.it.asis.nexus.sentinel.infrastucture.token.TokenRepository;
import com.azoop.it.asis.nexus.sentinel.infrastucture.user.UserRepository;
import com.azoop.it.asis.nexus.sentinel.model.DTO.ClientAuthRequest;
import com.azoop.it.asis.nexus.sentinel.model.DTO.RefreshTokenRequest;
import com.azoop.it.asis.nexus.sentinel.model.DTO.TokenResponse;
import com.azoop.it.asis.nexus.sentinel.model.DTO.UserLoginRequest;
import com.azoop.it.asis.nexus.sentinel.model.Token;
import com.azoop.it.asis.nexus.sentinel.model.valueObjects.SubjectType;
import com.azoop.it.asis.nexus.sentinel.model.valueObjects.TokenType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class responsible for handling authentication logic for both users and clients.
 * <p>
 * This service provides the following key functionalities:
 * <ul>
 *     <li>Authenticates users and clients using their respective credentials</li>
 *     <li>Generates and returns JWT access and refresh tokens</li>
 *     <li>Supports token refresh mechanisms for both users and clients</li>
 *     <li>Stores generated tokens with expiration metadata in MongoDB</li>
 * </ul>
 * </p>
 *
 * <p>
 * It interacts with {@code UserRepository}, {@code ClientRepository}, and {@code TokenRepository}
 * for persistence, and uses {@code JwtService} to issue and validate tokens.
 * </p>
 * <p>
 * Configuration:
 * <ul>
 *     <li>{@code jwt.issuer} - Name of the token issuer (default: nexus-auth)</li>
 *     <li>{@code jwt.access.token.time.to.expire} - Access token expiration time in minutes</li>
 *     <li>{@code jwt.refresh.token.time.to.expire} - Refresh token expiration time in minutes</li>
 * </ul>
 * <p>
 * Dependencies:
 * <ul>
 *     <li>{@link UserRepository} for user data access</li>
 *     <li>{@link ClientRepository} for client data access</li>
 *     <li>{@link TokenRepository} for token persistence</li>
 *     <li>{@link JwtService} for JWT creation and validation</li>
 *     <li>{@link PasswordEncoder} for credential verification</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${jwt.issuer:nexus-auth:nexus-auth}")
    private String issuer;

    @Value("${jwt.access.token.time.to.expire:15}")
    private String accessTokenTTE;

    @Value("${jwt.refresh.token.time.to.expire:30}")
    private String refreshTokenTTE;

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Immutable DTO representing claims to embed into JWTs for client authentication.
     */
    @Builder
    private record ClientClaims(
            List<String> roles,
            List<String> scopes,
            List<String> grantTypes,
            String team,
            String tier
    ) {
    }

    /**
     * Immutable DTO representing claims to embed into JWTs for user authentication.
     */
    @Builder
    private record UserClaims(
            List<String> roles,
            String department,
            String region,
            String email
    ) {
    }

    /**
     * Authenticates a user using a username-password pair and issues access and refresh tokens.
     *
     * @param request user login request
     * @return {@link TokenResponse} containing both tokens and claims
     * @throws UsernameNotFoundException if the user is not found
     * @throws BadCredentialsException   if the password is incorrect
     */
    public TokenResponse authenticateUser(UserLoginRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        Map<String, Object> claims = new ObjectMapper().convertValue(UserClaims.builder()
                .roles(user.getRoles())
                .department(user.getDepartment())
                .region(user.getRegion())
                .email(user.getEmail())
                .build(), new TypeReference<>() {
        });

        String accessToken = jwtService.generateAccessToken(
                user.getUsername(), TokenType.BEARER_JWT, claims, SubjectType.USER);

        String refreshToken = jwtService.generateRefreshToken(
                user.getUsername(), TokenType.REFRESH_TOKEN, claims, SubjectType.USER
        );

        saveToken(user.getId(), SubjectType.USER, accessToken, TokenType.BEARER_JWT,
                parseDuration(accessTokenTTE, Duration.ofMinutes(15)));
        saveToken(user.getId(), SubjectType.USER, refreshToken, TokenType.REFRESH_TOKEN,
                parseDuration(refreshTokenTTE, Duration.ofDays(15)));

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .subject(user.getUsername())
                .scopes(user.getRoles())
                .tokenType(TokenType.BEARER_JWT)
                .expiresAt(Instant.now().plus(Duration.ofMinutes(15)))
                .issuer(issuer)
                .build();
    }

    /**
     * Authenticates a client using client ID and secret and issues access and refresh tokens.
     *
     * @param request client authentication request
     * @return {@link TokenResponse} with access and refresh tokens
     * @throws UsernameNotFoundException if the client is not found
     * @throws BadCredentialsException   if the secret is incorrect
     */
    public TokenResponse authenticateClient(ClientAuthRequest request) {
        var client = clientRepository.findByClientId(request.getClientId())
                .orElseThrow(() -> new UsernameNotFoundException("Client not found"));

        if (!passwordEncoder.matches(request.getClientSecret(), client.getClientSecret())) {
            throw new BadCredentialsException("Invalid client credentials");
        }

        ClientClaims clientClaims = ClientClaims.builder()
                .roles(client.getRoles())
                .scopes(client.getScopes())
                .grantTypes(client.getGrantTypes())
                .team(client.getTeam())
                .tier(client.getServiceTier())
                .build();

        Map<String, Object> claims = new ObjectMapper().convertValue(clientClaims, new TypeReference<>() {
        });

        String accessToken = jwtService.generateAccessToken(
                client.getClientId(), TokenType.BEARER_JWT, claims, SubjectType.CLIENT);

        String refreshToken = jwtService.generateRefreshToken(
                client.getClientId(), TokenType.REFRESH_TOKEN, claims, SubjectType.CLIENT
        );

        saveToken(client.getId(), SubjectType.CLIENT, accessToken, TokenType.BEARER_JWT,
                parseDuration(accessTokenTTE, Duration.ofMinutes(15)));
        saveToken(client.getId(), SubjectType.CLIENT, refreshToken, TokenType.REFRESH_TOKEN,
                parseDuration(refreshTokenTTE, Duration.ofDays(15)));

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .scopes(client.getScopes())
                .subject(client.getClientId())
                .tokenType(TokenType.BEARER_JWT)
                .expiresAt(Instant.now().plus(Duration.ofHours(1)))
                .issuer(issuer)
                .build();
    }

    /**
     * Issues a new access token for a user using a valid refresh token.
     *
     * @param refreshToken refresh token request
     * @return {@link TokenResponse} containing new access token
     * @throws BadCredentialsException if refresh token is invalid or not a refresh token
     */
    public TokenResponse userRefreshAccessToken(RefreshTokenRequest refreshToken) {
        if (!jwtService.validate(refreshToken.getRefresh_token()) ||
                !jwtService.isRefreshToken(refreshToken.getRefresh_token())) {
            throw new BadCredentialsException("Invalid or expired refresh token");
        }

        var claims = jwtService.extractAllClaims(refreshToken.getRefresh_token());
        var subject = claims.getSubject();
        var subjectType = SubjectType.valueOf(claims.get("subject_type", String.class));

        Map<String, Object> newUserClaims = new HashMap<>(claims);
        newUserClaims.keySet().removeIf(key -> key.equals("exp") || key.equals("iat") || key.equals("jti") || key.equals("type"));

        String newAccessToken = jwtService.generateAccessToken(subject, TokenType.BEARER_JWT, newUserClaims, subjectType);

        saveToken(subject, subjectType, newAccessToken, TokenType.BEARER_JWT,
                parseDuration(accessTokenTTE, Duration.ofMinutes(15)));

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken.getRefresh_token())
                .tokenType(TokenType.BEARER_JWT)
                .expiresAt(Instant.now().plus(Duration.ofMinutes(Long.parseLong(accessTokenTTE))))
                .issuer(issuer)
                .build();
    }

    /**
     * Issues a new access token for a client using a valid refresh token.
     *
     * @param refreshToken refresh token request
     * @return {@link TokenResponse} with a new access token
     * @throws BadCredentialsException if token is invalid or not refresh type
     */
    public TokenResponse clientRefreshAccessToken(RefreshTokenRequest refreshToken) {
        if (!jwtService.validate(refreshToken.getRefresh_token()) ||
                !jwtService.isRefreshToken(refreshToken.getRefresh_token())) {
            throw new BadCredentialsException("Invalid or expired refresh token");
        }

        var claims = jwtService.extractAllClaims(refreshToken.getRefresh_token());
        var subject = claims.getSubject();
        var subjectType = SubjectType.valueOf(claims.get("subject_type", String.class));

        Map<String, Object> newClientClaims = new HashMap<>(claims);
        newClientClaims.keySet().removeIf(key ->
                key.equals("roles") || key.equals("scopes") || key.equals("grant_types") ||
                        key.equals("team") || key.equals("tier"));

        String newAccessToken = jwtService.generateAccessToken(subject, TokenType.BEARER_JWT, newClientClaims, subjectType);

        saveToken(subject, subjectType, newAccessToken, TokenType.BEARER_JWT,
                parseDuration(accessTokenTTE, Duration.ofMinutes(15)));

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken.getRefresh_token())
                .tokenType(TokenType.BEARER_JWT)
                .expiresAt(Instant.now().plus(Duration.ofMinutes(Long.parseLong(accessTokenTTE))))
                .issuer(issuer)
                .build();
    }

    /**
     * Persists a generated token into the token repository with metadata.
     *
     * @param subjectId   ID of user or client
     * @param subjectType subject type enum (USER or CLIENT)
     * @param tokenValue  the JWT token string
     * @param type        token type (ACCESS or REFRESH)
     * @param duration    token validity duration
     */
    private void saveToken(String subjectId, SubjectType subjectType, String tokenValue, TokenType type, Duration duration) {
        Token token = Token.builder()
                .token(tokenValue)
                .tokenType(type)
                .subjectId(subjectId)
                .subjectType(subjectType)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(duration))
                .build();
        tokenRepository.save(token);
    }

    /**
     * Safely parses a string into a {@link Duration}. Falls back if parsing fails.
     *
     * @param value    string value representing duration in minutes
     * @param fallback fallback duration in case of parse failure
     * @return parsed duration or fallback
     */
    private Duration parseDuration(String value, Duration fallback) {
        try {
            return Duration.ofMinutes(Long.parseLong(value));
        } catch (Exception e) {
            return fallback;
        }
    }
}

