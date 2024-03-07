package com.kenpb.app;

import java.util.List;
import java.util.stream.Collectors;

import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AppController {

    @Autowired(required = false)
    private PluginManager pluginManager;

    @GetMapping
    public List<String> list() {
        return pluginManager.getResolvedPlugins().stream()
                .map(PluginWrapper::getPluginId).collect(Collectors.toList());
    }

}
