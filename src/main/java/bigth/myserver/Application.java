package bigth.myserver;

import bigth.myserver.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static bigth.myserver.domain.Role.Type.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Profile("local")
    @Component
    @RequiredArgsConstructor
    @Slf4j
    @Transactional
    public static class DataInit implements ApplicationRunner {

        @Value("${admin.username}")
        private String adminUsername;
        @Value("${admin.password}")
        private String adminPassword;
        @Value("${admin.email}")
        private String adminEmail;
        private final UserRepository userRepository;
        private final UserRoleRepository userRoleRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;

        @Override
        public void run(ApplicationArguments args) {
            log.info("Data initialization starts");
            var userRole = Role.builder()
                    .name(ROLE_USER)
                    .build();
            var adminRole = Role.builder()
                    .name(ROLE_ADMIN)
                    .build();

            var adminUser = User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .email(adminEmail)
                    .build();
            userRepository.save(adminUser);
            List<Role> adminRoles = List.of(userRole, adminRole);
            roleRepository.saveAll(adminRoles);
            var adminUserRoles = adminRoles.stream()
                    .map(r -> UserRole.builder()
                            .role(r)
                            .user(adminUser)
                            .build())
                    .collect(Collectors.toList());
            userRoleRepository.saveAll(adminUserRoles);

            var user = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("1234"))
                    .email("user@email.com")
                    .build();
            userRepository.save(user);
            userRoleRepository.save(UserRole.builder()
                    .role(userRole)
                    .user(user)
                    .build());
            log.info("Data initialization ends");
        }
    }


}
