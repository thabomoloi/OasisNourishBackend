package com.oasisnourish.dao.mappers.impl;

import com.oasisnourish.enums.AccountStatus;
import com.oasisnourish.enums.Role;
import com.oasisnourish.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static com.oasisnourish.db.constants.UserTable.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRowMapperTest {

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private UserRowMapper userRowMapper;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
                0,
                "John",
                "Doe",
                "john.doe@oasis.test",
                "0123456789",
                "P@ssw0rdHash",
                false,
                "secret",
                AccountStatus.ACTIVE,
                2,
                Instant.now(),
                Role.ADMIN
        );
    }

    @Test
    void mapToRow_whenIdNotIncluded_shouldMapCorrectly() throws SQLException {
        // Act
        userRowMapper.mapToRow(preparedStatement, user, false);

        // Assert
        verify(preparedStatement).setString(1, user.getFirstName());
        verify(preparedStatement).setString(2, user.getLastName());
        verify(preparedStatement).setString(3, user.getEmail());
        verify(preparedStatement).setString(4, user.getPhoneNumber());
        verify(preparedStatement).setString(5, user.getPasswordHash());
        verify(preparedStatement).setBoolean(6, user.isTwoFactorEnabled());
        verify(preparedStatement).setString(7, user.getTwoFactorSecret());
        verify(preparedStatement).setString(8, user.getAccountStatus().name());
        verify(preparedStatement).setInt(9, user.getLoginAttempts());
        verify(preparedStatement).setTimestamp(10, Timestamp.from(user.getLastLoginAt()));
        verify(preparedStatement).setString(11, user.getRole().name());
        verify(preparedStatement, never()).setLong(12, user.getId());
    }

    @Test
    void mapToRow_whenIdIncluded_shouldMapCorrectly() throws SQLException {
        // Act
        userRowMapper.mapToRow(preparedStatement, user, true);

        // Assert
        verify(preparedStatement).setLong(12, user.getId());
    }

    @Test
    void mapToEntity_shouldMapCorrectly() throws SQLException {
        // Arrange
        when(resultSet.getLong(ID)).thenReturn(user.getId());
        when(resultSet.getString(FIRST_NAME)).thenReturn(user.getFirstName());
        when(resultSet.getString(LAST_NAME)).thenReturn(user.getLastName());
        when(resultSet.getString(EMAIL)).thenReturn(user.getEmail());
        when(resultSet.getString(PHONE_NUMBER)).thenReturn(user.getPhoneNumber());
        when(resultSet.getString(PASSWORD_HASH)).thenReturn(user.getPasswordHash());
        when(resultSet.getBoolean(TWO_FACTOR_ENABLED)).thenReturn(user.isTwoFactorEnabled());
        when(resultSet.getString(TWO_FACTOR_SECRET)).thenReturn(user.getTwoFactorSecret());
        when(resultSet.getString(ACCOUNT_STATUS)).thenReturn(user.getAccountStatus().name());
        when(resultSet.getInt(LOGIN_ATTEMPTS)).thenReturn(user.getLoginAttempts());
        when(resultSet.getTimestamp(LAST_LOGIN_AT)).thenReturn(Timestamp.from(user.getLastLoginAt()));
        when(resultSet.getString(ROLE)).thenReturn(user.getRole().name());

        // Act
        var entity = userRowMapper.mapToEntity(resultSet);

        // Assert
        assertEquals(user.getId(), entity.getId());
        assertEquals(user.getFirstName(), entity.getFirstName());
        assertEquals(user.getLastName(), entity.getLastName());
        assertEquals(user.getEmail(), entity.getEmail());
        assertEquals(user.getPhoneNumber(), entity.getPhoneNumber());
        assertEquals(user.getPasswordHash(), entity.getPasswordHash());
        assertEquals(user.isTwoFactorEnabled(), entity.isTwoFactorEnabled());
        assertEquals(user.getTwoFactorSecret(), entity.getTwoFactorSecret());
        assertEquals(user.getAccountStatus().name(), entity.getAccountStatus().name());
        assertEquals(user.getLoginAttempts(), entity.getLoginAttempts());
        assertEquals(user.getLastLoginAt(), entity.getLastLoginAt());
        assertEquals(user.getRole().name(), entity.getRole().name());
    }

    @Test
    void mapToEntity_shouldHandleNullTimestamp() throws SQLException {
        // Arrange
        when(resultSet.getLong(ID)).thenReturn(user.getId());
        when(resultSet.getString(FIRST_NAME)).thenReturn(user.getFirstName());
        when(resultSet.getString(LAST_NAME)).thenReturn(user.getLastName());
        when(resultSet.getString(EMAIL)).thenReturn(user.getEmail());
        when(resultSet.getString(PHONE_NUMBER)).thenReturn(user.getPhoneNumber());
        when(resultSet.getString(PASSWORD_HASH)).thenReturn(user.getPasswordHash());
        when(resultSet.getBoolean(TWO_FACTOR_ENABLED)).thenReturn(user.isTwoFactorEnabled());
        when(resultSet.getString(TWO_FACTOR_SECRET)).thenReturn(user.getTwoFactorSecret());
        when(resultSet.getString(ACCOUNT_STATUS)).thenReturn(user.getAccountStatus().name());
        when(resultSet.getInt(LOGIN_ATTEMPTS)).thenReturn(user.getLoginAttempts());
        when(resultSet.getTimestamp(LAST_LOGIN_AT)).thenReturn(null);
        when(resultSet.getString(ROLE)).thenReturn(user.getRole().name());

        // Act
        var entity = userRowMapper.mapToEntity(resultSet);

        // Assert
        assertNull(entity.getLastLoginAt());
    }
}
