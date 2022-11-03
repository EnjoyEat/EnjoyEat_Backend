package com.example.EnjoyEat.Security.OAuth2.User;

import java.util.Map;

// 각 소셜마다 받아오는 데이터의 JSON 형태가 다르므로 기본적으로 필요한 정보를 추상화하고 상속받아 구현
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
