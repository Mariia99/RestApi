package com.petstore.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Userio {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("userStatus")
    private int userStatus;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("id")
    private int id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;
    @JsonIgnore
    @JsonProperty("code")
    private int code;
    @JsonIgnore
    @JsonProperty("message")
    private String message;

    public Userio() {
    }

    public Userio(Integer id, String username, String firstName, String lastName, String email, String password, String phone, Integer userStatus) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userStatus = userStatus;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return
                "{" +
                        "id = '" + id + '\'' +
                        ",username = '" + username + '\'' +
                        ",firstName = '" + firstName + '\'' +
                        ",lastName = '" + lastName + '\'' +
                        ",email = '" + email + '\'' +
                        ",password = '" + password + '\'' +
                        ",phone = '" + phone + '\'' +
                        ",userStatus = '" + userStatus + '\'' +
                        "}";
    }
}