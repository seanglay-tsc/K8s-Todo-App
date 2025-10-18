package com.seanglay.authservice.seeder;

import com.seanglay.authservice.model.User;
import com.seanglay.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            log.info("No users found — seeding default admin...");
            User admin = User.builder().email("admin@gmail.com").password(passwordEncoder.encode("admin1234"))
                    .build();
            userRepository.save(admin);
            log.info("Default admin created: {}", admin.getEmail());
        } else {
            log.info("Users already exist — skipping seeding.");
        }
    }
}
