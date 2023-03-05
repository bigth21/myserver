package bigth.myserver.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @GetMapping("/messages")
    public String messages() {
        return "messages";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
