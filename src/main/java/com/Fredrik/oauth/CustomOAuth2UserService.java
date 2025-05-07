package com.Fredrik.oauth;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final WebClient webClient = WebClient.create();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = new HashMap<>(oauth2User.getAttributes());

        if ("github".equals(registrationId)) {
            String token = userRequest.getAccessToken().getTokenValue();

            // Fetch primary verified email
            List<Map<String, Object>> emails = webClient.get()
                    .uri("https://api.github.com/user/emails")
                    .headers(headers -> headers.setBearerAuth(token))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                    .block();

            String email = emails.stream()
                    .filter(e -> Boolean.TRUE.equals(e.get("primary")) && Boolean.TRUE.equals(e.get("verified")))
                    .map(e -> (String) e.get("email"))
                    .findFirst()
                    .orElse(null);

            //String name = (String) attributes.getOrDefault("name", attributes.get("login"));
            String name = Optional.ofNullable(attributes.get("name"))
                    .map(Object::toString)
                    .orElse((String) attributes.get("login"));
            String photo = (String) attributes.get("avatar_url");

            attributes.put("name", name);
            attributes.put("email", email);
            attributes.put("photo", photo);
        } else if ("google".equals(registrationId)) {
            attributes.put("photo", attributes.get("picture"));
        }

        return new DefaultOAuth2User(
                oauth2User.getAuthorities(),
                attributes,
                "name"
        );
    }
}
