package com.kenpb.sample_plugin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;

@Controller
@RequestMapping("/sample-plugin")
public class SamplePluginController {

    @HxRequest
    @GetMapping
    public String htmxRequest() {
        return "plugin";
    }

}
