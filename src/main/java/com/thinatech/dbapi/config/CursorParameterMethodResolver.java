package com.thinatech.dbapi.config;

import com.thinatech.dbapi.Cursor;
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
public class CursorParameterMethodResolver implements HandlerMethodArgumentResolver {

    @Value("${query.defaults.limit}")
    private int defaultLimit;

    @Value("${query.defaults.start}")
    private int defaultStart;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Cursor.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return new Cursor(
                getParameter(webRequest, "start", defaultStart, s -> Integer.parseInt(s)),
                getParameter(webRequest, "limit", defaultLimit, s -> Integer.parseInt(s))
        );
    }
}
