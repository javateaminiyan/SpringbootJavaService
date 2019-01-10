package com.restservice.restservicejavateam.service;


import com.restservice.restservicejavateam.domain.User;
import com.restservice.restservicejavateam.exception.NotFoundException.ResourceNotFoundException;
import com.restservice.restservicejavateam.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);


        if (user == null) {
            throw new ResourceNotFoundException(id, "user not found");
        }
        return user;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User updateUser) {
        User user = userRepository.findById(updateUser.getId()).orElse(null);


        if (user == null) {
            throw new ResourceNotFoundException(updateUser.getId(), "user id not found");
        }


        return userRepository.save(updateUser);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        if (user != null) {
            userRepository.deleteById(id);
        }
    }

      @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public int findbySomething(String name) {


        return userRepository.findbyUsername(name);

    }

    @Override
    public List<User> selectUser() {
        return userRepository.findbyUsername();
    }


}