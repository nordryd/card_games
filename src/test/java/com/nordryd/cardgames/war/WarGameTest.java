package com.nordryd.cardgames.war;

import static com.nordryd.cardgames.gameobjects.Card.Rank;
import static com.nordryd.cardgames.gameobjects.Card.Suit;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.nordryd.cardgames.AppConfig;
import com.nordryd.cardgames.Main;
import com.nordryd.cardgames.gameobjects.Card;
import com.nordryd.cardgames.gameobjects.deck.Deck;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * <p>
 * Unit tests for {@link WarGame}.
 * </p>
 *
 * @author Nordryd
 */
@SpringBootTest(classes = { Main.class })
@ExtendWith({ SpringExtension.class, MockitoExtension.class })
@ContextConfiguration(classes = { AppConfig.class })
public class WarGameTest
{
    @MockBean
    private Deck deck;

    @Test
    public void testBuilderNoParam() {
        assertDoesNotThrow(() -> WarGame.newGame().start());
    }

    @Test
    public void testBuilderValidTurnsForVictory() {
        assertDoesNotThrow(() -> WarGame.newGame().winsRequiredForVictory(1).start());
    }

    @Test
    public void testPlayOneTurn() {
        final WarGame game = WarGame.newGame().winsRequiredForVictory(1).start();
        final Card expectedP1 = Card.get(Rank.TWO).of(Suit.CLUBS), expectedP2 = Card.get(Rank.NINE).of(Suit.HEARTS);
        final Card.BattleResult expectedResult = Card.BattleResult.LOSE;
        when(deck.draw()).thenReturn(expectedP1).thenReturn(expectedP2);
        assertEquals(new Pair<>(expectedResult, new Pair<>(expectedP1, expectedP2)), game.play());
    }

    @Test
    public void testBuilderTurnsForVictoryZero() {
        assertEquals("Must be at least 1 win required to win, otherwise there's no game to be played.",
                assertThrows(IllegalStateException.class, () -> WarGame.newGame().winsRequiredForVictory(0).start())
                        .getMessage());
    }

    @Test
    public void testBuilderTurnsForVictoryNegative() {
        assertEquals("Must be at least 1 win required to win, otherwise there's no game to be played.",
                assertThrows(IllegalStateException.class, () -> WarGame.newGame().winsRequiredForVictory(-1).start())
                        .getMessage());
    }
}
