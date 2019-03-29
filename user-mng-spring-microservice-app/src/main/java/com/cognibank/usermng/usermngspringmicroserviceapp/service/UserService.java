package com.cognibank.usermng.usermngspringmicroserviceapp.service;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class UserService {
    public static final String EMAIL = "Email";
    //    private final String EMAIL_VALIDATED = "Email-isValidated";
    public static final String MOBILE_PHONE = "MobilePhone";
//    private final String MOBILE_PHONE_VALIDATED = "MobilePhone-isValidated";

    @Autowired
    private UserRepository userRepository;

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
    public ValidatedUser validateUser(final String userName, final String password) {
        User user = userRepository.findByUserNameAndPassword(userName, hashPassword(userName, password));

        return new ValidatedUser(user, user.getDetails());
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

    public class UserAlreadyExistsException extends RuntimeException {
        private UserAlreadyExistsException(Throwable e) {
            super(e);
        }
    }

    public class PasswordHashingNotPossibleException extends RuntimeException {
        private PasswordHashingNotPossibleException(Throwable e) {
            super(e);
        }
    }

    public class ValidatedUser {
        private String userId;
        private boolean hasPhone;
        private boolean hasEmail;

        private ValidatedUser(User user, List<UserDetails> details) {
            setUserId(user.getId());
            details.stream().
                    collect(Collectors.toMap(UserDetails::getFieldName, UserDetails::getFieldValue))
                    .entrySet().stream()
                    .filter(entry -> entry.getKey().equals(EMAIL) || entry.getKey().equals(MOBILE_PHONE))
                    .forEach(e -> {
                        switch (e.getKey()) {
                            case EMAIL:
                                setHasEmail();
                                break;
                            case MOBILE_PHONE:
                                setHasPhone();
                                break;
                        }
                    });
        }

        public boolean getHasPhone() {
            return hasPhone;
        }

        private void setHasPhone() {
            this.hasPhone = true;
        }

        public boolean getHasEmail() {
            return hasEmail;
        }

        private void setHasEmail() {
            this.hasEmail = true;
        }

        public String getUserId() {
            return userId;
        }

        private void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
