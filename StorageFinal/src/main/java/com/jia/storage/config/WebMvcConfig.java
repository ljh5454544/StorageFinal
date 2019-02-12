package com.jia.storage.config;

import com.jia.storage.component.LoginIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){

        return new WebMvcConfigurer(){
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("/index.html");
                registry.addViewController("/index").setViewName("/index.html");
                registry.addViewController("/error").setViewName("/403.html");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(getMyInterceptor()).addPathPatterns("/user/**").excludePathPatterns("/user/login")
                        .addPathPatterns("/role/**")
                        .addPathPatterns("/comm/**").excludePathPatterns("/comm/download")
                        .addPathPatterns("/inventory/**")
                        .addPathPatterns("/record/**")
                        .addPathPatterns("/position/**")
                        .addPathPatterns("/room/**")
                        .addPathPatterns("/shelf/**")
                        .addPathPatterns("/tenant/**").excludePathPatterns("/tenant/download");
                WebMvcConfig.super.addInterceptors(registry);
            }
        };
    }

    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new LoginIntercepter();
    }


}
