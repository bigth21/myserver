package bigth.myserver.config.security.api;

import bigth.myserver.web.UserSignInRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
public class ApiAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public ApiAuthenticationProcessingFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/api/v1/sign-in"));
        this.objectMapper = objectMapper;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        var requestDTO = objectMapper.readValue(request.getReader(), UserSignInRequestDTO.class);
        if (!hasText(requestDTO.getUsername()) || !hasText(requestDTO.getPassword())) {
            log.error("Username or password is not valid");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return null;
        }
        var authentication = new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword());
        return getAuthenticationManager().authenticate(authentication);
    }
}
