package com.kenpb.sample_plugin;

import org.laxture.sbp.spring.boot.IPluginConfigurer;
import org.laxture.sbp.spring.boot.SpringBootstrap;
import org.springframework.context.support.GenericApplicationContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class SbpThymeleafConfigurer implements IPluginConfigurer {

    @Override
    public String[] excludeConfigurations() {
        return new String[] {
                "org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration",
                "de.tschuehly.spring.viewcomponent.thymeleaf.ThymeleafViewComponentAutoConfiguration"
        };
    }

    @Override
    public void onBootstrap(SpringBootstrap bootstrap, GenericApplicationContext pluginApplicationContext) {
        SpringTemplateEngine templateEngine = (SpringTemplateEngine) bootstrap.getMainApplicationContext()
                .getBean("templateEngine");
        templateEngine.addTemplateResolver(this.pluginTemplateResolver(pluginApplicationContext.getClassLoader()));
    }

    /**
     * @implNote Current workaround is to add a template resolver for each plugin I guess?
     */
    ClassLoaderTemplateResolver pluginTemplateResolver(final ClassLoader classLoader) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver(classLoader);

        templateResolver.setPrefix("");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(1);
        templateResolver.setCheckExistence(true);

        return templateResolver;
    }

}
