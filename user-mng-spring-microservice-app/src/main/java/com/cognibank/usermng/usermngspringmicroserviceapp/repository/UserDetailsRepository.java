package com.cognibank.usermng.usermngspringmicroserviceapp.repository;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
}
