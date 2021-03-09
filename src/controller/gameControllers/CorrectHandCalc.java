package controller.gameControllers;

import model.Card;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A new HandCalculation that also takes the community cards into account.
 *
 * @author Oscar Kareld 22/2-2021
 * @version 1.0
 */
public class CorrectHandCalc {
    private ArrayList<Card> hand;
    private ArrayList<Card> cardsInHandValue;

    /**
     * Constructor
     * @param hand The player's hole cards and all known community cards
     */
    public CorrectHandCalc(ArrayList<Card> hand) {
        this.hand = hand;
        cardsInHandValue = new ArrayList<>();
    }


    /**
     * Makes a calculation based on all known cards
     * @return A string that says what the player holds
     */
    public HandValue calculateHand() {
        int size = hand.size();
        boolean[] checked = new boolean[15]; //Index motsvarar kortets valör
        int[] sameValue = new int[size];
        int counter = 0;
        HandValue bestHand = HandValue.HIGH_CARD;

        for (Card allKnownCard : hand) {
            System.out.print(allKnownCard + ", ");
        }

        //quads, trips, pairs:
        for (int i = 0; i < size; i++) {
            for ( int j = i+1; j < size; j++) {
                if (hand.get(i).getCardValue() == hand.get(j).getCardValue() && !checked[hand.get(i).getCardValue()]) {
                    cardsInHandValue.add(hand.get(i));
                    cardsInHandValue.add(hand.get(j));
                    if (sameValue[counter] == 0) {
                        sameValue[counter] = 2;
                    } else sameValue[counter]++;
                }
            }
            counter++;
            checked[hand.get(i).getCardValue()] = true;
        }

        for(int i = 0; i < size; i++) {
            if (sameValue[i] == 4) {
                bestHand = HandValue.FOUR_OF_A_KIND;
                return bestHand;
            }
            else if (sameValue[i] == 3) {
                for (int j = i+1; j < size; j++) {
                    if (sameValue[j] == 2) {
                        bestHand = HandValue.FULL_HOUSE;
                        return bestHand;
                    }
                    else bestHand = HandValue.THREE_OF_A_KIND;
                }
            }
            else if (checkFlush()) {
                bestHand = HandValue.FLUSH;
                return bestHand;
            }
            else if (checkStraight()) {
                bestHand = HandValue.STRAIGHT;
                return bestHand;
            }
            else if (sameValue[i] == 3) {
                bestHand = HandValue.THREE_OF_A_KIND;
                return bestHand;
            }

            else if (sameValue[i] == 2) {
                for (int j = i+1; j < size; j++) {
                    if (sameValue[j] == 2) {
                        bestHand = HandValue.TWO_PAIR;
                        return bestHand;
                    } else  {
                        bestHand = HandValue.ONE_PAIR;
                    }
                }
            }
        }
        return bestHand;
    }

    /**
     * Checks if the player has a straight.
     * @return straight true/false
     */
    private boolean checkStraight() {
        int[] cards = new int[hand.size()+1];
        int cardsToStraight = 0;
        boolean hasAce = false;

        for (int i = 0; i < hand.size(); i++) {
            cards[i] = hand.get(i).getCardValue();
            if (cards[i] == 14) hasAce = true; //Om det finns ett ess bland korten
        }
        if (hasAce) cards[cards.length-1] = 1; //Lägger till ett card med value = 1, eftersom ess kan räknas både som 1 och 14.
        Arrays.sort(cards);

        for(int i = 0; i < cards.length -1; i++) {
            if (cards[i+1] - cards[i] == 1) {
                cardsToStraight++;
            }
        }
        return cardsToStraight >= 4;
    }

    /**
     * Checks if the player has a flush.
     * @return flush true/false
     */
    private boolean checkFlush() {
        boolean[] cardsInFlush = new boolean[7];
        int nbrOfSameSuit = 0;
        for(int i = 0; i < hand.size(); i++) {
            for(int j = i+1; j < hand.size(); j++) {
                if (hand.get(i).getCardSuit().equals(hand.get(j).getCardSuit())) {
                    cardsInFlush[i] = true;
                    cardsInFlush[j] = true;
                    if (nbrOfSameSuit == 0) {
                        nbrOfSameSuit = 2;
                    }
                    else {
                        nbrOfSameSuit++;
                    }

                }
            }
            if (nbrOfSameSuit > 4) {
                for(int k = 0; k < cardsInFlush.length; k++) {
                    if (cardsInFlush[k]) cardsInHandValue.add(hand.get(k));
                }
                return true;
            }
            nbrOfSameSuit = 0;
        }

        return false;
    }

    public ArrayList<Card> getCardsInHandValue() {
        return cardsInHandValue;
    }
}
