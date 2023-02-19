package bigth.myserver.web;

import bigth.myserver.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String signIn(@RequestParam(required = false) Boolean isException,
                         @RequestParam(required = false) String message,
                         Model model) {
        if (isException != null && isException) {
            model.addAttribute("isException", isException);
            model.addAttribute("message", message);
        }
        return "users/sign-in";
    }

    @GetMapping("/denied")
    public String accessDenied(@RequestParam(required = false) String message, Model model) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = (String) authentication.getPrincipal();
        model.addAttribute("username", username);
        model.addAttribute("message", message);
        return "users/denied";
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
