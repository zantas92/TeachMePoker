package controller;

import java.util.ArrayList;
import java.util.Collections;

import controller.aiControllers.Ai;
import controller.gameControllers.ActionType;
import controller.gameControllers.Hand;
import model.Card;
import model.CommunityCards;
import model.Deck;
import controller.gameControllers.GameController;


/**
 * @author Rikard Almgren, Cornelia Sköld
 * @version 2.0
 */
public class SPController extends Thread {
    private GameController gameController;

    private ArrayList<Ai> aiPlayers;
    private ArrayList<String> name;
    private int numberOfAi;
    private int fixedNumberOfAIs;
    private int numberOfPlayers;

    private int playTurn = 0;
    private int currentPlayer = 0;

    private int dealer = 0;
    private int blindCounter;
    private int bigBlindPlayer;
    private int bigBlind = 10;
    private int smallBlindPlayer;
    private int smallBlind;

    private Hand playerHand;
    private int playerPot;
    private int alreadyPaid;
    private int playerDecision;
    private int playerIndex;

    private int potSize;
    private int currentPotSize;
    private int tablePot;
    private int[][] potSplits;

    private int currentMaxBet;

    private boolean allCalledOrFolded = false;
    private boolean winnerDeclared = false;
    private boolean doAllInCheck;
    private volatile boolean active = false;
    private int psCounter = 0;

    private ArrayList<Card> personalCards;
    private CommunityCards communityCards;
    private ArrayList<Card> flopCards;
    private Card turnCard;
    private Card riverCard;


    /**
     * Method which receives and sets a number of starting variables and for the game to be set up.
     *
     * @param noOfAi     Number of AI-players
     * @param potSize    The potsize for the table(game).
     * @param playerName The players' name.
     */
    public void startGame(int noOfAi, int potSize, String playerName) {
        this.fixedNumberOfAIs = noOfAi;
        this.potSize = potSize;
        this.numberOfAi = noOfAi;
        numberOfPlayers = noOfAi + 1;
        playerIndex = noOfAi;

        gameController.disableButtons();
        gameController.setUsername(playerName);

        bigBlind = (int) (potSize * 0.02); // Calculates bigBlind
        if (bigBlind < 2) {
            bigBlind = 2;
        }
        currentMaxBet = bigBlind;
        this.smallBlind = bigBlind / 2;
        int individualPotSize = potSize / numberOfPlayers;

        // create aiPlayers
        setNames();
        for (int i = 0; i < noOfAi; i++) {
            aiPlayers.add(new Ai(potSize, name.remove(0), i));
        }
        gameController.setAiPlayers(aiPlayers);
        playerPot = potSize;

        //potSplits = new int[numberOfPlayers][1];


        try {
            setActive(true);
            setupPhase();
        } catch (InstantiationException | IllegalAccessException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method which sets a GameController, the controller that controls the GUI while the game is
     * running.
     *
     * @param gController An instance of GameController
     */
    public void setGameController(GameController gController) {

        this.gameController = gController;

    }


    /**
     * Method which returns the current max bet for the table.
     *
     * @return currentMaxbet the current max bet
     */
    public int getCurrentMaxBet() {

        return currentMaxBet;
    }


    /**
     * Method which returns the current potsize.
     *
     * @return potSize The pot.
     */
    public int getPotSize() {

        return potSize;
    }


    /**
     * Method that creates a list of names for AI-Players to pull from
     */
    public void setNames() {

        name.add("Max");
        name.add("Vedrana");
        name.add("Lykke");
        name.add("Amin");
        name.add("Rikard");
        name.add("Kristina");
        name.add("Rolf");
        Collections.shuffle(name);
    }


    /**
     * Method which prepares a new gameround.
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void setupPhase() throws InstantiationException, IllegalAccessException, InterruptedException {

        // Check if the player lost last turn
        if (playerPot > bigBlind) {
            /*
             * if not, reset the all-in check and potsplit counter Create a new deck, shuffle it and deal
             * cards
             */
            doAllInCheck = false;
            psCounter = 0;

            Deck deck = new Deck();
            deck.shuffle();

            //Initiate player cards and empty community cards
            communityCards = new CommunityCards();
            personalCards = new ArrayList<>();
            personalCards.add(deck.getCard());
            personalCards.add(deck.getCard());
            playerHand = new Hand(personalCards);
            gameController.setStartingHand(playerHand);
            gameController.setCommunityCards(communityCards.getCommunityCards());

            //Generate community cards for each round
            flopCards = new ArrayList<>();
            flopCards.add(deck.getCard());
            flopCards.add(deck.getCard());
            flopCards.add(deck.getCard());
            turnCard = deck.getCard();
            riverCard = deck.getCard();

            this.currentPotSize = 0;
            tablePot = 0;
            potSplits = new int[numberOfPlayers][1];

            gameController.playerReset("");
            /*
             * Reset the AI players unless they've lost
             */
            for (Ai ai : aiPlayers) {
                //System.out.println(ai.getName() + " : " + ai.calculateDecision() + (ai.aiPot() < bigBlind));
                //ai.setBigBlind(0, false);
                //ai.setSmallBlind(0, false);
                ai.setPaidThisTurn(0);
                ai.setAllInViability(99);
                ArrayList<Card> aiPersonalCards = new ArrayList<>();
                aiPersonalCards.add(deck.getCard());
                aiPersonalCards.add(deck.getCard());
                ai.setStartingHand(aiPersonalCards);
            }
            // set the blinds
            setBlinds(numberOfPlayers);

            // If thread isn't active, start, else run it again.
            if (!this.isAlive()) {
                start();
            } else {
                run();
            }
            // If the player did lose, make sure he knows it.
        } else {
            gameController.playerLost();
        }
    }

