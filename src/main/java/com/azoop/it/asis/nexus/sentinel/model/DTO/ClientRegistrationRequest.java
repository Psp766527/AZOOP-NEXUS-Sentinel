package com.azoop.it.asis.nexus.sentinel.model.DTO;

import com.azoop.it.asis.nexus.sentinel.model.valueObjects.TokenType;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) for handling client registration requests.
 * <p>
 * This class encapsulates all necessary information required to register a new client
 * into the authentication system. It is typically used in API endpoints where a service
 * or application registers itself to obtain credentials and configuration for secure access.
 * </p>
 *
 * <p>Fields include:
 * <ul>
 *     <li>{@code clientId} - the unique identifier for the client</li>
 *     <li>{@code clientSecret} - the secret used to authenticate the client</li>
 *     <li>{@code roles} - list of roles assigned to the client (e.g., "ROLE_SERVICE")</li>
 *     <li>{@code scopes} - OAuth2 scopes the client can access</li>
 *     <li>{@code grantTypes} - supported OAuth2 grant types for the client</li>
 *     <li>{@code allowedTokenTypes} - token types the client can request</li>
 *     <li>{@code team} - team responsible for the client</li>
 *     <li>{@code serviceTier} - the environment or tier this client is registered for</li>
 * </ul>
 * </p>
 *
 * <p>
 * Lombok annotation used:
 * <ul>
 *     <li>{@code @Data} - generates getters, setters, equals, hashCode, and toString methods</li>
 * </ul>
 * </p>
 */
@Data
public class ClientRegistrationRequest implements RegistrationEntity {

    /**
     * The unique identifier to assign to the client.
     */
    private String clientId;

    /**
     * The secret/password associated with the client ID for authentication.
     */
    private String clientSecret;

    /**
     * The roles assigned to the client (e.g., "ROLE_API", "ROLE_ADMIN").
     */
    private List<String> roles;

    /**
     * The OAuth2 scopes that the client is allowed to access.
     */
    private List<String> scopes;

    /**
     * The OAuth2 grant types supported by this client (e.g., "client_credentials", "refresh_token").
     */
    private List<String> grantTypes;

    /**
     * A list of allowed token types (e.g., BEARER, MAC) for this client.
     */
    private List<TokenType> allowedTokenTypes;

    /**
     * The team responsible for or owning the client.
     */
    private String team;

    /**
     * The service tier (e.g., "production", "development") the client operates in.
     */
    private String serviceTier;
}
