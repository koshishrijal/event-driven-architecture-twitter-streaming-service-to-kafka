package com.kosh.eventDriven.config.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityCOnfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/actuator/**")
                .antMatchers("/encrypt/**")
                .antMatchers("/decrypt/**");
        super.configure(web);
    }
}
