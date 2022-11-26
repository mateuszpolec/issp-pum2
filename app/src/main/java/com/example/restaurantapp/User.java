package com.example.restaurantapp;

import java.util.UUID;

public class User {

    private UUID mId;
    private String mName;
    private String mPassword;
    private String mEmail;
    private Permissions.Type mPermission;
    private boolean mLogged;

    public User()
    {
        this.mId = UUID.randomUUID();
        this.mName = "TestAccount";
        this.mPassword = "test123";
        this.mEmail = "test@gmail.com";
        this.mPermission = Permissions.Type.USER;
        this.mLogged = false;
    }

    public User(String name, String password, String email, Permissions.Type permission)
    {
        this.mName = name;
        this.mPassword = password;
        this.mEmail = email;
        this.mPermission = permission;
        this.mLogged = false;
    }

    public void setName(String name) { this.mName = name; }
    public String getName() { return this.mName; }

    public void setPassword(String password) { this.mPassword = password; }
    public String getPassword() { return this.mPassword; }

    public void setEmail(String email) { this.mEmail = email; }
    public String getEmail() { return this.mEmail; }

    public void setPermission(Permissions.Type permission) { this.mPermission = permission; }
    public Permissions.Type getPermission() { return this.mPermission; }

    public UUID getId() { return this.mId; }

    public void SetLogged(boolean logged) { this.mLogged = logged; }
    public boolean IsLogged() { return this.mLogged; }

    public boolean IsEmployee()
    {
        if (this.mPermission == Permissions.Type.COOK || this.mPermission == Permissions.Type.OWNER || this.mPermission == Permissions.Type.WAITER)
        {
            return true;
        }
        return false;
    }
}
