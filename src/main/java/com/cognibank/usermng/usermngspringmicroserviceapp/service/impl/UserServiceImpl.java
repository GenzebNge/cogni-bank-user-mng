package com.cognibank.usermng.usermngspringmicroserviceapp.service.impl;

import com.cognibank.usermng.usermngspringmicroserviceapp.model.User;
import com.cognibank.usermng.usermngspringmicroserviceapp.model.UserDetails;
import com.cognibank.usermng.usermngspringmicroserviceapp.repository.UserDetailsRepository;
import com.cognibank.usermng.usermngspringmicroserviceapp.repository.UserRepository;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.UserService;
import com.cognibank.usermng.usermngspringmicroserviceapp.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * UserServiceImpl is a subclass of {@link UserService}
 *  @see UserService
 *  @see UserRepository
 *  @see UserDetailsRepository
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    /**
     * Constructor to create beans which are dependent in this class
     * @param userRepository {@link UserServiceImpl#userRepository}
     * @param userDetailsRepository {@link UserServiceImpl#userDetailsRepository}
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    /**
     * {@inheritDoc}
     * @throws UserAlreadyExistsException {@link UserAlreadyExistsException}- if the User is already present in the database
     * @see #hashPassword(String, String)
     * @see UserService#createNewUser(User)
     * @see UserRepository#save(Object)
     * @see User
     */
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

    /**
     * {@inheritDoc}
     * @throws UserNameOrPasswordWrongException {@link UserNameOrPasswordWrongException} - if the given UserId or Password does not match
     * @throws UserLockedException {@link UserLockedException}- if the given User is Locked and needs to be unlocked
     * @see UserService#authenticateUser(String, String)
     * @see AuthenticatedUser#AuthenticatedUser(User)
     * @see UserRepository#findByUserNameAndPassword(String, String)
     * @see User
     */
    public AuthenticatedUser authenticateUser(final String userName, final String password) {
        final User user = userRepository.findByUserNameAndPassword(userName, hashPassword(userName, password))
                .orElseThrow(UserNameOrPasswordWrongException::new);

        if (!user.getActive()) {
            throw new UserLockedException();
        }

        return new AuthenticatedUser(user);
    }


    /**
     * {@inheritDoc}
     * @throws UserNotFoundException {@link UserNotFoundException} - if the given User is not Found on the database
     * @see User
     * @see UserRepository#findById(Object)
     */
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

    /**
     * {@inheritDoc}
     * @throws UserDetailsUpdateException {@link UserDetailsUpdateException} - if the details which need to be updated contain FirstName and lastName
     * @throws UserNotFoundException {@link UserNotFoundException} - if the given User is not Found on the database
     * @see User
     * @see UserDetails
     * @see UserRepository#saveAll(Iterable)
     */
    @Override
    public boolean updateUser(String id, Map<String, String> details) {
        // throws an exception if details contains FirstName or LastName
        if (details.keySet().stream()
                .anyMatch(d -> d.equalsIgnoreCase(FIRST_NAME)
                        || d.equalsIgnoreCase(LAST_NAME))) {
            throw new UserDetailsUpdateException("First name and last name cannot be changed by the user themselves.");
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

    /**
     *  Hashes the password
     * @param userName UserName of the User
     * @param clearPassword Password of the User before Hashing
     * @return <code> String </code> (Hashed Password)
     * @throws PasswordHashingNotPossibleException {@link PasswordHashingNotPossibleException} - if password hashing is not possible
     */
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
