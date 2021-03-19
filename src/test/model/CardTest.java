package model;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void getCardValue() {
        ImageIcon image = new ImageIcon("resources/images/4H.png");
        Card card = new Card(Suit.HEARTS, 4, image);

        assertEquals(4, card.getCardValue());
    }

    @Test
    void getCardValueWhenInputIsInvalid() {
        ImageIcon image = new ImageIcon("resources/images/4H.png");
        Card card = new Card(Suit.HEARTS, 1, image); //Only valid inputs are 2-14

        assertEquals(1, card.getCardValue());
    }

    @Test
    void getCardSuit() {
        ImageIcon image = new ImageIcon("resources/images/4H.png");
        Card card = new Card(Suit.HEARTS, 4, image);

        assertEquals(Suit.HEARTS.toString(), card.getCardSuit());
    }

    @Test
    void getCardSuitWhenInputIsNull() {
        ImageIcon image = new ImageIcon("resources/images/4H.png");
        Card card = new Card(null, 4, image);

        Throwable exception = assertThrows(Exception.class, () -> card.getCardSuit());
        assertEquals("Cannot invoke \"model.Suit.toString()\" because \"this.cardSuit\" is null", exception.getMessage());
    }

    @Test
    void getCardIcon() {
        ImageIcon image = new ImageIcon("resources/images/4H.png");
        Card card = new Card(Suit.HEARTS, 4, image);

        assertEquals(image, card.getCardIcon());
    }

    @Test
    void getCardIconWhenInputIsNull() {
        Card card = new Card(Suit.HEARTS, 4, null);

        assertNull(card.getCardIcon());
    }

    @Test
    void getCardIconWhenInputIsInvalid() {
        ImageIcon image = new ImageIcon("resources/images/4HHH.png"); //4HHH is not a real filepath
        Card card = new Card(Suit.HEARTS, 4, image);

        assertEquals(image, card.getCardIcon());
    }
}