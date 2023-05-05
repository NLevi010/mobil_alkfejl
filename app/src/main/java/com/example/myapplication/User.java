package com.example.myapplication;

public class User {
    private String userName;
    private String email;
    private String phone;
    private String address;

    public User(String userName, String email, String phone, String address) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public User(){}

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