    public Hand getPlayerHand() {
        return playerHand;
    }


    /**
     * Method that runs the gameround itself
     */
    public void run() {

        if (active) {
            gameController.hideAllIn();
            gameController.activeSlider();
            String winner = "";

            while (playTurn < 4 && active) {
                gameController.roundStatus(playTurn);
                if (playTurn == 0 && active) {
                    gameController.addLogMessage("Första satsningsrundan (pre flop):");
                    try {
                        for (int i = dealer; i < playerIndex + dealer; i++) {                 //loopar igenom alla spelare
                            if (i != playerIndex) {

                                if (dealer == i && active) {
                                    Thread.sleep(1000);
                                    aiPlayers.get(dealer).newMove(ActionType.DEALER, 0);
                                    gameController.addLogMessage(aiPlayers.get(dealer).getName() + " är givaren");
                                    gameController.updateAiLabels(dealer);
                                }
                                if (smallBlindPlayer == i && active) {
                                    Thread.sleep(1000);
                                    aiPlayers.get(smallBlindPlayer).newMove(ActionType.SMALL_BLIND, smallBlind);
                                    gameController.addLogMessage(aiPlayers.get(smallBlindPlayer).getName() + " har liten mörk: " + smallBlind);
                                    gameController.updateAiLabels(smallBlindPlayer);
                                }
                                if (bigBlindPlayer == i && active) {
                                    Thread.sleep(1000);
                                    aiPlayers.get(bigBlindPlayer).newMove(ActionType.BIG_BLIND, bigBlind);
                                    gameController.addLogMessage(aiPlayers.get(bigBlindPlayer).getName() + " har stor mörk: " + bigBlind);
                                    gameController.updateAiLabels(bigBlindPlayer);
                                    currentMaxBet = bigBlind;
                                } else {
                                    aiAction(i);
                                }
                            } else {
                                playerAction();
                            }

                        }
                    } catch (InterruptedException e) {
                        setActive(false);
                        break;
                    }
                    updateActivePlayers(); //TODO: istället för att ha en metod där den kollar aktiva spelare kan aiPlayers uppdateras när en spelare åker ut ur spelet, och antal aktiva spelare är då antalet spelare i aiPlayers
                } else if (playTurn == 1 && active) {
                    gameController.addLogMessage("Andra satsningsrundan (flop):");
                    setCommunityCards(2);
                    updateActivePlayers();
                } else if (playTurn == 2 && active) {
                    gameController.addLogMessage("Fjärde gatan (turn):");
                    setCommunityCards(3);
                    updateActivePlayers();
                } else if (playTurn == 3 && active) {
                    gameController.addLogMessage("Femte gatan (river):");
                    setCommunityCards(4);
                    updateActivePlayers();
                }


                // If the game goes to the final round and no one has won yet, check the winner.
                if (playTurn >= 4 && !winnerDeclared && active) {
                    checkWinner();
                }

                if (active) {
                    // If an AI player has run out of money, they have lost.
                    for (Ai ai : aiPlayers) {
                        //TODO: metod för att hantera förlust måste uppdateras
                        if (ai.aiPot() < bigBlind && !ai.calculateDecision().contains("lost")) {
                            gameController.addLogMessage(ai.getName() + " förlorade...");
                            ai.setDecision("lost");
                            ai.updateWinner(-ai.aiPot());
                            gameController.setUIAiStatus(aiPlayers.indexOf(ai), "inactive");
                        }
                        System.out.println(ai.getName() + " : " + ai.calculateDecision() + (ai.aiPot() < bigBlind));

                    }
                }

                if (active) {
                    // Reset values
                    winnerDeclared = false;
                    playTurn = 0;
                    blindCounter++;
                    communityCards = new CommunityCards();
                    personalCards = new ArrayList<>();
                }
                // update the blinds
                if (blindCounter >= 15 && active) {
                    bigBlind += (int) (potSize / numberOfPlayers * 0.02);
                    currentMaxBet = bigBlind;
                    smallBlind = bigBlind / 2;
                    blindCounter = 0;
                }

                if (active) {
                    // Set new dealer
                    dealer = (dealer + 1) % numberOfPlayers;
                }

                if (active) {
                    try {
                        setupPhase();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        System.out.println("Thread exited when trying to setup the next round");
                    }
                }
            }

        }
    }

