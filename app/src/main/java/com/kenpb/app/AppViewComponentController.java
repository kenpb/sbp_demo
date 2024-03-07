package com.kenpb.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.tschuehly.spring.viewcomponent.jte.ViewContext;

@Controller
@RequestMapping("/component")
public class AppViewComponentController {

    private final AppViewComponent appViewComponent;

    public AppViewComponentController(AppViewComponent appViewComponent) {
        this.appViewComponent = appViewComponent;
    }

    @GetMapping
    ViewContext indexComponent() {
        return appViewComponent.render();
    }

}
