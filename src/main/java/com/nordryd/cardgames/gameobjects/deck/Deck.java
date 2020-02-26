package com.nordryd.cardgames.gameobjects.deck;

import java.util.List;

import com.nordryd.cardgames.gameobjects.Card;

/**
 * <p>
 * Represents a deck of playing cards.
 * </p>
 *
 * @author Nordryd
 */
public interface Deck
{
    /**
     * @return a single {@link Card].
     */
    Card draw();

    /**
     * Draw multiple {@link Card cards}.
     *
     * @param amount the number of cards to draw.
     * @return a list of {@link Card cards}.
     */
    List<Card> draw(final int amount);

    /**
     * @return the remaining drawable cards in the {@link Deck deck}.
     */
    int remainingCards();
}
