package com.thinatech.mariadbclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
@Slf4j
public class SortParameterMethodResolver implements HandlerMethodArgumentResolver {

    @Value("${query.defaults.sort:asc}")
    private String sortDefaultValue;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(GenericRepository.Sort.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (Objects.isNull(webRequest.getParameter("sort"))) {
            return GenericRepository.Sort.valueOf(sortDefaultValue.toUpperCase());
        }
        return GenericRepository.Sort.valueOf(webRequest.getParameter("sort").toUpperCase());
    }
}
