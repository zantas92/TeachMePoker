package controller.aiControllers;

import model.Card;
import model.Deck;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AiCalculationTest {

    /*
        *ALl tests in this unit are subject to the stability of the Deck, Card classes
        *Test data will be comprised of valid inputs(will be made up of arrays of 2-5 cards) and invalid inputs (which be made
        *up of String arrays that do not follow the intended structure or empty cards lists).
     */


    @Test
    void checkHighCards() { //2 cards
        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();


        String sA = A.getCardValue() + "," + String.valueOf(A);
        String sB = B.getCardValue() + "," + String.valueOf(B);

        ArrayList<String> cards = new ArrayList<String>();
        cards.add(sA);
        cards.add(sB);

        AiCalculation calculation = new AiCalculation(cards);

        if(A.getCardValue() + B.getCardValue() > 17) {
            assertTrue(calculation.checkHighCards());
        }
        else {
            assertFalse(calculation.checkHighCards());
        }
    }

    @Test
    void checkSuit() { //5 cards
        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        Card C = deck.getCard();
        Card D = deck.getCard();
        Card E = deck.getCard();

        String sA = A.getCardValue() + "," + String.valueOf(A);
        String sB = B.getCardValue() + "," + String.valueOf(B);
        String sC = C.getCardValue() + "," + String.valueOf(C);
        String sD = D.getCardValue() + "," + String.valueOf(D);
        String sE = E.getCardValue() + "," + String.valueOf(E);

        ArrayList<String> cards = new ArrayList<String>();
        cards.add(sA);
        cards.add(sB);
        cards.add(sC);
        cards.add(sD);
        cards.add(sE);

        AiCalculation calculation = new AiCalculation(cards);
        System.out.println("suit nbr " + calculation.checkSuit());

        assertTrue(calculation.checkSuit() >= 0 && calculation.checkSuit() <= 5);
    }

    @Test
    void checkPairAndMore() { //4 cards
        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        Card C = deck.getCard();
        Card D = deck.getCard();

        String sA = A.getCardValue() + "," + String.valueOf(A);
        String sB = B.getCardValue() + "," + String.valueOf(B);
        String sC = C.getCardValue() + "," + String.valueOf(C);
        String sD = D.getCardValue() + "," + String.valueOf(D);

        ArrayList<String> cards = new ArrayList<String>();
        cards.add(sA);
        cards.add(sB);
        cards.add(sC);
        cards.add(sD);

        AiCalculation calculation = new AiCalculation(cards);

        assertTrue(calculation.checkPairAndMore() >= 0 && calculation.checkPairAndMore() <=4 );
    }


    @Test
    void checkStraight() { //5 cards
        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        Card C = deck.getCard();
        Card D = deck.getCard();
        Card E = deck.getCard();

        String sA = A.getCardValue() + "," + String.valueOf(A);
        String sB = B.getCardValue() + "," + String.valueOf(B);
        String sC = C.getCardValue() + "," + String.valueOf(C);
        String sD = D.getCardValue() + "," + String.valueOf(D);
        String sE = E.getCardValue() + "," + String.valueOf(E);

        ArrayList<String> cards = new ArrayList<String>();
        cards.add(sA);
        cards.add(sB);
        cards.add(sC);
        cards.add(sD);
        cards.add(sE);

        AiCalculation calculation = new AiCalculation(cards);

        //TODO
    }

    @Test
    void calcHandstrenght() { //2 cards
        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();


        String sA = A.getCardValue() + "," + String.valueOf(A);
        String sB = B.getCardValue() + "," + String.valueOf(B);

        ArrayList<String> cards = new ArrayList<String>();
        cards.add(sA);
        cards.add(sB);


        AiCalculation calculation = new AiCalculation(cards);

        assertTrue(calculation.calcHandstrenght() >= 0 && calculation.calcHandstrenght() < 9);
    }

    @Test
    void CalculationWithEmptyInput() {
        ArrayList<String> cards = new ArrayList<String>();

        Throwable exception = assertThrows(Exception.class, () -> new AiCalculation(cards));
        assertEquals("Index 0 out of bounds for length 0", exception.getMessage());
    }

    @Test
    void CalculationWithInputIsNull() {
        ArrayList<String> cards = new ArrayList<String>();

        Throwable exception = assertThrows(Exception.class, () -> new AiCalculation(null));
        assertEquals("Cannot invoke \"java.util.ArrayList.size()\" because \"this.aiCards\" is null", exception.getMessage());
    }

    @Test
    void CalculationWithIncorrectCardStructure() {
        ArrayList<String> cards = new ArrayList<String>();
        cards.add("I am an incorrect card!");

        Throwable exception = assertThrows(Exception.class, () -> new AiCalculation(cards));
        assertEquals("For input string: \"I am an incorrect card!\"", exception.getMessage());
    }

}