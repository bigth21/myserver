package bigth.myserver.web;

import bigth.myserver.service.StringCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/strings")
@RequiredArgsConstructor
public class StringCalculationController {

    private final StringCalculationService stringCalculationService;

    @GetMapping("/calculators/length")
    public String stringCalculator(Model model) {
        model.addAttribute("result", "");
        return "strings/calculators/length";
    }

    @PostMapping("/calculators/length")
    public String calculate(Model model, @RequestParam String str) {
        model.addAttribute("str", str);
        model.addAttribute("charsLength", stringCalculationService.calculateCharsLength(str));
        model.addAttribute("bytesLength", stringCalculationService.calculateBytesLength(str));
        return "strings/calculators/length";
    }
}
