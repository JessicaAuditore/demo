package com.soul.springboot.configuration;

import com.soul.springboot.component.ErrorPageHandlerInterceptor;
import com.soul.springboot.component.LoginHandlerInterceptor;
import com.soul.springboot.component.LocaleChangeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //区域解析器
    @Bean
    public LocaleResolver localeResolver() {
        return new LocaleChangeResolver();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/index_v1.html").setViewName("index_v1");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ErrorPageHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error/**");
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/img/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/loginHandle")
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/swagger-ui.html#")
                .excludePathPatterns("/webjars/**");
    }
}
