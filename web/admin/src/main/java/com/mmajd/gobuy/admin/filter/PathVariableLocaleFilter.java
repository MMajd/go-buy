package com.mmajd.gobuy.admin.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.defaultString;
import static com.mmajd.gobuy.admin.filter.LocaleAttributeChangeInterceptor.LOCALE_ATTR_NAME;

@Component
@Slf4j
public class PathVariableLocaleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String url = defaultString(request.getRequestURI().substring(request.getContextPath().length()));

        String[] variables = url.split("/");

        if (variables.length > 1 && isLocale(variables[1])) {
            log.debug("Found locale {}", variables[1]);
            request.setAttribute(LOCALE_ATTR_NAME, variables[1]);
            String newUrl = StringUtils.removeStart(url, '/' + variables[1]);
            log.trace("Dispatching to new url \"{}\"", newUrl);
            RequestDispatcher dispatcher = request.getRequestDispatcher(newUrl);
            dispatcher.forward(request, response);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isLocale(String locale) {
        try {
            // TODO: Fix to be usable with dialects
            if (locale.length() > 2) return false;
            LocaleUtils.toLocale(locale);
            return true;
        } catch (IllegalArgumentException e) {
            log.trace("Variable \"{}\" is not a Locale", locale);
        }
        return false;
    }
}
