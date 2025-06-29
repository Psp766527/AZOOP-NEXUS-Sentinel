package com.daimlertrucksasia.it.dsc.nexus.sentinel.model;

import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.valueObjects.SubjectType;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.valueObjects.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Represents an issued authentication or authorization token stored in the system.
 * <p>
 * This class is persisted in the MongoDB collection named {@code tokens}. It tracks metadata
 * such as issuance time, expiration, the subject associated with the token (user or client),
 * and the type of token.
 * </p>
 *
 * <p>Lombok Annotations:</p>
 * <ul>
 *     <li>{@code @Data} - Generates standard getters, setters, equals, hashCode, and toString methods.</li>
 *     <li>{@code @Builder} - Enables the builder pattern for flexible instantiation.</li>
 *     <li>{@code @NoArgsConstructor} - Generates a no-argument constructor.</li>
 *     <li>{@code @AllArgsConstructor} - Generates a constructor including all fields.</li>
 * </ul>
 *
 * <p>MongoDB Annotations:</p>
 * <ul>
 *     <li>{@code @Document("tokens")} - Specifies the MongoDB collection for token documents.</li>
 *     <li>{@code @Id} - Marks the primary identifier for the token document.</li>
 * </ul>
 */
@Document("tokens")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    /**
     * Unique identifier for the token document (MongoDB ID).
     */
    @Id
    private String id;

    /**
     * The token string value, such as a JWT, opaque token, or session identifier.
     */
    private String token;

    /**
     * Type of token issued, defined by {@link TokenType} (e.g., BEARER_JWT, API_KEY).
     */
    private TokenType tokenType;

    /**
     * Identifier of the subject (user or client) to whom the token was issued.
     */
    private String subjectId;

    /**
     * Type of subject (e.g., USER or CLIENT), defined by {@link SubjectType}.
     */
    private SubjectType subjectType;

    /**
     * Timestamp indicating when the token was issued.
     */
    private Instant issuedAt;

    /**
     * Timestamp indicating when the token will expire.
     */
    private Instant expiresAt;
}
