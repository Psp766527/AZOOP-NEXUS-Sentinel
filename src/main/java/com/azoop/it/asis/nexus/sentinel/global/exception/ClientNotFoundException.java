package com.azoop.it.asis.nexus.sentinel.global.exception;

/**
 * Exception thrown when a requested client entity is not found in the system.
 * <p>
 * This exception is typically used when client lookups (e.g., by client ID)
 * fail to find a corresponding entry in the data store.
 * </p>
 *
 * <p>Usage example:</p>
 * <pre>
 * throw new ClientNotFoundException("Client with ID 'abc123' not found");
 * </pre>
 */
public class ClientNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code ClientNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public ClientNotFoundException(String message) {
        super(message);
    }
}
