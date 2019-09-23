package dev.drugowick.investments.bootstrap;

import dev.drugowick.investments.configuration.ScheduledTasks;
import dev.drugowick.investments.services.ProfileService;
import dev.drugowick.investments.services.dto.ProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * This is a Data Loader, responsible for creating new data on the database.
 */
@Component
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    private final ProfileService profileService;

    public DataLoader(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder, ProfileService profileService) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.profileService = profileService;
    }

    public void loadData() {

        cleanUpDatabase();

        // Fake Users
        UserDetails userDetails = new User("admin", passwordEncoder.encode("admin"), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        userDetailsManager.createUser(userDetails);
        log.info("Created user " + userDetails);
        userDetails = new User("user", passwordEncoder.encode("user"), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        userDetailsManager.createUser(userDetails);
        log.info("Created user " + userDetails);

        // Fake Profiles
        ProfileDTO administratorProfile = new ProfileDTO(
                1L,
                "Administrador",
                "administrador@investmentsapp.com",
                "I'm an administrator.",
                "admin");
        profileService.save(administratorProfile);
        log.info("Created profile " + administratorProfile);

        ProfileDTO userProfile = new ProfileDTO(
                2L,
                "User",
                "administrador@investmentsapp.com",
                "I'm an administrator.",
                "user");
        profileService.save(userProfile);
        log.info("Created profile " + userProfile);

    }

    /**
     * Cleans up main entities. Cascade takes care of deleting other entities.
     */
    private void cleanUpDatabase() {
        log.warn("Cleaning up database (main entities).");
        //TODO
    }

}
