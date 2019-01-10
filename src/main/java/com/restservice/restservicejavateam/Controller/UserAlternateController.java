package com.restservice.restservicejavateam.Controller;

import com.restservice.restservicejavateam.domain.User;
import com.restservice.restservicejavateam.exception.NotFoundException.UserNotFoundException;
import com.restservice.restservicejavateam.repo.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
//Accept:application/xml
//Content-Type:application/json
@RestController
//@CrossOrigin(origins = "http://localhost:8085")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")

@RequestMapping("/")


public class UserAlternateController {

    private final UserRepository userRepository;


    private org.slf4j.Logger logger;

    @Autowired
    public UserAlternateController(UserRepository userService) {
        this.userRepository = userService;
        logger = LoggerFactory.getLogger(UserController.class);
    }

    @ApiOperation(value = "retrieve All User",
            notes = "Get All User details")
    @GetMapping("/students")
    public List<User> retrieveAllStudents() {
        return userRepository.findAll();
    }


    @ApiOperation(value = "retrieve All User based On Id",
            notes = "Get All User details based on User Id")
    @GetMapping("/students/{id}")
    public User retrieveStudent(@PathVariable long id) {
        Optional<User> student = userRepository.findById(id);

        if (!student.isPresent())
            throw new UserNotFoundException("id-" + id);

        return student.get();
    }


    @ApiOperation(value = "retrieve All User based on Id",
            notes = "Delete based on UserId")
    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable long id) {
        userRepository.deleteById(id);
    }

    @ApiOperation(value = "Insert Call RequestBody",
            notes = "Insert Data")

    @PostMapping("/students")
    public ResponseEntity<Object> createStudent(@RequestBody User student) {
        User savedStudent = userRepository.save(student);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedStudent.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @ApiOperation(value = "Update  Call RequestBody",
            notes = "update based on userid")
    @PutMapping("/students/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody User student, @PathVariable long id) {

        Optional<User> studentOptional = userRepository.findById(id);

        if (!studentOptional.isPresent())
            return ResponseEntity.notFound().build();

        student.setId(id);

        userRepository.save(student);

        return ResponseEntity.noContent().build();
    }


}
