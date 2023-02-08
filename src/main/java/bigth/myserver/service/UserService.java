package bigth.myserver.service;

import bigth.myserver.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static bigth.myserver.domain.Role.Type.ROLE_ADMIN;
import static bigth.myserver.domain.Role.Type.ROLE_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Transactional
    public Long createUser(String username, String password, String email) {
        if (isUserPresent(username))
            throw new IllegalArgumentException("User already exist");
        var user = saveUser(username, password, email);
        var role = saveRole(ROLE_USER);
        return mapUserAndRole(user, role).getUser().getId();
    }

    @Transactional
    public Long createAdminUser(String username, String password, String email) {
        if (isUserPresent(username))
            throw new IllegalArgumentException("Administrator already exist");
        var user = saveUser(username, password, email);
        var role = saveRole(ROLE_ADMIN);
        return mapUserAndRole(user, role).getUser().getId();
    }

    private UserRole mapUserAndRole(User user, Role role) {
        return userRoleRepository.save(UserRole.builder()
                .user(user)
                .role(role)
                .build());
    }

    private Role saveRole(Role.Type roleUser) {
        return roleRepository.save(Role.builder()
                .name(roleUser)
                .build());
    }

    private User saveUser(String username, String password, String email) {
        return userRepository.save(User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build());
    }

    private boolean isUserPresent(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
