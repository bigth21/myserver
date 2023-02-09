package bigth.myserver.web;

import bigth.myserver.service.UserService;
import lombok.RequiredArgsConstructor;
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
        return "redirect:/login";
    }

    @GetMapping("/my-page")
    public String myPage() {
        return "users/my-page";
    }
}
