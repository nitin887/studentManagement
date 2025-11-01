package com.studentManagementSystem.studentManagement.util;

import com.studentManagementSystem.studentManagement.entity.User;
import com.studentManagementSystem.studentManagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create a default admin user if one doesn't exist
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("password")); // Use a strong password in production!
            adminUser.setRoles("ROLE_ADMIN,ROLE_USER");
            userRepository.save(adminUser);
        }
    }
}