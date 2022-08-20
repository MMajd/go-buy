package com.mmajd.gobuy.admin;

import com.fasterxml.jackson.core.JsonParser;
import com.mmajd.gobuy.admin.filter.PathVariableLocaleFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.mmajd.gobuy"})
@EntityScan({"com.mmajd.gobuy.common.entity"})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }


//    @Bean
//    public FilterRegistrationBean<PathVariableLocaleFilter> pathVariableLocaleFilter() {
//        FilterRegistrationBean<PathVariableLocaleFilter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(new PathVariableLocaleFilter());
//        registration.addUrlPatterns("/*");
//        return registration;
//    }
}