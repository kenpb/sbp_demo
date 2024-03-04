package com.kenpb.sample_plugin;

import org.laxture.sbp.SpringBootPlugin;
import org.laxture.sbp.spring.boot.SpringBootstrap;
import org.pf4j.PluginWrapper;

public class SamplePluginPlugin extends SpringBootPlugin {

    public SamplePluginPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    protected SpringBootstrap createSpringBootstrap() {
        return new SpringBootstrap(this, SamplePluginStarter.class);
    }
}
