package controller.gameControllers;

import model.Card;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Does the actuall calculation and decides what help
 * the noob player gets.
 *
 * @author Max Frennessen, Oscar Kareld
 * @version 1.6
 * 22/2-2021
 */
public class HandCalculation {
    private ArrayList<String> finalHihglight = new ArrayList<String>();
    private ArrayList<String> nbrForStraight = new ArrayList<String>();
    private ArrayList<String> nbrForStraight1 = new ArrayList<String>();
    private ArrayList<String> aiCards = new ArrayList<String>();
    private ArrayList<Integer> cardNbr = new ArrayList<Integer>();
    private ArrayList<String> cardClr = new ArrayList<String>();
    private boolean highCards = false;
    private boolean lowCards = false;
    private boolean rlyhighCards = false;
    private int colorChance;
    private int straightChance;
    private int pairsNmore;
    private String yourCard = "1,1";
    private String yourCard2 = "1,1";
    private String otherCard = "1,1";
    private String theColor;
    private ArrayList<String> toHighlight = new ArrayList<String>();
    private String advicee;
    private String whatStraight;
    private int handStrenght = 0;

    ArrayList<Integer> allKnownCards = new ArrayList<Integer>();
    ArrayList<Card> knownCards = new ArrayList<>();
    CorrectHandCalc correctHandCalc = new CorrectHandCalc(knownCards);

    /**
     * @param aiCards Current cards needed for evaluate.
     * @param cards
     */
    public HandCalculation(ArrayList<String> aiCards, ArrayList<Card> cards) {
        updateAllKnownCards(cards);
        this.aiCards = aiCards;
        getCardValues(aiCards);
        toHighlight.clear();
        highCards = checkHighCards();
        colorChance = checkSuit();
        pairsNmore = checkPairAndMore();
        straightChance = checkStraight();

        Help();
    }


    /**
     * converts the cards value into two diffirent arraylists.
     * one for cardnumber and one for cardcolor.
     *
     * @param aiCards current card being used
     */
    public void getCardValues(ArrayList<String> aiCards) {

        for (int i = 0; i < aiCards.size(); i++) {            //CardNumber
            String temp = aiCards.get(i);
            String[] splitter = temp.split(",");
            int tempInt = Integer.parseInt(splitter[0]);
            cardNbr.add(tempInt);
        }


        for (int i = 0; i < aiCards.size(); i++) {            //CardColor
            String temp = aiCards.get(i);
            String[] splitter = temp.split(",");
            String tempString = splitter[1];
            cardClr.add(tempString);
        }
    }


    /**
     * @return returns how many pairs or more the player has.
     */
    public int checkPairAndMore() {
        int same = 1;
        int nbrOftemp = 0;
        int nbrOftemp1 = 0;
        int nbrOftemp2 = 0;
        int size = aiCards.size(); //Antal kort i spelarens hand + kort på bordet.
        int[] cards = new int[size + 1];

        //Oscars kod: (Den här biten är för att highlighta även par och dylikt som finns bland community-korten på bordet)
        boolean[] isPaired = new boolean[size + 1];

        for (int i = 0; i < size; i++) {
            cards[i] = cardNbr.get(i);
            allKnownCards.add(cards[i]);
        }

        System.out.println(allKnownCards.size());
        for (int i = 0; i < allKnownCards.size(); i++) {
            int temp = allKnownCards.get(i);
            for (int j = i+1; j < allKnownCards.size(); j++) {
                if (temp == allKnownCards.get(j)) {
                    isPaired[i] = true;
                    isPaired[j] = true;
                }
            }
        }
        toHighlight.clear();

        for (int i = 0; i < isPaired.length; i++) {
            if (isPaired[i]) {
                toHighlight.add(aiCards.get(i));
            }
        }
        //Slut på Oscars kod.


        if (cards[0] == cards[1]) { //Kollar om man har par på hand
            int temp = cards[0]; //temp = första kortet på hand
            nbrOftemp = 2;
            yourCard = aiCards.get(0);
            otherCard = aiCards.get(1);

            for (int i = 2; i < cards.length; i++) {
                if (cards[i] == temp) { //cards är en array av int som bara sparar kortens valörer. cards[0] och cards[1] är spelarens hålkort
                    nbrOftemp++;

                }
            }
        } else {
            int temp1 = cards[0];
            int temp2 = cards[1];


            nbrOftemp1 = 1;
            nbrOftemp2 = 1;


            for (int i = 2; i < cards.length; i++) {

                if (cards[i] == temp1) {
                    if (cards[i] + temp2 <= 10) {
                        lowCards = true;
                    }
                    if (cards[i] + temp2 > 17) {
                        highCards = true;
                    }

                    nbrOftemp1++;

                    yourCard = aiCards.get(0);
                    otherCard = aiCards.get(i);
                    yourCard2 = aiCards.get(0);
                }
                if (cards[i] == temp2) {

                    if (cards[i] + temp2 > 17) {
                        highCards = true;
                    }

                    if (cards[i] + temp2 <= 10) {
                        lowCards = true;
                    }
                    nbrOftemp2++;
                    yourCard = aiCards.get(1);
                    otherCard = aiCards.get(i);

                }
            }
        }

        if (nbrOftemp > 0) {
            same = nbrOftemp;
        }

        if (nbrOftemp1 > 1) {
            same = nbrOftemp1;
        }

        if (nbrOftemp2 > 1) {
            if (nbrOftemp1 > 1) {
                same = Integer.parseInt(nbrOftemp1 + "" + nbrOftemp2);
            } else
                same = nbrOftemp2;
        }

        if (same == 1)
            same = 0;
        return same;

    }

