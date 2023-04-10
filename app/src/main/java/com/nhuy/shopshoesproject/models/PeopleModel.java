package com.nhuy.shopshoesproject.models;

public class PeopleModel {
    private String UserId;
    private String name;
    private String email;
    private String Address;
    private String phoneNumber;
    private boolean gender;
    private String password;
    private String photoUrl;
    private String userType;
    public PeopleModel() {
    }

    public PeopleModel(String userId, String name, String email, String address, String phoneNumber, boolean gender, String password, String photoUrl, String userType) {
        this.UserId = userId;
        this.name = name;
        this.email = email;
        this.Address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.password = password;
        this.photoUrl = photoUrl;
        this.userType = userType;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", Address='" + Address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                '}';
    }
}