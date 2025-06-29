package com.daimlertrucksasia.it.dsc.nexus.sentinel.model.DTO;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing a refresh token request.
 * <p>
 * Used when a user or client attempts to renew an access token using a valid refresh token,
 * typically in OAuth2 authentication flows.
 * </p>
 *
 * <p><b>Field:</b></p>
 * <ul>
 *     <li>{@link #refresh_token} - the refresh token string used to obtain a new access token</li>
 * </ul>
 *
 * <p><b>Lombok-generated methods:</b></p>
 * <ul>
 *     <li>{@code String getRefresh_token()} - returns the current refresh token</li>
 *     <li>{@code void setRefresh_token(String refresh_token)} - sets the refresh token value</li>
 *     <li>{@code boolean equals(Object o)} - compares objects for equality</li>
 *     <li>{@code int hashCode()} - generates hash code based on field(s)</li>
 *     <li>{@code String toString()} - returns string representation of the object</li>
 * </ul>
 *
 * <p>
 * Lombok annotation used:
 * <ul>
 *     <li>{@code @Data} - a shortcut for {@code @Getter}, {@code @Setter}, {@code @ToString}, {@code @EqualsAndHashCode},
 *          and {@code @RequiredArgsConstructor}
 *     </li>
 * </ul>
 * </p>
 */
@Data
public class RefreshTokenRequest {

    /**
     * The refresh token string used to request a new access token.
     */
    private String refresh_token;

}