    public void setCommunityCards(int playTurn) {
        switch (playTurn) {
            case 1 -> {
                for (Ai ai : aiPlayers) {
                    ai.getHand().getCommunityCards().setFlopCards(flopCards);
                }
                communityCards.setFlopCards(flopCards);
                playerHand.getCommunityCards().setFlopCards(flopCards);
                gameController.setCommunityCards(communityCards.getCommunityCards());
            }
            case 2 -> {
                for (Ai ai : aiPlayers) {
                    ai.getHand().getCommunityCards().setTurn(turnCard);
                }
                gameController.addLogMessage("Fjärde gatan (turn):");
                communityCards.setTurn(turnCard);
                playerHand.getCommunityCards().setTurn(turnCard);
                gameController.setCommunityCards(communityCards.getCommunityCards());
            }
            case 3 -> {
                for (Ai ai : aiPlayers) {
                    ai.getHand().getCommunityCards().setRiver(riverCard);
                }
                gameController.addLogMessage("Femte gatan (river):");
                playerHand.getCommunityCards().setRiver(riverCard);
                communityCards.setRiver(riverCard);
                gameController.setCommunityCards(communityCards.getCommunityCards());
            }
        }
    }


    /**
     * Method which checks who the winner is.
     */

    //TODO: uppdatera efter nytt sätt att hantera drag och handstyrka
    private void checkWinner() {

        // if someone has gone all in, check winners through the all-in method instead.
        if (doAllInCheck) {
            checkAllInWinners();
        } else {
            // List of "second winners", on the rare occasion of people having the same handstrength and
            // highcard.
            ArrayList<Integer> secWin = new ArrayList<Integer>();

            String winner = "";
            int bestHand = 0;
            Ai bestHandPlayer = new Ai(0, "");
            /*
             * Go through all AI players that have not folded, check which player has the best hand. That
             * player is now the bestHandPlayer
             */
            for (Ai ai : aiPlayers) {
                if (!ai.calculateDecision().equals("fold")) {
                    if (ai.handStrength() > bestHand) {
                        bestHandPlayer = ai;
                        bestHand = ai.handStrength();
                        secWin.clear();
                    } else if (ai.handStrength() == bestHand) {
                        if (ai.getHighCard() > bestHandPlayer.getHighCard()) {
                            bestHandPlayer = ai;
                            bestHand = ai.handStrength();
                            secWin.clear();
                        } else if (ai.getHighCard() == bestHandPlayer.getHighCard()) {
                            secWin.add(aiPlayers.indexOf((ai)));
                        }
                    }
                }
            }
            // If the player hasn't folded, compare the players hand to that of the best AI player.
            if (!gameController.getPlayerDecision().contains("fold")) {
                // Player wins
                if (gameController.getHandStrength() > bestHand) {
                    gameController.setPlayerPot(currentPotSize);
                    winner = gameController.getUsername();
                    gameController.setWinnerLabel(winner, gameController.getHandStrength());
                    // draw
                } else if (gameController.getHandStrength() == bestHand) {
                    // Player wins
                    if (gameController.getGetHighCard() > bestHandPlayer.getHighCard()) {
                        gameController.setPlayerPot(currentPotSize);
                        winner = gameController.getUsername();
                        gameController.setWinnerLabel(winner, gameController.getHandStrength());
                        // Draw
                    } else if (gameController.getGetHighCard() == bestHandPlayer.getHighCard()) {
                        bestHandPlayer.updateWinner(currentPotSize / 2);
                        gameController.setPlayerPot(currentPotSize / 2);
                        winner = gameController.getUsername() + " och " + bestHandPlayer.getName();
                        gameController.setWinnerLabel(winner, bestHand);
                        // AI wins and there are second winners.
                    } else {
                        if (!secWin.isEmpty()) {
                            int divBy = currentPotSize = secWin.size();
                            for (int i : secWin) {
                                aiPlayers.get(i).updateWinner(divBy);
                            }
                            // Ai wins and there aren't
                        } else {
                            bestHandPlayer.updateWinner(currentPotSize);
                            winner = bestHandPlayer.getName();
                            gameController.setWinnerLabel(winner, bestHand);
                        }
                    }
                    // Same thing as above but the player lost and no draw.
                } else {
                    if (!secWin.isEmpty()) {
                        int divBy = currentPotSize = secWin.size();
                        for (int i : secWin) {
                            aiPlayers.get(i).updateWinner(divBy);
                        }

                    } else {
                        bestHandPlayer.updateWinner(currentPotSize);
                        winner = bestHandPlayer.getName();
                        gameController.setWinnerLabel(winner, bestHand);
                    }
                }
                // Same thing as above but the player had folded.
            } else {
                if (!secWin.isEmpty()) {
                    int divBy = currentPotSize = secWin.size();
                    for (int i : secWin) {
                        aiPlayers.get(i).updateWinner(divBy);
                    }

                } else {
                    bestHandPlayer.updateWinner(currentPotSize);
                    winner = bestHandPlayer.getName();
                    gameController.setWinnerLabel(winner, bestHand);
                }
            }
        }

    }


