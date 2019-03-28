package com.cognibank.usermng.usermngspringmicroserviceapp.service;

import com.cognibank.usermng.usermngspringmicroserviceapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

}
