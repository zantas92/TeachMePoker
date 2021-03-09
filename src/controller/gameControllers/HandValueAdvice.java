package controller.gameControllers;

import model.Card;
import model.CommunityCards;
import java.util.ArrayList;


public class HandValueAdvice {
    private HandValue handValue;
    private String handValueName;
    private String advice;
    private ArrayList<Card> personalCards;
    private CommunityCards communityCards;
    private ArrayList<Card> handArray;
    private CorrectHandCalc handCalculator;
    private int round;
    private int power;
    private int[] handStrength;

    public HandValueAdvice(Hand hand) {
        personalCards = hand.getPersonalCards();
        communityCards = hand.getCommunityCards();
        handArray = hand.handArray();
        handCalculator = new CorrectHandCalc(handArray);
        handValue = handCalculator.calculateHand();
        handValueName = handValue.getHandValueName();
        round = communityCards.getRound();
        power = 1;
        calculateAdvise();
        hand.setHighCards(highCardInHandValue(), highCardOutsideHandValue());
        hand.setCardsToHighLight(handCalculator.getCardsInHandValue());
        handStrength = new int[2]; //Index: 0=handValueRank, 1=highCardValue
        handStrength[0] = handValue.getHandValueRank();
        handStrength[1] = highCardInHandValue();
    }

    public int[] getHandStrength(){
        return handStrength;
    }

    public HandValue getHandValue() {
        return handCalculator.calculateHand();
    }

    public String getHandValueName(){
        return handValueName;
    }

    public String getAdvice() {
        return advice;
    }

    public void calculateAdvise() {
        int highValueCardCount = highValueCardCount(handCalculator.getCardsInHandValue());
        String handValueString = "svag";

        switch (handValue) {
            case HIGH_CARD:
                handValueString = "svag";
                if (round == 1) {
                    power = 2;
                } else {
                    power = 1;
                }
                break;
            case ONE_PAIR:
                if ((round == 1) && (highValueCardCount == 2)){
                    handValueString = "mycket bra";
                    power = 3;
                } else if (highValueCardCount == 2) {
                    handValueString = "bra";
                    power = 2;
                } else {
                    handValueString = "svag";
                    power = 1;
                }
                break;
            case TWO_PAIR:
                handValueString = "bra";
                power = 2;
                break;
            case THREE_OF_A_KIND:
                handValueString = "mycket bra";
                power = 3;
                break;
            case FLUSH:
            case STRAIGHT:
            case FULL_HOUSE:
            case FOUR_OF_A_KIND:
            case STRAIGHT_FLUSH:
            case ROYAL_STRAIGHT_FLUSH:
                handValueString = "extremt bra";
                power = 4;
                break;
        }
        String powerString = switch (power) {
            case 1 -> "Lägg dig. Men om det är billigt så kan du prova.\n";
            case 2 -> "Om det inte är för dyrt kan du prova och se.\n";
            case 3 -> "Kör på! Fundera även på att höja insatsen.\n";
            case 4 -> "Kör på! Din hand är svårslagen!\n";
            default -> "Om det är billigt så kan du prova och se.\n";
        };

        advice = "Det är en " + handValueString + "hand.\n" + powerString;
    }

    public int highValueCardCount(ArrayList<Card> cardsToValue) {
        int highValueCards = 0;
        for (Card c : cardsToValue) {
            if (c.getCardValue() > 10) {
                highValueCards++;
            }
        }
        return highValueCards;
    }

    public int highCardInHandValue(){
        ArrayList<Card> cards = handCalculator.getCardsInHandValue();
        int highCard = 0;
        for (int i = 0; i >= cards.size(); i++ ){
            if (cards.get(i).getCardValue() > highCard){
                highCard = cards.get(i).getCardValue();
            }
        }
        return highCard;
    }

    public int highCardOutsideHandValue(){
        int highCard = 0;
        for (int i = 0; i >= handArray.size(); i++ ){
            if (handArray.get(i).getCardValue() > highCard){
                highCard = handArray.get(i).getCardValue();
            }
        }
        return highCard;
    }

    public int getPower(){
        return power;
    }

}