    /**
     * Method which checks the winners if there was one or more all-ins
     */
    private void checkAllInWinners() {
        //TODO: uppdatera efter nytt sätt att hantera drag och handstyrka

        /*
         * This method does the same thing as checkWinners except the pot is split over multiple subpots
         * and one winner is declared for each subpot
         */
        int allInPotSize;
        for (int i = potSplits.length - 1; i >= 0; i--) {
            if (potSplits[i][0] > 0) {
                allInPotSize = potSplits[i][0];
                for (Ai test : aiPlayers) {
                    if (test.getAllInViability() <= i && !test.calculateDecision().contains("fold")
                            && !test.calculateDecision().contains("lost")) {
                        potSplits[i][0] += potSplits[i][0];

                    }
                }
                potSplits[i][0] -= potSplits[i][0];

                currentPotSize -= potSplits[i][0];
                ArrayList<Integer> secWin = new ArrayList<Integer>();

                String winner = "";
                int bestHand = 0;
                Ai bestHandPlayer = new Ai(0, "");
                for (Ai ai : aiPlayers) {
                    if ((!ai.calculateDecision().equals("fold") && !ai.calculateDecision().contains("lost"))
                            && ai.getAllInViability() <= i) {
                        if (ai.handStrength() > bestHand) {
                            bestHandPlayer = ai;
                            bestHand = ai.handStrength();
                            secWin.clear();
                        } else if (ai.handStrength() == bestHand) {
                            if (ai.getHighCard() > bestHandPlayer.getHighCard()) {
                                bestHandPlayer = ai;
                                bestHand = ai.handStrength();
                                secWin.clear();
                            } else if (ai.getHighCard() == bestHandPlayer.getHighCard()) {
                                secWin.add(aiPlayers.indexOf((ai)));
                            }
                        }
                    }
                }
                if (!gameController.getPlayerDecision().contains("fold")
                        && gameController.getAllInViability() <= i) {
                    if (gameController.getHandStrength() > bestHand) {
                        gameController.setPlayerPot(allInPotSize);
                        winner = gameController.getUsername();
                        gameController.setWinnerLabel(winner, gameController.getHandStrength());
                    } else if (gameController.getHandStrength() == bestHand) {
                        if (gameController.getGetHighCard() > bestHandPlayer.getHighCard()) {
                            gameController.setPlayerPot(allInPotSize);
                            winner = gameController.getUsername();
                            gameController.setWinnerLabel(winner, gameController.getHandStrength());
                        } else if (gameController.getGetHighCard() == bestHandPlayer.getHighCard()) {
                            bestHandPlayer.updateWinner(allInPotSize / 2);
                            gameController.setPlayerPot(allInPotSize / 2);
                            winner = gameController.getUsername() + " och " + bestHandPlayer.getName();
                            gameController.setWinnerLabel(winner, bestHand);
                        } else {
                            if (!secWin.isEmpty()) {
                                int divBy = allInPotSize = secWin.size();
                                for (int x : secWin) {
                                    aiPlayers.get(x).updateWinner(divBy);
                                }

                            } else {
                                bestHandPlayer.updateWinner(allInPotSize);
                                winner = bestHandPlayer.getName();
                                gameController.setWinnerLabel(winner, bestHand);
                            }
                        }
                    } else {
                        if (!secWin.isEmpty()) {
                            int divBy = allInPotSize = secWin.size();
                            for (int x : secWin) {
                                aiPlayers.get(x).updateWinner(divBy);
                            }
                        } else {
                            bestHandPlayer.updateWinner(allInPotSize);
                            winner = bestHandPlayer.getName();
                            gameController.setWinnerLabel(winner, bestHand);
                        }
                    }
                } else {
                    if (!secWin.isEmpty()) {
                        int divBy = allInPotSize = secWin.size();
                        for (int x : secWin) {
                            aiPlayers.get(x).updateWinner(divBy);
                        }

                    } else {
                        bestHandPlayer.updateWinner(allInPotSize);
                        winner = bestHandPlayer.getName();
                        gameController.setWinnerLabel(winner, bestHand);
                    }
                }
            }
        }

    }


