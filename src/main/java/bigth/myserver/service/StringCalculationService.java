package bigth.myserver.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;

@Service
public class StringCalculationService {

    public int calculateCharsLength(@RequestParam String str) {
        return str.length();
    }

    public int calculateBytesLength(@RequestParam String str) {
        return str.getBytes(StandardCharsets.UTF_8).length;
    }
}
