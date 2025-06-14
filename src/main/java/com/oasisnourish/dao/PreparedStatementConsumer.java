package com.oasisnourish.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Functional interface for applying logic to a {@link PreparedStatement}.
 */
@FunctionalInterface
public interface PreparedStatementConsumer {
    void accept(PreparedStatement ps) throws SQLException;
}
