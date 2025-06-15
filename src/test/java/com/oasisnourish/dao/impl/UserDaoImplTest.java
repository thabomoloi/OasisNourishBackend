package com.oasisnourish.dao.impl;

import com.oasisnourish.enums.AccountStatus;
import com.oasisnourish.enums.Role;
import com.oasisnourish.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.oasisnourish.db.constants.UserTableConstants.*;

@ExtendWith(MockitoExtension.class)
public class UserDaoImplTest extends DaoTestHelper<User> {

    @InjectMocks private UserDaoImpl userDao;

    private User user;

    @BeforeEach
    @Override
    void setUp() throws SQLException {
        super.setUp();
        user = new User(
                123L,
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
    void findByEmail_whenUserFound_returnsUser() throws SQLException {
        // Arrange
        mockEntityFound(SQL_FIND_BY_EMAIL, user);

        // Act
        Optional<User> result = userDao.findByEmail(user.getEmail());

        // Assert
        assertEntityFound(user, result.orElse(null));
    }

    @Test
    void findByEmail_whenUserNotFound_returnsEmpty() throws SQLException {
        // Arrange
        mockEntityNotFound(SQL_FIND_BY_EMAIL);
        var email = "missing@example.com";

        // Act
        Optional<User> result = userDao.findByEmail(email);

        // Assert
        assertEntityNotFound(result.orElse(null));
    }

    @Test
    void find_whenUserFound_returnsUser() throws SQLException {
        // Arrange
        mockEntityFound(SQL_FIND_BY_ID, user);

        // Act
        Optional<User> result = userDao.find(1L);

        // Assert
        assertEntityFound(user, result.orElse(null));
    }

    @Test
    void find_whenUserNotFound_returnsEmpty() throws SQLException {
        // Arrange
        mockEntityNotFound(SQL_FIND_BY_ID);

        // Act
        Optional<User> result = userDao.find(99L);

        // Assert
        assertEntityNotFound(result.orElse(null));
    }

    @Test
    void findAll_whenFound_returnsUsersList() throws SQLException {
        // Arrange
        List<User> expected = List.of(user);
        mockEntityList(SQL_FIND_ALL, expected);

        // Act
        List<User> actual = userDao.findAll();

        // Assert
        assertEntityListMatches(expected, actual);
    }

    @Test
    void findAll_whenNoneFound_returnsEmptyList() throws SQLException {
        // Arrange
        mockEmptyEntityList(SQL_FIND_ALL);

        // Act
        List<User> actual = userDao.findAll();

        // Assert
        assertEntityListMatches(Collections.emptyList(), actual);
    }

    @Test
    void create_insertsUser() throws SQLException {
        // Arrange
        user.setId(0);
        long generatedId = 456L;
        mockInsertReturningId(SQL_CREATE, generatedId);

        // Act
        userDao.create(user);

        // Assert
        assertEntityInserted(generatedId, user);
    }

    @Test
    void update_updatesUser() throws SQLException {
        // Arrange
        mockUpdate(SQL_UPDATE);

        // Act
        userDao.update(user);

        // Assert
        assertEntityUpdated(user);
    }

    @Test
    void delete_deletesUserById() throws SQLException {
        // Arrange
        mockDelete(SQL_DELETE);

        // Act
        userDao.delete(user.getId());

        // Assert
        assertEntityDeleted(user.getId());
    }
}
