package com.daimlertrucksasia.it.dsc.nexus.sentinel.infrastucture.client;

import com.daimlertrucksasia.it.dsc.nexus.sentinel.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Client} entities in MongoDB.
 * <p>
 * This interface extends {@link MongoRepository}, providing CRUD operations
 * and custom queries for {@link Client} entities.
 * </p>
 */
public interface ClientRepository extends MongoRepository<Client, String> {

    /**
     * Retrieves a {@link Client} entity by its unique client ID.
     *
     * @param clientId the client ID to search for
     * @return an {@link Optional} containing the client if found, or empty if not
     */
    Optional<Client> findByClientId(String clientId);
}
