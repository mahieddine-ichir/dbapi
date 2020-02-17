package com.thinatech.mariadbclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Slf4j
public class SortParameterMethodResolverConfiguration implements WebMvcConfigurer {

    private final SortParameterMethodResolver sortParameterMethodResolver;

    public SortParameterMethodResolverConfiguration(SortParameterMethodResolver sortParameterMethodResolver) {
        this.sortParameterMethodResolver = sortParameterMethodResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        log.info("Adding {}", sortParameterMethodResolver);
        resolvers.add(sortParameterMethodResolver);
    }
}
