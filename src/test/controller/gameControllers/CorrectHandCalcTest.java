package controller.gameControllers;

import model.Card;
import model.Deck;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CorrectHandCalcTest {

    @Test
    void calculateHandWithValidDeckObject() {
        Deck deck = new Deck();
        deck.shuffle();
        ArrayList<Card> cards = new ArrayList<Card>();

        for(int i = 0; i < 7; i++) { //We use player hand and a full set of community cards
            cards.add(deck.getCard());
        }

        CorrectHandCalc handCalculator = new CorrectHandCalc(cards);

        String[] s =
                {"HIGH CARD", "FOUR OF A KIND", "FULL HOUSE", "THREE OF A KIND"
                , "FLUSH", "STRAIGHT",  "TWO PAIR", "ONE PAIR"};

        ArrayList<String> outcomes = new ArrayList<String>();


        for(int i = 0; i < 8; i++) {
            outcomes.add(s[i]);
        }

        assertTrue(outcomes.contains(handCalculator.calculateHand()));
    }

    @Test
    void calculateHandWithDeckAsNull() {
        CorrectHandCalc handCalculator = new CorrectHandCalc(null);

        Throwable exception = assertThrows(Exception.class, () -> handCalculator.calculateHand());

        assertEquals("Cannot invoke \"java.util.ArrayList.size()\" because \"this.allKnownCards\" is null", exception.getMessage());
    }

    @Test
    void getCardsToHighLight() {
        Deck deck = new Deck();
        deck.shuffle();
        ArrayList<Card> cards = new ArrayList<Card>();

        for(int i = 0; i < 7; i++) { //We use player hand and a full set of community cards
            cards.add(deck.getCard());
        }

        CorrectHandCalc handCalculator = new CorrectHandCalc(cards);


        assertTrue(handCalculator.getCardsToHighLight()[0] == true || handCalculator.getCardsToHighLight()[0] == false); //We check if the first card in the combined set of cards is set to false or true. Either is acceptable, but null is not
    }
}