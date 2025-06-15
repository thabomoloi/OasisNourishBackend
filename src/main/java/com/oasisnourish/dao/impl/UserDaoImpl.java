package com.oasisnourish.dao.impl;

import com.oasisnourish.dao.UserDao;
import com.oasisnourish.dao.mappers.EntityRowMapper;
import com.oasisnourish.db.JdbcConnection;
import com.oasisnourish.models.User;

import java.util.Optional;

import static com.oasisnourish.db.constants.UserTable.*;

/**
 * DAO implementation for User entity.
 */
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    public UserDaoImpl(JdbcConnection jdbcConnection, EntityRowMapper<User> entityRowMapper) {
        super(jdbcConnection, entityRowMapper);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL + " = ?";
        return querySingle(sql, ps -> ps.setString(1, email));
    }

    @Override
    public Optional<User> find(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        return querySingle(sql, ps -> ps.setLong(1, id));
    }

    @Override
    public Iterable<User> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return queryMultiple(sql, ps -> {});
    }

    @Override
    public void create(User user) {
        String sql = String.format("""
                INSERT INTO %s (
                    %s, %s, %s, %s, %s, %s,
                    %s, %s, %s, %s, %s
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, TABLE_NAME,
                FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, PASSWORD_HASH, TWO_FACTOR_ENABLED,
                TWO_FACTOR_SECRET, ACCOUNT_STATUS, LOGIN_ATTEMPTS, LAST_LOGIN_AT, ROLE
        );
        executeUpdate(sql, ps -> entityRowMapper.mapToRow(ps, user, true), null);
    }

    @Override
    public void update(User user) {
        String sql = String.format("""
                UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?,
                %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?
                WHERE %s = ?
                """, TABLE_NAME,
                FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, PASSWORD_HASH,
                TWO_FACTOR_ENABLED, TWO_FACTOR_SECRET, ACCOUNT_STATUS,
                LOGIN_ATTEMPTS, LAST_LOGIN_AT, ROLE, ID
        );
        executeUpdate(sql, ps -> entityRowMapper.mapToRow(ps, user, false), null);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        executeUpdate(sql, ps -> ps.setLong(1, id), null);
    }
}
