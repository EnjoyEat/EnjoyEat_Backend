package com.example.EnjoyEat.Security.OAuth2;

import com.example.EnjoyEat.DTO.UserDTO;
import com.example.EnjoyEat.Model.User;
import com.example.EnjoyEat.Repository.UserRepository;
import com.example.EnjoyEat.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserOAuth2Service extends DefaultOAuth2UserService {

    // DefaultOAuth2UserService를 상속한 UserOAuth2Service 클래스 구현.
    // OAuth2UserService 클래스는 사용자의 정보들을 기반으로 가입 및 정보수정, 세션 저장등의 기능을 지원
    // DefaultOAuth2UserService 클래스 안의 loadUser 메서드를 호출 -> 회원가입 작업 실행
    // loadUser(OAuth2UserRequest oAuth2UserRequest) 메서드는 사용자 정보를 요청할 수 있는 access token 을 얻고나서 실행된다.
    // access token을 이용해 서드파티 서버로부터 사용자 정보를 받아온다.
    // 해당 사용자가 회원가입되어있는지 아닌지 확인한다.
    // UserPrincipal 을 return 한다. 세션 방식에서는 여기서 return한 객체가 시큐리티 세션에 저장된다.
    // 하지만 JWT 방식에서는 저장하지 않는다. (JWT 방식에서는 인증&인가 수행시 HttpSession을 사용하지 않을 것이다.)

    private final HttpSession httpSession;

    UserDTO userDTO = new UserDTO();

    private final UserService userService;

    private final UserRepository userRepository;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // access token을 이용해 서버로부터 사용자 정보를 받아온다.
        // DefaultOAuth2UserService 클래스의 loadUser() 메서드에 이 기능이 구현되어있기 때문에 super.loadUser() 를 호출하기만하면 된다.

         /*
        System.out.println(attributes);
            {
                "id":123456789,
                "connected_at": "2022-04-11T01:45:28Z",
                "kakao_account": {
                    // 프로필 또는 닉네임 동의 항목 필요
                    "profile_nickname_needs_agreement	": false,
                    // 프로필 또는 프로필 사진 동의 항목 필요
                    "profile_image_needs_agreement	": false,
                    "profile": {
                        // 프로필 또는 닉네임 동의 항목 필요
                        "nickname": "홍길동",
                        // 프로필 또는 프로필 사진 동의 항목 필요
                        "thumbnail_image_url": "http://yyy.kakao.com/.../img_110x110.jpg",
                        "profile_image_url": "http://yyy.kakao.com/dn/.../img_640x640.jpg",
                        "is_default_image":false
                    },
                    // 이름 동의 항목 필요
                    "name_needs_agreement":false,
                    "name":"홍길동",
                    // 카카오계정(이메일) 동의 항목 필요
                    "email_needs_agreement":false,
                    "is_email_valid": true,
                    "is_email_verified": true,
                    "email": "sample@sample.com",
                    // 연령대 동의 항목 필요
                    "age_range_needs_agreement":false,
                    "age_range":"20~29",
                    // 출생 연도 동의 항목 필요
                    "birthyear_needs_agreement": false,
                    "birthyear": "2002",
                    // 생일 동의 항목 필요
                    "birthday_needs_agreement":false,
                    "birthday":"1130",
                    "birthday_type":"SOLAR",
                    // 성별 동의 항목 필요
                    "gender_needs_agreement":false,
                    "gender":"female",
                    // 카카오계정(전화번호) 동의 항목 필요
                    "phone_number_needs_agreement": false,
                    "phone_number": "+82 010-1234-5678",
                    // CI(연계정보) 동의 항목 필요
                    "ci_needs_agreement": false,
                    "ci": "${CI}",
                    "ci_authenticated_at": "2019-03-11T11:25:22Z",
                },
                "properties":{
                    "${CUSTOM_PROPERTY_KEY}": "${CUSTOM_PROPERTY_VALUE}",
                    ...
                }
            }
        */


        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println(attributes);
        String providerId = String.valueOf(attributes.get("id")) ;
        System.out.println(attributes);



        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        System.out.println(attributes);
        String email = String.valueOf(kakao_account.get("email"));
        System.out.println("email은 " + email);


        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");
        System.out.println(profile);
        String username = String.valueOf(profile.get("nickname"));
        System.out.println("username은 "+username);
        String profileImage = String.valueOf(profile.get("profile_image_url"));
        System.out.println("프로필이미지는 "+profileImage);

        String nickname = username;
        System.out.println("=임시 nickname은 "+nickname);
        String intro = "자기소개 적어주세요.";
        System.out.println("임시 intro는 "+intro);

        User kakaoUser = userRepository.findByEmail(email).orElse(null);


        if (kakaoUser == null) {
            userDTO.setProfileImage(profileImage);
            userDTO.setNickname(nickname);
            userDTO.setProviderId(providerId);
            userDTO.setIntro(intro);
            userDTO.setEmail(email);
            userDTO.setUsername(username);
            System.out.println(userDTO);
            userService.signUp(userDTO);
            System.out.println("카카오로 가입~");
        } else {
            System.out.println("가입한적 있음.");
        }

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER")), attributes, "id");
    }
}
