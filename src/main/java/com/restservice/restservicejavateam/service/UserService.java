package com.restservice.restservicejavateam.service;

import com.restservice.restservicejavateam.domain.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);

    List<User> getAllUser();


    int findbySomething(String name);

    List<User> selectUser();


}