    /**
     * @return returns true if cards value >= 17.
     * 'rlyHigh not yet implemented.
     */
    public boolean checkHighCards() {
        boolean high = false;

        int card1 = cardNbr.get(0);
        int card2 = cardNbr.get(1);

        int total = (card1 + card2);

        if (total >= 17) {
            high = true;
        }
        if (card1 >= 10 && card2 >= 10) {
            rlyhighCards = true;
        }

        return high;
    }

    /**
     * @return returns if the player has a suit or even has a chance for one.
     */
    public int checkSuit() {
        int C = 0;
        int S = 0;
        int H = 0;
        int D = 0;
        int color = 0;

        for (String x : cardClr) {
            if (x.equals("S")) {
                S++;
            }
            if (x.equals("C")) {
                C++;
            }
            if (x.equals("D")) {
                D++;
            }
            if (x.equals("H")) {
                H++;
            }
        }

        if (S > color) {
            toHighlight.clear();
            color = S;
            theColor = "spader";
            for (int i = 0; i < cardClr.size(); i++) {
                String temp = cardClr.get(i);
                if (S == 5)
                    if (temp.equals("S")) {
                        toHighlight.add(aiCards.get(i));
                    }
            }
        }

        if (H > color) {
            toHighlight.clear();
            color = H;
            theColor = "hjärter";
            for (int i = 0; i < cardClr.size(); i++) {
                String temp = cardClr.get(i);
                if (H == 5)
                    if (temp.equals("H")) {
                        toHighlight.add(aiCards.get(i));
                    }
            }
        }
        if (D > color) {
            toHighlight.clear();
            color = D;
            theColor = "ruter";
            for (int i = 0; i < cardClr.size(); i++) {
                String temp = cardClr.get(i);
                if (D == 5)
                    if (temp.equals("D")) {
                        toHighlight.add(aiCards.get(i));
                    }
            }
        }
        if (C > color) {
            toHighlight.clear();
            color = C;
            theColor = "klöver";
            for (int i = 0; i < cardClr.size(); i++) {
                String temp = cardClr.get(i);
                if (C == 5)
                    if (temp.equals("C")) {
                        toHighlight.add(aiCards.get(i));
                    }
            }
        }

        return color;
    }

