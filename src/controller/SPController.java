package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import controller.aiControllers.Ai;
import model.Card;
import model.Deck;
import controller.gameControllers.GameController;


/**
 *
 * 
 * @author Rikard Almgren
 * @version 1.0
 *
 */
public class SPController extends Thread {

  private Deck deck;
  private LinkedList<Ai> aiPlayers = new LinkedList<Ai>();
  private int numberOfAi;
  private int playTurn = 0;
  private int dealer = 0;
  private int currentPlayer = 0;
  private int bigBlindPlayer;
  private int smallBlindPlayer;
  private int smallBlind;
  private int bigBlind = 10;
  private int potSize;
  private int currentPotSize;
  private int currentMaxBet;
  private int blindCounter;
  private Card card1;
  private Card card2;
  private Card turn;
  private Card river;
  private Card[] flop = new Card[3];
  private int numberOfPlayers = 0;
  private boolean allCalledOrFolded = false;
  private boolean winnerDeclared = false;
  private ArrayList<String> name = new ArrayList<String>();
  private GameController gameController;
  private int fixedNumberOfAIs;
  private int[][] potSplits;
  private boolean doAllInCheck;
  private int psCounter = 0;


  /**
   * Method which receives and sets a number of starting variables and for the game to be set up.
   * 
   * @param noOfAi Number of AI-players
   * @param potSize The potsize for the table(game).
   * @param playerName The players' name.
   */
  public void startGame(int noOfAi, int potSize, String playerName) {

    this.fixedNumberOfAIs = noOfAi;
    gameController.disableButtons();
    this.potSize = potSize;
    this.numberOfAi = noOfAi;
    setNames();
    numberOfPlayers = noOfAi + 1;
    bigBlind = (int) (potSize * 0.02); // Calculates bigBlind
    if (bigBlind < 2) {
      bigBlind = 2;
    }
    currentMaxBet = bigBlind;
    this.smallBlind = bigBlind / 2;
    gameController.setPlayerPot((potSize));
    // create aiPlayers
    for (int i = 0; i < noOfAi; i++) {
      aiPlayers.add(new Ai(potSize, name.remove(0)));
    }
    gameController.setAiPlayers(aiPlayers, false, 69);
    potSplits = new int[numberOfPlayers][1];

    try {
      setupPhase();
    } catch (InstantiationException | IllegalAccessException e) {
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
  private void setupPhase() throws InstantiationException, IllegalAccessException {

    // Check if the player lost last turn
    if (gameController.getPlayerPot() > bigBlind) {
      /*
       * if not, reset the all-in check and potsplit counter Create a new deck, shuffle it and deal
       * cards
       */
      doAllInCheck = false;
      psCounter = 0;
      deck = new Deck();
      deck.shuffle();
      card1 = deck.getCard();
      card2 = deck.getCard();
      gameController.setStartingHand(card1, card2);
      this.currentPotSize = 0;
      potSplits = new int[numberOfPlayers][1];
      gameController.updatePots(potSplits, currentPotSize);
      gameController.playerReset("");
      /*
       * Reset the AI players unless they've lost
       */
      for (Ai ai : aiPlayers) {
        System.out.println(ai.getName() + " : " + ai.getDecision() + (ai.aiPot() < bigBlind));
        ai.setBigBlind(0, false);
        ai.setSmallBlind(0, false);
        ai.setPaidThisTurn(0);
        ai.setAllInViability(99);
        if (!ai.getDecision().contains("lost")) {
          ai.setDecision("");
          card1 = deck.getCard();
          card2 = deck.getCard();
          ai.setStartingHand(card1, card2);
        }
      }
      // set the blinds
      setBlinds(numberOfPlayers);
      // Generate a flop, turn and river.
      for (int i = 0; i < flop.length; i++) {
        flop[i] = deck.getCard();
      }
      turn = deck.getCard();
      river = deck.getCard();
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


  /**
   * Method that runs the gameround itself
   */
  public void run() {

    gameController.hideAllIn();
    gameController.activeSlider();
    String winner = "";

    Card[] turnCards = {flop[0], flop[1], flop[2], turn};
    Card[] riverCards = {flop[0], flop[1], flop[2], turn, river};
    while (playTurn < 4) {
      gameController.roundStatus(playTurn);
      // set dealer, smallblind and bigBlind.
      if (playTurn == 0) {
        int playerNr = numberOfPlayers - 1;
        if (playerNr != 1) {
          try {
            if (dealer != playerNr) {
              Thread.sleep(1000);
              gameController.aiAction(dealer, "Dealer");
            }
            if (smallBlindPlayer != playerNr) {
              Thread.sleep(1000);
              gameController.aiAction(smallBlindPlayer, "SmallBlind");
            }
            if (bigBlindPlayer != playerNr) {
              Thread.sleep(1000);
              gameController.aiAction(bigBlindPlayer, "BigBlind");
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      } else if (playTurn == 1) {
        gameController.setFlopTurnRiver(flop);
      } else if (playTurn == 2) {
        gameController.setFlopTurnRiver(turnCards);
      } else if (playTurn == 3) {
        gameController.setFlopTurnRiver(riverCards);
      }

      while (!allCalledOrFolded) {
        // Check if its the players turn.
        if (currentPlayer == numberOfPlayers - 1) {
          if (!gameController.getPlayerDecision().equals("fold")
              && !gameController.getPlayerDecision().contains("allin")) {
            if (!(checkLivePlayers() > 1)) {
              gameController.setPlayerPot(currentPotSize);
              winner = gameController.getUsername();
              gameController.setWinnerLabel(winner, 99);
              winnerDeclared = true;
              break;
            }
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            askForPlayerDecision(currentMaxBet);
          }
          // if it isn't the players turn, let the AI do their turn
        } else {
          if (!aiPlayers.get(currentPlayer).getDecision().contains("lost")) {
            if (!aiPlayers.get(currentPlayer).getDecision().contains("fold")
                && !aiPlayers.get(currentPlayer).getDecision().contains("all-in")) {
              if (!(checkLivePlayers() > 1)) {
                aiPlayers.get(currentPlayer).updateWinner(currentPotSize);
                winner = aiPlayers.get(currentPlayer).getName();
                gameController.setWinnerLabel(winner, 98);
                winnerDeclared = true;
                break;
              }

              askForAiDecision();
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
          }
        }


        // After each player(AI or real), update the pot(s)
        gameController.updatePots(potSplits, currentPotSize);
        // Prevent AI from thinking it's a new turn.
        if (currentPlayer != numberOfPlayers - 1) {
          aiPlayers.get(currentPlayer).setSameTurn(true);
        }
        // move on to the next player
        currentPlayer = (currentPlayer + 1) % numberOfPlayers;
        // check if everyone has checked, called or folded.
        allCallorFold();
      }
      // Next turn
      playTurn++;
      allCalledOrFolded = false;
      // if a player Hasn't folded, gone all in or lost, reset their decision
      for (Ai ai : aiPlayers) {
        if (!ai.getDecision().contains("fold") && !ai.getDecision().contains("lost")
            && !ai.getDecision().contains("all-in")) {
          ai.setDecision("");
          ai.setSameTurn(false);
        }
      }
      // if winner was declared earlier, break the loop here and start a new round
      if (winnerDeclared) {
        break;
      }
    }
    // If the game goes to the final round and no one has won yet, check the winner.
    if (playTurn >= 4 && !winnerDeclared) {
      checkWinner();
    }
    // If an AI player has run out of money, they have lost.
    for (Ai ai : aiPlayers) {
      if (ai.aiPot() < bigBlind && !ai.getDecision().contains("lost")) {
        ai.setDecision("lost");
        ai.updateWinner(-ai.aiPot());
        gameController.setUIAiStatus(aiPlayers.indexOf(ai), "inactive");
      }
      System.out.println(ai.getName() + " : " + ai.getDecision() + (ai.aiPot() < bigBlind));

    }

    // Reset values
    winnerDeclared = false;
    playTurn = 0;
    blindCounter++;
    // update the blinds
    if (blindCounter >= 15) {
      bigBlind += (int) (potSize / numberOfPlayers * 0.02);
      currentMaxBet = bigBlind;
      smallBlind = bigBlind / 2;
      blindCounter = 0;
    }
    // Set new dealer
    dealer = (dealer + 1) % numberOfPlayers;

    try {
      setupPhase();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

  }


  /**
   * Method which checks who the winner is.
   */
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
        if (!ai.getDecision().equals("fold")) {
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

    /*
     * This method does the same thing as checkWinners except the pot is split over multiple subpots
     * and one winner is declared for each subpot
     */
    int allInPotSize;
    for (int i = potSplits.length - 1; i >= 0; i--) {
      if (potSplits[i][0] > 0) {
        allInPotSize = potSplits[i][0];
        for (Ai test : aiPlayers) {
          if (test.getAllInViability() <= i && !test.getDecision().contains("fold")
              && !test.getDecision().contains("lost")) {
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
          if ((!ai.getDecision().equals("fold") && !ai.getDecision().contains("lost"))
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
      if (!ai.getDecision().equals("fold") && !ai.getDecision().contains("lost")) {
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
  private void askForPlayerDecision(int currentMaxBet2) {

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

    String playerDecision = gameController.getPlayerDecision();
    playerDecision.toLowerCase();

    String[] split;
    if (playerDecision.contains("raise")) {
      split = playerDecision.split(",");
      currentMaxBet = Integer.parseInt(split[1]);
      currentPotSize += Integer.parseInt(split[1]);
    } else if (playerDecision.contains("fold")) {
      // do nothing. Handled elsewhere.
    } else if (playerDecision.contains("call")) {
      split = playerDecision.split(",");
      currentPotSize += currentMaxBet;
    } else if (playerDecision.contains("check")) {
      // do nothing. Handled elsewhere.
    } else if (playerDecision.contains("allin")) {
      split = playerDecision.split(",");
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
  private void askForAiDecision() {

    Ai ai = aiPlayers.get(currentPlayer);
    // Starting Hand
    if (playTurn == 0) {
      ai.makeDecision(currentMaxBet);
      aiAction(currentPlayer);
      // Flop
    } else if (playTurn == 1) {
      ai.makeDecision(currentMaxBet, flop);
      aiAction(currentPlayer);
      // Turn
    } else if (playTurn == 2) {
      ai.makeDecision(currentMaxBet, turn);
      aiAction(currentPlayer);
      // River
    } else if (playTurn == 3) {
      ai.makeDecision(currentMaxBet, river);
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

    Ai ai = aiPlayers.get(currentPlayer);

    String aiDecision = ai.getDecision();
    String[] split;
    if (aiDecision.contains("raise")) {
      split = aiDecision.split(",");
      currentMaxBet = Integer.parseInt(split[1]);
      currentPotSize += Integer.parseInt(split[1]);
      gameController.aiAction(currentPlayer, aiDecision);

    } else if (aiDecision.contains("fold")) {
      gameController.aiAction(currentPlayer, aiDecision);
    } else if (aiDecision.contains("call")) {

      split = aiDecision.split(",");
      if (Integer.parseInt(split[1]) > currentMaxBet) {
        currentMaxBet = Integer.parseInt(split[1]);
        currentPotSize += Integer.parseInt(split[1]);
      } else {
        currentPotSize += Integer.parseInt(split[1]);
      }

      if (Integer.parseInt(split[1]) <= 0) {
        gameController.aiAction(currentPlayer, "check");
      } else {
        gameController.aiAction(currentPlayer, split[0]);
      }

    } else if (aiDecision.contains("check")) {
      gameController.aiAction(currentPlayer, aiDecision);
    } else if (aiDecision.contains("all-in")) {
      split = aiDecision.split(",");
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
      gameController.aiAction(currentPlayer, aiDecision);
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
    while (dealer != noOfPlayers - 1 && aiPlayers.get(dealer).getDecision().contains("lost")) {
      dealer = (dealer + 1) % noOfPlayers;
      smallBlindPlayer = (smallBlindPlayer + 1) % noOfPlayers;
      bigBlindPlayer = (bigBlindPlayer + 1) % noOfPlayers;
    }
    // if the intended smallblind has lost, shift one step over until a player(AI or otherwise) has
    // not lost.
    while (smallBlindPlayer != (noOfPlayers - 1)
        && aiPlayers.get(smallBlindPlayer).getDecision().contains("lost")) {
      smallBlindPlayer = (smallBlindPlayer + 1) % noOfPlayers;
      bigBlindPlayer = (bigBlindPlayer + 1) % noOfPlayers;
    }
    // if the intended bigblind has lost, shift one step over until a player(AI or otherwise) has
    // not lost.
    while (bigBlindPlayer != (noOfPlayers - 1)
        && aiPlayers.get(bigBlindPlayer).getDecision().contains("lost")) {
      bigBlindPlayer = (bigBlindPlayer + 1) % noOfPlayers;
    }
    // set small and bigBlind
    if (smallBlindPlayer == noOfPlayers - 1) {
      gameController.playerSmallBlind(smallBlind);
      aiPlayers.get(bigBlindPlayer).setBigBlind(bigBlind, true);
    } else if (bigBlindPlayer == noOfPlayers - 1) {
      aiPlayers.get(smallBlindPlayer).setSmallBlind(smallBlind, true);
      gameController.playerBigBlind(bigBlind);
    } else {

      aiPlayers.get(smallBlindPlayer).setSmallBlind(smallBlind, true);
      aiPlayers.get(bigBlindPlayer).setBigBlind(bigBlind, true);
      aiPlayers.get(smallBlindPlayer).setDecision("SmallBlind");
      aiPlayers.get(bigBlindPlayer).setDecision("BigBlind");

      // sets dealer as well
    }
    if (dealer != noOfPlayers - 1) {
    } else {
      gameController.playerIsDealer(dealer);
    }
    // update GUI.
    gameController.setBlindsMarker(dealer, smallBlindPlayer, bigBlindPlayer);
    this.currentPotSize = smallBlind + bigBlind;
    gameController.updatePots(potSplits, currentPotSize);
  }


  /**
   * Method which checks if everyone has folded or checked/called.
   */
  public void allCallorFold() {

    int noOfAIFoldedorCalled = 0;
    // For each AI player
    for (Ai ai : aiPlayers) {
      // Check if folded.
      if (ai.getDecision().contains("fold") || ai.getDecision().contains("lost")) {
        noOfAIFoldedorCalled++;
        // if not folded, check if checked or called.
      } else if (ai.getDecision().contains("call") && ai.getPaidThisTurn() == currentMaxBet
          || ai.getDecision().contains("check") && ai.getPaidThisTurn() == currentMaxBet
          || ai.getDecision().contains("all-in")) {
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

}

