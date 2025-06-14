package com.oasisnourish.dao;

import com.oasisnourish.models.User;

import java.util.Optional;

/**
 * DAO interface for {@link User} entity.
 */
public interface UserDao extends Dao<User> {

    /**
     * Finds an entity by its ID.
     *
     * @param email The email of the {@link User} to find.
     * @return An {@link  Optional} containing {@link User} if found, otherwise an empy {@link Optional}.
     */
    Optional<User> findByEmail(String email);
}
