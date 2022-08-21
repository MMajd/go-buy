package com.mmajd.gobuy.admin.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LocaleAttributeChangeInterceptor implements HandlerInterceptor {
    public static final String LOCALE_ATTR_NAME = LocaleAttributeChangeInterceptor.class.getName()+".LOCALE";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object newLocale = request.getAttribute(LOCALE_ATTR_NAME);
        String url = request.getRequestURI();

        if (newLocale != null) {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver == null) {
                throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }
            localeResolver.setLocale(request, response, StringUtils.parseLocaleString(newLocale.toString()));
        }
        // Proceed in any case.
        return true;
    }
}
