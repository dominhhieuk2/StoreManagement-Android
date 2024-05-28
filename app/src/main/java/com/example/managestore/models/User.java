package com.example.managestore.models;

public class User {
    private int userID;
    private String userName;
    private String password;
    private String accountName;
    private String avatar;
    private String address;
    private String phone;
    private boolean userStatus;
    private int roleID;

    public User() {
    }

    public User(String userName, String password, String accountName, String avatar, String address, String phone, boolean userStatus, int roleID) {
        this.userName = userName;
        this.password = password;
        this.accountName = accountName;
        this.avatar = avatar;
        this.address = address;
        this.phone = phone;
        this.userStatus = userStatus;
        this.roleID = roleID;
    }


    public User(int userID, String userName, String password, String accountName, String avatar, String address, String phone, boolean userStatus, int roleID) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.accountName = accountName;
        this.avatar = avatar;
        this.address = address;
        this.phone = phone;
        this.userStatus = userStatus;
        this.roleID = roleID;
    }


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
}

