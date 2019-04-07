package id.bnv.jupiter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ComponentScan("id.bnv.jupiter.security")
public class Security extends WebSecurityConfigurerAdapter {

    private final AuthProvider provider;

    @Autowired
    public Security(AuthProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();

//        http.formLogin()
//                .loginProcessingUrl("/j_security")
//                .usernameParameter("j_username")
//                .passwordParameter("j_password");

//        http.antMatcher("/v1/user").

        http.anonymous().and().csrf().disable();
//        http//.authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and().httpBasic();
    }
}
