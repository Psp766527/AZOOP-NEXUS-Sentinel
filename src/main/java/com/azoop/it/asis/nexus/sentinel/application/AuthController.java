package com.azoop.it.asis.nexus.sentinel.application;

import com.azoop.it.asis.nexus.sentinel.model.DTO.ClientAuthRequest;
import com.azoop.it.asis.nexus.sentinel.model.DTO.RefreshTokenRequest;
import com.azoop.it.asis.nexus.sentinel.model.DTO.TokenResponse;
import com.azoop.it.asis.nexus.sentinel.model.DTO.UserLoginRequest;
import com.azoop.it.asis.nexus.sentinel.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for handling authentication-related endpoints.
 * <p>
 * This controller provides endpoints for both user and client authentication,
 * as well as for refreshing tokens. It delegates the core authentication logic
 * to the {@link AuthenticationService}.
 * </p>
 *
 * <p>Base path: <code>/auth</code></p>
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * Service responsible for authenticating users and clients,
     * and generating JWT access and refresh tokens.
     */
    private final AuthenticationService authenticationService;

    /**
     * Authenticates a user with username and password and returns access and refresh tokens.
     *
     * @param request the login request containing user credentials
     * @return a {@link ResponseEntity} containing the generated {@link TokenResponse}
     */
    @PostMapping("/user/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(authenticationService.authenticateUser(request));
    }

    /**
     * Authenticates a client using client ID and secret, and returns access and refresh tokens.
     *
     * @param request the client authentication request
     * @return a {@link ResponseEntity} containing the generated {@link TokenResponse}
     */
    @PostMapping("/client/login")
    public ResponseEntity<TokenResponse> clientAuth(@RequestBody ClientAuthRequest request) {
        return ResponseEntity.ok(authenticationService.authenticateClient(request));
    }

    /**
     * Refreshes a user access token using a valid refresh token.
     *
     * @param request the refresh token request
     * @return a {@link ResponseEntity} containing the new access {@link TokenResponse}
     */
    @PostMapping("/login/refresh")
    public ResponseEntity<TokenResponse> loginRefresh(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authenticationService.userRefreshAccessToken(request));
    }

    /**
     * Refreshes a client access token using a valid refresh token.
     *
     * @param request the refresh token request
     * @return a {@link ResponseEntity} containing the new access {@link TokenResponse}
     */
    @PostMapping("/client/refresh")
    public ResponseEntity<TokenResponse> clientAuthRefresh(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authenticationService.clientRefreshAccessToken(request));
    }
}
