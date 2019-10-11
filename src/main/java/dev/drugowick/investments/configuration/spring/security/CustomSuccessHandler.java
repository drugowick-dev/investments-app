package dev.drugowick.investments.configuration.spring.security;

import dev.drugowick.investments.services.UserService;
import dev.drugowick.investments.services.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    public static final String GITHUB_CLIENTID = "github";
    public static final String GOOGLE_CLIENTID = "google";
    private static final Logger log = LoggerFactory.getLogger(SpringSecurityConfig.class);
    private final UserService userService;

    public CustomSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.debug("Successfully authenticated via OAuth with provider ");
        String clientId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        userService.save(getUserDTO(clientId, user));
    }

    private UserDTO getUserDTO(String clientId, OAuth2User user) {
        UserDTO userDTO = null;
        if (clientId.equals(GOOGLE_CLIENTID)) {
            userDTO = new UserDTO().builder()
                    .email(user.getAttributes().get("email").toString())
                    .bio(user.getAttributes().getOrDefault("bio", "").toString())
                    .enabled(true)
                    .fullName(user.getAttributes().get("name").toString())
                    .provider(GOOGLE_CLIENTID)
                    .providerId(user.getName())
                    .build();
        } else if (clientId.equals(GITHUB_CLIENTID)) {
            userDTO = new UserDTO().builder()
                    .email(user.getAttributes().get("email").toString())
                    .bio(user.getAttributes().getOrDefault("bio", "").toString())
                    .enabled(true)
                    .fullName(user.getAttributes().get("name").toString())
                    .provider(GITHUB_CLIENTID)
                    .providerId(user.getName())
                    .build();
        }
        return userDTO;
    }
}
