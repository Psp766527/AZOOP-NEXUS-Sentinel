package com.azoop.it.asis.nexus.sentinel.model;

import com.azoop.it.asis.nexus.sentinel.model.DTO.RegistrationEntity;
import com.azoop.it.asis.nexus.sentinel.model.valueObjects.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Represents a client entity registered in the system with its associated authentication
 * credentials, authorization settings, and metadata.
 * <p>
 * This class is stored as a MongoDB document in the {@code client_credentials} collection.
 * It implements {@link RegistrationEntity}, indicating it can be used in registration workflows.
 * </p>
 *
 * <p>Lombok Annotations:</p>
 * <ul>
 *     <li>{@code @Data} - generates getters, setters, equals, hashCode, and toString</li>
 *     <li>{@code @NoArgsConstructor} - generates a no-arg constructor</li>
 *     <li>{@code @AllArgsConstructor} - generates a constructor with all fields</li>
 *     <li>{@code @Builder} - enables the builder pattern for constructing instances</li>
 * </ul>
 *
 * <p>MongoDB Annotations:</p>
 * <ul>
 *     <li>{@code @Document} - indicates this is a MongoDB document stored in "client_credentials"</li>
 *     <li>{@code @Id} - marks the primary identifier for the document</li>
 *     <li>{@code @Indexed(unique = true)} - ensures {@code clientId} is unique across documents</li>
 * </ul>
 */
@Document(collection = "client_credentials")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client implements RegistrationEntity {

    /**
     * Unique identifier for the client document (MongoDB document ID).
     */
    @Id
    private String id;

    /**
     * Unique client ID used for authentication.
     */
    @Indexed(unique = true)
    private String clientId;

    /**
     * Secret associated with the client for secure authentication.
     */
    private String clientSecret;

    /**
     * List of OAuth2 scopes granted to the client (e.g., "read", "write").
     */
    private List<String> scopes;

    /**
     * List of supported OAuth2 grant types for the client
     * (e.g., "client_credentials", "refresh_token").
     */
    private List<String> grantTypes;

    /**
     * List of allowed token types (e.g., BEARER_JWT, API_KEY).
     */
    private List<TokenType> allowedTokenTypes;

    /**
     * List of roles assigned to the client for access control
     * (e.g., "ROLE_EUREKA_CLIENT", "ROLE_ADMIN").
     */
    private List<String> roles;

    /**
     * Name of the team responsible for this client.
     */
    private String team;

    /**
     * The service tier/environment in which the client operates
     * (e.g., "production", "development").
     */
    private String serviceTier;
}
