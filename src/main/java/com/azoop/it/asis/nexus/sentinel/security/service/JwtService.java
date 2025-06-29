package com.azoop.it.asis.nexus.sentinel.security.service;

import com.azoop.it.asis.nexus.sentinel.model.valueObjects.SubjectType;
import com.azoop.it.asis.nexus.sentinel.model.valueObjects.TokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
/**
 * Service class responsible for generating, parsing, and validating JWT tokens.
 *
 * <p>This class provides functionalities for creating access and refresh tokens,
 * extracting claims and subjects, verifying token types, and checking expiration status.</p>
 *
 * <p>Configuration:</p>
 * <ul>
 *     <li>{@code jwt.secret} - Secret key used for signing JWTs</li>
 *     <li>{@code jwt.issuer} - Token issuer name (default: nexus-auth)</li>
 * </ul>
 *
 * <p>Dependencies:</p>
 * <ul>
 *     <li>{@link io.jsonwebtoken.Jwts} - Used for building and parsing JWTs</li>
 *     <li>{@link io.jsonwebtoken.security.Keys} - Used for generating signing keys</li>
 * </ul>
 */
@Component
@Slf4j
public class JwtService {

    /**
     * Secret key (Base64 encoded) used for signing the JWT tokens.
     * Injected from application properties via {@code jwt.secret}.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * The issuer name used in the JWT tokens.
     * Defaults to {@code nexus-auth} if not explicitly set.
     */
    @Value("${jwt.issuer:nexus-auth}")
    private String issuer;

    /**
     * The secret key object generated from the decoded Base64 secret.
     */
    private SecretKey secretKey;

    /**
     * Initializes the {@link SecretKey} used for JWT signing by decoding the Base64 secret.
     * This method runs automatically after bean construction.
     */
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a short-lived JWT access token with a 1-hour expiration time.
     *
     * @param subject       the identifier for the subject (e.g., username or client ID)
     * @param tokenType     the type of token being created (ACCESS_TOKEN)
     * @param customClaims  additional claims to be included in the token
     * @param subjectType   type of the subject (e.g., USER or CLIENT)
     * @return signed JWT access token as a String
     */
    public String generateAccessToken(String subject, TokenType tokenType, Map<String, Object> customClaims, SubjectType subjectType) {
        return generateToken(subject, tokenType, customClaims, subjectType, Duration.ofHours(1));
    }

    /**
     * Generates a long-lived JWT refresh token with a 30-day expiration time.
     *
     * @param subject       the identifier for the subject
     * @param tokenType     the type of token being created (REFRESH_TOKEN)
     * @param customClaims  additional claims to be included in the token
     * @param subjectType   type of the subject (e.g., USER or CLIENT)
     * @return signed JWT refresh token as a String
     */
    public String generateRefreshToken(String subject, TokenType tokenType, Map<String, Object> customClaims, SubjectType subjectType) {
        return generateToken(subject, tokenType, customClaims, subjectType, Duration.ofDays(30));
    }

    /**
     * Internal method for building JWT tokens with dynamic expiration durations.
     *
     * @param subject       the identifier for the subject
     * @param tokenType     the type of token (ACCESS_TOKEN or REFRESH_TOKEN)
     * @param customClaims  additional claims
     * @param subjectType   subject classification
     * @param duration      expiration duration of the token
     * @return signed JWT token string
     */
    private String generateToken(String subject, TokenType tokenType, Map<String, Object> customClaims, SubjectType subjectType, Duration duration) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(duration)))
                .claim("type", tokenType.name())
                .claim("subject_type", subjectType)
                .addClaims(customClaims)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts all claims (standard and custom) from the given JWT token.
     *
     * @param token the JWT token string
     * @return parsed {@link Claims} object
     * @throws JwtException if token is invalid
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts the subject (e.g., username or client ID) from the JWT token.
     *
     * @param token the JWT token string
     * @return subject as a String
     */
    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extracts the token type (ACCESS_TOKEN or REFRESH_TOKEN) from the JWT.
     *
     * @param token the JWT token
     * @return token type enum
     */
    public TokenType extractTokenType(String token) {
        return TokenType.valueOf(extractAllClaims(token).get("type", String.class));
    }

    /**
     * Checks if the given JWT token has expired.
     *
     * @param token the JWT token
     * @return {@code true} if the token has expired; {@code false} otherwise
     */
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    /**
     * Validates the given JWT token for structure, signature, and expiration.
     *
     * @param token the JWT token
     * @return {@code true} if token is valid; {@code false} otherwise
     */
    public boolean validate(String token) {
        try {
            extractAllClaims(token); // throws if invalid
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Invalid JWT: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the given token is a refresh token.
     *
     * @param token the JWT token
     * @return {@code true} if token type is REFRESH_TOKEN; {@code false} otherwise
     */
    public boolean isRefreshToken(String token) {
        try {
            TokenType type = extractTokenType(token);
            return type == TokenType.REFRESH_TOKEN;
        } catch (Exception e) {
            return false;
        }
    }
}

