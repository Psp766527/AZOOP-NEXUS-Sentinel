package com.azoop.it.asis.nexus.sentinel.infrastucture.token;

import com.azoop.it.asis.nexus.sentinel.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Token} entities in MongoDB.
 * <p>
 * This interface extends {@link MongoRepository}, providing built-in methods for
 * standard CRUD operations and supports custom query methods for token-based lookups.
 * </p>
 */
public interface TokenRepository extends MongoRepository<Token, String> {

    /**
     * Finds a {@link Token} entity by its token string.
     *
     * @param token the token value to search for
     * @return an {@link Optional} containing the token if found, or empty if not
     */
    Optional<Token> findByToken(String token);
}
