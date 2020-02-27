package com.nordryd.cardgames;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main class for Card Games Application.
 *
 * @author Nordryd
 */
@SpringBootApplication
public class Main
{
    public static final String CORE_ENDPOINT = "/api/cardgames/";

    /**
     * Main method.
     *
     * @param args command line arguments.
     */
    public static void main(final String... args) {
        final ApplicationContext config = new AnnotationConfigApplicationContext(AppConfig.class);
        run(Main.class, args);
    }
}
