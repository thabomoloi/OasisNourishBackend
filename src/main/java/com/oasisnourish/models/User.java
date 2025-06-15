package com.oasisnourish.models;

import com.oasisnourish.enums.AccountStatus;
import com.oasisnourish.enums.Role;

import java.time.Instant;

public class User extends EntityBase {
    private String firstName;
    private String lastName;

    private String email;
    private String phoneNumber;

    private String passwordHash;
    private boolean twoFactorEnabled;
    private String twoFactorSecret;

    private AccountStatus accountStatus;
    private int loginAttempts;
    private Instant lastLoginAt;

    private Role role;

    public User() {
        id = 0;
        role = Role.USER;
        accountStatus = AccountStatus.UNVERIFIED;
        twoFactorEnabled = false;
        loginAttempts = 0;
    }

    public User(
            long id,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String passwordHash,
            boolean twoFactorEnabled,
            String twoFactorSecret,
            AccountStatus accountStatus,
            int loginAttempts,
            Instant lastLoginAt,
            Role role
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.twoFactorEnabled = twoFactorEnabled;
        this.twoFactorSecret = twoFactorSecret;
        this.accountStatus = accountStatus;
        this.loginAttempts = loginAttempts;
        this.lastLoginAt = lastLoginAt;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setTwoFactorEnabled(boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public String getTwoFactorSecret() {
        return twoFactorSecret;
    }

    public void setTwoFactorSecret(String twoFactorSecret) {
        this.twoFactorSecret = twoFactorSecret;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Instant lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
