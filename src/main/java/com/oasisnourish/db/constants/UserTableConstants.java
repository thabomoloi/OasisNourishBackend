package com.oasisnourish.db.constants;

public final class UserTableConstants implements BaseTableConstants {
    public static final String TABLE_NAME = "users";

    // === Constants For Column Names ===
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_PASSWORD_HASH = "password_hash";
    public static final String COLUMN_TWO_FACTOR_ENABLED = "two_factor_enabled";
    public static final String COLUMN_TWO_FACTOR_SECRET = "two_factor_secret";
    public static final String COLUMN_ACCOUNT_STATUS = "account_status";
    public static final String COLUMN_LOGIN_ATTEMPTS = "login_attempts";
    public static final String COLUMN_LAST_LOGIN_AT = "last_login_at";
    public static final String COLUMN_ROLE = "role";

    // === Constants For SQL statements ====
    public static final String SQL_FIND_BY_EMAIL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ?";
    public static final String SQL_FIND_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
    public static final String SQL_FIND_ALL = "SELECT * FROM " + TABLE_NAME;
    public static final String SQL_CREATE = String.format("""
            INSERT INTO %s (
                    %s, %s, %s, %s, %s, %s,
                    %s, %s, %s, %s, %s
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """, TABLE_NAME,
            COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_EMAIL, COLUMN_PHONE_NUMBER, COLUMN_PASSWORD_HASH, COLUMN_TWO_FACTOR_ENABLED,
            COLUMN_TWO_FACTOR_SECRET, COLUMN_ACCOUNT_STATUS, COLUMN_LOGIN_ATTEMPTS, COLUMN_LAST_LOGIN_AT, COLUMN_ROLE
    );
    public static final String SQL_UPDATE = String.format("""
           UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?,
                %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?
                WHERE %s = ?
            """, TABLE_NAME,
            COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_EMAIL, COLUMN_PHONE_NUMBER, COLUMN_PASSWORD_HASH, COLUMN_TWO_FACTOR_ENABLED,
            COLUMN_TWO_FACTOR_SECRET, COLUMN_ACCOUNT_STATUS, COLUMN_LOGIN_ATTEMPTS, COLUMN_LAST_LOGIN_AT, COLUMN_ROLE, COLUMN_ID
    );
    public static final String SQL_DELETE =  "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";

    private UserTableConstants() {}
}
