package com.restservice.restservicejavateam;

import com.restservice.restservicejavateam.service.DBFileStorageService;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.Resource;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.restservice.restservicejavateam")//repo
@EntityScan(basePackages = "com.restservice.restservicejavateam")//domain


public class RestservicejavateamApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestservicejavateamApplication.class, args);
    }









}

//
////Insert a User
//localhost:8085/user post
//
//        {
//        "userId": 1,
//        "fullName": "Iniyan Arul",
//        "username": "Iniyan455"
//        }
//
//        response
//        {
//        "userId": 1,
//        "fullName": "Iniyan Arul",
//        "username": "Iniyan455"
//        }



//GET
//localhost:8085/user/1
//
//        {
//        "userId": 1,
//        "fullName": "Iniyan Arul",
//        "username": "Iniyan455"
//        }


//DElete localhost:8085/user/2
//PUT
