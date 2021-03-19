package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void shuffle() {
        Deck deckA = new Deck();
        Deck deckB = new Deck();

        deckA.shuffle();
        deckB.shuffle();

        ArrayList<Card> shuffledA = new ArrayList<Card>();
        ArrayList<Card> shuffledB = new ArrayList<Card>();

        for(int i = 0; i < 52; i++) {
            shuffledA.add(deckA.getCard());
            shuffledB.add(deckB.getCard());
        }

        int counter = 0;
        for(int i = 0; i < 52; i++) {
            if(shuffledA.get(i).getCardValue() == shuffledB.get(i).getCardValue()
            && shuffledA.get(i).getCardSuit() == shuffledB.get(i).getCardSuit()) {
                counter++;
            }
        }
        assertTrue(counter != 52); //The likelihood of two decks being shuffled into the same order is small
        // enough that if it happens its more likely that the shuffle method is not working as intended.
    }

    @Test
    void getCardIfDeckIsNotEmpty() {
        Deck deck = new Deck();
        Card card = deck.getCard();

        assertNotNull(card);
    }

    @Test
    void getCardIfDeckIsEmpty() {
        Deck deck = new Deck();

        for(int i = 0; i < 52; i++) {
            deck.getCard();
        }


        Throwable exception = assertThrows(Exception.class, () -> deck.getCard());
        assertEquals("Index 0 out of bounds for length 0", exception.getMessage());
    }

    @Test
    void getNumberOfCardsInDeckIfDeckIsNotEmpty() {
        Deck deck = new Deck();

        assertNotEquals(0, deck.getNumberOfCardsInDeck());
    }

    @Test
    void getNumberOfCardsInDeckIfDeckIsEmpty() {
        Deck deck = new Deck();
        for(int i = 0; i < 52; i++) {
            deck.getCard();
        }

        assertEquals(0, deck.getNumberOfCardsInDeck());
    }
}