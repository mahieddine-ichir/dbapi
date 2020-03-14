package com.thinatech.dbapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.function.Function;

@Configuration
public class SortParameterMethodResolverConfiguration implements WebMvcConfigurer {

    private final SortParameterMethodResolver sortParameterMethodResolver;

    private final CursorParameterMethodResolver cursorParameterMethodResolver;

    public SortParameterMethodResolverConfiguration(SortParameterMethodResolver sortParameterMethodResolver, CursorParameterMethodResolver cursorParameterMethodResolver) {
        this.sortParameterMethodResolver = sortParameterMethodResolver;
        this.cursorParameterMethodResolver = cursorParameterMethodResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(sortParameterMethodResolver);
        resolvers.add(cursorParameterMethodResolver);
    }

    public static <T> T getParameter(WebRequest webRequest, String parameter, T defaultValue, Function<String, T> transform) {
        String parameterString = webRequest.getParameter(parameter);
        if (StringUtils.isEmpty(parameterString)) {
            return defaultValue;
        } else {
            return transform.apply(parameterString);
        }
    }
}
