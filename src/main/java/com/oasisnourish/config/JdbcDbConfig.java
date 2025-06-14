package com.oasisnourish.config;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Configuration class for JDBC database access.
 * <p>
 * Loads database-related environment variables using the {@link Dotenv} library.
 * </p>
 * <p>
 * Required:
 * <ul>
 *   <li>{@code POSTGRES_DB_URL} - JDBC connection string to the PostgreSQL database</li>
 * </ul>
 * Optional:
 * <ul>
 *   <li>{@code POSTGRES_USER} - Username for the database</li>
 *   <li>{@code POSTGRES_PASSWORD} - Password for the database</li>
 * </ul>
 * </p>
 */
public class JdbcDbConfig extends ConfigLoader {

    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    /**
     * Constructs a new {@code JdbcDbConfig} using the provided {@link Dotenv} instance.
     * <p>
     * Throws an {@link IllegalArgumentException} if {@code POSTGRES_DB_URL} is not set.
     * </p>
     *
     * @param dotenv the {@code Dotenv} instance used to load environment variables
     */
    public JdbcDbConfig(Dotenv dotenv) {
        super(dotenv);
        this.dbUrl = getEnvVar("POSTGRES_DB_URL", null);
        if (dbUrl == null) {
            throw new IllegalArgumentException("Environment variable DB_URL is required but not set.");
        }

        this.dbUsername = getEnvVar("POSTGRES_USER", null);
        this.dbPassword = getEnvVar("POSTGRES_PASSWORD", null);
    }

    /**
     * Returns the JDBC URL for the PostgreSQL database.
     *
     * @return the database URL
     */
    public String getDbUrl() {
        return dbUrl;
    }

    /**
     * Returns the username for the PostgreSQL database, or {@code null} if not set.
     *
     * @return the database username or {@code null}
     */
    public String getDbUsername() {
        return dbUsername;
    }

    /**
     * Returns the password for the PostgreSQL database, or {@code null} if not set.
     *
     * @return the database password or {@code null}
     */
    public String getDbPassword() {
        return dbPassword;
    }
}
