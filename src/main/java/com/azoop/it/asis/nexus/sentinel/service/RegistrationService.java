package com.azoop.it.asis.nexus.sentinel.service;

import com.azoop.it.asis.nexus.sentinel.model.DTO.RegistrationResponse;
import com.azoop.it.asis.nexus.sentinel.model.DTO.RegistrationEntity;
import com.azoop.it.asis.nexus.sentinel.model.valueObjects.EntityType;

/**
 * Sealed interface representing a registration service for different types of entities.
 * <p>
 * Implementations of this interface handle the registration logic for specific {@link EntityType}s,
 * such as users or clients (applications/services).
 * </p>
 *
 * <p>
 * Permitted implementations:
 * <ul>
 *     <li>{@link UserRegistrationService}</li>
 *     <li>{@link ClientRegistrationService}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Each implementation must provide:
 * <ul>
 *     <li>A method to register the corresponding {@link RegistrationEntity}</li>
 *     <li>A method to return the {@link EntityType} that the implementation supports</li>
 * </ul>
 * </p>
 */
public sealed interface RegistrationService permits UserRegistrationService, ClientRegistrationService {

    /**
     * Registers a new entity of the appropriate type.
     *
     * @param registration the registration data, must be a concrete implementation of {@link RegistrationEntity}
     * @return a {@link RegistrationResponse} containing the persisted entity and type information
     */
    RegistrationResponse register(RegistrationEntity registration);

    /**
     * Returns the type of entity this registration service supports.
     *
     * @return the {@link EntityType} handled by this implementation
     */
    EntityType getEntityType();
}
