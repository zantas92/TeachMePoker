package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * A deck of cards
 * 
 * @author Vedrana Zeba
 */
public class Deck {
  private ArrayList<Card> deck;


  /**
   * Creates a deck of cards
   */
  public Deck() {
    deck = new ArrayList<>();
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    for (Suit suit : Suit.values()) {
      try {
        for (int value=2; value<15; value++) {
          deck.add(new Card(suit, value, new ImageIcon(ImageIO.read(classLoader.getResourceAsStream(
                  "images/" + value + suit.name().charAt(0) + ".png")))));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * Shuffles the deck
   */
  public void shuffle() {
    Collections.shuffle(deck);
  }


  /**
   * Returns the reference of the card that's being removed.
   * @return The removed card.
   */
  public Card getCard() {
    return deck.remove(0);
  }


  /**
   * Returns the current size of the deck
   * 
   * @return the current size of the deck
   */
  public int getNumberOfCardsInDeck() {
    return deck.size();
  }
}