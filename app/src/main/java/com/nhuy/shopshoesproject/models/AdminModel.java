package com.nhuy.shopshoesproject.models;

public class AdminModel extends PeopleModel{

    public AdminModel() {
    }

    public AdminModel(String userId, String name, String email, String address, String phoneNumber, boolean gender, String photoUrl, String userType) {
        super(userId, name, email, address, phoneNumber, gender, photoUrl, userType);
    }
}
