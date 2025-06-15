package com.oasisnourish.db.constants;

public final class UserTable {
    public static final String TABLE_NAME = "users";

    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String PASSWORD_HASH = "password_hash";
    public static final String TWO_FACTOR_ENABLED = "two_factor_enabled";
    public static final String TWO_FACTOR_SECRET = "two_factor_secret";
    public static final String ACCOUNT_STATUS = "account_status";
    public static final String LOGIN_ATTEMPTS = "login_attempts";
    public static final String LAST_LOGIN_AT = "last_login_at";
    public static final String ROLE = "role";

    private UserTable() {}
}
