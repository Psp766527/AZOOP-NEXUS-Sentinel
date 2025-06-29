package com.daimlertrucksasia.it.dsc.nexus.sentinel.model.DTO;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing the request body for user login.
 * <p>
 * This class encapsulates user credentials submitted during the login process.
 * </p>
 *
 * <p>
 * Lombok {@code @Data} annotation is used to automatically generate:
 * <ul>
 *     <li>Getters and setters for all fields</li>
 *     <li>{@code equals()}, {@code hashCode()}, and {@code toString()} methods</li>
 * </ul>
 * </p>
 */
@Data
public class UserLoginRequest {

    /**
     * The username provided by the user during login.
     */
    private String username;

    /**
     * The password corresponding to the username.
     */
    private String password;
}
