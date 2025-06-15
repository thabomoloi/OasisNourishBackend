package com.oasisnourish.dao.impl;

import com.oasisnourish.dao.PreparedStatementConsumer;
import com.oasisnourish.dao.ResultSetConsumer;
import com.oasisnourish.dao.mappers.EntityRowMapper;
import com.oasisnourish.db.JdbcConnection;
import com.oasisnourish.exceptions.DatabaseAccessException;
import com.oasisnourish.models.EntityBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Defines methods for executing queries for DAOs.
 * @param <T> the type of the entity.
 */
public abstract class AbstractDao<T extends EntityBase> {
    protected final JdbcConnection jdbcConnection;
    protected final EntityRowMapper<T> entityRowMapper;

    public AbstractDao(JdbcConnection jdbcConnection, EntityRowMapper<T> entityRowMapper) {
        this.jdbcConnection = jdbcConnection;
        this.entityRowMapper = entityRowMapper;
    }

    /**
     * Executes a parameterized query and maps a single result.
     *
     * @param sql a SQL query string with '?' placeholders, not user input
     * @param consumer a lambda that sets PreparedStatement parameters
     * @return an Optional containing the mapped entity, or empty if none found
     */
    protected Optional<T> querySingle(String sql, PreparedStatementConsumer consumer) {
        try (Connection connection = jdbcConnection.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                // Invokes lambda with ps as argument
                consumer.accept(ps);

                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() ? Optional.of(entityRowMapper.mapToEntity(rs)) : Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Database query error", e);
        }
    }


    /**
     * Executes a parameterized query and maps multiple results.
     *
     * @param sql a SQL query string with '?' placeholders, not user input
     * @param consumer a lambda that sets PreparedStatement parameters
     * @return a list containing the mapped entities
     */
    protected List<T> queryMultiple(String sql, PreparedStatementConsumer consumer) {
        List<T> results = new ArrayList<>();

        try (Connection connection = jdbcConnection.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                consumer.accept(ps);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        T entity = entityRowMapper.mapToEntity(rs);
                        results.add(entity);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Database query error", e);
        }

        return results;
    }

    /**
     * Executes an update (INSERT, UPDATE, DELETE) statement and optionally consumes the generated keys.
     *
     * @param sql                 a SQL string with '?' placeholders
     * @param consumer            a lambda that sets PreparedStatement parameters
     * @param resultSetConsumer  a lambda that handles the ResultSet of generated keys
     */
    protected void executeUpdate(String sql, PreparedStatementConsumer consumer, ResultSetConsumer resultSetConsumer) {
        try (Connection connection = jdbcConnection.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                consumer.accept(ps);
                ps.executeUpdate();

                if (resultSetConsumer != null) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            resultSetConsumer.accept(rs);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Database update error", e);
        }
    }

}
