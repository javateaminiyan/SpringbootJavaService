package com.restservice.restservicejavateam.Controller;

import com.restservice.restservicejavateam.domain.Users;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")

@RequestMapping("/HateoausController")
public class HateoausController {


    @ApiOperation(value = "Getting all Sample list with Hateoaus",
            notes = "All records shown")
    @GetMapping("/all")
    public List<Users> getAll() {
        Users users1 = getUser();
        Users users2 = new Users("Sam", 2400L);
        Link link = ControllerLinkBuilder.linkTo(HateoausController.class)
                .slash(users2.getName())
                .withSelfRel();
        users2.add(link);
        return Arrays.asList(users1, users2);
    }

    private Users getUser() {
        Users users = new Users("Peter", 2300L);
        Link link = ControllerLinkBuilder.linkTo(HateoausController.class)
                .slash(users.getName())
                .withSelfRel();
        users.add(link);
        return users;
    }


    @ApiOperation(value = "Getting Full sample Records Sample list with Hateoas",
            notes = "All records shown")
    @GetMapping(value = "/hateoas/all", produces = MediaTypes.HAL_JSON_VALUE)
    public List<Users> getHateOASAll() {
        Users users1 = new Users("Peter", 2300L);
        Link link = ControllerLinkBuilder.linkTo(HateoausController.class)
                .slash(users1.getSalary()).withSelfRel();
        Link link2 = ControllerLinkBuilder.linkTo(HateoausController.class)
                .slash(users1.getSalary()).withRel("salary");
        users1.add(link, link2);
        Users users2 = new Users("Sam", 2400L);
        users2.add(ControllerLinkBuilder.linkTo(HateoausController.class)
                .slash(users2.getSalary()).withSelfRel());
        return Arrays.asList(users1, users2);
    }

}
