package com.nordryd.cardgames.gameobjects.deck.impl;

import static com.nordryd.cardgames.gameobjects.Card.Rank;
import static com.nordryd.cardgames.gameobjects.Card.Suit;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Random;

import com.nordryd.cardgames.gameobjects.Card;
import com.nordryd.cardgames.gameobjects.deck.Deck;
import com.nordryd.cardgames.gameobjects.deck.impl.SimpleDeck;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * <p>
 * Unit tests for {@link SimpleDeck}.
 * </p>
 *
 * @author Nordryd
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleDeckTest
{
    private static final List<Rank> POSSIBLE_RANKS = asList(Rank.values());
    private static final List<Suit> POSSIBLE_SUITS = asList(Suit.values());

    private static final ArgumentMatcher<Integer> isPositive = value -> value > 0;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Mock
    private Random mockRng;

    public Deck deck;

    @Before
    public void setup() {
        setRandomIntToGenerate(0);
    }

    @Test
    public void testDraw() {
        final int numberToGen = 2;
        setRandomIntToGenerate(numberToGen);
        deck = new SimpleDeck(mockRng);
        assertThat(deck.draw(), is(Card.get(Rank.values()[numberToGen]).of(Suit.values()[numberToGen])));
    }

    @Test
    public void testDrawMultiple() {
        deck = new SimpleDeck(mockRng);
        deck.draw(16).forEach(card -> assertThat(isCardValid(card), is(true)));
    }

    @Test
    public void testDrawMultipleZero() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot draw 0 cards.");
        new SimpleDeck(mockRng).draw(0);
    }

    @Test
    public void testDrawMultipleNegative() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot draw a negative number of cards.");
        new SimpleDeck(mockRng).draw(-1);
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
