package com.oasisnourish.dao.impl;

import com.oasisnourish.dao.UserDao;
import com.oasisnourish.dao.mappers.EntityRowMapper;
import com.oasisnourish.db.JdbcConnection;
import com.oasisnourish.models.User;

import java.util.List;
import java.util.Optional;

import static com.oasisnourish.db.constants.UserTableConstants.*;

/**
 * DAO implementation for User entity.
 */
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    public UserDaoImpl(JdbcConnection jdbcConnection, EntityRowMapper<User> entityRowMapper) {
        super(jdbcConnection, entityRowMapper);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return querySingle(SQL_FIND_BY_EMAIL, ps -> ps.setString(1, email));
    }

    @Override
    public Optional<User> find(long id) {
        return querySingle(SQL_FIND_BY_ID, ps -> ps.setLong(1, id));
    }

    @Override
    public List<User> findAll() {
        return queryMultiple(SQL_FIND_ALL, ps -> {});
    }

    @Override
    public void create(User user) {
        executeUpdate(SQL_CREATE, ps -> entityRowMapper.mapToRow(ps, user, false), rs -> user.setId(rs.getLong(1)));
    }

    @Override
    public void update(User user) {
        executeUpdate(SQL_UPDATE, ps -> entityRowMapper.mapToRow(ps, user, true), null);
    }

    @Override
    public void delete(long id) {
        executeUpdate(SQL_DELETE, ps -> ps.setLong(1, id), null);
    }
}