    /**
     * Method which checks the amount of "living" players. The amount of players whose decision is not
     * fold.
     *
     * @return Number of "living" players
     */
    private int checkLivePlayers() {

        int livePlayers = 0;
        for (Ai ai : aiPlayers) {
            if (!ai.calculateDecision().equals("fold") && !ai.calculateDecision().contains("lost")) {
                livePlayers++;
            }
        }
        if (!gameController.getPlayerDecision().equals("fold")) {
            livePlayers++;
        }
        return livePlayers;
    }


    /**
     * Method which asks the GUi to give the player a choice and calls an action when a decision has
     * been made.
     *
     * @param currentMaxBet2 the currentmaxbet.
     */
    private void askForPlayerDecision(int currentMaxBet2) throws InterruptedException {
        //TODO: uppdatera efter nytt sätt att hantera drag och handstyrka

        if (!gameController.getPlayerDecision().contains("allin")) {
            gameController.askForPlayerDecision();
            playerAction();
        } else {
            allCallorFold();
        }
    }


    /**
     * A method which controls what to do depending on the players' action.
     */
    private void playerAction() {
        //TODO: uppdatera efter nytt sätt att hantera drag och handstyrka

        String playerDecision = gameController.getPlayerDecision();
        playerDecision.toLowerCase();

        String[] split;
        split = playerDecision.split(",");
        if (playerDecision.contains("raise")) {

            currentMaxBet = Integer.parseInt(split[1]);
            currentPotSize += Integer.parseInt(split[1]);
            gameController.addLogMessage(gameController.getUsername() + " höjde med " + Integer.parseInt(split[1]));
        } else if (playerDecision.contains("fold")) {
            gameController.addLogMessage(gameController.getUsername() + " la sig");
            // do nothing. Handled elsewhere.
        } else if (playerDecision.contains("call")) {

            gameController.addLogMessage(gameController.getUsername() + " synade med " + Integer.parseInt(split[1]));
            currentPotSize += currentMaxBet;
        } else if (playerDecision.contains("check")) {
            gameController.addLogMessage(gameController.getUsername() + " passar insats");
            // do nothing. Handled elsewhere.
        } else if (playerDecision.contains("allin")) {
            gameController.addLogMessage(gameController.getUsername() + " går all-in med " + Integer.parseInt(split[1]));
            int allin = Integer.parseInt(split[1]);
            // if all-in
            if (currentMaxBet < allin) {

                if ((Integer.parseInt(split[1]) + Integer.parseInt(split[2])) > currentMaxBet) {
                    currentMaxBet = Integer.parseInt(split[1]) + Integer.parseInt(split[2]);
                }
                currentPotSize += allin;
                allin = currentPotSize;
                doAllInCheck = true;
                potSplits[psCounter][0] = allin;
                gameController.setAllInViability(psCounter);
                // Check if AiPlayers are viable for the same subpot
                for (Ai aips : aiPlayers) {
                    if ((aips.getPaidThisTurn() + aips.aiPot()) > allin) {
                        aips.setAllInViability(psCounter);
                    }
                }
                psCounter++;
            } else {
                if ((Integer.parseInt(split[1]) + Integer.parseInt(split[2])) > currentMaxBet) {
                    currentMaxBet = Integer.parseInt(split[1]) + Integer.parseInt(split[2]);
                }

                currentPotSize += allin;
                allin = currentPotSize;
                doAllInCheck = true;
                potSplits[psCounter][0] = allin;
                gameController.setAllInViability(psCounter);

                // Check if AiPlayers are viable for the same subpot
                for (Ai aips : aiPlayers) {
                    if ((aips.getPaidThisTurn() + aips.aiPot()) > allin) {
                        aips.setAllInViability(psCounter);
                    }
                }
                psCounter++;
            }
        }
        // Check all call or fold
        allCallorFold();
    }


