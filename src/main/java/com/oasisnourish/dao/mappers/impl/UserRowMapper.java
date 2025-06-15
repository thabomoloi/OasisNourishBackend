package com.oasisnourish.dao.mappers.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.oasisnourish.dao.mappers.EntityRowMapper;
import com.oasisnourish.enums.AccountStatus;
import com.oasisnourish.enums.Role;
import com.oasisnourish.models.User;

import static com.oasisnourish.db.constants.UserTable.*;

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
        user.setId(resultSet.getLong(ID));
        user.setFirstName(resultSet.getString(FIRST_NAME));
        user.setLastName(resultSet.getString(LAST_NAME));
        user.setEmail(resultSet.getString(EMAIL));
        user.setPhoneNumber(resultSet.getString(PHONE_NUMBER));
        user.setPasswordHash(resultSet.getString(PASSWORD_HASH));
        user.setTwoFactorEnabled(resultSet.getBoolean(TWO_FACTOR_ENABLED));
        user.setTwoFactorSecret(resultSet.getString(TWO_FACTOR_SECRET));
        user.setAccountStatus(AccountStatus.valueOf(resultSet.getString(ACCOUNT_STATUS)));
        user.setLoginAttempts(resultSet.getInt(LOGIN_ATTEMPTS));
        var timestamp = resultSet.getTimestamp(LAST_LOGIN_AT);
        user.setLastLoginAt(timestamp == null ? null : timestamp.toInstant());
        user.setRole(Role.valueOf(resultSet.getString(ROLE)));
        return user;
    }
}