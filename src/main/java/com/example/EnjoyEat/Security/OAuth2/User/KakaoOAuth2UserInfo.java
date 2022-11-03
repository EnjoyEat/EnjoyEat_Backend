package com.example.EnjoyEat.Security.OAuth2.User;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super((Map<String, Object>) attributes.get("kakao_account"));
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        return (String) ((Map<String, Object>) attributes.get("profile")).get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) ((Map<String, Object>) attributes.get("profile")).get("thumbnail_image_url");
    }
}
