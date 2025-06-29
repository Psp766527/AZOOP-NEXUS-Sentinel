package com.azoop.it.asis.nexus.sentinel.model;

import com.azoop.it.asis.nexus.sentinel.model.DTO.RegistrationEntity;
import com.azoop.it.asis.nexus.sentinel.model.valueObjects.TokenType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Represents a user entity within the authentication and authorization system.
 * <p>
 * This class is mapped to the MongoDB collection {@code user_credentials} and contains
 * essential data required for user authentication, authorization, and identity claims.
 * </p>
 *
 * <p>Implements {@link RegistrationEntity} to support polymorphic registration handling.</p>
 *
 * <p><b>Lombok Annotations:</b></p>
 * <ul>
 *     <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString methods.</li>
 *     <li>{@code @NoArgsConstructor} - Generates a no-argument constructor.</li>
 *     <li>{@code @AllArgsConstructor} - Generates a constructor with all fields.</li>
 *     <li>{@code @Builder} - Enables the builder pattern for object creation.</li>
 * </ul>
 *
 * <p><b>Spring Data Annotations:</b></p>
 * <ul>
 *     <li>{@code @Document("user_credentials")} - Maps the class to the MongoDB collection {@code user_credentials}.</li>
 *     <li>{@code @Id} - Marks the primary identifier of the document.</li>
 *     <li>{@code @Indexed(unique = true)} - Ensures the {@code username} is unique in the collection.</li>
 * </ul>
 */
@Document(collection = "user_credentials")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements RegistrationEntity {

    /**
     * Unique identifier for the user document (MongoDB ID).
     */
    @Id
    private String id;

    /**
     * The unique username used to identify and authenticate the user.
     */
    @Indexed(unique = true)
    private String username;

    /**
     * The hashed password used for user authentication.
     */
    @JsonIgnore
    private String password;

    /**
     * List of roles assigned to the user (e.g., ROLE_USER, ROLE_ADMIN).
     */
    private List<String> roles;

    /**
     * List of token types the user is allowed to use (e.g., BEARER_JWT, API_KEY).
     */
    private List<TokenType> allowedTokenTypes;

    /**
     * Indicates whether the user account is active and allowed to authenticate.
     */
    private boolean active;

    // Optional claims and additional metadata

    /**
     * Department to which the user belongs.
     */
    private String department;

    /**
     * Geographical region or organizational unit.
     */
    private String region;

    /**
     * User's email address used for communication or recovery purposes.
     */
    private String email;
}
