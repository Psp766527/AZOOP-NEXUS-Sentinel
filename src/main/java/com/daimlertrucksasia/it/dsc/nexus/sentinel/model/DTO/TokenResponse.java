package com.daimlertrucksasia.it.dsc.nexus.sentinel.model.DTO;

import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.valueObjects.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing the structure of a token response returned after
 * successful authentication or token issuance.
 *
 * <p>This object typically contains the issued access token, optional refresh token, token
 * type, expiration, and additional metadata like scopes and subject identity.</p>
 *
 * <p>Lombok annotations used:
 * <ul>
 *     <li>{@code @Data} - Generates getters, setters, toString, equals, and hashCode methods.</li>
 *     <li>{@code @Builder} - Provides the builder pattern for constructing instances.</li>
 *     <li>{@code @AllArgsConstructor} - Creates a constructor including all fields.</li>
 * </ul>
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
public class TokenResponse {

    /**
     * The actual access token string issued to the client or user.
     * Typically a JWT (JSON Web Token) or an opaque token depending on the implementation.
     */
    private String accessToken;

    /**
     * The type of token issued, defined by {@link TokenType}.
     * For example: JWT or OPAQUE.
     */
    private TokenType tokenType;

    /**
     * The expiration timestamp for the access token.
     * Represented as an {@link Instant} (can be ISO-8601 or epoch time).
     */
    private Instant expiresAt;

    /**
     * The refresh token issued alongside the access token.
     * May be {@code null} or omitted if not applicable.
     */
    private String refreshToken;

    /**
     * A list of scopes granted to the access token, such as "read", "write", etc.
     */
    private List<String> scopes;

    /**
     * The authenticated subject for whom the token was issued.
     * This is usually the username or client ID.
     */
    private String subject;

    /**
     * The issuer of the token, representing the authentication server or system.
     * Example: "nexus-auth".
     */
    private String issuer;
}
