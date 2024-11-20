package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // Pasar la fecha y hora actuales al template
        model.addAttribute("currentTime", LocalDateTime.now());
        return "index";  // El nombre del archivo index.html
    }
}
