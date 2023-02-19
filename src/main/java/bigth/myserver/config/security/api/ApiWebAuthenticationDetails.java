package bigth.myserver.config.security.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class ApiWebAuthenticationDetails extends WebAuthenticationDetails {

    public ApiWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
    }
}
