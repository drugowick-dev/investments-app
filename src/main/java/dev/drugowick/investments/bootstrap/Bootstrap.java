package dev.drugowick.investments.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This class extends CommandLineRunner, which Spring runs just after loading everything
 * and before running the application custom code.
 *
 * This is a bootstrap class to create example data for development and test purposes.
 */
@Component
public class Bootstrap implements CommandLineRunner {

    private final DataLoader dataLoader;

    public Bootstrap(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    @Value("${dev.drugowick.investments.devmode:#{'0'}}")
    private String devMode;

    @Value("${dev.drugowick.investments.devmode.password:#{'xibanga'}}")
    private String devPass;

    @Override
    public void run(String... args) throws Exception {

        if (devMode.equals("1") && devPass.equals("aiowas")) {
            log.info("DEVMODE: Sample data will be created (Initialization).");
            dataLoader.loadData();
        }
    }

}
