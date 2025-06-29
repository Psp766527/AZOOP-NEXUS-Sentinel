package com.azoop.it.asis.nexus.sentinel.model.DTO;

import lombok.Builder;
import lombok.Data;

/**
 * Represents the response returned after a successful registration of a user or client entity.
 * <p>
 * This class encapsulates the registered entity and the type of the entity (e.g., "USER" or "CLIENT").
 * It is typically used as the response body for API endpoints related to registration operations.
 * </p>
 *
 * <p>
 * Lombok Annotations:
 * <ul>
 *     <li>{@code @Data} - Generates getters, setters, {@code toString()}, {@code equals()}, and {@code hashCode()} methods.</li>
 *     <li>{@code @Builder} - Enables the builder pattern for constructing instances of {@code RegistrationResponse}.</li>
 * </ul>
 * </p>
 */
@Builder
@Data
public class RegistrationResponse {

    /**
     * The entity that was successfully registered.
     * This can be an instance of {@link UserRegistrationRequest}
     * or {@link ClientRegistrationRequest},
     * both of which implement the {@link RegistrationEntity} interface.
     */
    RegistrationEntity registeredEntity;

    /**
     * A string representing the type of entity that was registered (e.g., "USER", "CLIENT").
     */
    String entityType;
}
