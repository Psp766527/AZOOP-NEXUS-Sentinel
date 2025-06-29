package com.azoop.it.asis.nexus.sentinel.infrastucture.user;

import com.azoop.it.asis.nexus.sentinel.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link User} entities in MongoDB.
 * <p>
 * This interface extends {@link MongoRepository}, which provides generic implementations
 * for basic CRUD and paging operations. It also includes a custom method for retrieving
 * a user by their username.
 * </p>
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Retrieves a {@link User} entity by their username.
     *
     * @param username the username to search for
     * @return an {@link Optional} containing the found user, or empty if no match is found
     */
    Optional<User> findByUsername(String username);
}
