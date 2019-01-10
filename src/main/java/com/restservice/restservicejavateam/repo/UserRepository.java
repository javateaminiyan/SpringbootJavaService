package com.restservice.restservicejavateam.repo;


import com.restservice.restservicejavateam.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DAM on 2/25/17.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {




//    @Query("select count(u.firstName)  from User u where u.firstName=:firstName")
//    int findByEmail(@Param("email") String firstName);

    @Query("select count(j.username) from User j where j.username=:username")

    int findbyUsername(@Param("username") String username);

        //WHERE EMAIL_ADDRESS = ?1
    @Query( value = "SELECT * FROM User ", nativeQuery = true)
    List<User> findbyUsername();

}