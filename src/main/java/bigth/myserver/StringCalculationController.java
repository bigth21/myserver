package bigth.myserver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/string-calculator")
@RequiredArgsConstructor
public class StringCalculationController {

    private final StringCalculationService stringCalculationService;

    @GetMapping
    public String stringCalculator(Model model) {
        model.addAttribute("result", "");
        return "string-calculator";
    }

    @GetMapping("/calculate")
    public String calculate(Model model, @RequestParam String str) {
        model.addAttribute("str", str);
        model.addAttribute("charsLength", stringCalculationService.calculateCharsLength(str));
        model.addAttribute("bytesLength", stringCalculationService.calculateBytesLength(str));
        return "string-calculator";
    }
}
