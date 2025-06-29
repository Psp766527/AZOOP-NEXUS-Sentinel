package com.daimlertrucksasia.it.dsc.nexus.sentinel.model.valueObjects;

/**
 * Enumeration of supported token types used for authentication and authorization.
 * <p>
 * This enum helps the system determine what types of security tokens are allowed or
 * supported for clients and users in different contexts (e.g., REST APIs, internal services, sessions).
 * </p>
 *
 * <ul>
 *     <li>{@link #BEARER_JWT} - JSON Web Token (JWT), a self-contained token carrying claims and signed for verification.</li>
 *     <li>{@link #REFRESH_TOKEN} - A long-lived token used to obtain new access tokens.</li>
 *     <li>{@link #OPAQUE} - A token with no readable content; validated via server-side introspection.</li>
 *     <li>{@link #API_KEY} - A static token often used for service-to-service authentication.</li>
 *     <li>{@link #SESSION} - A token used in stateful applications, usually stored in cookies to maintain user sessions.</li>
 *     <li>{@link #HMAC} - A custom token signed with HMAC using a shared secret for secure exchange.</li>
 * </ul>
 */
public enum TokenType {

    /**
     * JSON Web Token (JWT) – a self-contained, signed token carrying user claims.
     */
    BEARER_JWT,

    /**
     * Refresh token (if supported).
     */
    REFRESH_TOKEN,

    /**
     * Opaque token – a non-transparent token requiring server-side introspection.
     */
    OPAQUE,

    /**
     * API key – a static token used for internal service authentication.
     */
    API_KEY,

    /**
     * Session token – used in stateful applications, stored in cookies.
     */
    SESSION,

    /**
     * HMAC token – a custom, signed token (e.g., generated using HMAC with a shared secret).
     */
    HMAC
}
