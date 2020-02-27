package com.nordryd.cardgames.war;

import static java.lang.String.format;

import com.nordryd.cardgames.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Contains all endpoints for a game of War.
 * </p>
 * <p>
 * These endpoints start with {@code /cardgames/war}.
 * </p>
 *
 * @author Nordryd
 */
@RestController
@RequestMapping(Main.CORE_ENDPOINT + "war")
public class WarController
{
    private final WarService service;

    /**
     * Constructor.
     *
     * @param service the {@link WarService}.
     */
    @Autowired
    public WarController(final WarService service) {
        this.service = service;
    }
}
