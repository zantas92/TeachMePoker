package oldTests;
import controller.gameControllers.CorrectHandCalc;
import model.Card;
import model.Suit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class testCorrectHandCalc {

    @DisplayName("Four of a kind")
    @Test
    public void testFourOfAKind() {
        Card one = new Card(Suit.HEARTS, 10, new ImageIcon());
        Card two = new Card(Suit.HEARTS, 9, new ImageIcon());
        Card three = new Card(Suit.DIAMONDS, 10, new ImageIcon());
        Card four = new Card(Suit.SPADES, 10, new ImageIcon());
        Card five = new Card(Suit.CLUBS, 10, new ImageIcon());

        ArrayList<Card> listOfCards = new ArrayList<>();
        listOfCards.add(one);
        listOfCards.add(two);
        listOfCards.add(three);
        listOfCards.add(four);
        listOfCards.add(five);

        CorrectHandCalc c = new CorrectHandCalc(listOfCards);
        assertEquals("FOUR OF A KIND", c.calculateHand());
    }

    @DisplayName("Full house")
    @Test
    public void testFullHouse() {
        Card one = new Card(Suit.HEARTS, 10, new ImageIcon());
        Card two = new Card(Suit.HEARTS, 9, new ImageIcon());
        Card three = new Card(Suit.DIAMONDS, 9, new ImageIcon());
        Card four = new Card(Suit.SPADES, 10, new ImageIcon());
        Card five = new Card(Suit.CLUBS, 10, new ImageIcon());


        ArrayList<Card> listOfCards = new ArrayList<>();
        listOfCards.add(one);
        listOfCards.add(two);
        listOfCards.add(three);
        listOfCards.add(four);
        listOfCards.add(five);

        CorrectHandCalc c = new CorrectHandCalc(listOfCards);
        assertEquals("FULL HOUSE", c.calculateHand());
    }

    @DisplayName("Flush")
    @Test
    public void testFlush() {
        Card one = new Card(Suit.HEARTS, 2, new ImageIcon());
        Card two = new Card(Suit.HEARTS, 3, new ImageIcon());
        Card three = new Card(Suit.HEARTS, 5, new ImageIcon());
        Card four = new Card(Suit.HEARTS, 10, new ImageIcon());
        Card five = new Card(Suit.HEARTS, 12, new ImageIcon());


        ArrayList<Card> listOfCards = new ArrayList<>();
        listOfCards.add(one);
        listOfCards.add(two);
        listOfCards.add(three);
        listOfCards.add(four);
        listOfCards.add(five);

        CorrectHandCalc c = new CorrectHandCalc(listOfCards);
        assertEquals("FLUSH", c.calculateHand());
    }

    @DisplayName("Straight")
    @Test
    public void testStraight() {
        Card one = new Card(Suit.HEARTS, 11, new ImageIcon());
        Card two = new Card(Suit.HEARTS, 3, new ImageIcon());
        Card three = new Card(Suit.HEARTS, 6, new ImageIcon());
        Card four = new Card(Suit.HEARTS, 4, new ImageIcon());
        Card five = new Card(Suit.DIAMONDS, 5, new ImageIcon());
        Card six = new Card(Suit.DIAMONDS, 10, new ImageIcon());
        Card seven = new Card(Suit.DIAMONDS, 7, new ImageIcon());


        ArrayList<Card> listOfCards = new ArrayList<>();
        listOfCards.add(one);
        listOfCards.add(two);
        listOfCards.add(three);
        listOfCards.add(four);
        listOfCards.add(five);
        listOfCards.add(six);
        listOfCards.add(seven);

        CorrectHandCalc c = new CorrectHandCalc(listOfCards);
        assertEquals("STRAIGHT", c.calculateHand());
    }

    @DisplayName("Three of a kind")
    @Test
    public void testThreeOfAKind() {
        Card one = new Card(Suit.HEARTS, 12, null); //Testar om null funkar
        Card two = new Card(Suit.HEARTS, 3, new ImageIcon());
        Card three = new Card(Suit.HEARTS, 12, new ImageIcon());
        Card four = new Card(Suit.SPADES, 10, new ImageIcon());
        Card five = new Card(Suit.HEARTS, 12, new ImageIcon());


        ArrayList<Card> listOfCards = new ArrayList<>();
        listOfCards.add(one);
        listOfCards.add(two);
        listOfCards.add(three);
        listOfCards.add(four);
        listOfCards.add(five);

        CorrectHandCalc c = new CorrectHandCalc(listOfCards);
        assertEquals("THREE OF A KIND", c.calculateHand());
    }

    @DisplayName("Two pair")
    @Test
    public void testTwoPair() {
        Card one = new Card(Suit.HEARTS, 2, new ImageIcon());
        Card two = new Card(Suit.CLUBS, 3, new ImageIcon());
        Card three = new Card(Suit.HEARTS, 5, new ImageIcon());
        Card four = new Card(Suit.HEARTS, 2, new ImageIcon());
        Card five = new Card(Suit.HEARTS, 3, new ImageIcon());


        ArrayList<Card> listOfCards = new ArrayList<>();
        listOfCards.add(one);
        listOfCards.add(two);
        listOfCards.add(three);
        listOfCards.add(four);
        listOfCards.add(five);

        CorrectHandCalc c = new CorrectHandCalc(listOfCards);
        assertEquals("TWO PAIR", c.calculateHand());
    }

    @DisplayName("One pair")
    @Test
    public void testOnePair() {
        Card one = new Card(Suit.HEARTS, 12, new ImageIcon());
        Card two = new Card(Suit.HEARTS, 3, new ImageIcon());
        Card three = new Card(Suit.HEARTS, 5, new ImageIcon());
        Card four = new Card(Suit.SPADES, 10, new ImageIcon());
        Card five = new Card(Suit.HEARTS, 12, new ImageIcon());


        ArrayList<Card> listOfCards = new ArrayList<>();
        listOfCards.add(one);
        listOfCards.add(two);
        listOfCards.add(three);
        listOfCards.add(four);
        listOfCards.add(five);

        CorrectHandCalc c = new CorrectHandCalc(listOfCards);
        assertEquals("ONE PAIR", c.calculateHand());
    }

    @DisplayName("High card")
    @Test
    public void testHighCard() {
        Card one = new Card(Suit.SPADES, 2, new ImageIcon());
        Card two = new Card(Suit.HEARTS, 3, new ImageIcon());
        Card three = new Card(Suit.HEARTS, 5, new ImageIcon());
        Card four = new Card(Suit.HEARTS, 10, new ImageIcon());
        Card five = new Card(Suit.HEARTS, 12, new ImageIcon());


        ArrayList<Card> listOfCards = new ArrayList<>();
        listOfCards.add(one);
        listOfCards.add(two);
        listOfCards.add(three);
        listOfCards.add(four);
        listOfCards.add(five);

        CorrectHandCalc c = new CorrectHandCalc(listOfCards);
        assertEquals("HIGH CARD", c.calculateHand());
    }
}
