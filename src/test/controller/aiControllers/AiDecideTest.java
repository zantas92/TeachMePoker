package controller.aiControllers;

import model.Card;
import model.Deck;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AiDecideTest {
    /*
      *ALl tests in this unit are subject to the stability of the Deck, Card classes
      *Due to time constraint set by the complexity of the code, every test will assume the following
      * AI pot is 5000 on 'turnOne' and diminishes by an increment of 500 corresponding to the bet of 500 (se below)
      * AI has not yet bet if its turn one
      * AI will have to bet at least 500 to join/stay
    */


    @Test
    void turnOneNewTurn() {
        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();

        String sA = A.getCardValue() + "," + String.valueOf(A);
        String sB = B.getCardValue() + "," + String.valueOf(B);

        ArrayList<String> aiCards = new ArrayList<String>();
        aiCards.add(sA);
        aiCards.add(sB);

        ArrayList<String> decisions = new ArrayList<String>();
        decisions.add("call,500");
        decisions.add("fold");
        decisions.add("all-in");
        decisions.add("raise,"+625);
        decisions.add("check");

        AiDecide decision = new AiDecide(aiCards, 5000, 500, 0, false);
        decision.turnOne();

        System.out.println("turnOneNewTurn " + decision.decision());

        assertTrue(decisions.contains(decision.decision()));
    }

    @Test
    void turnOneSameTurn() {
        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();

        String sA = A.getCardValue() + "," + String.valueOf(A);
        String sB = B.getCardValue() + "," + String.valueOf(B);

        ArrayList<String> aiCards = new ArrayList<String>();
        aiCards.add(sA);
        aiCards.add(sB);

        ArrayList<String> decisions = new ArrayList<String>();
        decisions.add("call,500");
        decisions.add("fold");
        decisions.add("all-in");
        decisions.add("raise,"+625);
        decisions.add("check");

        AiDecide decision = new AiDecide(aiCards, 5000, 500, 0, true);
        decision.turnOne();

        System.out.println("turnOneSameTurn " + decision.decision());

        assertTrue(decisions.contains(decision.decision()));
    }

    @Test
    void turnTwoNewTurn() {
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


        ArrayList<String> aiCards = new ArrayList<String>();
        aiCards.add(sA);
        aiCards.add(sB);
        aiCards.add(sC);
        aiCards.add(sD);
        aiCards.add(sE);

        ArrayList<String> decisions = new ArrayList<String>();
        decisions.add("call,500");
        decisions.add("fold");
        decisions.add("all-in");
        decisions.add("raise,"+1250);
        decisions.add("check");

        AiDecide decision = new AiDecide(aiCards, 4500, 1000, 500, false);
        decision.turnTwo();

        System.out.println("turnTwoNewTurn " + decision.decision());

        assertTrue(decisions.contains(decision.decision()));
    }

    @Test
    void turnTwoSameTurn() {
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


        ArrayList<String> aiCards = new ArrayList<String>();
        aiCards.add(sA);
        aiCards.add(sB);
        aiCards.add(sC);
        aiCards.add(sD);
        aiCards.add(sE);

        ArrayList<String> decisions = new ArrayList<String>();
        decisions.add("call,500");
        decisions.add("fold");
        decisions.add("all-in");
        decisions.add("raise,"+1250);
        decisions.add("check");

        AiDecide decision = new AiDecide(aiCards, 4500, 1000, 500, true);
        decision.turnTwo();

        System.out.println("turnTwoSameTurn " + decision.decision());

        assertTrue(decisions.contains(decision.decision()));
    }

    @Test
    void turnThreeNewTurn() {
        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();

        Card C = deck.getCard();
        Card D = deck.getCard();
        Card E = deck.getCard();
        Card F = deck.getCard();


        String sA = A.getCardValue() + "," + String.valueOf(A);
        String sB = B.getCardValue() + "," + String.valueOf(B);
        String sC = C.getCardValue() + "," + String.valueOf(C);
        String sD = D.getCardValue() + "," + String.valueOf(D);
        String sE = E.getCardValue() + "," + String.valueOf(E);
        String sF = F.getCardValue() + "," + String.valueOf(F);


        ArrayList<String> aiCards = new ArrayList<String>();
        aiCards.add(sA);
        aiCards.add(sB);
        aiCards.add(sC);
        aiCards.add(sD);
        aiCards.add(sE);
        aiCards.add(sF);

        ArrayList<String> decisions = new ArrayList<String>();
        decisions.add("call,500");
        decisions.add("fold");
        decisions.add("all-in");
        decisions.add("raise,"+1875);
        decisions.add("check");

        AiDecide decision = new AiDecide(aiCards, 4000, 1500, 1000, false);
        decision.turnThree();

        System.out.println("turnThreeNewTurn " + decision.decision());

        assertTrue(decisions.contains(decision.decision()));
    }

    @Test
    void turnThreeSameTurn() {
        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();

        Card C = deck.getCard();
        Card D = deck.getCard();
        Card E = deck.getCard();
        Card F = deck.getCard();


        String sA = A.getCardValue() + "," + String.valueOf(A);
        String sB = B.getCardValue() + "," + String.valueOf(B);
        String sC = C.getCardValue() + "," + String.valueOf(C);
        String sD = D.getCardValue() + "," + String.valueOf(D);
        String sE = E.getCardValue() + "," + String.valueOf(E);
        String sF = F.getCardValue() + "," + String.valueOf(F);


        ArrayList<String> aiCards = new ArrayList<String>();
        aiCards.add(sA);
        aiCards.add(sB);
        aiCards.add(sC);
        aiCards.add(sD);
        aiCards.add(sE);
        aiCards.add(sF);

        ArrayList<String> decisions = new ArrayList<String>();
        decisions.add("call,500");
        decisions.add("fold");
        decisions.add("all-in");
        decisions.add("raise,"+1875);
        decisions.add("check");

        AiDecide decision = new AiDecide(aiCards, 4000, 1500, 1000, true);
        decision.turnThree();

        System.out.println("turnThreeSameTurn " + decision.decision());

        assertTrue(decisions.contains(decision.decision()));
    }

    @Test
    void turnFourNewTurn() {
        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();

        Card C = deck.getCard();
        Card D = deck.getCard();
        Card E = deck.getCard();
        Card F = deck.getCard();
        Card G = deck.getCard();


        String sA = A.getCardValue() + "," + String.valueOf(A);
        String sB = B.getCardValue() + "," + String.valueOf(B);
        String sC = C.getCardValue() + "," + String.valueOf(C);
        String sD = D.getCardValue() + "," + String.valueOf(D);
        String sE = E.getCardValue() + "," + String.valueOf(E);
        String sF = F.getCardValue() + "," + String.valueOf(F);
        String sG = G.getCardValue() + "," + String.valueOf(G);


        ArrayList<String> aiCards = new ArrayList<String>();
        aiCards.add(sA);
        aiCards.add(sB);
        aiCards.add(sC);
        aiCards.add(sD);
        aiCards.add(sE);
        aiCards.add(sF);
        aiCards.add(sG);

        ArrayList<String> decisions = new ArrayList<String>();
        decisions.add("call,500");
        decisions.add("fold");
        decisions.add("all-in");
        decisions.add("raise,"+2500);
        decisions.add("check");

        AiDecide decision = new AiDecide(aiCards, 3500, 2000, 1500, false);
        decision.turnFour();

        System.out.println("turnFourNewTurn " + decision.decision());

        assertTrue(decisions.contains(decision.decision()));
    }

    @Test
    void turnFourSameTurn() {
        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();

        Card C = deck.getCard();
        Card D = deck.getCard();
        Card E = deck.getCard();
        Card F = deck.getCard();
        Card G = deck.getCard();

        String sA = A.getCardValue() + "," + String.valueOf(A);
        String sB = B.getCardValue() + "," + String.valueOf(B);
        String sC = C.getCardValue() + "," + String.valueOf(C);
        String sD = D.getCardValue() + "," + String.valueOf(D);
        String sE = E.getCardValue() + "," + String.valueOf(E);
        String sF = F.getCardValue() + "," + String.valueOf(F);
        String sG = G.getCardValue() + "," + String.valueOf(G);


        ArrayList<String> aiCards = new ArrayList<String>();
        aiCards.add(sA);
        aiCards.add(sB);
        aiCards.add(sC);
        aiCards.add(sD);
        aiCards.add(sE);
        aiCards.add(sF);
        aiCards.add(sG);

        ArrayList<String> decisions = new ArrayList<String>();
        decisions.add("call,500");
        decisions.add("fold");
        decisions.add("all-in");
        decisions.add("raise,"+2500);
        decisions.add("check");

        AiDecide decision = new AiDecide(aiCards, 3500, 2000, 1500, true);
        decision.turnFour();

        System.out.println("turnFourSameTurn " + decision.decision());

        assertTrue(decisions.contains(decision.decision()));
    }

    @Test
    void gethandStrength() {
        Deck deck = new Deck();
        deck.shuffle();
        Card A = deck.getCard();
        Card B = deck.getCard();

        Card C = deck.getCard();
        Card D = deck.getCard();

        String sA = A.getCardValue() + "," + String.valueOf(A);
        String sB = B.getCardValue() + "," + String.valueOf(B);

        ArrayList<String> aiCards = new ArrayList<String>();
        aiCards.add(sA);
        aiCards.add(sB);


        ArrayList<Integer> strengthLevels = new ArrayList<Integer>();
        for(int i = 0; i <= 8; i++) {
            strengthLevels.add(i);
        }

        AiDecide decision = new AiDecide(aiCards, 4500, 1000, 500, false);

        assertTrue(strengthLevels.contains(decision.gethandStrength()));
    }

    /*
     *The following test are done with the same restrictions as above, and with 'turnOne'
     *So that a change can be guaranteed in values
     */
    @Test
    void updateAiPot() {
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

        ArrayList<String> aiCards = new ArrayList<String>();
        aiCards.add(sA);
        aiCards.add(sB);
        aiCards.add(sC);
        aiCards.add(sD);


        ArrayList<String> decisions = new ArrayList<String>();


        AiDecide decision = new AiDecide(aiCards, 5000, 500, 0, true);
        System.out.println("updateAIPot decision " + decision.decision() + " ");



       if(decision.decision().equals("fold")) {
           assertEquals(5000, decision.updateAiPot());
       }
       else if(decision.decision().equals("call,500")) {
           assertEquals(4500, decision.updateAiPot());
       }
       else {
           assertEquals(4375, decision.updateAiPot());
       }
    }

    /*
        *getDecision() requires the same setup as some of the other tests in the unit so
        *instead one of the previous tests is called upon
     */
    @Test
    void getDecision() {
        turnOneSameTurn();
    }
}