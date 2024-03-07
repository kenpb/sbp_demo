package com.kenpb.sample_plugin;

import org.laxture.sbp.spring.boot.IPluginConfigurer;
import org.laxture.sbp.spring.boot.SpringBootstrap;
import org.springframework.context.support.GenericApplicationContext;

public class SbpSpringViewComponentConfigurer implements IPluginConfigurer {

    @Override
    public String[] excludeConfigurations() {
        return new String[] {
            "de.tschuehly.spring.viewcomponent.jte.JteViewComponentAutoConfiguration"
        };
    }

    @Override
    public void onBootstrap(SpringBootstrap bootstrap, GenericApplicationContext pluginApplicationContext) {
        bootstrap.importBeanFromMainContext(pluginApplicationContext, "requestMappingHandlerMapping");
        bootstrap.importBeanFromMainContext(pluginApplicationContext, "viewActionConfiguration");
    }

}
