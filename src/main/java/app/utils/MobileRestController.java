package app.utils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile")
public class MobileRestController {
    @GetMapping("/greeting")
    public String greeting() {
        return "Hello, " + "Mayowa" + "!";
    }
}