    /**
     * Method which asks the current AIplayer to make a decision based on the current max bet.
     */
    private void askForAiDecision() throws InterruptedException {
        Ai ai = aiPlayers.get(currentPlayer);


        // Starting Hand
        if (playTurn == 0) {
            aiAction(currentPlayer);
            // Flop
        } else if (playTurn == 1) {
            ai.getHand().getCommunityCards().setFlopCards(flopCards);
            aiAction(currentPlayer);
            // Turn
        } else if (playTurn == 2) {
            ai.getHand().getCommunityCards().setTurn(turnCard);
            aiAction(currentPlayer);
            // River
        } else if (playTurn == 3) {
            ai.getHand().getCommunityCards().setRiver(riverCard);
            aiAction(currentPlayer);
        }
        // Check all call or fold
        allCallorFold();
    }


    /**
     * Method which controls what to do depending on the Ai players' action.
     *
     * @param currentPlayer current AI player
     */
    private void aiAction(int currentPlayer) {
        //TODO: uppdatera efter nytt sätt att hantera drag och handstyrka

        aiPlayers.get(currentPlayer).calculateDecision(currentMaxBet);
        Ai ai = aiPlayers.get(currentPlayer);
        ai.calculateDecision(currentMaxBet);

        String aiDecision = ai.calculateDecision();
        String[] split = aiDecision.split(",");
        if (aiDecision.contains("raise")) {
            currentMaxBet = Integer.parseInt(split[1]);
            currentPotSize += Integer.parseInt(split[1]);
            gameController.updateAiLabels(currentPlayer, aiDecision);
            gameController.addLogMessage(ai.getName() + " höjde med " + Integer.parseInt(split[1]));
        } else if (aiDecision.contains("fold")) {
            gameController.updateAiLabels(currentPlayer, aiDecision);
            gameController.addLogMessage(ai.getName() + " la sig");
        } else if (aiDecision.contains("call")) {
            if (Integer.parseInt(split[1]) > currentMaxBet) {
                currentMaxBet = Integer.parseInt(split[1]);
            }
            currentPotSize += Integer.parseInt(split[1]);

            if (Integer.parseInt(split[1]) <= 0) {
                gameController.updateAiLabels(currentPlayer, "check");
                gameController.addLogMessage(ai.getName() + " passar insats");
            } else {
                gameController.updateAiLabels(currentPlayer, split[0]);
                gameController.addLogMessage(ai.getName() + " synar med " + Integer.parseInt(split[1]));
            }

        } else if (aiDecision.contains("check")) {
            gameController.updateAiLabels(currentPlayer, aiDecision);
            gameController.addLogMessage(ai.getName() + " passar insats");
        } else if (aiDecision.contains("all-in")) {
            int allin;
            if (playTurn > 0) {
                if (!doAllInCheck) {
                    allin = Integer.parseInt(split[1]) + currentMaxBet;
                } else {
                    allin =
                            Integer.parseInt(split[1]) + (ai.getPaidThisTurn() - (Integer.parseInt(split[1])));
                }
            } else {
                allin = Integer.parseInt(split[1]);
            }
            if (currentMaxBet < allin) {

                currentMaxBet = allin;
                currentPotSize += allin;
                doAllInCheck = true;
                potSplits[psCounter][0] = allin;
                // Check if the player is viable for the same subpot
                if (gameController.getPlayerPot() + gameController.getPlayerAlreadyPaid() > allin) {
                    gameController.setAllInViability(psCounter);
                }
                // Check if AiPlayers are viable for the same subpot
                for (Ai aips : aiPlayers) {
                    if ((aips.getPaidThisTurn() + aips.aiPot()) > allin) {
                        aips.setAllInViability(psCounter);
                    }
                }
                psCounter++;
            } else {

                currentPotSize += allin;
                doAllInCheck = true;
                potSplits[psCounter][0] = allin;
                if (gameController.getPlayerPot() + gameController.getPlayerAlreadyPaid() > allin) {
                    gameController.setAllInViability(psCounter);
                }
                // Check if AiPlayers are viable for the same subpot
                for (Ai aips : aiPlayers) {
                    if ((aips.getPaidThisTurn() + aips.aiPot()) > allin) {
                        aips.setAllInViability(psCounter);
                    }
                }
                psCounter++;
            }
            gameController.updateAiLabels(currentPlayer, aiDecision);
            gameController.addLogMessage(ai.getName() + " går all-in med " + Integer.parseInt(split[1]));
        }
    }


