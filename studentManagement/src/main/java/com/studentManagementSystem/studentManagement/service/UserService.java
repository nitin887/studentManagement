package com.studentManagementSystem.studentManagement.service;

import com.studentManagementSystem.studentManagement.dto.PasswordChangeDTO;
import com.studentManagementSystem.studentManagement.entity.User;
import com.studentManagementSystem.studentManagement.exception.IncorrectPasswordException;
import com.studentManagementSystem.studentManagement.exception.UserAlreadyExistsException;
import com.studentManagementSystem.studentManagement.exception.UserNotFoundException;
import com.studentManagementSystem.studentManagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for handling user-related business logic, including password hashing.
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new user with a securely hashed password.
     *
     * @param username The username for the new user.
     * @param rawPassword The plain-text password to be hashed.
     * @param roles The roles to assign to the user (e.g., "ROLE_STUDENT").
     * @return The created User entity.
     */
    @Transactional
    public User createUser(String username, String rawPassword, String roles) {
        // Check if a user with this username already exists
        userRepository.findByUsername(username).ifPresent(u -> {
            throw new UserAlreadyExistsException("User with username '" + username + "' already exists.");
        });

        User newUser = new User();
        newUser.setUsername(username);
        // Here is the crucial step: encode the password before saving
        newUser.setPassword(passwordEncoder.encode(rawPassword));
        newUser.setRoles(roles);

        return userRepository.save(newUser);
    }

    /**
     * Changes a user's password after verifying their old password.
     *
     * @param username The username of the user changing their password.
     * @param passwordChangeDTO DTO containing the old and new passwords.
     */
    @Transactional
    public void changePassword(String username, PasswordChangeDTO passwordChangeDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        // Verify the old password
        if (!passwordEncoder.matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
            throw new IncorrectPasswordException("Incorrect old password.");
        }

        // Encode and set the new password
        user.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));

        userRepository.save(user);
    }
}