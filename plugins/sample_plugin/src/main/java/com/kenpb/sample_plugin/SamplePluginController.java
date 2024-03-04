package com.kenpb.sample_plugin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample-plugin")
public class SamplePluginController {

    @GetMapping("/hello")
    public String getHello() {
        return "hello from sample plugin";
    }

}