    /**
     * Method which sets who the small and big blind players are. Depending on who the dealer is.
     *
     * @param noOfPlayers Number of players in the game
     */
    private void setBlinds(int noOfPlayers) {
        currentMaxBet = bigBlind;
        smallBlind = bigBlind / 2;
        // In heads-up play
        if (noOfPlayers == 2) {
            currentPlayer = dealer;
            smallBlindPlayer = dealer;
            bigBlindPlayer = (dealer + 1) % noOfPlayers;
            // in "not" heads up play.
        } else if (noOfPlayers >= 3) {
            currentPlayer = (dealer + 3) % noOfPlayers;
            smallBlindPlayer = (dealer + 1) % noOfPlayers;
            bigBlindPlayer = (dealer + 2) % noOfPlayers;
        }
        // If the intended dealer has lost, shift one step over until a player(AI or otherwise) has not
        // lost.
        //TODO: använd istället antalet objekt i aiPlayers, ta bort de som åkt ut ur spelet

        while (dealer != noOfPlayers - 1 && aiPlayers.get(dealer).calculateDecision().contains("lost")) {
            dealer = (dealer + 1) % noOfPlayers;
            smallBlindPlayer = (smallBlindPlayer + 1) % noOfPlayers;
            bigBlindPlayer = (bigBlindPlayer + 1) % noOfPlayers;
        }
        // if the intended smallblind has lost, shift one step over until a player(AI or otherwise) has
        // not lost.
        while (smallBlindPlayer != (noOfPlayers - 1)
                && aiPlayers.get(smallBlindPlayer).calculateDecision().contains("lost")) {
            smallBlindPlayer = (smallBlindPlayer + 1) % noOfPlayers;
            bigBlindPlayer = (bigBlindPlayer + 1) % noOfPlayers;
        }
        // if the intended bigblind has lost, shift one step over until a player(AI or otherwise) has
        // not lost.
        while (bigBlindPlayer != (noOfPlayers - 1)
                && aiPlayers.get(bigBlindPlayer).calculateDecision().contains("lost")) {
            bigBlindPlayer = (bigBlindPlayer + 1) % noOfPlayers;
        }
    }


