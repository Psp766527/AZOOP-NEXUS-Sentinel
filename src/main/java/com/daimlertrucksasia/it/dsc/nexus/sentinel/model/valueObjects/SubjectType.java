package com.daimlertrucksasia.it.dsc.nexus.sentinel.model.valueObjects;

/**
 * Enumeration representing the type of subject involved in authentication or authorization.
 *
 * <p>This enum is used to distinguish between different kinds of principals
 * (i.e., users or clients) when processing tokens, assigning roles, or managing permissions.</p>
 *
 * <ul>
 *     <li>{@link #USER} - Represents a human user subject.</li>
 *     <li>{@link #CLIENT} - Represents a system or application client subject.</li>
 * </ul>
 */
public enum SubjectType {

    /**
     * Represents a human user subject.
     */
    USER,

    /**
     * Represents a system or application client subject.
     */
    CLIENT

}
