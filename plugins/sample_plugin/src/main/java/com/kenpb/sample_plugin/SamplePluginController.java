package com.kenpb.sample_plugin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample-plugin")
public class SamplePluginController {

    @GetMapping
    public String samplePluginViewComponent() {
        return "plugin_demo";
    }

}
