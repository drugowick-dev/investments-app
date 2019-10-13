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
import java.util.Optional;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private static final String GITHUB_CLIENTID = "github";
    private static final String GOOGLE_CLIENTID = "google";
    private static final Logger log = LoggerFactory.getLogger(SpringSecurityConfig.class);
    private final UserService userService;

    public CustomSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        String clientId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        OAuth2User oAuthUser = (OAuth2User) authentication.getPrincipal();

        log.info("Successfully authenticated via OAuth with provider " + clientId);

        String userEmail = getUserEmail(clientId, oAuthUser);
        Optional<UserDTO> optionalUserDTO = userService.findOne(userEmail);
        if (!optionalUserDTO.isPresent()) {
            log.info("Creating new user " + userEmail);
            userService.save(newUserDTO(
                    clientId,
                    oAuthUser));
        } else {
            /**
             * Provider information gets overwritten based on user email.
             * TODO provide a better implementation, linking the User account with several providers.
             */
            log.info("Updating user provider information " + userEmail);
            UserDTO userDTOToUpdate = optionalUserDTO.get();
            userDTOToUpdate.setProvider(clientId);
            userDTOToUpdate.setProviderId(oAuthUser.getName());

            userService.save(userDTOToUpdate);
        }

        /**
         * https://docs.spring.io/spring-security/site/docs/5.1.6.RELEASE/api/org/springframework/security/web/authentication/AuthenticationSuccessHandler.html
         */
        httpServletResponse.sendRedirect("/");
    }

    private String getUserEmail(String clientId, OAuth2User user) {
        String userEmail = null;
        if (clientId.equals(GOOGLE_CLIENTID) || clientId.equals(GITHUB_CLIENTID)) {
            userEmail = user.getAttributes().get("email").toString();
        }

        return userEmail;
    }

    private UserDTO newUserDTO(String clientId, OAuth2User user) {
        UserDTO userDTO = null;
        if (clientId.equals(GOOGLE_CLIENTID) || clientId.equals(GITHUB_CLIENTID)) {
            userDTO = new UserDTO().builder()
                    .email(user.getAttributes().get("email").toString())
                    .bio(user.getAttributes().getOrDefault("bio", "").toString())
                    .enabled(true)
                    .fullName(user.getAttributes().get("name").toString())
                    .provider(clientId)
                    .providerId(user.getName())
                    .build();
        }

        return userDTO;
    }
}
