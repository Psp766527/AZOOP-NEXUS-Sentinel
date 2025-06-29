package com.daimlertrucksasia.it.dsc.nexus.sentinel.application;

import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.Client;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.DTO.ClientRegistrationRequest;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.DTO.RegistrationResponse;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.DTO.UserResponseBody;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.User;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.DTO.UserRegistrationRequest;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.valueObjects.EntityType;
import com.daimlertrucksasia.it.dsc.nexus.sentinel.service.RegistrationServiceResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * REST controller that handles registration of users and clients.
 * <p>
 * This controller provides endpoints for registering new user and client entities
 * into the system. It uses the {@link RegistrationServiceResolver} to delegate
 * registration logic to the appropriate service based on entity type.
 * </p>
 *
 * <p>Base path: <code>/api/register</code></p>
 */
@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegistrationController {

    /**
     * Resolver to determine the appropriate registration service based on entity type
     * (e.g., USER or CLIENT).
     */
    private final RegistrationServiceResolver registrationServiceResolver;

    /**
     * Registers a new user entity in the system.
     *
     * @param request the user registration request containing necessary user details
     * @return a {@link ResponseEntity} with the created user information and location URI
     */
    @PostMapping("/user")
    public ResponseEntity<UserResponseBody> registerUser(@RequestBody UserRegistrationRequest request) {
        RegistrationResponse userResponse = registrationServiceResolver
                .resolve(EntityType.USER)
                .register(request);

        UserResponseBody userResponseBody = UserResponseBody
                .builder()
                .id(((User) userResponse.getRegisteredEntity()).getId())
                .username(((User) userResponse.getRegisteredEntity()).getId())
                .roles(((User) userResponse.getRegisteredEntity()).getRoles())
                .active(((User) userResponse.getRegisteredEntity()).isActive())
                .department(((User) userResponse.getRegisteredEntity()).getDepartment())
                .region(((User) userResponse.getRegisteredEntity()).getRegion())
                .build();

        return ResponseEntity
                .created(URI.create("/api/resource/user/" + ((User) userResponse.getRegisteredEntity()).getId()))
                .body(userResponseBody);
    }

    /**
     * Registers a new client entity in the system.
     *
     * @param request the client registration request containing necessary client details
     * @return a {@link ResponseEntity} with the created client information and location URI
     */
    @PostMapping("/client")
    public ResponseEntity<Client> registerClient(@RequestBody ClientRegistrationRequest request) {
        RegistrationResponse clientResponse = registrationServiceResolver
                .resolve(EntityType.CLIENT)
                .register(request);

        return ResponseEntity
                .created(URI.create("/api/resource/client/" + ((Client) clientResponse.getRegisteredEntity()).getClientId()))
                .body((Client) clientResponse.getRegisteredEntity());
    }
}
