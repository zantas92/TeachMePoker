package controller.aiControllers;

import model.Card;
import model.Deck;
import org.junit.jupiter.api.Test;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AiTest {
    //ALl tests in this unit are subject to the stability of the Deck, Card and AiDecide classes

    @Test
    void setStartingHandWithCorrectInput() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        Card highCard;
        if(A.getCardValue() > B.getCardValue()) {
            highCard = A;
        }
        else {
            highCard = B;
        }

        testAI.setStartingHand(A, B);

        assertEquals(highCard.getCardValue(), testAI.getHighCard());
    }

    @Test
    void setStartingHandWithBothInputAsNull() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);
        Throwable exception = assertThrows(Exception.class, () -> testAI.setStartingHand(null, null));
        assertEquals("Cannot invoke \"model.Card.getCardSuit()\" because \"card1\" is null", exception.getMessage());
    }

    @Test
    void setStartingHandWithFirstCardAsNull() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();

        Throwable exception = assertThrows(Exception.class, () -> testAI.setStartingHand(A, null));
        assertEquals("Cannot invoke \"model.Card.getCardSuit()\" because \"card2\" is null", exception.getMessage());
    }

    @Test
    void setStartingHandWithSecondCardAsNull() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card B = deck.getCard();

        Throwable exception = assertThrows(Exception.class, () -> testAI.setStartingHand(null, B));
        assertEquals("Cannot invoke \"model.Card.getCardSuit()\" because \"card1\" is null", exception.getMessage());
    }


    @Test
    void makeDecisionWithinPotValue() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        testAI.setStartingHand(A, B);
        testAI.makeDecision(2500);

        assertEquals(String.class, testAI.getDecision().getClass());
    }

    @Test
    void makeDecisionWhenBetIsBelowZero() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        testAI.setStartingHand(A, B);

        testAI.makeDecision(-1);


        assertEquals(String.class, testAI.getDecision().getClass());
    }

    @Test
    void makeDecisionWhenBetIsZero() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        testAI.setStartingHand(A, B);

        testAI.makeDecision(0);


        assertEquals(String.class, testAI.getDecision().getClass());
    }


    @Test
    void makeDecisionWithAllInPossibility() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        testAI.setStartingHand(A, B);

        testAI.makeDecision(5001);

        assertEquals(String.class, testAI.getDecision().getClass());
    }

    @Test
    void makeDecisionWithFlopIncludedAndWithinPotValue() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        testAI.setStartingHand(A, B);
        Card[] flop = new Card[3];
        for(int i = 0; i < 3; i++) {
            flop[i] = deck.getCard();
        }

        testAI.makeDecision(2500, flop);


        assertEquals(String.class, testAI.getDecision().getClass());
    }

    @Test
    void makeDecisionWithFlopIncludedAndWhenBetIsBelowZero() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        testAI.setStartingHand(A, B);
        Card[] flop = new Card[3];
        for(int i = 0; i < 3; i++) {
            flop[i] = deck.getCard();
        }

        testAI.makeDecision(-1, flop);


        assertEquals(String.class, testAI.getDecision().getClass());
    }

    @Test
    void makeDecisionWithFlopIncludedAndWhenBetIsZero() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        testAI.setStartingHand(A, B);
        Card[] flop = new Card[3];
        for(int i = 0; i < 3; i++) {
            flop[i] = deck.getCard();
        }

        testAI.makeDecision(0, flop);


        assertEquals(String.class, testAI.getDecision().getClass());
    }

    @Test
    void makeDecisionWithFlopIncludedAndWithAllInPossibility() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        testAI.setStartingHand(A, B);
        Card[] flop = new Card[3];
        for(int i = 0; i < 3; i++) {
            flop[i] = deck.getCard();
        }

        testAI.makeDecision(5001, flop);


        assertEquals(String.class, testAI.getDecision().getClass());
    }

    @Test
    void makeDecisionOnTheLastTwoTurnsAndWithinPotValue() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        testAI.setStartingHand(A, B);

        testAI.makeDecision(2500, deck.getCard());


        assertEquals(String.class, testAI.getDecision().getClass());
    }

    @Test
    void makeDecisionOnTheLastTwoTurnsAndWhenBetIsBelowZero() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        testAI.setStartingHand(A, B);

        testAI.makeDecision(-1, deck.getCard());


        assertEquals(String.class, testAI.getDecision().getClass());
    }

    @Test
    void makeDecisionOnTheLastTwoTurnsAndWhenBetIsZero() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        testAI.setStartingHand(A, B);

        testAI.makeDecision(0, deck.getCard());


        assertEquals(String.class, testAI.getDecision().getClass());
    }

    @Test
    void makeDecisionOnTheLastTwoTurnsAndWithAllInPossibility() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        testAI.setStartingHand(A, B);

        testAI.makeDecision(5001, deck.getCard());


        assertEquals(String.class, testAI.getDecision().getClass());
    }

    @Test
    void setAndGetDecisionValidInput() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        testAI.setDecision("reset Decisions");
        assertEquals("reset Decisions", testAI.getDecision());
    }

    @Test
    void setAndGetDecisionInputIsNull() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        testAI.setDecision(null);
        assertEquals(null, testAI.getDecision());
    }

    @Test
    void aiPot() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        assertEquals(5000, testAI.aiPot());
    }

    @Test
    void updateWinner() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        testAI.updateWinner(500);

        assertEquals(5500, testAI.aiPot());
    }

    @Test
    void getName() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        assertEquals("testAI", testAI.getName());
    }

    @Test
    void setAndGetBigBlind() { //No reason to consider if
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        testAI.setBigBlind(50, true);

        assertEquals(true, testAI.getIsBigBlind());
    }


    @Test
    void setAndGetSmallBlind() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        testAI.setSmallBlind(50, true);

        assertEquals(true, testAI.getIsSmallBlind());
    }

    @Test
    void setAndGetPaidThisTurn() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        testAI.setPaidThisTurn(500);

        assertEquals(500, testAI.getPaidThisTurn());
    }

    @Test
    void getHighCard() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        testAI.setStartingHand(A, B);

        if(A.getCardValue() > B.getCardValue()) {
            assertEquals(A.getCardValue(), testAI.getHighCard());
        }
        else {
            assertEquals(B.getCardValue(), testAI.getHighCard());
        }

    }

    @Test
    void handStrength() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);
        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();
        testAI.setStartingHand(A, B);

        testAI.makeDecision(500); //Doesn't matter


        assertTrue(testAI.handStrength() >= 0 && testAI.handStrength() < 9);
    }

    @Test
    void setAndGetAllInViabilityAboveDefault() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        testAI.setAllInViability(100);
        assertEquals(99, testAI.getAllInViability());
    }

    @Test
    void setAndGetAllInViabilityEqualToDefault() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        testAI.setAllInViability(99);
        assertEquals(99, testAI.getAllInViability());
    }

    @Test
    void setAndGetAllInViabilityBelowDefault() {
        int testAIPot = 5000;
        String testAIName = "testAI";
        Ai testAI = new Ai(testAIPot, testAIName);

        testAI.setAllInViability(98);
        assertEquals(98, testAI.getAllInViability());
    }

}