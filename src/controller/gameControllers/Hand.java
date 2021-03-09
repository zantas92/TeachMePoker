package controller.gameControllers;

import java.util.ArrayList;
import java.util.Collection;

import controller.aiControllers.Ai;
import model.Card;
import model.CommunityCards;

/**
 * The hand-class that keep the cards of a player
 * and the advice based on those cards
 *
 * @author Cornelia Sköld 21-03-05
 * @version 1.0
 */
public class Hand {
    private final CommunityCards communityCards;
    private final ArrayList<Card> personalCards;
    private ArrayList<Card> hand;
    private ArrayList<Card> cardsToHighLight;
    private HandValueAdvice handValueAdvice;
    private AiDecision aiDecision;
    private int highCardValue;
    private int highCardOutsideHandValue;

    /**
     * Constructor
     *
     * @param personalCards is the players two personal cards.
     */
    public Hand(ArrayList<Card> personalCards) {
        this.personalCards = personalCards;
        hand = new ArrayList<>();
        communityCards = new CommunityCards();
        hand.addAll(personalCards);
        handValueAdvice = new HandValueAdvice(this);
    }

    public ArrayList<Card> getPersonalCards() {
        return personalCards;
    }

    /**
     * Method used to access the community cards
     *
     * @return the community cards
     */
    public CommunityCards getCommunityCards() {
        return communityCards;
    }

    /**
     * Method used to update the cards on the hand
     * Used to make sure that the cards are "up to date"
     */
    public void updateHand() {
        hand = new ArrayList<>();
        hand.addAll(personalCards);
        hand.addAll(communityCards.getCommunityCards());
    }

    /**
     * Method used to get the best hand value in hand
     *
     * @return the hand value
     */
    public HandValue handValue() {
        updateHand();
        handValueAdvice = new HandValueAdvice(this);
        return handValueAdvice.getHandValue();
    }

    /**
     * Method used to get the advice based on the cards in hand
     *
     * @return the advice
     */
    public HandValueAdvice handValueAdvice() {
        updateHand();
        handValueAdvice = new HandValueAdvice(this);
        return handValueAdvice;
    }

    public AiDecision getAiDecision(){
        return aiDecision;
    }

    public void newDecision(){
        aiDecision = new AiDecision();
    }

    public void setActionType(ActionType actionType){
        aiDecision.setAiActionType(actionType);
    }

    public void setMinimumBet(int minimumBet){
        aiDecision.setAmountToBet(minimumBet);
    }

    public int calculateAiDecision(ActionType actionType, int minimumBet) {
        updateHand();
        handValueAdvice = new HandValueAdvice(this);
        aiDecision.setPlayTurn(communityCards.getRound());
        aiDecision.setHandValueAdvice(handValueAdvice);
        aiDecision.calculateDecision(actionType, minimumBet);
        return aiDecision.getAmountToBet();
    }

    public ArrayList<Card> handArray() {
        updateHand();
        return hand;
    }

    public int[] getHandStrength() {
        return handValueAdvice.getHandStrength();
    }

    public void setHighCards(int highCardValue, int highCardOutsideHandValue) {
        this.highCardValue = highCardValue;
        this.highCardOutsideHandValue = highCardOutsideHandValue;
    }

    public void setCardsToHighLight(ArrayList<Card> cardsToHighLight) {
        this.cardsToHighLight = cardsToHighLight;
    }

    public int getHighCardValue() {
        return highCardValue;
    }

    public int getHighCardOutsideHandValue() {
        return highCardOutsideHandValue;
    }

    public ArrayList<Card> getHighlightedCards() {
        return cardsToHighLight;
    }
}
