package com.daimlertrucksasia.it.dsc.nexus.sentinel.global.exception;

/**
 * Exception thrown when a requested application user is not found in the system.
 * <p>
 * This exception typically indicates that a user lookup (by username, ID, etc.)
 * failed to find a matching user in the data store.
 * </p>
 *
 * <p>Usage example:</p>
 * <pre>
 * throw new ApplicationUserNotFoundException("User with ID 123 not found");
 * </pre>
 */
public class ApplicationUserNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code ApplicationUserNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public ApplicationUserNotFoundException(String message) {
        super(message);
    }
}