    /**
     * Method which checks if everyone has folded or checked/called.
     */
    public void allCallorFold() {
        //TODO: uppdatera efter nytt sätt att hantera drag och handstyrka


        int noOfAIFoldedorCalled = 0;
        // For each AI player
        for (Ai ai : aiPlayers) {
            // Check if folded.
            if (ai.calculateDecision().contains("fold") || ai.calculateDecision().contains("lost")) {
                noOfAIFoldedorCalled++;
                // if not folded, check if checked or called.
            } else if (ai.calculateDecision().contains("call") && ai.getPaidThisTurn() == currentMaxBet
                    || ai.calculateDecision().contains("check") && ai.getPaidThisTurn() == currentMaxBet
                    || ai.calculateDecision().contains("all-in")) {
                noOfAIFoldedorCalled++;
                // if neither checked, called or folded, at least one AI is live.
            } else {
                allCalledOrFolded = false;
            }
        }
        // If all AI have folded or called, check if player has folded or called.
        if (noOfAIFoldedorCalled >= numberOfAi) {
            String[] split = gameController.getPlayerDecision().split(",");

            if (gameController.getPlayerDecision().contains("fold")
                    || gameController.getPlayerDecision().contains("call")) {
                allCalledOrFolded = true;
            } else if (gameController.getPlayerDecision().contains("raise")
                    && Integer.parseInt(split[1]) == currentMaxBet) {
                allCalledOrFolded = true;
            } else if (gameController.getPlayerDecision().contains("check")
                    || gameController.getPlayerDecision().contains("allin")) {
                allCalledOrFolded = true;
            } else {
                allCalledOrFolded = false;
            }
        }
    }


    /**
     * Method which returns the small blind value.
     *
     * @return Current small blind
     */
    public int getSmallBlind() {

        return smallBlind;
    }


    /**
     * Method which returns the big blind value.
     *
     * @return Current big blind
     */
    public int getBigBlind() {

        return bigBlind;
    }


    /**
     * Method which Saves chosen number of AIs
     *
     * @return Number of chosen AIs as int
     */
    public int getFixedNumberOfAIs() {

        return this.fixedNumberOfAIs;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

