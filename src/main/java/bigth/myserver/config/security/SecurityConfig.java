package bigth.myserver.config.security;

import bigth.myserver.domain.UserRepository;
import bigth.myserver.domain.UserRoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

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

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        return new SimpleAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    @Order(2)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/strings/**").permitAll()
                .requestMatchers("/users/sign-up").permitAll()
                .requestMatchers("/users/sign-in").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/users/sign-in")
                .loginProcessingUrl("/login-proc")
                .authenticationDetailsSource((AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails>) SimpleWebAuthenticationDetails::new)
                .successHandler(new SimpleAuthenticationSuccessHandler())
                .defaultSuccessUrl("/")
                .and()
                .rememberMe()
                .and()
                .logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.sendRedirect("/");
                })

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
                .loginPage("/users/sign-in")
                .loginProcessingUrl("/login-proc")
                .authenticationDetailsSource((AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails>) SimpleWebAuthenticationDetails::new)
                .successHandler(new SimpleAuthenticationSuccessHandler())
                .defaultSuccessUrl("/")
                .and()
                .rememberMe()
                .and()
                .logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.sendRedirect("/");
                })

                .and()
                .build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/hi.html");
//    }
}
