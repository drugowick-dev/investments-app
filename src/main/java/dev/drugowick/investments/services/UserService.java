package dev.drugowick.investments.services;

import dev.drugowick.investments.domain.security.User;
import dev.drugowick.investments.repositories.UserRepository;
import dev.drugowick.investments.services.dto.UserDTO;
import dev.drugowick.investments.services.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Save a User
     *
     * @param userDto the entity to save.
     * @return the saved entity.
     */
    public UserDTO save(UserDTO userDto) {
        log.debug("Request to save User : {}", userDto);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                userDto.getUsername(),
                userDto.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
        /**
         * JPA repositories handles the creation or update through the `save` method,
         * but the UserDetailsManager does not.
         */
        if (userDetailsManager.userExists(userDetails.getUsername()))
            userDetailsManager.updateUser(userDetails);
        else
            userDetailsManager.createUser(userDetails);

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
        log.debug("Request to get all Users");
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    /**
     * Find one specific User
     *
     * @param username the id of the User to find
     * @return the User DTO if found
     */
    @Transactional(readOnly = true)
    public Optional<UserDTO> findOne(String username) {
        log.debug("Request to get User : {}", username);
        return userRepository.findById(username)
                .map(userMapper::toDto);
    }

    /**
     * Delete User by id
     *
     * @param username the id of the User to delete
     */
    public void delete(String username) {
        log.debug("Request to delete User : {}", username);
        userRepository.deleteById(username);
    }

}
