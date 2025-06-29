package com.azoop.it.asis.nexus.sentinel.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) used for sending user details in API responses.
 * <p>
 * This class encapsulates user-related data such as identity, roles, activity status,
 * and organizational metadata like department and region.
 * </p>
 *
 * <p>
 * Lombok {@code @Data} annotation generates:
 * <ul>
 *     <li>Getters and setters for all fields</li>
 *     <li>{@code equals()}, {@code hashCode()}, and {@code toString()} methods</li>
 * </ul>
 * </p>
 *
 * <p>
 * Lombok {@code @Builder} allows easy object creation using the builder pattern.
 * </p>
 */
@Data
@Builder
public class UserResponseBody {

    /**
     * Unique identifier of the user.
     */
    private String id;

    /**
     * Username associated with the user account.
     */
    private String username;

    /**
     * User's password (excluded from JSON responses for security reasons).
     */
    @JsonIgnore
    private String password;

    /**
     * List of roles or authorities assigned to the user.
     */
    private List<String> roles;

    /**
     * Indicates whether the user's account is active.
     */
    private boolean active;

    /**
     * Name of the department the user belongs to.
     */
    private String department;

    /**
     * Geographical region associated with the user.
     */
    private String region;
}
