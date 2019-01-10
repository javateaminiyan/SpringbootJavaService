package com.restservice.restservicejavateam.Controller;

import com.restservice.restservicejavateam.Assembler.UserAssembler;

import com.restservice.restservicejavateam.domain.User;
import com.restservice.restservicejavateam.service.UserService;
import com.restservice.restservicejavateam.vo.CreateUserVO;
import com.restservice.restservicejavateam.vo.UpdateUserVO;
import com.restservice.restservicejavateam.vo.UserVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;


@RestController
//@CrossOrigin(origins = "http://localhost:8085")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")

@RequestMapping("/user")
public class UserController {

    private final UserAssembler userAssembler;


    private final UserService userService;


    private org.slf4j.Logger logger;


    @Autowired
    public UserController(UserAssembler userAssembler, UserService userService) {
        this.userAssembler = userAssembler;
        this.userService = userService;
        logger = LoggerFactory.getLogger(UserController.class);
    }


    @ApiOperation(value = "Insert Call RequestBody",
            notes = "Insert User Detail Based on CreateUserVo" +
                    "reponse :{\n" +
                    "  \"userId\": 16,\n" +
                    "  \"fullName\": \"iniyan arul\",\n" +
                    "  \"username\": \"arul\"\n" +
                    "}" +
                    "request" +
                    "{\n" +
                    "  \"firstName\": \"iniyan\",\n" +
                    "  \"lastName\": \"arul\",\n" +
                    "  \"username\": \"arul\"\n" +
                    "}")
    @RequestMapping(method = RequestMethod.POST)
    public UserVO createUser(@Valid @RequestBody CreateUserVO userVO) {
        //convert to User
        User user = userAssembler.toUser(userVO);
        //save User
        User savedUser = userService.createUser(user);
        //convert to UserVO
        return userAssembler.toUserVO(savedUser);
    }


    @ApiOperation(value = "Get All User Data",
            notes = "All User Details Show Here ")
    @GetMapping("getAllUsers")
    public List<User> getAllUsers() {
        int checkMobileNo = userService.findbySomething("Arul");

        logger.info("response count" + checkMobileNo + userService.selectUser().get(0).getUsername());

        return userService.getAllUser();
    }


    @ApiOperation(value = "Find User by id",
            notes = "Getting Individual Record From User Table")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserVO getUser(@PathVariable("id") Long id) {


        return userAssembler.toUserVO(userService.getUserById(id));
    }

    @ApiOperation(value = "Update Call RequestBody",
            notes = "Update Total Body Update Used" +
                    "respnose: {\n" +
                    "  \n" +
                    " \"userId\": 4,\n" +
                    "\"firstName\": \"Ashok\",\n" +
                    "  \"lastName\": \"Ashok\",\n" +
                    " \"username\": \"Ashok\"\n" +
                    "}"+"\n "+"request:{\n" +
                    "  \"userId\": 4,\n" +
                    "  \"fullName\": \"Ashok Ashok\",\n" +
                    "  \"username\": \"Ashok\"\n" +
                    "}"
    )

    @RequestMapping(method = RequestMethod.PUT)
    public UserVO updateUser(@RequestBody UpdateUserVO updateUserVO) {
        //convert to User
        User user = userAssembler.toUser(updateUserVO);
        //update User
        User updatedUser = userService.updateUser(user);
        //convert to UserVO
        return userAssembler.toUserVO(updatedUser);
    }


//Working another update method
//    @RequestMapping(method = RequestMethod.PUT)
//    // public UserVO updateUser(@RequestBody UpdateUserVO updateUserVO) {
//    public ResponseEntity<?> updateUser(@RequestBody UpdateUserVO updateUserVO) {
//
//
//
//
//        //convert to User
//        User user = userAssembler.toUser(updateUserVO);
//
//
//        UserVO userVO= getUser(updateUserVO.getUserId());
//
//        if( userVO.getUserId()!=null){
//            //update User
//            User updatedUser = userService.updateUser(user);
//            //convert to UserVO
//            //return userAssembler.toUserVO(updatedUser);
//            return new ResponseEntity<>( userAssembler.toUserVO(updatedUser), HttpStatus.OK);
//        }else
//            return new ResponseEntity<>( "Sorry No Id Found ",HttpStatus.BAD_REQUEST);
//
//    }


    @ApiOperation(value = "Delete By Long Id",
            notes = "Response Successfully deleted Once deleteUserId Called")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);

        return "Successfully deleted";
    }


}