package bigth.myserver.config.security.api;

import bigth.myserver.web.UserSignInRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
public class ApiAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public ApiAuthenticationProcessingFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager,
                                             AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource) {
        super(new AntPathRequestMatcher("/api/v1/sign-in"), authenticationManager);
        this.objectMapper = objectMapper;
        setAuthenticationDetailsSource(authenticationDetailsSource);
        setSessionAuthenticationStrategy(new CompositeSessionAuthenticationStrategy(List.of(new ChangeSessionIdAuthenticationStrategy())));
        setSecurityContextRepository(new DelegatingSecurityContextRepository(List.of(new HttpSessionSecurityContextRepository(), new RequestAttributeSecurityContextRepository())));
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
        setDetails(request, authentication);
        return getAuthenticationManager().authenticate(authentication);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authentication) {
        authentication.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
