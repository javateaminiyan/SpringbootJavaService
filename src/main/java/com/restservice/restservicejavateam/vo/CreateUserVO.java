package com.restservice.restservicejavateam.vo;

import com.restservice.restservicejavateam.utils.validators.Username;

import javax.validation.constraints.NotNull;

/**
 * Created by DAM on 3/1/17.
 */
public class CreateUserVO {

    @NotNull(message = "first name can not be null.")
    private String firstName;

    @NotNull(message = "last name can not be null.")
    private String lastName;

    @Username(message = "Invalid username.")
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}