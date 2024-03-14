package com.kenpb.sbp.spring.boot;

import org.laxture.sbp.spring.boot.IPluginConfigurer;
import org.laxture.sbp.spring.boot.SpringBootstrap;
import org.springframework.context.support.GenericApplicationContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

public class SbpThymeleafConfigurer implements IPluginConfigurer {

    @Override
    public String[] excludeConfigurations() {
        return new String[] {
                "org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration"
        };
    }

    @Override
    public void onBootstrap(SpringBootstrap bootstrap, GenericApplicationContext pluginApplicationContext) {
        SpringTemplateEngine templateEngine = (SpringTemplateEngine) bootstrap.getMainApplicationContext()
                .getBean("templateEngine");
        templateEngine.addTemplateResolver(this.pluginTemplateResolver(pluginApplicationContext));
    }

    SpringResourceTemplateResolver pluginTemplateResolver(GenericApplicationContext pluginApplicationContext) {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(pluginApplicationContext);
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        return resolver;
    }

}
