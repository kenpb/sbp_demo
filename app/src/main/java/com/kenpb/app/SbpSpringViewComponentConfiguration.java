package com.kenpb.app;

import java.util.List;

import org.laxture.sbp.SpringBootPluginManager;
import org.laxture.sbp.internal.webmvc.PluginRequestMappingHandlerMapping;
import org.laxture.sbp.spring.boot.SbpWebMvcPatchAutoConfiguration;
import org.pf4j.PluginManager;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import de.tschuehly.spring.viewcomponent.core.component.ViewContextMethodReturnValueHandler;
import de.tschuehly.spring.viewcomponent.thymeleaf.ThymeleafViewComponentAutoConfiguration;

/**
 * @implNote No ViewAction implementation atm since it also depends on the RequestMappingHandlerMapping
 */
@Configuration
@ComponentScan("de.tschuehly.spring.viewcomponent.core.component")
@ConditionalOnClass({ PluginManager.class, SpringBootPluginManager.class })
@AutoConfigureBefore({ SbpWebMvcPatchAutoConfiguration.class })
@EnableAutoConfiguration(exclude = { ThymeleafViewComponentAutoConfiguration.class })
public class SbpSpringViewComponentConfiguration {

    @Bean
    @Primary
    public WebMvcRegistrations webMvcRegistrations() {
        return new CustomWebMvcRegistrationsBean();
    }

    @Bean
    public ClassLoaderTemplateResolver viewComponentTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(1);
        templateResolver.setCheckExistence(true);

        return templateResolver;
    }

    private static class CustomWebMvcRegistrationsBean implements WebMvcRegistrations, WebMvcConfigurer {

        @Override
        public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
            return new PluginRequestMappingHandlerMapping();
        }

        @Override
        public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
            return null;
        }

        @Override
        public ExceptionHandlerExceptionResolver getExceptionHandlerExceptionResolver() {
            return null;
        }

        @Override
        public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
            handlers.add(new ViewContextMethodReturnValueHandler());
        }

    }

}
