package com.kenpb.sample_plugin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.tschuehly.spring.viewcomponent.jte.ViewContext;

@Controller
@RequestMapping("/sample-plugin")
public class SamplePluginController {

    private final SamplePluginVIewComponent samplePluginVIewComponent;

    public SamplePluginController(SamplePluginVIewComponent samplePluginVIewComponent) {
        this.samplePluginVIewComponent = samplePluginVIewComponent;
    }

    @GetMapping
    public ViewContext hello() {
        return samplePluginVIewComponent.render();
    }

}