    /**
     * @return returns if the player has a straight or even has a chance for one.
     */
    public int checkStraight() {

        int threshold = 0;

        for (int i = 0; i < cardNbr.size(); i++) {
            if (cardNbr.get(i) == 14) {
                cardNbr.add(1);
            }
        }

        int[] CurrentCardsArray = new int[cardNbr.size()];

        for (int i = 0; i < cardNbr.size(); i++) {
            CurrentCardsArray[i] = cardNbr.get(i);
        }

        Arrays.sort(CurrentCardsArray);
        int inStraight = 0;
        int check = 4;

        for (int x = 0; x < CurrentCardsArray.length; x++) {
            int CurrentHighestInStraight = CurrentCardsArray[x] + check;
            int CurrentLowestInStraight = CurrentCardsArray[x];
            String tempStraight = CurrentLowestInStraight + "-" + CurrentHighestInStraight;

            inStraight = 0;


            for (int i = 0; i < CurrentCardsArray.length; i++) {

                if (CurrentCardsArray[i] <= CurrentHighestInStraight && !(CurrentCardsArray[i] < CurrentLowestInStraight)) {

                    if (i == 0) {                            //kollar om 0 är samma som 1.

                        inStraight++;
                        if (CurrentCardsArray[i] == 1) {
                            nbrForStraight.add(String.valueOf(CurrentCardsArray[CurrentCardsArray.length - 1]));
                        } else {
                            nbrForStraight.add(String.valueOf(CurrentCardsArray[i]));
                        }

                    }

                    if (i >= 1) {
                        if (!(CurrentCardsArray[i] == CurrentCardsArray[i - 1])) {        //kollar om 1-4 är samma som nån annan.
                            inStraight++;
                            nbrForStraight.add(String.valueOf(CurrentCardsArray[i]));
                        }
                    }

                }
            }

            if (inStraight >= threshold) {  // >= så om man får 5 igen men med högre tal så blir det den som visas.

                threshold = inStraight;
                whatStraight = tempStraight;
                nbrForStraight1.clear();
                for (String a : nbrForStraight) {
                    nbrForStraight1.add(a);
                }
            }
            nbrForStraight.clear();
        }

        return threshold;
    }

    public ArrayList<String> getToHighlight() {
        for (int y = 0; y < nbrForStraight1.size(); y++) {
            int same = 1;
            for (int i = 0; i < aiCards.size(); i++) {

                String temp = aiCards.get(i);
                String[] tempSplit = temp.split(",");
                if (nbrForStraight1.get(y).equals(tempSplit[0])) {
                    if (same == 1) {
                        finalHihglight.add(aiCards.get(i));
                        same++;
                    }
                }
            }
        }
        return finalHihglight;
    }


    public int calcPwrBarLvl() {
        int pwrBar = 1;
        //TURNONE PWRLEVEL
        if (aiCards.size() == 2) {
            pwrBar = pwrBarLvlOnTurnOne();
        }
        //TURNTWO PWRLEVEL
        if (aiCards.size() == 5) {
            pwrBar = pwrBarLvlOnTurnTwo();
        }
        //TURNTHREE PWRLEVEL
        if (aiCards.size() == 6) {
            pwrBar = pwrBarLvlOnTurnThree();
        }
        //TURNFOUR PWRLEVEL
        if (aiCards.size() == 7) {
            pwrBar = pwrBarLvlOnTurnFour();
        }
        return pwrBar;
    }

    public int pwrBarLvlOnTurnOne() {
        int pwrBar = 1;
        if (colorChance == 2) {
            pwrBar = 2;
        }
        if (highCards) {
            pwrBar = 2;
            if (rlyhighCards) {
                pwrBar = 3;
            }
        }
        if (straightChance == 2) {
            pwrBar = 2;
            if (rlyhighCards) {
                pwrBar = 3;
            }
            if (colorChance == 2) {
                pwrBar = 3;
            }
        }
        if (pairsNmore > 0) {
            pwrBar = 4;
        }
        return pwrBar;
    }

    public int pwrBarLvlOnTurnTwo() {
        int pwrBar = 1;

        if (highCards) {
            pwrBar = 1;
            if (rlyhighCards) {
                pwrBar = 2;
            }
        }
        if (colorChance == 3) {
            pwrBar = 2;
        }
        if (straightChance >= 2) {
            pwrBar = 2;
            if (straightChance >= 4) {
                pwrBar = 3;
            }
            if (colorChance == 3) {
                pwrBar = 3;
            }
        }
        if (pairsNmore == 2) {
            pwrBar = 3;
        }
        if (pairsNmore == 22) {
            pwrBar = 4;
        }
        if (pairsNmore == 3) {
            pwrBar = 4;
        }
        if (pairsNmore == 4 || pairsNmore == 24) {
            pwrBar = 4;
        }
        if (pairsNmore == 23 || pairsNmore == 32) {
            pwrBar = 4;
        }
        if (straightChance == 5 || colorChance == 5) {
            pwrBar = 4;
        }


        return pwrBar;
    }

