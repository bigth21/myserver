package bigth.myserver.web;

import bigth.myserver.config.security.SimpleWebAuthenticationDetails;
import bigth.myserver.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/sign-up")
    public String signUpView() {
        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String email) {
        userService.createUser(username, password, email);
        return "redirect:/sign-in";
    }

    @GetMapping("/sign-in")
    public String signIn(@RequestParam(name = "SPRING_SECURITY_LAST_EXCEPTION", required = false) Exception exception,
                         Model model) {
        if (exception != null) {
            System.out.println(exception.getMessage());
            model.addAttribute("exception", exception);
            model.addAttribute("message", exception.getMessage());
        }
        return "users/sign-in";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpServletRequest request, HttpServletResponse response) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    @GetMapping("/my-page")
    public String myPage() {
        return "users/my-page";
    }
}
