package com.nordryd.cardgames.gameobjects;

import static java.lang.String.format;
import static java.util.Objects.hash;

/**
 * <p>
 * Represents a single playing card from a standard 52 card deck.
 * </p>
 *
 * @author Nordryd
 */
public class Card
{
    private final Rank rank;
    private final Suit suit;

    private Card(final Rank rank, final Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * @return this {@link Card card's} {@link Rank}.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * @return this {@link Card card's} {@link Suit}.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Pits the current {@link Card} against another in glorious combat.
     *
     * @param opponent the opposing {@link Card} to battle.
     * @param isAceHigh {@code true} if aces are <i>HIGH</i> for this battle.
     * @return the {@link BattleResult resolution} of the battle (win, lose, or tie).
     */
    public BattleResult battle(final Card opponent, final boolean isAceHigh) {
        if (this.equals(opponent)) {
            return BattleResult.TIE;
        }

        final boolean ranksEqual = rank.equals(opponent.rank);
        if (ranksEqual && (!suit.equals(opponent.suit) && suit.beats(opponent.suit))) {
            return BattleResult.WIN;
        }

        final boolean rankWins = rank.beats(opponent.rank, isAceHigh);
        if (!ranksEqual && !rankWins) {
            return BattleResult.LOSE;
        }

        return (suit.equals(opponent.suit) && rankWins) ?
                BattleResult.WIN :
                rankWins ? BattleResult.WIN : BattleResult.LOSE;
    }

    /**
     * Pits the current {@link Card} against another in glorious combat. <b>Aces will be treated as <i>HIGH</i></b>.
     *
     * @param opponent the opposing {@link Card} to battle.
     * @return the {@link BattleResult result of the battle} (win, lose, or tie).
     */
    public BattleResult battle(final Card opponent) {
        return battle(opponent, true);
    }

    @Override
    public String toString() {
        return format("%s of %s", rank, suit);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if ((other == null) || (getClass() != other.getClass())) {
            return false;
        }
        final Card card = (Card) other;
        return (rank == card.rank) && (suit == card.suit);
    }

    @Override
    public int hashCode() {
        return hash(rank, suit);
    }

    /**
     * Gets a card, but will not have a suit. To finish creating a card, chain a call to {@link Builder#of(Suit)}.
     *
     * @param rank the desired card {@link Rank rank (number)}.
     */
    public static Builder get(final Rank rank) {
        return new Builder(rank);
    }

    /**
     * Represents a card rank (number).
     */
    public enum Rank
    {
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(11),
        QUEEN(12),
        KING(13),
        /**
         * This can be high or low, depending on what the game state requires.
         */
        ACE(14);

        private int strength;

        Rank(final int strength) {
            this.strength = strength;
        }

        /**
         * @return the strength of the current {@link Rank}.
         */
        public int getStrength() {
            return strength;
        }

        private boolean beats(final Rank opponent, final boolean isAceHigh) {
            return ((!isAceHigh && ACE.equals(this)) ? 1 : strength) >
                    ((!isAceHigh && ACE.equals(opponent)) ? 1 : opponent.strength);
        }
    }

    /**
     * Represents a card suit.
     */
    public enum Suit
    {
        CLUBS(0),
        DIAMONDS(1),
        HEARTS(2),
        SPADES(3);

        private final int strength;

        Suit(final int strength) {
            this.strength = strength;
        }

        private boolean beats(final Suit opponent) {
            return strength > opponent.strength;
        }
    }

    /**
     * The possible outcomes of a {@link Card} battle.
     */
    public enum BattleResult
    {
        /**
         * If the attacking {@link Card} won.
         */
        WIN,
        /**
         * If the attacking {@link Card} lost.
         */
        LOSE,
        /**
         * If the battle resulted in a tie.
         */
        TIE;
    }

    /**
     * Builder class for {@link Card}.
     */
    public static final class Builder
    {
        private final Rank rank;

        private Builder(final Rank rank) {
            this.rank = rank;
        }

        /**
         * Completes the creation of a {@link Card} by setting a {@link Suit card suit}.
         *
         * @param suit the desired {@link Suit card suit}.
         * @return a {@link Card} with the set {@link Rank} and {@link Suit}.
         */
        public Card of(final Suit suit) {
            return new Card(rank, suit);
        }
    }
}
