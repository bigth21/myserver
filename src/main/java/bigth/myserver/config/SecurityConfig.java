package bigth.myserver.config;

import bigth.myserver.domain.Role;
import bigth.myserver.domain.UserRepository;
import bigth.myserver.domain.UserRoleRepository;
import bigth.myserver.service.SimpleUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

import static bigth.myserver.domain.Role.Type.ROLE_ADMIN;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new SimpleUserDetailsService(userRepository, userRoleRepository);
    }

    @Order(2)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/strings/**").permitAll()
                .requestMatchers("/users/sign-up").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()

                .and()
                .logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.sendRedirect("/");
                })

                .and()
                .rememberMe()

                .and()
                .build();
    }

    @Order(1)
    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/admin")
                .authorizeHttpRequests()
                .anyRequest().hasRole(ROLE_ADMIN.getRole())

                .and()
                .formLogin()

                .and()
                .build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/hi.html");
//    }
}
