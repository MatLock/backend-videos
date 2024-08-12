package com.backend.vids.config;

import com.backend.vids.filter.AuthExistenceHeaderFilter;
import com.backend.vids.filter.AuthValidationFilter;
import com.backend.vids.security.CustomUserDetailsService;
import com.backend.vids.security.JWTService;
import com.backend.vids.security.UserValidatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {


  @Bean
  public FilterRegistrationBean<AuthExistenceHeaderFilter> createAuthExistenceFilter(ObjectMapper objectMapper, JWTService jwtService){
    FilterRegistrationBean<AuthExistenceHeaderFilter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(new AuthExistenceHeaderFilter(objectMapper, jwtService));
    registrationBean.addUrlPatterns("/users/*","/folders/*","/videos/*");
    registrationBean.setOrder(0);
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean<AuthValidationFilter> createAuthFilter(
      ObjectMapper objectMapper,
      JWTService jwtService,
      CustomUserDetailsService customUserDetailsService,
      UserValidatorService userValidatorService){
    FilterRegistrationBean<AuthValidationFilter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(new AuthValidationFilter(objectMapper, jwtService, customUserDetailsService, userValidatorService));
    registrationBean.addUrlPatterns("/users/*","/folders/*","/videos/*");
    registrationBean.setOrder(1);
    return registrationBean;
  }


}
