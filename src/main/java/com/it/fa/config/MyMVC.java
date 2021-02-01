package com.it.fa.config;

import com.it.fa.utils.MyLocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Component
public class MyMVC implements WebMvcConfigurer {
    @Autowired
    private BaseInterceptor baseInterceptor;

    @Autowired
    private MyLocaleResolver localeResolver;
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor);
    }
    @Bean
    public LocaleResolver localeResolver(){
        return localeResolver;
    }
}
