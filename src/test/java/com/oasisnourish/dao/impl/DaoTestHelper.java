package com.oasisnourish.dao.impl;

import com.oasisnourish.dao.mappers.EntityRowMapper;
import com.oasisnourish.db.JdbcConnection;
import com.oasisnourish.models.EntityBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Abstract base class to assist with DAO unit tests.
 * Mocks common JDBC behavior for reuse across test classes.
 *
 * @param <T> Type of the entity being tested.
 */
@ExtendWith(MockitoExtension.class)
public abstract class DaoTestHelper<T extends EntityBase> {

    @Mock protected JdbcConnection jdbcConnection;
    @Mock protected Connection connection;
    @Mock protected PreparedStatement preparedStatement;
    @Mock protected ResultSet resultSet;
    @Mock protected EntityRowMapper<T> entityRowMapper;

    @BeforeEach
    void setUp() throws SQLException {
        when(jdbcConnection.getConnection()).thenReturn(connection);
    }

    protected void mockEntityFound(String sql, T entity) throws SQLException {
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(entityRowMapper.mapToEntity(resultSet)).thenReturn(entity);
    }

    protected void assertEntityFound(T expected, T actual) {
        assertNotNull(actual, "Expected entity to be found, but it was not.");
        assertEquals(expected, actual);
    }

    protected void mockEntityNotFound(String sql) throws SQLException {
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
    }

    protected void assertEntityNotFound(T actual) {
        assertNull(actual, "Expected entity to not be found, but it was.");
    }

    protected void mockEntityList(String sql, List<T> expectedList) throws SQLException {
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenAnswer(new Answer<Boolean>() {
            private int index = 0;

            @Override
            public Boolean answer(org.mockito.invocation.InvocationOnMock invocation) {
                return index++ < expectedList.size();
            }
        });

        when(entityRowMapper.mapToEntity(resultSet)).thenAnswer(AdditionalAnswers.returnsElementsOf(expectedList));
    }

    protected void mockEmptyEntityList(String sql) throws SQLException {
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
    }

    protected void assertEntityListMatches(List<T> expected, List<T> actual) {
        assertEquals(expected, actual, "Entity list mismatch");
    }

    protected void mockInsertReturningId(String sql, long generatedId) throws SQLException {
        when(connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(generatedId);
    }

    protected void assertEntityInserted(long expectedId, T entity) throws SQLException {
        verify(entityRowMapper).mapToRow(preparedStatement, entity, false);
        verify(preparedStatement).executeUpdate();
        assertEquals(expectedId, entity.getId(), "Inserted entity ID mismatch");
    }

    protected void mockUpdate(String sql) throws SQLException {
        when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
    }

    protected void assertEntityUpdated(T entity) throws SQLException {
        verify(entityRowMapper).mapToRow(preparedStatement, entity, true);
        verify(preparedStatement).executeUpdate();
    }

    protected void mockDelete(String sql) throws SQLException {
        when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
    }

    protected void assertEntityDeleted(long id) throws SQLException {
        verify(preparedStatement).setLong(1, id);
        verify(preparedStatement).executeUpdate();
    }
}
