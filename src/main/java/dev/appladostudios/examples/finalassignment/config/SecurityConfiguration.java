package dev.appladostudios.examples.finalassignment.config;

import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
public class SecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {
    @Autowired
    public void configureGlobal(
            AuthenticationManagerBuilder auth) throws Exception {

        KeycloakAuthenticationProvider keycloakAuthenticationProvider
                = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(
                new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }


    //protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //    return http.
    //            authorizeRequests()
    //            .antMatchers(HttpMethod.GET, "/**/actuator/health").permitAll()
    //            .antMatchers(HttpMethod.GET, "/**/actuator/metrics").authenticated()
    //            .antMatchers(HttpMethod.GET, "/**/actuator/loggers").authenticated()
    //            .antMatchers("/actuator/**").denyAll()
    //            //.and()
    //            //    .csrf().disable()
    //            .and()
    //            .build();
    //}

    @Override
    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(
                new SessionRegistryImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.
                authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**/actuator/health").permitAll()
                .antMatchers(HttpMethod.GET, "/**/actuator/metrics").authenticated()
                .antMatchers(HttpMethod.GET, "/**/actuator/loggers").authenticated()
                .antMatchers("/actuator/**").denyAll()
                .antMatchers("/order/**").authenticated()
                .antMatchers("/product/**").authenticated()
                .and()
                    .csrf().disable();

    }
}
