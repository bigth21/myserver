package bigth.myserver;

import bigth.myserver.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

    @Component
    @RequiredArgsConstructor
    @Slf4j
    @Transactional
    public static class DataInit implements ApplicationRunner {
        private final UserRepository userRepository;
        private final UserRoleRepository userRoleRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            log.info("Data initialization starts");
            var member = User.builder()
                    .username("superkorean")
                    .password(passwordEncoder.encode("1111"))
                    .email("taekhyeon.nam@gmail.com")
                    .build();
            userRepository.save(member);
            List<Role> roles = new ArrayList<>();
            roles.add(Role.builder()
                    .name(ROLE_USER)
                    .build());
            roles.add(Role.builder()
                    .name(ROLE_ADMIN)
                    .build());
            roleRepository.saveAll(roles);
            var memberRoles = roles.stream()
                    .map(r -> UserRole.builder()
                            .role(r)
                            .user(member)
                            .build())
                    .collect(Collectors.toList());
            userRoleRepository.saveAll(memberRoles);
            log.info("Data initialization ends");
        }
    }


}
