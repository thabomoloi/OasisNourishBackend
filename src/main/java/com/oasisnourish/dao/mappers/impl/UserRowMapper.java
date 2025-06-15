package com.oasisnourish.dao.mappers.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.oasisnourish.dao.mappers.EntityRowMapper;
import com.oasisnourish.enums.AccountStatus;
import com.oasisnourish.enums.Role;
import com.oasisnourish.models.User;

import static com.oasisnourish.db.constants.UserTableConstants.*;

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
        statement.setTimestamp(10, Timestamp.from(user.getLastLoginAt()));
        statement.setString(11, user.getRole().name());

        if (includeId) {
            statement.setLong(12, user.getId());
        }
    }

    @Override
    public User mapToEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(COLUMN_ID));
        user.setFirstName(resultSet.getString(COLUMN_FIRST_NAME));
        user.setLastName(resultSet.getString(COLUMN_LAST_NAME));
        user.setEmail(resultSet.getString(COLUMN_EMAIL));
        user.setPhoneNumber(resultSet.getString(COLUMN_PHONE_NUMBER));
        user.setPasswordHash(resultSet.getString(COLUMN_PASSWORD_HASH));
        user.setTwoFactorEnabled(resultSet.getBoolean(COLUMN_TWO_FACTOR_ENABLED));
        user.setTwoFactorSecret(resultSet.getString(COLUMN_TWO_FACTOR_SECRET));
        user.setAccountStatus(AccountStatus.valueOf(resultSet.getString(COLUMN_ACCOUNT_STATUS)));
        user.setLoginAttempts(resultSet.getInt(COLUMN_LOGIN_ATTEMPTS));
        var timestamp = resultSet.getTimestamp(COLUMN_LAST_LOGIN_AT);
        user.setLastLoginAt(timestamp == null ? null : timestamp.toInstant());
        user.setRole(Role.valueOf(resultSet.getString(COLUMN_ROLE)));
        return user;
    }
}