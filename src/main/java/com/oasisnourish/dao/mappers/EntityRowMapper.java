package com.oasisnourish.dao.mappers;

import com.oasisnourish.models.EntityBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Defines a contract for mapping between a {@link ResultSet} and an entity
 * object of type {@code T}, and vice versa.
 * <p>
 * This interface abstracts the mapping logic used in data access layers to
 * convert database rows to domain entities and domain entities back to SQL
 * statements for persistence.
 * </p>
 *
 * @param <T> the type of the entity that extends {@link EntityBase}
 */
public interface EntityRowMapper<T extends EntityBase> {

    /**
     * Maps a single row from the provided {@link ResultSet} to an instance of
     * the entity type {@code T}.
     *
     * @param resultSet the {@link ResultSet} containing entity data
     * @return an instance of {@code T} populated with data from the {@link ResultSet}
     * @throws SQLException if there is an error accessing the {@link ResultSet}
     */
    T mapToEntity(ResultSet resultSet) throws SQLException;

    /**
     * Maps an entity of type {@code T} to a {@link PreparedStatement} for database
     * insertion or update.
     *
     * @param preparedStatement the {@link PreparedStatement} to populate with entity data
     * @param entity the {@code T} entity containing the data
     * @param includeId whether to include the entity's ID in the {@link PreparedStatement};
     *                  typically {@code true} for update operations and {@code false} for inserts
     * @throws SQLException if there is an error setting values in the {@link PreparedStatement}
     */
    void mapToRow(PreparedStatement preparedStatement, T entity, boolean includeId) throws SQLException;
}
