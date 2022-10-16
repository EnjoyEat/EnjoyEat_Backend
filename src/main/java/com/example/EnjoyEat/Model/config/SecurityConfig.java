package com.example.EnjoyEat.Model.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity// 해당 파일로 시큐리티를 활성화 -> 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
@Configuration // IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/auth/**").authenticated() // 인증필요
                .anyRequest().permitAll() // 위의 요청이 아닌 모든 요청은 허용
                .and()
                .formLogin()
                .loginProcessingUrl("auth/signin")
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login();
    }
}
