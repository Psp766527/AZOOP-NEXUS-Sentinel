package com.azoop.it.asis.nexus.sentinel.model.valueObjects;

/**
 * Enumeration representing the types of entities that can be registered
 * or authenticated within the system.
 *
 * <p>This enum is typically used to distinguish between user-related
 * and client-related operations such as registration, authentication,
 * and authorization.</p>
 *
 * <ul>
 *     <li>{@link #USER} - Represents an end user entity.</li>
 *     <li>{@link #CLIENT} - Represents an external system or service acting as a client.</li>
 * </ul>
 */
public enum EntityType {

    /**
     * Represents an end user entity.
     */
    USER,

    /**
     * Represents a client application or system entity.
     */
    CLIENT

}
