package bigth.myserver.config.security.form;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import java.io.IOException;

public class FormAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            var redirectUrl = savedRequest.getRedirectUrl();
            super.getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        } else {
            super.getRedirectStrategy().sendRedirect(request, response, super.getDefaultTargetUrl());
        }
    }
}
