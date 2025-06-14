package com.oasisnourish.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Functional interface for applying logic to a {@link ResultSet}.
 */
@FunctionalInterface
public interface ResultSetConsumer {
    void accept(ResultSet rs) throws SQLException;
}
