package com.cognibank.usermng.usermngspringmicroserviceapp.repository;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserNameAndPassword(String userName, String password);
}
