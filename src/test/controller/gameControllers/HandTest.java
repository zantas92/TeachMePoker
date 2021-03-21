package controller.gameControllers;

import model.Card;
import model.Deck;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {


    @Test
    void toPowerBar() { //With 7 cards
        Deck deck = new Deck();
        deck.shuffle();

        ArrayList<Card> cards = new ArrayList<Card>();
        for(int i = 0; i < 7; i++) {
            cards.add(deck.getCard());
        }
        Hand hand = new Hand(cards);

        assertTrue(hand.toPowerBar() >= 1 && hand.toPowerBar() <= 4);
    }

    @Test
    void theHelp() { //With 5 cards
        Deck deck = new Deck();
        deck.shuffle();

        ArrayList<Card> cards = new ArrayList<Card>();
        for(int i = 0; i < 5; i++) {
            cards.add(deck.getCard());
        }
        Hand hand = new Hand(cards);

        String[] help = {"HIGH CARD", "ONE PAIR", "TWO PAIR", "THREE OF A KIND", "FOUR OF A KIND",
        "FLUSH", "STRAIGHT", "FULL HOUSE"};

        ArrayList<String> bestHands = new ArrayList<String>();
        for(int i = 0; i < 8; i++) {
            bestHands.add(help[i]);
        }

        assertTrue(bestHands.contains(hand.theHelp()));
    }


    @Test
    void theAdvice() { //With 5 cards
        Deck deck = new Deck();
        deck.shuffle();

        ArrayList<Card> cards = new ArrayList<Card>();
        for(int i = 0; i < 5; i++) {
            cards.add(deck.getCard());
        }
        Hand hand = new Hand(cards);

        String[] advices = {"Du har bara 'HIGH CARD'. \nOm det är billigt så kan du prova och se.\n",
                            "Du har ett högt 'HIGH CARD'. \nOm det är billigt så kan du prova och se.\n",
                            "'ONE-PAIR' på första-handen är en stark hand!\nSå kör på!\n",
                            "'ONE-PAIR' på första-handen är en stark hand!\nOch då detta är även är ett högt par, Så kör verkligen!!\n",
                            "'ONE-PAIR' är en ok hand. Om det inte kostar för mycket. Så kör på!\n",
                            "'ONE-PAIR' är en ok hand, även då detta är ett lågt par.\nOm det inte kostar för mycket. Så kör på!\n",
                            "'ONE-PAIR' är en ok hand. Och detta är även ett högt par vilket är ännu bättre.\nOm det inte kostar för mycket. Kör på!\n",
                            "'ONE-PAIR' är en hyfsat ok hand. Om det inte kostar för mycket. Så kör på!\n",
                            "'ONE-PAIR' är en hyfsat ok hand, även då detta är ett lågt par.\nOm det inte kostar för mycket. Så kör på!\n",
                            "'ONE-PAIR'  är en hyfsat ok hand. Och detta är även ett högt par vilket är ännu bättre.\nOm det inte kostar för mycket. Kör på!\n;",
                            "'TWO PAIRS' är en bra hand, kör på.\n",
                            "'THREE OF A KIND' är en väldigt stark hand. Kör på! Fundera även på att höja!\n",
                            "En 'STRAIGHT' är en riktigt bra hand. Kör på! \nFundera även på att höja!\n",
                            "Du har en 'FLUSH'! Kör på, din hand är svår att slå!\n",
                            "Det är inte mycket som slår denna hand! Höja är rekomenderat!\n",
                            "'FOUR OF A KIND' är en av de bästa händerna. Kör på! Fundera även på att höja!\n",
                            "'STRAIGHT FLUSH' är bästa handen i spelet. Kör på och höj!\n",
                            };

        String[] advicesAddon = {
                                "Du har en chans på en 'STRAIGHT'",
                                "Du har en chans för en 'FLUSH'",
                                };



        ArrayList<String> adviceList = new ArrayList<String>();
        for(int i = 0; i < advices.length; i++) {
            adviceList.add(advices[i]);
        }

        assertTrue(adviceList.contains(hand.theAdvice()) || hand.theAdvice().contains(advicesAddon[0]) ||
                hand.theAdvice().contains(advicesAddon[1]));
    }

    @Test
    void Hand_WithInputAsNull() { //null
        Throwable exception = assertThrows(Exception.class, () -> new Hand(null));
        assertEquals("Cannot invoke \"java.util.ArrayList.size()\" because \"this.cards\" is null", exception.getMessage());
    }

    @Test
    void Hand_WithInValidAmountOfCards() { //Less than 2 cards
        Deck deck = new Deck();
        deck.shuffle();

        ArrayList<Card> cards = new ArrayList<Card>();
        for(int i = 0; i < 1; i++) {
            cards.add(deck.getCard());
        }

        Throwable exception = assertThrows(Exception.class, () -> new Hand(cards));
        assertEquals("Index 1 out of bounds for length 1", exception.getMessage());
    }

    @Test
    void getHighlightedCards() { //7 cards
        Deck deck = new Deck();
        deck.shuffle();

        ArrayList<Card> cards = new ArrayList<Card>();
        for(int i = 0; i < 7; i++) {
            cards.add(deck.getCard());
        }
        Hand hand = new Hand(cards);

        assertTrue(hand.getHighlightedCards() instanceof ArrayList); //Lite tamt men alternativet är att kolla om array size 0 till mängden kort som hand skapades med
    }

    @Test
    void getHandStrength() { //6 cards
        Deck deck = new Deck();
        deck.shuffle();

        ArrayList<Card> cards = new ArrayList<Card>();
        for(int i = 0; i < 7; i++) {
            cards.add(deck.getCard());
        }
        Hand hand = new Hand(cards);

        assertTrue(hand.getHandStrenght() >= 0 && hand.getHandStrenght() <= 8);
    }

}