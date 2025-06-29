package com.daimlertrucksasia.it.dsc.nexus.sentinel.service;

import com.daimlertrucksasia.it.dsc.nexus.sentinel.infrastucture.client.ClientRepository;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.Client;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.DTO.ClientRegistrationRequest;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.DTO.RegistrationResponse;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.DTO.RegistrationEntity;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.valueObjects.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service implementation responsible for handling the registration logic for clients
 * (such as backend services or external applications).
 *
 * <p>
 * This service implements the {@link RegistrationService} interface to support the
 * registration of entities of type {@link EntityType#CLIENT}. It persists the registered
 * client in the database and returns a structured {@link RegistrationResponse}.
 * </p>
 *
 * <p>
 * Dependencies:
 * <ul>
 *     <li>{@link ClientRepository} - for storing the client entity</li>
 *     <li>{@link PasswordEncoder} - for securely encoding the client secret</li>
 * </ul>
 * </p>
 */
@Service
@RequiredArgsConstructor
public final class ClientRegistrationService implements RegistrationService {

    /**
     * Repository used to persist {@link Client} entities.
     */
    private final ClientRepository clientRepository;

    /**
     * Password encoder used to securely store client secrets.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Specifies the type of entity this service handles.
     *
     * @return {@link EntityType#CLIENT}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.CLIENT;
    }

    /**
     * Registers a new client entity in the system.
     *
     * <p>The request must be of type {@link ClientRegistrationRequest}. The client
     * secret is encoded before being saved. The resulting client is persisted using
     * {@link ClientRepository}, and a {@link RegistrationResponse} is returned.</p>
     *
     * @param registrationRequest must be an instance of {@link ClientRegistrationRequest}
     * @return {@link RegistrationResponse} containing the persisted client and its entity type
     * @throws IllegalArgumentException if the request is not a {@link ClientRegistrationRequest}
     */
    @Override
    public RegistrationResponse register(RegistrationEntity registrationRequest) {
        if (!(registrationRequest instanceof ClientRegistrationRequest request)) {
            throw new IllegalArgumentException("Invalid client registration request type.");
        }

        Client client = Client.builder()
                .id(UUID.randomUUID().toString())
                .clientId(request.getClientId())
                .clientSecret(passwordEncoder.encode(request.getClientSecret()))
                .scopes(request.getScopes())
                .grantTypes(request.getGrantTypes())
                .allowedTokenTypes(request.getAllowedTokenTypes())
                .roles(request.getRoles())
                .team(request.getTeam())
                .serviceTier(request.getServiceTier())
                .build();

        return RegistrationResponse.builder()
                .registeredEntity(clientRepository.save(client))
                .entityType(EntityType.CLIENT.name())
                .build();
    }
}
