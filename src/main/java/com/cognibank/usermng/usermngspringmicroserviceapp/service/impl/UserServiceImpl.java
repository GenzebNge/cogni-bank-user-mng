package com.cognibank.usermng.usermngspringmicroserviceapp.service.impl;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.repository.UserDetailsRepository;
import com.cognibank.usermng.usermngspringmicroserviceapp.repository.UserRepository;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
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

    public AuthenticatedUser authenticateUser(final String userName, final String password) {
        final User user = userRepository.findByUserNameAndPassword(userName, hashPassword(userName, password))
                .orElseThrow(UserNameOrPasswordWrongException::new);

        if (!user.getActive()) {
            throw new UserLockedException();
        }

        return new AuthenticatedUser(user);
    }

    @Override
    public boolean unlockUser(String id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        if (user.getActive()) {
            return true;
        }

        userRepository.save(user.withActive(true));

        return true;
    }

    @Override
    public boolean updateUser(String id, Map<String, String> details) {
        // TODO: throw an exception if details contains FirstName or LastName
        if (details.keySet().stream()
                .anyMatch(d -> d.equalsIgnoreCase(FIRST_NAME)
                        || d.equalsIgnoreCase(LAST_NAME))) {
            throw new UserDetailsUpdateException("First name and last name cannot be changed by the user themself.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        List<UserDetails> userDetails = user.getDetails();

        // Extract a key set from UserDetails table.
        Set<String> userDetailFieldsSet =
                userDetails.stream()
                        .map(UserDetails::getFieldName)
                        .collect(Collectors.toSet());

        // Update existing records
        userDetails.stream()
                .filter(d -> details.keySet().contains(d.getFieldName()))
                .forEach(d -> d.withFieldValue(details.get(d.getFieldName())));

        // Add new records
        details.entrySet().stream()
                .filter(d -> !userDetailFieldsSet.contains(d.getKey()))
                .forEach(d -> userDetails.add(new UserDetails()
                        .withUser(user)
                        .withFieldName(d.getKey())
                        .withFieldValue(d.getValue())));

        userDetailsRepository.saveAll(userDetails);

        return true;
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
