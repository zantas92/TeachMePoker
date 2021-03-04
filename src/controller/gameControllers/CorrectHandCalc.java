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
    ArrayList<Card> allKnownCards;
    boolean[] cardsToHighLight = new boolean[7];

    /**
     * Constructor
     * @param allKnownCards The player's hole cards and all known community cards
     */
    public CorrectHandCalc(ArrayList<Card> allKnownCards) {
        this.allKnownCards = allKnownCards;
    }


    /**
     * Makes a calculation based on all known cards
     * @return A string that says what the player holds
     */
    public String calculateHand() {
        int size = allKnownCards.size();
        boolean[] checked = new boolean[15]; //Index motsvarar kortets valör
        int[] sameValue = new int[size];
        int counter = 0;
        String bestHand = "HIGH CARD";

        for (int in = 0; in < size; in++) {
            System.out.print(allKnownCards.get(in) + ", ");
        }

        //quads, trips, pairs:
        for (int i = 0; i < size; i++) {
            for ( int j = i+1; j < size; j++) {
                if (allKnownCards.get(i).getCardValue() == allKnownCards.get(j).getCardValue() && !checked[allKnownCards.get(i).getCardValue()]) {
                    cardsToHighLight[i] = true;
                    cardsToHighLight[j] = true;
                    if (sameValue[counter] == 0) {
                        sameValue[counter] = 2;
                    } else sameValue[counter]++;
                }
            }
            counter++;
            checked[allKnownCards.get(i).getCardValue()] = true;
        }

        for(int i = 0; i < size; i++) {
            if (sameValue[i] == 4) {
                bestHand = "FOUR OF A KIND";
                return bestHand;
            }
            else if (sameValue[i] == 3) {
                for (int j = i+1; j < size; j++) {
                    if (sameValue[j] == 2) {
                        bestHand = "FULL HOUSE";
                        return bestHand;
                    }
                    else bestHand = "THREE OF A KIND";
                }
            }
            else if (checkFlush()) {
                bestHand = "FLUSH";
                return bestHand;
            }
            else if (checkStraight()) {
                bestHand = "STRAIGHT";
                return bestHand;
            }
            else if (sameValue[i] == 3) {
                bestHand = "THREE OF A KIND";
                return bestHand;
            }

            else if (sameValue[i] == 2) {
                for (int j = i+1; j < size; j++) {
                    if (sameValue[j] == 2) {
                        bestHand = "TWO PAIR";
                        return bestHand;
                    } else  {
                        bestHand = "ONE PAIR";
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
        int[] cards = new int[allKnownCards.size()+1];
        int cardsToStraight = 0;
        boolean hasAce = false;

        for (int i = 0; i < allKnownCards.size(); i++) {
            cards[i] = allKnownCards.get(i).getCardValue();
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
        for(int i = 0; i < allKnownCards.size(); i++) {
            for(int j = i+1; j < allKnownCards.size(); j++) {
                if (allKnownCards.get(i).getCardSuit().equals(allKnownCards.get(j).getCardSuit())) {
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
                    if (cardsInFlush[k]) cardsToHighLight[k] = true;
                }
                return true;
            }
            nbrOfSameSuit = 0;
        }

        return false;
    }

    //Den här är inte implementerad, bara förberedd. Insåg senare att de har gjort en konstig lösning med en
    //String-ArrayList, vilket gjorde det svårt att implementera min lösning. /Oscar
    public boolean[] getCardsToHighLight() {
        return cardsToHighLight;
    }

    /**
     * Updates all known cards when one or more new cards are added to the current game
     */
    public void updateAllKnownCards(ArrayList<Card> allKnownCards) {
        this.allKnownCards = allKnownCards;
    }
}
