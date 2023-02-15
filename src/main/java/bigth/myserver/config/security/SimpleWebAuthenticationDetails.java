package bigth.myserver.config.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Getter
public class SimpleWebAuthenticationDetails extends WebAuthenticationDetails {

    private final String secretKey;

    public SimpleWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        secretKey = request.getParameter("secretKey");
    }
}
