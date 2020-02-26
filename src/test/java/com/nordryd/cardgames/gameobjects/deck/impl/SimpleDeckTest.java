package com.nordryd.cardgames.gameobjects.deck.impl;

import static com.nordryd.cardgames.gameobjects.Card.Rank;
import static com.nordryd.cardgames.gameobjects.Card.Suit;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Random;

import com.nordryd.cardgames.gameobjects.Card;
import com.nordryd.cardgames.gameobjects.deck.Deck;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * <p>
 * Unit tests for {@link SimpleDeck}.
 * </p>
 *
 * @author Nordryd
 */
@ExtendWith(MockitoExtension.class)
public class SimpleDeckTest
{
    private static final List<Rank> POSSIBLE_RANKS = asList(Rank.values());
    private static final List<Suit> POSSIBLE_SUITS = asList(Suit.values());

    @Mock
    private Random mockRng;

    public Deck deck;

    @Test
    public void testDraw() {
        final int numberToGen = 2;
        setRandomIntToGenerate(numberToGen);
        deck = new SimpleDeck(mockRng);
        assertEquals(Card.get(Rank.values()[numberToGen]).of(Suit.values()[numberToGen]), deck.draw());
    }

    @Test
    public void testDrawMultiple() {
        setRandomIntToGenerate(0);
        deck = new SimpleDeck(mockRng);
        deck.draw(16).forEach(card -> assertTrue(isCardValid(card)));
    }

    @Test
    public void testDrawMultipleZero() {
        assertEquals("Cannot draw 0 cards.",
                assertThrows(IllegalArgumentException.class, () -> new SimpleDeck(mockRng).draw(0),
                        "Cannot draw a negative number of cards.").getMessage());
    }

    @Test
    public void testDrawMultipleNegative() {
        assertEquals("Cannot draw a negative number of cards.",
                assertThrows(IllegalArgumentException.class, () -> new SimpleDeck(mockRng).draw(-1),
                        "Cannot draw a negative number of cards.").getMessage());
    }

    private boolean isCardValid(final Card card) {
        return POSSIBLE_RANKS.contains(card.getRank()) && POSSIBLE_SUITS.contains(card.getSuit());
    }

    private void setRandomIntToGenerate(final int numberToGen) {
        if (numberToGen > 0) {
            when(mockRng.nextInt(intThat(integer -> integer > 0))).thenReturn(numberToGen);
        }
        else {
            when(mockRng.nextInt(intThat(integer -> integer > 0))).thenCallRealMethod();
        }
    }
}
