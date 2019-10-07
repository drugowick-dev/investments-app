package dev.drugowick.investments.services;

import dev.drugowick.investments.domain.security.User;
import dev.drugowick.investments.repositories.UserRepository;
import dev.drugowick.investments.services.dto.UserDTO;
import dev.drugowick.investments.services.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CustomUserService implements OAuth2UserService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CustomUserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Save a User
     *
     * @param userDto the entity to save.
     * @return the saved entity.
     */
    public UserDTO save(UserDTO userDto) {
        log.debug("Request to save User : {}", userDto);

        /**
         * JPA repositories handles the creation or update through the `save` method,
         * but the UserDetailsManager does not.
         */

        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * Get all Users
     *
     * @param pageable the pagination information
     * @return the list of UsersDTO
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
//        log.debug("Request to get all Users");
//        return userRepository.findAll(pageable)
//                .map(userMapper::toDto);
        return Page.empty();
    }

    /**
     * Find one specific User
     *
     * @param username the id of the User to find
     * @return the User DTO if found
     */
    @Transactional(readOnly = true)
    public Optional<UserDTO> findOne(String username) {
        return Optional.empty();
//        log.debug("Request to get User : {}", username);
//        return userRepository.findById(username)
//                .map(userMapper::toDto);
    }

    /**
     * Delete User by id
     *
     * @param username the id of the User to delete
     */
    public void delete(String username) {
//        log.debug("Request to delete User : {}", username);
//        userRepository.deleteById(username);
    }

    /**
     * For now this CustomUserService uses the DefaultUserService to provide the OAuthUser.
     * <p>
     * This is an entry point to customize the UserService when logging via OAuth2/OIDC.
     *
     * @param userRequest
     * @return an OAuth2User
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService defaultUserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = defaultUserService.loadUser(userRequest);
        this.save(this.toDto(oAuth2User, userRequest.getClientRegistration().getClientName()));
        return oAuth2User;
    }

    private UserDTO toDto(OAuth2User oAuth2User, String clientId) {
        UserDTO userDTO = new UserDTO();
        if (clientId.equals("GitHub")) {
            userDTO.setBio((oAuth2User.getAttributes().get("bio").toString()));
            userDTO.setEmail(oAuth2User.getAttributes().get("email").toString());
            userDTO.setEnabled(true);
            userDTO.setFullName(oAuth2User.getAttributes().get("name").toString());
//        } else if (clientId.equals("Google")) {
//            userDTO.setBio((oAuth2User.getAttributes().get("bio").toString()));
//            userDTO.setEmail(oAuth2User.getAttributes().get("email").toString());
//            userDTO.setEnabled(true);
//            userDTO.setFullName(oAuth2User.getAttributes().get("name").toString());
        }
        return userDTO;
    }
}
