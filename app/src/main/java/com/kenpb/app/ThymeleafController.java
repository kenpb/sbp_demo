package com.kenpb.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;

@Controller
@RequestMapping("/app")
public class ThymeleafController {

    @HxRequest
    @GetMapping
    public String app(Model model) {
        return "app";
    }

}
