package com.daimlertrucksasia.it.dsc.nexus.sentinel.application.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class to load URIs and admin users that are allowed to bypass authentication or receive special access.
 *
 * <p>This class maps properties from the application's configuration file (e.g., `application.yml` or `application.properties`)
 * using the prefix {@code spring.auth.allowed.end.uri}.</p>
 *
 * <p>Expected configuration structure:</p>
 * <pre>{@code
 * spring:
 *   auth:
 *     allowed:
 *       end:
 *         uri:
 *           patterns:
 *             - /health
 *             - /public/**
 *           admins:
 *             - admin1
 *             - admin2
 * }</pre>
 *
 * <p>These values can be used to define security filters or rules within your security configuration.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *     <li>{@code patterns} - a list of URI patterns allowed without authentication</li>
 *     <li>{@code admins} - a list of usernames or identifiers considered as admin users</li>
 * </ul>
 * <p>
 * An example usage would be within a custom security filter that checks if the requested URI matches one of the allowed patterns.
 * <p>
 * This class is automatically registered as a Spring bean due to {@code @Configuration} and populated by Spring Boot because of {@code @ConfigurationProperties}.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.auth.allowed.end.uri")
public class AllowedUriConfig {

    /**
     * List of URI patterns that are permitted without requiring authentication.
     */
    private List<String> patterns;

    /**
     * List of admin usernames or identifiers who have elevated permissions.
     */
    private List<String> admins;
}

