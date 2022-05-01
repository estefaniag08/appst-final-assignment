package dev.appladostudios.examples.finalassignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**/actuator/health").permitAll()
                .antMatchers(HttpMethod.GET, "/**/actuator/metrics").authenticated()
                .antMatchers(HttpMethod.GET, "/**/actuator/loggers").authenticated()
                .antMatchers("/actuator/**").denyAll()
                //.and()
                //    .csrf().disable()
                .and()
                .build();
    }
}
