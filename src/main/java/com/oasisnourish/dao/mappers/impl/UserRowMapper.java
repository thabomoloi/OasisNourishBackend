package com.oasisnourish.dao.mappers.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.oasisnourish.dao.mappers.EntityRowMapper;
import com.oasisnourish.enums.AccountStatus;
import com.oasisnourish.enums.Role;
import com.oasisnourish.models.User;

/**
 * Implementation of {@link EntityRowMapper} for {@link User} entity.
 */
public class UserRowMapper implements EntityRowMapper<User> {

    @Override
    public void mapToRow(PreparedStatement statement, User user, boolean includeId) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPhoneNumber());
        statement.setString(5, user.getPasswordHash());
        statement.setBoolean(6, user.isTwoFactorEnabled());
        statement.setString(7, user.getTwoFactorSecret());
        statement.setString(8, user.getAccountStatus().name());
        statement.setInt(9, user.getLoginAttempts());
        statement.setString(10, user.getRole().name());
        statement.setTimestamp(11, Timestamp.from(user.getLastLoginAt()));

        if (includeId) {
            statement.setLong(12, user.getId());
        }
    }

    @Override
    public User mapToEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setPasswordHash(resultSet.getString("password_hash"));
        user.setTwoFactorEnabled(resultSet.getBoolean("two_factor_enabled"));
        user.setTwoFactorSecret(resultSet.getString("two_factor_secret"));
        user.setAccountStatus(AccountStatus.valueOf(resultSet.getString("account_status")));
        user.setLoginAttempts(resultSet.getInt("login_attempts"));
        var timestamp = resultSet.getTimestamp("last_login_at");
        user.setLastLoginAt(timestamp == null ? null : timestamp.toInstant());
        user.setRole(Role.valueOf(resultSet.getString("role")));
        return user;
    }
}