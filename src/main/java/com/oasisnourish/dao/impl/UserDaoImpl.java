package com.oasisnourish.dao.impl;

import com.oasisnourish.dao.UserDao;
import com.oasisnourish.dao.mappers.EntityRowMapper;
import com.oasisnourish.db.JdbcConnection;
import com.oasisnourish.models.User;

import java.util.Optional;

/**
 * DAO implementation for User entity.
 */
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    public UserDaoImpl(JdbcConnection jdbcConnection, EntityRowMapper<User> entityRowMapper) {
        super(jdbcConnection, entityRowMapper);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return querySingle(sql, ps -> ps.setString(1, email));
    }

    @Override
    public Optional<User> find(long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return querySingle(sql, ps -> ps.setLong(1, id));
    }

    @Override
    public Iterable<User> findAll() {
        String sql = "SELECT * FROM users";
        return queryMultiple(sql, ps -> {});
    }

    @Override
    public void create(User user) {
        String sql = """
                INSERT INTO users (
                    first_name, last_name, email, phone_number, password_hash, two_factor_enabled,
                    two_factor_secret, account_status, login_attempts, last_login_at, role
                )
                VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
               """;
        executeUpdate(sql, ps -> entityRowMapper.mapToRow(ps, user, true), null);
    }

    @Override
    public void update(User user) {
        String sql = """
                UPDATE users SET first_name = ?, last_name = ?, email = ?, phone_number = ?, password_hash = ?,
                two_factor_enabled = ?,  two_factor_secret = ?, account_status = ?, login_attempts = ?, last_login_at = ?, role = ?
                WHERE id = ?
                """;
        executeUpdate(sql, ps -> entityRowMapper.mapToRow(ps, user, false), null);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        executeUpdate(sql, ps -> ps.setLong(1, id), null);
    }
}
