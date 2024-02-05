package com.example.coffee_not_coughee.Model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    public String full_name;
    public String email;
    public String phone;
    public String address;
    public int cup_count;
    public int points;

    public UserModel(String full_name, String email, @NonNull String phone, String address) {
        this.full_name = full_name;
        this.email = email;

        // check if phone number is valid using regex (10 digits, starts with 0)
        if (!phone.matches("0[0-9]{9}")) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        this.phone = phone;
        this.address = address;
    }


}
