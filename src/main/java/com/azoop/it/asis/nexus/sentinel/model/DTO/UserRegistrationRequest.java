package com.azoop.it.asis.nexus.sentinel.model.DTO;

import com.azoop.it.asis.nexus.sentinel.model.valueObjects.TokenType;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) used for registering a new user within the system.
 * <p>
 * This class holds all necessary fields required to create a user account,
 * including credentials, assigned roles, token permissions, and organizational metadata.
 * </p>
 *
 * <p>
 * Lombok {@code @Data} annotation generates:
 * <ul>
 *     <li>Getters and setters for all fields</li>
 *     <li>{@code equals()}, {@code hashCode()}, and {@code toString()} methods</li>
 * </ul>
 * </p>
 */
@Data
public class UserRegistrationRequest implements RegistrationEntity {

    /**
     * The unique username to be assigned to the new user.
     */
    private String username;

    /**
     * The user's password used for authentication.
     */
    private String password;

    /**
     * A list of roles granted to the user (e.g., "ROLE_USER", "ROLE_ADMIN").
     */
    private List<String> roles;

    /**
     * The list of token types (e.g., BEARER, MAC) this user is allowed to request.
     */
    private List<TokenType> allowedTokenTypes;

    /**
     * The department the user belongs to within the organization.
     */
    private String department;

    /**
     * The region or location the user is associated with.
     */
    private String region;

    /**
     * The user's email address.
     */
    private String email;
}
