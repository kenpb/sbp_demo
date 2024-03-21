package com.kenpb.sample_plugin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.tschuehly.spring.viewcomponent.thymeleaf.ViewContext;

@Controller
@RequestMapping("/sample-plugin")
public class SamplePluginController {

    private final SamplePluginViewComponent samplePluginViewComponent;

    public SamplePluginController(SamplePluginViewComponent samplePluginViewComponent) {
        this.samplePluginViewComponent = samplePluginViewComponent;
    }

    @GetMapping
    ViewContext pluginComponent() {
        return samplePluginViewComponent.render();
    }

}
