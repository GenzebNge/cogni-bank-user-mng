package com.cognibank.usermng.usermngspringmicroserviceapp.service.impl;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.repository.UserRepository;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringJoiner;

@Service
public class UserServiceImpl implements UserService {
    //private final String EMAIL_VALIDATED = "Email-isValidated";
    //private final String MOBILE_PHONE_VALIDATED = "MobilePhone-isValidated";

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createNewUser(final User user) {
        try {
            return userRepository.save(
                    user.withPassword(
                            hashPassword(user.getUserName(), user.getPassword())))
                    .getId();
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException(e);
        }
    }

    @Transactional
    public AuthenticatedUser authenticateUser(final String userName, final String password) {
        final User user = userRepository.findByUserNameAndPassword(userName, hashPassword(userName, password));

        if (null == user) {
            throw new UserNameOrPasswordWrongException();
        }

        if (!user.getActive()) {
            throw new UserLockedException();
        }

        return new AuthenticatedUser(user);
    }

    private String hashPassword(final String userName, final String clearPassword) {
        final StringJoiner sj = new StringJoiner(" | ");
        final String salt = "COGN1-BANK-5ALT-F0R-HASH1NG-PASSWORD5";
        try {
            // Create MessageDigest instance for SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            //Add password bytes to digest
            md.update(sj.add(salt).add(clearPassword).add(userName).toString().getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new PasswordHashingNotPossibleException(e);
        }
    }

}
