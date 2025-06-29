package com.daimlertrucksasia.it.dsc.nexus.sentinel.service;

import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.valueObjects.EntityType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Resolver component responsible for resolving a specific {@link RegistrationService}
 * implementation based on the provided {@link EntityType}.
 *
 * <p>
 * This class enables decoupling of registration logic by dynamically delegating
 * to the appropriate implementation of the sealed {@link RegistrationService} interface.
 * It helps in selecting the right service (e.g., user or client registration) at runtime.
 * </p>
 *
 * <p>
 * It is automatically populated by Spring with all beans implementing {@link RegistrationService},
 * and organizes them into a map for quick lookup based on {@link EntityType}.
 * </p>
 *
 * <p><strong>Usage example:</strong></p>
 * <pre>{@code
 * RegistrationService userService = resolver.resolve(EntityType.USER);
 * userService.register(request);
 * }</pre>
 *
 * @see RegistrationService
 * @see EntityType
 */
@Component
public class RegistrationServiceResolver {

    /**
     * A map of {@link EntityType} to the corresponding {@link RegistrationService} implementation.
     */
    private final Map<EntityType, RegistrationService> serviceMap;

    /**
     * Constructs a new instance of {@code RegistrationServiceResolver} by mapping each
     * {@link RegistrationService} to its supported {@link EntityType}.
     *
     * @param services the list of all available {@link RegistrationService} implementations,
     *                 injected by Spring during component initialization
     */
    public RegistrationServiceResolver(List<RegistrationService> services) {
        this.serviceMap = services.stream()
                .collect(Collectors.toMap(RegistrationService::getEntityType, s -> s));
    }

    /**
     * Resolves and returns the {@link RegistrationService} for the specified {@link EntityType}.
     *
     * @param entityType the entity type for which the appropriate service is required
     * @return the corresponding {@link RegistrationService} or {@code null} if no matching service is found
     */
    public RegistrationService resolve(EntityType entityType) {
        return serviceMap.get(entityType);
    }
}
