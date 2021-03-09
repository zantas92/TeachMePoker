package controller.aiControllers;

import java.util.ArrayList;

import controller.gameControllers.ActionType;
import controller.gameControllers.Hand;
import model.Card;


/**
 * Main class for Ai-player that depending on turn, creates a calculation and returns a respond to
 * controller.
 * 
 * @author Max Frennessen 17-05-25
 * @version 2.0
 */

public class Ai {
  private int index;
  private int position;

  private int aiPot;
  private int paidThisTurn = 0;
  private String name;

  private int allInViability = 99;
  private Hand aiHand;
  private int[] handStrength;
  private boolean madeItsMove;

  /**
   * sets the starting potsize and the ai's name.
   * 
   * @param aiPot The potsize that the ai will start from.
   * @param name the Ai's name for the entire name.
   */
  public Ai(int aiPot, String name, int index) {
    this.name = name;
    this.aiPot = aiPot;
    this.index = index;
  }


  /**
   * Receives the Ai-players two first cards.
   * 
   * @param personalCard is the cards in AI-players hand
   *
   */
  public void setStartingHand(ArrayList<Card> personalCard) {
    aiHand = new Hand(personalCard);
  }

  public void resetAi(){
    allInViability = 99;
    handStrength = new int[2];
    madeItsMove = false;
    paidThisTurn = 0;
  }

  public void newMove(ActionType actionType, int minimumBet){
    aiHand.newDecision();
    switch (actionType){
      case DEALER:
        aiHand.setActionType(actionType);
        madeItsMove = true;
        break;
      case SMALL_BLIND:
      case BIG_BLIND:
        aiHand.setActionType(actionType);
        aiHand.setMinimumBet(minimumBet);
        aiPot -= minimumBet;
        paidThisTurn += minimumBet;
        madeItsMove = true;
        break;
      case TO_BE_DECIDED:
        int toBet =aiHand.calculateAiDecision(actionType,minimumBet);
        aiPot -= toBet;
        paidThisTurn += toBet;
        //TODO: kalla på metod som berättar för SP-controller vad draget är och summa på drag + pott
        break;
    }
  }

  public Hand getHand(){
    return aiHand;
  }


  /**
   * Makes decision for AI
   * 
   * @param minimumBet How much the Ai-player as to bet to be able to play this turn.
   */

  /**
   * Returns how much the ai-player has left in his pot.
   * 
   * @return returns the ai potSize
   */
  public int aiPot() {
    return aiPot;
  }

  public void withDrawFromPot(int amount){
    aiPot = aiPot - amount;
  }

  public void addToPot(int amount){
    aiPot = aiPot + amount;
  }

  /**
   * Returns the name of the AI-player
   * 
   * @return returns the name of the ai-player
   */
  public String getName() {
    return name;
  }

  public String getActionString(){
    int amount = aiHand.getAiDecision().getAmountToBet();
    String actionType = aiHand.getAiDecision().getAiActionType().getActionName();
    String actionString;
    if (amount > 0) {
      actionString =
              actionType +
                      " " +
                      amount +
                      "SEK";
    } else {
      actionString = actionType;
    }
    return actionString;
  }

  public void setPosition(int position){
    this.position = position;
  }

  public int getPosition() {
    return position;
  }

  public int getIndex() {
    return index;
  }


  /**
   * Returns how much the ai-player has paid this turn
   * 
   * @return Returns how much the ai-player has paid this turn
   */
  public int getPaidThisTurn() {

    return paidThisTurn;
  }


  /**
   * Sets how much the ai-player as already paid this turn
   * 
   * @param paidThisTurn Sets how much the ai-player as already paid this turn
   */
  public void setPaidThisTurn(int paidThisTurn) {
    this.paidThisTurn = paidThisTurn;
  }


  /**
   * Returns the ai-players highest card
   * 
   * @return returns the ai-players highest card
   */
  public int getHighCard() {

    return aiHand.getHighCardValue();
  }


  /**
   * returns the handstrength of the ai-player
   * 
   * @return returns the handstrength of the ai-player
   */
  public int[] handStrength() {

    return aiHand.getHandStrength();
  }


  /**
   * returns if the ai-players viable for the currentpot.
   * 
   * @return returns if the ai-players viable for the currentpot.
   */
  public int getAllInViability() {

    return allInViability;
  }


  /**
   * sets if the ai-players viable for the currentpot or not.
   * 
   * @param allInViability sets if the ai-players viable for the currentpot or not.
   */
  public void setAllInViability(int allInViability) {

    if (allInViability < this.allInViability) {
      this.allInViability = allInViability;
    } else {
      System.out.println("AI was already viable");
    }
  }

  //TODO: metod som återställer värden inför en ny runda (paid this turn, hand strength)

}

