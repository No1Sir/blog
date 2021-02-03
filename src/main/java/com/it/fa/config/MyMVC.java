package com.it.fa.config;

import com.it.fa.utils.MyLocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
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
        List<String> statics_patterns = new ArrayList<>();
        statics_patterns.add("/admin/*.html");
        statics_patterns.add("/comm/*.html");
        statics_patterns.add("/site/*.html");
        statics_patterns.add("/admin/css/**");
        statics_patterns.add("/admin/editormd/**");
        statics_patterns.add("/admin/images/**");
        statics_patterns.add("/admin/js/**");
        statics_patterns.add("/admin/plugins/**");
        statics_patterns.add("/site/js/**");
        statics_patterns.add("/site/fonts/**");
        statics_patterns.add("/site/images/**");
        statics_patterns.add("/site/jquery/**");
        statics_patterns.add("/site/css/**");
        registry.addInterceptor(baseInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(statics_patterns);
    }
    @Bean
    public LocaleResolver localeResolver(){
        return localeResolver;
    }
}
