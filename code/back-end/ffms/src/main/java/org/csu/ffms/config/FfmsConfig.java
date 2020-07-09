package org.csu.ffms.config;

import org.csu.ffms.jwt.handler.FfmsLoginAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/9
 * @描述
 **/
@Component
public class FfmsConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(FfmsLoginAuthenticationInterceptor()).addPathPatterns("/**/**");
    }

    @Bean
    public FfmsLoginAuthenticationInterceptor FfmsLoginAuthenticationInterceptor(){
        return new FfmsLoginAuthenticationInterceptor();
    }
}
