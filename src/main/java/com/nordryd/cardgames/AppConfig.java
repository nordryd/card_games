package com.nordryd.cardgames;

import java.util.Random;

import com.nordryd.cardgames.gameobjects.deck.Deck;
import com.nordryd.cardgames.gameobjects.deck.impl.StandardDeck;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Configuration for the Card Games application.
 * </p>
 *
 * @author Nordryd
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class AppConfig
{
    @Bean
    public Deck getDeck() {
        return new StandardDeck(getRNG());
    }

    @Bean
    public Random getRNG() {
        return new Random();
    }
}
