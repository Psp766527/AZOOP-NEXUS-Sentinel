package com.daimlertrucksasia.it.dsc.nexus.sentinel.model.DTO;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for handling client authentication requests.
 * <p>
 * This class is typically used when a client application attempts to authenticate
 * using its client credentials (ID and secret).
 * </p>
 *
 * <p>Lombok {@code @Data} annotation is used to automatically generate:
 * <ul>
 *     <li>Getters and setters</li>
 *     <li>{@code toString()}</li>
 *     <li>{@code equals()} and {@code hashCode()}</li>
 * </ul>
 * </p>
 */
@Data
public class ClientAuthRequest {

    /**
     * The unique identifier of the client attempting to authenticate.
     */
    private String clientId;

    /**
     * The secret or password associated with the client ID.
     */
    private String clientSecret;
}