    public int pwrBarLvlOnTurnThree() {
        int pwrBar = 1;

        if (highCards) {
            pwrBar = 1;
            if (rlyhighCards) {
                pwrBar = 2;
            }
        }
        if (colorChance == 4) {
            pwrBar = 2;
        }
        if (straightChance == 4) {
            pwrBar = 2;

            if (colorChance == 4) {
                pwrBar = 3;
            }
        }
        if (pairsNmore == 2) {
            pwrBar = 2;
        }
        if (pairsNmore == 22) {
            pwrBar = 3;
        }
        if (pairsNmore == 3) {
            pwrBar = 4;
        }
        if (pairsNmore == 4 || pairsNmore == 24) {
            pwrBar = 4;
        }
        if (pairsNmore == 23 || pairsNmore == 32) {
            pwrBar = 4;
        }
        if (straightChance == 5 || colorChance == 5) {
            pwrBar = 4;
        }


        return pwrBar;
    }

    public int pwrBarLvlOnTurnFour() {
        int pwrBar = 1;

        if (highCards) {
            pwrBar = 1;
            if (rlyhighCards) {
                pwrBar = 1;
            }
        }
        if (pairsNmore == 2) {
            pwrBar = 2;
        }
        if (pairsNmore == 22) {
            pwrBar = 3;
        }
        if (pairsNmore == 3) {
            pwrBar = 4;
        }
        if (pairsNmore == 4 || pairsNmore == 24) {
            pwrBar = 4;
        }
        if (pairsNmore == 23 || pairsNmore == 32) {
            pwrBar = 4;
        }
        if (straightChance == 5 || colorChance == 5) {
            pwrBar = 4;
        }


        return pwrBar;
    }

    /**
     * Updates all known cards in object and in the correctHandCalc object
     * @param allKnownCards
     */
    public void updateAllKnownCards(ArrayList<Card> allKnownCards) { //1
        correctHandCalc.updateAllKnownCards(allKnownCards);
        knownCards = allKnownCards;
    }

    /**
     * Creates a now CorrectHandCalc object
     */
    public void createNewCorrectHandCalc() { //2
        correctHandCalc = new CorrectHandCalc(knownCards);
//        System.out.println("NUVARANDE HAND: " + correctHandCalc.calculateHand());

    }

    //TODO: Som det ser ut nu stämmer inte "DU HAR"-info överens med "RÅD"-infon, då "DU HAR" har sin uträkning i CorrectHandCalc,
    //TODO medan "RÅD"-infon kommer från Help()-metoden här nedanför. /Oscar

    /**
     * New help-method
     * @return String with the player's best hand
     */
    public String newHelp() {
        return correctHandCalc.calculateHand();
    }

