package com.example.EnjoyEat.Security.OAuth2.User;

import com.example.EnjoyEat.Exception.OAuth2AuthenticationProcessingException;
import com.example.EnjoyEat.Model.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.kakao.name())) {
            return new KakaoOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
