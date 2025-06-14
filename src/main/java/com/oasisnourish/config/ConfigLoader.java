package com.oasisnourish.config;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Abstract base class for loading environment configuration using the Dotenv library.
 * <p>
 * Subclasses can extend this to implement specific configuration needs.
 * Provides helper methods to retrieve environment variables as {@code String} and {@code int},
 * with support for default values.
 * </p>
 */
public abstract class ConfigLoader {

    /**
     * Dotenv instance used to access environment variables.
     */
    protected final Dotenv dotenv;

    /**
     * Constructs a new {@link ConfigLoader} with the given {@link Dotenv} instance.
     *
     * @param dotenv the {@link Dotenv} instance to use for environment variable access
     */
    public ConfigLoader(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    /**
     * Retrieves a string environment variable by key, or returns a default value if not found.
     *
     * @param key          the name of the environment variable
     * @param defaultValue the value to return if the environment variable is not set
     * @return the environment variable value, or {@code defaultValue} if not found
     */
    protected String getEnvVar(String key, String defaultValue) {
        return dotenv.get(key, defaultValue);
    }

    /**
     * Retrieves an integer environment variable by key, or returns a default value if not found.
     * <p>
     * If the value exists but is not a valid integer, throws an {@link  IllegalArgumentException}.
     * </p>
     *
     * @param key          the name of the environment variable
     * @param defaultValue the value to return if the environment variable is not set
     * @return the integer value of the environment variable, or {@code defaultValue} if not found
     * @throws IllegalArgumentException if the value exists but is not a valid integer
     */
    protected int getEnvVarInt(String key, int defaultValue) {
        String value = dotenv.get(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid number format for " + key);
            }
        }
        return defaultValue;
    }
}
