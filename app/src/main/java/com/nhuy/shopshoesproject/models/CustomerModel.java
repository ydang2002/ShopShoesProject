package com.nhuy.shopshoesproject.models;

public class CustomerModel extends PeopleModel{

    private boolean PotentialCustomers;

    public CustomerModel() {
    }

    public CustomerModel(String userId, String name, String email, String address, String phoneNumber, boolean gender, String password, String photoUrl, String userType, boolean potentialCustomers) {
        super(userId, name, email, address, phoneNumber, gender, password, photoUrl, userType);
        PotentialCustomers = potentialCustomers;
    }

    public boolean isPotentialCustomers() {
        return PotentialCustomers;
    }

    public void setPotentialCustomers(boolean potentialCustomers) {
        PotentialCustomers = potentialCustomers;
    }

}