    /**
     * @return returns a advice for the player that is current for his or her hand.
     */
    public String Help() {

        String helper = "Ingenting, tyvärr...";

        String[] splitter = yourCard.split(",");
        int intCardNbr = Integer.parseInt(splitter[0]);
        String yourCardInt = "";
        yourCardInt = String.valueOf(intCardNbr);
        String cardOne = cardNbr.get(0) + ":or";
        String cardTwo = cardNbr.get(1) + ":or";

        if (cardNbr.get(0) > 10) {
            if (cardNbr.get(0) == 11) {
                cardOne = "Knektar";
            }
            if (cardNbr.get(0) == 12) {
                cardOne = "Damer";
            }

            if (cardNbr.get(0) == 13) {
                cardOne = "Kungar";
            }
            if (cardNbr.get(0) == 14) {
                cardOne = "Ess";
            }
        }

        if (cardNbr.get(1) > 10) {
            if (cardNbr.get(1) == 11) {
                cardTwo = "Knektar";
            }
            if (cardNbr.get(1) == 12) {
                cardTwo = "Damer";
            }

            if (cardNbr.get(1) == 13) {
                cardTwo = "Kungar";
            }
            if (cardNbr.get(1) == 14) {
                cardTwo = "Ess";
            }
        }
        if (intCardNbr < 11) {
            yourCardInt += ":or";
        }
        if (intCardNbr > 10) {
            if (intCardNbr == 11) {
                yourCardInt = "Knektar";
            }
            if (intCardNbr == 12) {
                yourCardInt = "Damer";
            }

            if (intCardNbr == 13) {
                yourCardInt = "Kungar";
            }
            if (intCardNbr == 14) {
                yourCardInt = "Ess";
            }

        }
        //Writing out what advice to give and help for player, starting to check the lowest possible and if the player has better than it,
        //im overwriting it with a better card. starting from high card only and ending on straight flush.

        //HIGH CARD
        String advice = "Du har bara 'HIGH CARD'. \nOm det är billigt så kan du prova och se.\n";

        if (highCards) {
            advice = "Du har ett högt 'HIGH CARD'. \nOm det är billigt så kan du prova och se.\n";
        }


        // ONE PAIR
        if (pairsNmore == 2) {
            helper = "'ONE-PAIR' i " + yourCardInt + "\n";
            if (aiCards.size() == 2) {
                advice = "'ONE-PAIR' på första-handen är en stark hand!\nSå kör på!\n";
                if (highCards) {
                    advice = "'ONE-PAIR' på första-handen är en stark hand!\nOch då detta är även är ett högt par, Så kör verkligen!!\n";
                }
            }
            if (aiCards.size() == 5) {
                advice = "'ONE-PAIR' är en ok hand. Om det inte kostar för mycket. Så kör på!\n";
                if (lowCards) {
                    advice = "'ONE-PAIR' är en ok hand, även då detta är ett lågt par.\nOm det inte kostar för mycket. Så kör på!\n";
                }
                if (highCards) {
                    advice = "'ONE-PAIR' är en ok hand. Och detta är även ett högt par vilket är ännu bättre.\nOm det inte kostar för mycket. Kör på!\n";
                }
            }

            if (aiCards.size() > 5) {
                advice = "'ONE-PAIR' är en hyfsat ok hand. Om det inte kostar för mycket. Så kör på!\n";
                if (lowCards) {
                    advice = "'ONE-PAIR' är en hyfsat ok hand, även då detta är ett lågt par.\nOm det inte kostar för mycket. Så kör på!\n";
                }
                if (highCards) {
                    advice = "'ONE-PAIR'  är en hyfsat ok hand. Och detta är även ett högt par vilket är ännu bättre.\nOm det inte kostar för mycket."
                            + " Kör på!\n";
                }
            }
            // writes the active cards to highlight
            if (straightChance < 5 && colorChance < 5) {
                toHighlight.clear();
                for (int i = 0; i < aiCards.size(); i++) {
                    String[] seeIfSame = aiCards.get(i).split(",");
                    int temp = Integer.parseInt(seeIfSame[0]);
                    if (intCardNbr == temp) {
                        toHighlight.add(aiCards.get(i));
                    }
                }
            }
        }


        //TWO PAIRS
        if (pairsNmore == 22) {
            helper = "'TWO PAIRS'  i " + cardOne + " och " + cardTwo;
            advice = "'TWO PAIRS' är en bra hand, kör på.\n";
            // writes the active cards to highlight
            if (straightChance < 5 && colorChance < 5) {
                toHighlight.clear();
                for (int i = 0; i < aiCards.size(); i++) {
                    int cardIntOne = cardNbr.get(0);
                    int cardIntTwo = cardNbr.get(1);

                    if (cardIntOne == cardNbr.get(i)) {
                        toHighlight.add(aiCards.get(i));
                    }
                    if (cardIntTwo == cardNbr.get(i)) {
                        toHighlight.add(aiCards.get(i));
                    }
                }
            }
        }

        //THREE OF A KIND
        if (pairsNmore == 3) {
            helper = "'THREE OF A KIND' i " + yourCardInt;
            advice = "'THREE OF A KIND' är en väldigt stark hand. Kör på! Fundera även på att höja!\n";
            // writes the active cards to highlight
            if (straightChance < 5 && colorChance < 5) {
                toHighlight.clear();
                for (int i = 0; i < aiCards.size(); i++) {
                    String[] seeIfSame = aiCards.get(i).split(",");
                    int temp = Integer.parseInt(seeIfSame[0]);
                    if (intCardNbr == temp) {
                        toHighlight.add(aiCards.get(i));
                    }
                }
            }
        }


        //STRAIGHT
        if (straightChance == 5) {
            helper = "En 'STRAIGHT'!! Du har 5/5.\n";
            advice = "En 'STRAIGHT' är en riktigt bra hand. Kör på! \nFundera även på att höja!\n";
            toHighlight.clear();
            toHighlight = getToHighlight();


        }

        //FLUSH
        if (colorChance == 5) {
            helper = "En 'FLUSH' i " + theColor + "!! Du har 5/5!!\n";
            advice = "Du har en 'FLUSH'! Kör på, din hand är svår att slå!\n";
            //To HIHGLIGHT IS IN checkSuit Method.
            toHighlight.clear();
            checkSuit();
        }


        //FULL HOUSE
        if (pairsNmore == 23 || pairsNmore == 32) {
            helper = "'FULL HOUSE' med " + cardOne + " och " + cardTwo + "!!";
            advice = "Det är inte mycket som slår denna hand! Höja är rekomenderat!\n";
            // writes the active cards to hihglight
            toHighlight.clear();
            for (int i = 0; i < aiCards.size(); i++) {
                int cardIntOne = cardNbr.get(0);
                int cardIntTwo = cardNbr.get(1);

                if (cardIntOne == cardNbr.get(i)) {
                    toHighlight.add(aiCards.get(i));
                }
                if (cardIntTwo == cardNbr.get(i)) {
                    toHighlight.add(aiCards.get(i));
                }
            }

        }

        //FOUR OF A KIND
        if (pairsNmore == 4 || pairsNmore == 42 || pairsNmore == 24) {
            helper = "'FOUR OF A KIND' i " + yourCardInt;
            advice = "'FOUR OF A KIND' är en av de bästa händerna. Kör på! Fundera även på att höja!\n";
            // writes the active cards to hihglight
            if (straightChance < 5 && colorChance < 5) {
                toHighlight.clear();
                for (int i = 0; i < aiCards.size(); i++) {
                    String[] seeIfSame = aiCards.get(i).split(",");
                    int temp = Integer.parseInt(seeIfSame[0]);
                    if (intCardNbr == temp) {
                        toHighlight.add(aiCards.get(i));
                    }
                }
            }
        }

        //STRAIGHT FLUSH
        if (straightChance == 5 && colorChance == 5) {                            //"i stegen  " + whatStraight;
            helper = "'STRAIGHT FLUSH' i färgen " + theColor + "! ";   //ev add what straight it is ex 2-6.
            advice = "'STRAIGHT FLUSH' är bästa handen i spelet. Kör på och höj!\n";
            // Highlightning happens in checkStraight and checkSuit.
        }

        //STRAIGHTCHANCE TEXT AND COLORCHANCE TEXT
        if (aiCards.size() < 3) {
            if (straightChance == 2) {
                advice += "Du har en chans på en 'STRAIGHT', du har 2/5. \n";
            }
            if (colorChance == 2) {
                advice += "Du har en chans för en 'FLUSH' i " + theColor + ", du har 2/5.\n";
            }
        }
        if (aiCards.size() < 6) {
            if (straightChance == 3) {
                advice += "Du har en chans på en 'STRAIGHT', du har 3/5.\n";
            }
            if (colorChance == 3) {
                advice += "Du har en chans för en 'FLUSH' i " + theColor + ", du har 3/5.\n";
            }
        }

        if (aiCards.size() < 7) {
            if (straightChance == 4) {
                advice += "Du har en chans på en 'STRAIGHT', du har 4/5.\n";
            }
            if (colorChance == 4) {
                advice += "Du har en chans för en 'FLUSH' i " + theColor + ", du har 4/5.\n";
            }
        }

        advicee = advice;
        return helper;

    }

    /**
     * returns what advice to give the user
     *
     * @return what advice to give the user
     */
    public String advice() {
        return advicee;
    }

    /**
     * @return what to be highlighed.
     */
    public ArrayList<String> toHighlight() {
        return toHighlight;

    }

    /**
     * sets and returns the current handStrength of the users cards.
     *
     * @return sets and returns the current handStrength of the users cards.
     */
    public int calcHandstrenght() {

        if (pairsNmore == 2) {     //Pair
            handStrenght = 1;
        }
        if (pairsNmore == 22) {     //Two pair
            handStrenght = 2;
        }
        if (pairsNmore == 3) {     //Three of a kind
            handStrenght = 3;
        }
        if (straightChance == 5) {    //Straight
            handStrenght = 4;
        }
        if (colorChance == 5) {        //Flush
            handStrenght = 5;
        }
        if (pairsNmore == 23 || pairsNmore == 32) {    //Full house
            handStrenght = 6;
        }
        if (pairsNmore == 4 || pairsNmore == 42 || pairsNmore == 24) {    //Four of a kind
            handStrenght = 7;
        }
        if (colorChance == 5 && straightChance == 5) {        //Straight flush
            handStrenght = 8;
        }

        return handStrenght;
    }
}
