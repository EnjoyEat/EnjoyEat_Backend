package com.example.EnjoyEat.Config;


import com.example.EnjoyEat.Repository.UserRepository;
import com.example.EnjoyEat.Security.CustomUserDetailsService;
import com.example.EnjoyEat.Security.JWT.JwtBasicAuthenticationFilter;
import com.example.EnjoyEat.Security.JWT.JwtCommonAuthorizationFilter;
import com.example.EnjoyEat.Security.JWT.JwtTokenProvider;
import com.example.EnjoyEat.Security.OAuth2.CustomOAuth2UserService;
import com.example.EnjoyEat.Security.Provider.CustomOAuth2Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@EnableWebSecurity// 해당 파일로 시큐리티를 활성화 -> 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
@Configuration // IoC
@PropertySource("classpath:application.yml")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private Environment env;

    private static String CLIENT_PROPERTY_KEY= "spring.security.oauth2.client.registration.";
    private static List<String> clients = Arrays.asList("kakao");

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = clients.stream()
                .map(c -> getRegistration(c))
                .filter(registration -> registration != null)
                .collect(Collectors.toList());
        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(String client){
        // API Client Id 불러오기
        String clientId = env.getProperty(
                CLIENT_PROPERTY_KEY + client + ".client-id");

        // API Client Id 값이 존재하는지 확인하기
        if (clientId == null) {
            return null;
        }

        // API Client Secret 불러오기
        String clientSecret = env.getProperty(
                CLIENT_PROPERTY_KEY + client + ".client-secret");

        if (client.equals("kakao")) {
            return CustomOAuth2Provider.KAKAO.getBuilder(client)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .build();
        }

        return null;
    }


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //csrf 토큰
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.httpBasic().disable();

        http.addFilter(new JwtBasicAuthenticationFilter());
        http.addFilter(new JwtCommonAuthorizationFilter(authenticationManager(), tokenProvider, userRepository));


        http.authorizeRequests()
                .antMatchers("/auth/**").authenticated()
                .anyRequest().permitAll() // 위의 요청이 아닌 모든 요청은 허용
                .and()
                .oauth2Login()
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(authorizedClientService())
                .userInfoEndpoint()
                .userService(customOAuth2UserService) // 로그인이 성공하면 해당 유저의 정보를 들고 customOAuth2UserService에서 후처리.
                .and()
                .successHandler(new AuthenticationSuccessHandler() {

                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        String token = tokenProvider.create(authentication);
                        response.addHeader("Authorization", "Bearer " +  token);
                        String targetUrl = "/auth/success";
                        RequestDispatcher dis = request.getRequestDispatcher(targetUrl);
                        dis.forward(request, response);
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                        AuthenticationException exception) throws IOException, ServletException {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                    }
                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(encodePWD());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties properties,
//                                                                     @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId) {
//        List<ClientRegistration> registrations = properties.getRegistration().keySet().stream()
//                .map(client -> getRegistration(properties, client))
//                .filter(Objects::nonNull).collect(Collectors.toList());
//
//        // 카카오 OAuth2 정보 추가
//        registrations.add(
//                CustomOAuth2Provider.KAKAO.getBuilder("kakao")
//                        .clientId(kakaoClientId)
//                        .clientSecret("tmp")
//                        .jwkSetUri("tmp")
//                        .build()
//        );
//
//        return new InMemoryClientRegistrationRepository(registrations);
//    }
//
//    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String provider) {
//        if("google".equals(provider)) {
//            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration()
//                    .get("google");
//
//            return CommonOAuth2Provider.GOOGLE.getBuilder(provider)
//                    .clientId(registration.getClientId())
//                    .clientSecret(registration.getClientSecret())
//                    .scope("email", "profile")
//                    .build();
//        }
//
//        if("facebook".equals(provider)) {
//            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration()
//                    .get("facebook");
//
//            return CommonOAuth2Provider.FACEBOOK.getBuilder(provider)
//                    .clientId(registration.getClientId())
//                    .clientSecret(registration.getClientSecret())
//                    .userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
//                    .scope("email")
//                    .build();
//        }
//        return null;
//
//    }

}
