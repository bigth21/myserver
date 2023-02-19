package bigth.myserver.config.security.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class ApiAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        writeResponseBody(response, authentication);
    }

    private void writeResponseBody(HttpServletResponse response, Authentication authentication) throws IOException {
        var principal = (String) authentication.getPrincipal();
        objectMapper.writeValue(response.getWriter(), new SignInResponse(principal));
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class SignInResponse {
        private String name;

        public SignInResponse(String name) {
            this.name = name;
        }
    }

}
