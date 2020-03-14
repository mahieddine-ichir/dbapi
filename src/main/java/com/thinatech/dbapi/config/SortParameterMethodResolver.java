package com.thinatech.dbapi.config;

import com.thinatech.dbapi.Sort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.thinatech.dbapi.config.SortParameterMethodResolverConfiguration.getParameter;

@Component
@Slf4j
public class SortParameterMethodResolver implements HandlerMethodArgumentResolver {

    @Value("${query.defaults.sort:asc}")
    private String sortDefaultValue;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Sort.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return getParameter(webRequest, "sort", Sort.valueOf(sortDefaultValue.toUpperCase()), s -> Sort.valueOf(s.toUpperCase()));
    }
}
