package com.daimlertrucksasia.it.dsc.nexus.sentinel.service;

import com.daimlertrucksasia.it.dsc.nexus.sentinel.infrastucture.user.UserRepository;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.DTO.RegistrationResponse;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.DTO.RegistrationEntity;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.User;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.DTO.UserRegistrationRequest;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.valueObjects.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation responsible for handling the registration logic for users
 * (such as internal employees or admin users).
 *
 * <p>
 * This service implements the {@link RegistrationService} interface to support the
 * registration of entities of type {@link EntityType#USER}. It validates and persists
 * user data including credentials, roles, and organizational attributes.
 * </p>
 *
 * <p>
 * Passwords are securely hashed using a {@link PasswordEncoder} before being stored in the
 * underlying repository.
 * </p>
 *
 * <p>
 * Dependencies:
 * <ul>
 *     <li>{@link UserRepository} - for storing the user entity</li>
 *     <li>{@link PasswordEncoder} - for securely encoding the user password</li>
 * </ul>
 * </p>
 *
 * @see RegistrationService
 * @see UserRegistrationRequest
 * @see EntityType
 */
@Service
@RequiredArgsConstructor
public final class UserRegistrationService implements RegistrationService {

    /**
     * Encoder used to securely hash user passwords before storing.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Repository for persisting {@link User} entities.
     */
    private final UserRepository userRepository;

    /**
     * Returns the supported entity type for this service, which is {@link EntityType#USER}.
     *
     * @return the {@link EntityType} this service supports
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.USER;
    }

    /**
     * Registers a new user in the system.
     *
     * <p>
     * This method casts the generic {@link RegistrationEntity} to a specific
     * {@link UserRegistrationRequest}, encodes the password, builds the user entity,
     * persists it, and returns a {@link RegistrationResponse}.
     * </p>
     *
     * @param registrationRequest must be of type {@link UserRegistrationRequest}
     * @return a {@link RegistrationResponse} containing the saved user and the entity type
     * @throws IllegalArgumentException if the provided registration entity is not of type {@link UserRegistrationRequest}
     */
    @Override
    public RegistrationResponse register(RegistrationEntity registrationRequest) {
        if (!(registrationRequest instanceof UserRegistrationRequest request)) {
            throw new IllegalArgumentException("Invalid user registration request type.");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles())
                .allowedTokenTypes(request.getAllowedTokenTypes())
                .active(true)
                .department(request.getDepartment())
                .region(request.getRegion())
                .email(request.getEmail())
                .build();

        return RegistrationResponse.builder()
                .registeredEntity(userRepository.save(user))
                .entityType(EntityType.USER.name())
                .build();
    }
}
