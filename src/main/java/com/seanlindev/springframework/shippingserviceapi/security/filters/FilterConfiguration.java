package com.seanlindev.springframework.shippingserviceapi.security.filters;

import com.seanlindev.springframework.shippingserviceapi.services.AccessSecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {
    @Bean
    @Autowired
    public FilterRegistrationBean apiAccessAuthorizationFilter(AccessSecretService accessSecretService) {
        FilterRegistrationBean<ApiAuthorizationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        ApiAuthorizationFilter apiAuthorizationFilter = new ApiAuthorizationFilter();
        apiAuthorizationFilter.setAccessSecretService(accessSecretService);
        filterRegistrationBean.setFilter(apiAuthorizationFilter);
        filterRegistrationBean.addUrlPatterns("/api/*");
        filterRegistrationBean.setName("apiAccessAuthorizationFilter");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean jwtAuthorizationFilter() {
        FilterRegistrationBean<JWTAuthorizationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new JWTAuthorizationFilter());
        filterRegistrationBean.addUrlPatterns("/service/*", "/internal/*");
        filterRegistrationBean.setName("jwtAuthorizationFilter");
        return filterRegistrationBean;
    }
}
