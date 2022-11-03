package com.example.EnjoyEat.Config;

import com.example.EnjoyEat.Security.OAuth2.UserOAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity// 해당 파일로 시큐리티를 활성화 -> 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
@Configuration // IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserOAuth2Service userOAuth2Service;

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
                .oauth2Login()
                .defaultSuccessUrl("/") // oauth2 인증이 성공했을 때, 이동되는 url을 설정.
                // .successHandler(oAuth2AuthenticationSuccessHandler) // 인증 프로세스에 따라 사용자 정의 로직을 실행.
                .userInfoEndpoint()
                .userService(userOAuth2Service); // 로그인이 성공하면 해당 유저의 정보를 들고 customOAuth2UserService에서 후처리.
    }
}
