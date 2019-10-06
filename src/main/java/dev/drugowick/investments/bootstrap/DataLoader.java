package dev.drugowick.investments.bootstrap;

import dev.drugowick.investments.configuration.ScheduledTasks;
import dev.drugowick.investments.services.CustomUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This is a Data Loader, responsible for creating new data on the database.
 */
@Component
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private final CustomUserService userService;

    public DataLoader(CustomUserService userService) {
        this.userService = userService;
    }

    public void loadData() {

        cleanUpDatabase();

        // Fake Users
//        UserDTO user = new UserDTO(
//                "user",
//                "user",
//                true,
//                "User",
//                "user@gmail.com",
//                "A simple user bio."
//        );
//        userService.save(user);
//        log.info("Created user " + user);
//
//        UserDTO admin = new UserDTO(
//                "admin",
//                "admin",
//                true,
//                "Admin",
//                "admin@gmail.com",
//                "A simple admin bio."
//        );
//        userService.save(admin);
//        log.info("Created user " + admin);
    }

    /**
     * Cleans up main entities. Cascade takes care of deleting other entities.
     */
    private void cleanUpDatabase() {
        log.warn("Cleaning up database (main entities).");
        //TODO
    }

}
