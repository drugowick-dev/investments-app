package dev.drugowick.investments.bootstrap;

import dev.drugowick.investments.configuration.ScheduledTasks;
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

    public DataLoader(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
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

    }

    /**
     * Cleans up main entities. Cascade takes care of deleting other entities.
     */
    private void cleanUpDatabase() {
        log.warn("Cleaning up database (main entities).");
        //TODO
    }

}
