package com.oasisnourish.dao;

import com.oasisnourish.models.EntityBase;

import java.util.List;
import java.util.Optional;

/**
 * Generic Data Access Object (DAO) interface for performing CRUD operations.
 * This class defines common methods for interacting with database entities.
 *
 * @param <T> the type of the entity this DAO will manage.
 */
public interface Dao<T extends EntityBase> {

    /**
     * Finds an entity by its ID.
     *
     * @param id the ID of the entity to find.
     * @return an {@link  Optional} containing entity if found, otherwise an empy {@link Optional}.
     */
    Optional<T> find(long id);

    /**
     * Retrieves all entities of type {@link T} from the database.
     *
     * @return list of all entities.
     */
    List<T> findAll();

    /**
     * Creates new entity of type {@link  T} to the database.
     *
     * @param entity the entity to save.
     */
    void create(T entity);

    /**
     * Updates an existing entity of type {@link T} in the database.
     *
     * @param entity the entity to update.
     */
    void update(T entity);

    /**
     * Deletes an entity from the database by its ID.
     *
     * @param id the ID of the entity to delete.
     */
    void delete(long id);
}
