package bigth.myserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
@Slf4j
public class ToyController {

    @GetMapping("/v1/strings/chars/length")
    public int calculateLength(@RequestParam String str) {
        return str.length();
    }

    @GetMapping("/v1/strings/bytes/length")
    public int calculateByteLength(@RequestParam String str) {
        return str.getBytes(StandardCharsets.UTF_8).length;
    }

}
