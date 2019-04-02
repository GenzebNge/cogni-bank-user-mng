package com.cognibank.usermng.usermngspringmicroserviceapp.repository;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository - performs operations on User
 * @see JpaRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    /**
     * Finds the User by UserName and Password
     * @param userName UserName of the User
     * @param password Passwor dof the User
     * @return Optional<User>
     * @see Optional
     */
    Optional<User> findByUserNameAndPassword(String userName, String password);
}
