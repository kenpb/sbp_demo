package com.kenpb.app;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.laxture.sbp.SpringBootPluginManager;
import org.laxture.sbp.internal.webmvc.PluginRequestMappingHandlerMapping;
import org.laxture.sbp.spring.boot.SbpWebMvcPatchAutoConfiguration;
import org.pf4j.PluginManager;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.condition.CompositeRequestCondition;
import org.springframework.web.servlet.mvc.condition.HeadersRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxHandlerInterceptor;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxHandlerMethodArgumentResolver;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxMvcAutoConfiguration;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequestHeader;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponseHandlerMethodReturnValueHandler;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import io.github.wimdeblauwe.htmx.spring.boot.thymeleaf.HtmxDialect;
import io.github.wimdeblauwe.htmx.spring.boot.thymeleaf.HtmxThymeleafAutoConfiguration;
import jakarta.servlet.http.HttpServletRequest;

import static io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequestHeader.*;

@Configuration
@ConditionalOnClass({ PluginManager.class, SpringBootPluginManager.class })
@AutoConfigureBefore({ SbpWebMvcPatchAutoConfiguration.class })
@EnableAutoConfiguration(exclude = { HtmxMvcAutoConfiguration.class, HtmxThymeleafAutoConfiguration.class })
public class CustomWebMvcRegistrations {

    @Bean
    public HtmxDialect htmxDialect(ObjectMapper mapper) {
        return new HtmxDialect(mapper);
    }

    @Bean
    @Primary
    public WebMvcRegistrations webMvcRegistrations() {
        return new CustomWebMvcRegistrationsBean();
    }

    private static class CustomWebMvcRegistrationsBean implements WebMvcRegistrations, WebMvcConfigurer {

        @Autowired
        @Qualifier("viewResolver")
        private ObjectFactory<ViewResolver> resolver;

        @Autowired
        private ObjectFactory<LocaleResolver> locales;

        @Autowired
        private ObjectMapper objectMapper;

        @Override
        public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
            return new CustomRequestMappingHandlerMapping();
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
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new HtmxHandlerInterceptor());
        }

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
            resolvers.add(new HtmxHandlerMethodArgumentResolver());
        }

        @Override
        public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
            handlers.add(new HtmxResponseHandlerMethodReturnValueHandler(resolver.getObject(), locales, objectMapper));
        }
    }

    private static class CustomRequestMappingHandlerMapping extends PluginRequestMappingHandlerMapping {

        @Override
        protected RequestCondition<?> getCustomMethodCondition(Method method) {
            HxRequest methodAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, HxRequest.class);
            return createCondition(methodAnnotation);
        }

        @Override
        protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
            HxRequest typeAnnotation = AnnotatedElementUtils.findMergedAnnotation(handlerType, HxRequest.class);
            return createCondition(typeAnnotation);
        }

        private RequestCondition<?> createCondition(HxRequest hxRequest) {
            if (hxRequest != null) {
                var conditions = new ArrayList<RequestCondition<?>>();
                conditions.add(new HeadersRequestCondition(HX_REQUEST.getValue()));

                if (StringUtils.hasText(hxRequest.value())) {
                    conditions
                            .add(new HtmxTriggerHeadersRequestCondition(hxRequest.value()));
                } else {
                    if (StringUtils.hasText(hxRequest.triggerId())) {
                        conditions
                                .add(new HeadersRequestCondition(HX_TRIGGER.getValue() + "=" + hxRequest.triggerId()));
                    }
                    if (StringUtils.hasText(hxRequest.triggerName())) {
                        conditions.add(new HeadersRequestCondition(
                                HX_TRIGGER_NAME.getValue() + "=" + hxRequest.triggerName()));
                    }
                }

                if (StringUtils.hasText(hxRequest.target())) {
                    conditions.add(new HeadersRequestCondition(HX_TARGET.getValue() + "=" + hxRequest.target()));
                }

                return new CompositeRequestCondition(conditions.toArray(RequestCondition[]::new));
            }

            return null;
        }
    }

    private static class HtmxTriggerHeadersRequestCondition
            implements RequestCondition<HtmxTriggerHeadersRequestCondition> {

        private static final HtmxTriggerHeadersRequestCondition EMPTY_CONDITION = new HtmxTriggerHeadersRequestCondition();

        private String value;

        public HtmxTriggerHeadersRequestCondition(String value) {
            this.value = value;
        }

        HtmxTriggerHeadersRequestCondition() {
        }

        @Override
        public HtmxTriggerHeadersRequestCondition combine(HtmxTriggerHeadersRequestCondition other) {
            return other.value != null ? other : this;
        }

        @Override
        public int compareTo(HtmxTriggerHeadersRequestCondition other, HttpServletRequest request) {
            if (this.value == null && other.value == null) {
                return 0;
            } else if (this.value == null) {
                return 1;
            } else if (other.value == null) {
                return -1;
            } else {
                return this.value.compareTo(other.value);
            }
        }

        @Override
        public HtmxTriggerHeadersRequestCondition getMatchingCondition(HttpServletRequest request) {
            if (CorsUtils.isPreFlightRequest(request)) {
                return EMPTY_CONDITION;
            }

            // HX-Trigger
            String headerValue = request.getHeader(HtmxRequestHeader.HX_TRIGGER.getValue());
            if (headerValue != null && headerValue.equals(value)) {
                return this;
            }

            // HX-Trigger-Name
            headerValue = request.getHeader(HtmxRequestHeader.HX_TRIGGER_NAME.getValue());
            if (headerValue != null && headerValue.equals(value)) {
                return this;
            }

            return null;
        }
    }

}
