package com.example.foodcourt;

public class User {
    String name,gender,mobile_number,email,password;
    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User( String name, String gender, String mobile_number, String email, String password) {
        this.name = name;
        this.gender = gender;
        this.mobile_number = mobile_number;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
