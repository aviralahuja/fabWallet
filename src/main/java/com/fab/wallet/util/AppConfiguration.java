package com.fab.wallet.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class AppConfiguration {
    private TokenUtil tokenUtil;

    @Autowired
    public AppConfiguration(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    @Bean
    public WebMvcConfigurer getWebMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }

    @Bean
    public FilterRegistrationBean authenticationFilter(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                    String token=request.getHeader(HttpHeaders.AUTHORIZATION);
                    if(token==null || tokenUtil.validateToken(token)==null){
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                    filterChain.doFilter(request,response);
            }
        });
        filterRegistrationBean.addUrlPatterns("/api/*");
        return filterRegistrationBean;
    }

}
