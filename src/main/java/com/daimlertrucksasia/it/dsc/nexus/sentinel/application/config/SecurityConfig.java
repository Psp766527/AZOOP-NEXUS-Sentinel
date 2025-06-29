package com.daimlertrucksasia.it.dsc.nexus.sentinel.application.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for defining the security behavior of the application.
 *
 * <p>This class uses Spring Security to set up the security filter chain, disabling CSRF and form login,
 * and permitting unauthenticated access to certain endpoints while securing others.</p>
 *
 * <p>Specifically:</p>
 * <ul>
 *     <li>Disables CSRF protection (as often done in stateless REST APIs)</li>
 *     <li>Allows unauthenticated access to endpoints matching <code>/auth/**</code> and <code>/api/**</code></li>
 *     <li>Requires authentication for all other requests</li>
 *     <li>Disables form login and HTTP Basic authentication mechanisms</li>
 *     <li>Provides a bean for {@link PasswordEncoder} using {@link BCryptPasswordEncoder}</li>
 * </ul>
 *
 * <p>This configuration uses {@link AllowedUriConfig} to potentially customize security rules dynamically,
 * although it's not used directly in path matchers here — it can be expanded to support that.</p>
 *
 * <p>The filter chain bean is logged on startup to help trace allowed URIs if needed.</p>
 */

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Configuration for URIs allowed to bypass security filters.
     */
    private final AllowedUriConfig allowedUriConfig;

    /**
     * Defines the main security filter chain for HTTP requests.
     *
     * @param http the {@link HttpSecurity} object to configure
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info(">>> MySecurityFilter is invoked for URI: {}", allowedUriConfig);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(allowedUriConfig.getPatterns().toArray(new String[0])).permitAll()
                        .requestMatchers(allowedUriConfig.getAdmins().toArray(new String[0])).permitAll()
                        .requestMatchers("/auth/**", "/api/**").permitAll()
                        .anyRequest().authenticated()
                ).formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    /**
     * Bean for password encoding using BCrypt hashing algorithm.
     *
     * @return a {@link PasswordEncoder} instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
