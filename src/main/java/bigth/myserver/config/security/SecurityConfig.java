package bigth.myserver.config.security;

import bigth.myserver.domain.UserRepository;
import bigth.myserver.domain.UserRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import static bigth.myserver.domain.Role.Type.ROLE_ADMIN;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

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
        return new FormAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationProvider provider) {
        return new ProviderManager(provider);
    }

    @Bean
    public AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource() {
        return new FormAuthenticationDetailsSource();
    }

    @Order(2)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager,
                                                   AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/strings/**").permitAll()
                .requestMatchers("/users/sign-up").permitAll()
                .requestMatchers("/users/sign-in*").permitAll()

                .requestMatchers("/api/v1/sign-in").permitAll()

                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/users/sign-in")
                .loginProcessingUrl("/login-proc")
                .authenticationDetailsSource(authenticationDetailsSource)
                .successHandler(new FormAuthenticationSuccessHandler())
                .failureHandler(new FormAuthenticationFailureHandler())
                .and()
                .rememberMe()
                .and()
                .logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.sendRedirect("/");
                })

                .and()
                .exceptionHandling()
                .accessDeniedHandler(new SimpleAccessDeniedHandler())

                .and()
                .addFilterBefore(new ApiAuthenticationProcessingFilter(objectMapper, authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()

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
                .successHandler(new FormAuthenticationSuccessHandler())
                .failureHandler(new FormAuthenticationFailureHandler())
                .and()
                .rememberMe()
                .and()
                .logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.sendRedirect("/");
                })

                .and()
                .exceptionHandling()
                .accessDeniedHandler(new SimpleAccessDeniedHandler())

                .and()
                .build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/hi.html");
//    }
}
