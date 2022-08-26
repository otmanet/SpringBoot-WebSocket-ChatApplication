package com.example.AppChats.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* TODO Auto-generated method stub */
        PasswordEncoder passEncoder = passwordEncoder();
        System.out.println("**************************");
        System.out.println(passEncoder.encode("1234"));
        System.out.println("*************************");

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select email as princibal , password  as Credentials,'true' as enabled"
                        + " from users where email=?")
                .authoritiesByUsernameQuery("select email as princibal , role as role  "
                        + "from users_roles where email=?")
                .passwordEncoder(passEncoder)
                .rolePrefix("ROLE_");
        // auth.inMemoryAuthentication().withUser("admin").password(passenEncoder().encode("1234")).roles("USER",
        // "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login","/signup").anonymous()
                .antMatchers("/").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .and()
                .csrf().disable()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .logout().clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");
    }
}
