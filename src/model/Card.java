package model;

import javax.swing.Icon;
/**
 * Representation of a playing card
 * @author Lykke Levin
 *
 */

public class Card {
	
	private final Suit cardSuit;
	private final int cardValue;
	private final Icon cardIcon;

	/**
	 * Creates a Card with a suit, value and picture 
	 * @param suit "Hearts", "Diamonds", "Clubs", "Spades"
	 * @param value from 2 to 14 (Ace)
	 * @param cardIcon Image that represents the card
	 */
	public Card(Suit suit, int value, Icon cardIcon){
		this.cardSuit = suit;
		this.cardValue = value;
		this.cardIcon = cardIcon;
	}
	/**
	 * Returns the value of the card. 
	 * @return cardValue
	 */
	public int getCardValue(){
		return cardValue;
	}
	/**
	 * Returns the suit of the card.
	 * @return cardSuit
	 */
	public String getCardSuit(){
		return cardSuit.toString();
	}
	/**
	 * Returns the icon of the card.
	 * @return cardIcon
	 */
	public Icon getCardIcon(){
		return cardIcon;
	}
	
}
