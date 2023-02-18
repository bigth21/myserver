package bigth.myserver.config.security.form;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class FormAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("formauthp");
        var username = authentication.getName();
        var credentials = (String) authentication.getCredentials();
        var userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(credentials, userDetails.getPassword())) {
            throw new BadCredentialsException("Password transmitted is wrong");
        }
        var details = (FormWebAuthenticationDetails) authentication.getDetails();
        if (!details.getSecretKey().equals("secret")) {
            throw new InsufficientAuthenticationException("Secret parameter is mismatch");
        }
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
