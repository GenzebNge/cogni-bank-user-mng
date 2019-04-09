package com.cognibank.usermng.usermngspringmicroserviceapp.repository;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * UserDetailsRepository - performs operations on UserDetails
 * @see JpaRepository
 */
@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    //Optional<UserDetails> findByFieldValue(String Email);

    @Query(value = "SELECT USER_ID FROM USER_DETAILS where FIELD_VALUE =:email ",nativeQuery = true)
    String  findByFieldValue(@Param("email") String email);


}
