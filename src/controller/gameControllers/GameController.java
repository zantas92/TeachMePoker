package controller.gameControllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import controller.SceneController;
import controller.aiControllers.Ai;
import controller.SPController;
import controller.Sound;
import javafx.scene.control.*;
import javafx.scene.text.*;
import model.Card;
import model.Scenes;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * @author Amin Harirchian, Vedrana Zeba, Lykke Levin, Rikard Almgren
 * @version 1.0
 */

public class GameController {

    @FXML
    private ImageView btCheck;
    @FXML
    private ImageView btCall;
    @FXML
    private ImageView btFold;
    @FXML
    private ImageView btRaise;
    @FXML
    private Slider slider;
    @FXML
    private Label lbPlayerAction;
    @FXML
    private Label lbPotValue;
    @FXML
    private Label lbAllIn;
    @FXML
    private Pane handStrengthImgArea;
    @FXML
    private ImageView cardOne;
    @FXML
    private Pane playerCardsArea;
    @FXML
    private Label adviceLabel;
    @FXML
    private Label helpLabel;
    @FXML
    private Label userName;
    @FXML
    private Label raiseLabel;
    @FXML
    private Pane tableCardArea;
    @FXML
    private AnchorPane AnchorPaneAll;

    @FXML
    private ImageView imgRoundStatus;
    @FXML
    private Pane paneRounds;

    @FXML
    private ImageView imgPlayerOneCards;
    @FXML
    private ImageView imgPlayerTwoCards;
    @FXML
    private ImageView imgPlayerThreeCards;
    @FXML
    private ImageView imgPlayerFourCards;
    @FXML
    private ImageView imgPlayerFiveCards;

    @FXML
    private Label labelPlayerOneName;
    @FXML
    private Label labelPlayerTwoName;
    @FXML
    private Label labelPlayerThreeName;
    @FXML
    private Label labelPlayerFourName;
    @FXML
    private Label labelPlayerFiveName;

    @FXML
    private Label labelPlayerOnePot;
    @FXML
    private Label labelPlayerTwoPot;
    @FXML
    private Label labelPlayerThreePot;
    @FXML
    private Label labelPlayerFourPot;
    @FXML
    private Label labelPlayerFivePot;

    @FXML
    private Label labelPlayerOneAction;
    @FXML
    private Label labelPlayerTwoAction;
    @FXML
    private Label labelPlayerThreeAction;
    @FXML
    private Label labelPlayerFourAction;
    @FXML
    private Label labelPlayerFiveAction;

    @FXML
    private ImageView imgCard1;
    @FXML
    private ImageView imgCard2;
    @FXML
    private ImageView imgCard3;
    @FXML
    private ImageView imgCard4;
    @FXML
    private ImageView imgCard5;
    @FXML
    private ImageView imgCard6;
    @FXML
    private ImageView imgCard7;

    @FXML
    private ImageView ivBigBlind;
    @FXML
    private ImageView ivSmallBlind;
    @FXML
    private ImageView ivDealer;

    //logg
    @FXML
    public ScrollPane logScrollPane;
    @FXML
    private TextFlow logTextFlow;
    @FXML
    public Text logText;

    @FXML
    public ImageView ivSound;
    @FXML
    public MenuItem miNewGame;
    @FXML
    public MenuItem miClose;
    @FXML
    public MenuItem miSettings;
    @FXML
    public MenuItem miAbout;
    @FXML
    public MenuItem miTutorial;

    @FXML
    public Pane panePot;
    @FXML
    public Label subPotOne;
    @FXML
    public Label subPotTwo;
    @FXML
    public Label subPotThree;
    @FXML
    public Label subPotFour;
    @FXML
    public Label subPotFive;
    @FXML
    public Label subPotSix;
    @FXML
    public Label mainPot;

    private ImageView handStrengthImgView = new ImageView();
    private ImageView[] collectionOfCardsAi;
    private ImageView[] collectionOfCardsTable;
    private Label[][] collectionOfLabelsAi;
    private Label[] collectionOfPots;

    private WinnerBox winnerBox;
    private SPController spController;

    private boolean playerMadeDecision = false;

    private ArrayList<Ai> aiPlayers;
    private int[][] aiPositions;

    private int tablePot;

    private int prevPlayerActive;
    private boolean isReady = false;
    private int AllInViability = 0;

    /**
     * Method for initializing FXML
     */
    public void initialize() {

        // Groups together labels for each AI-position.
        this.collectionOfLabelsAi =
                new Label[][]{{labelPlayerOneName, labelPlayerOnePot, labelPlayerOneAction},
                        {labelPlayerTwoName, labelPlayerTwoPot, labelPlayerTwoAction},
                        {labelPlayerThreeName, labelPlayerThreePot, labelPlayerThreeAction},
                        {labelPlayerFourName, labelPlayerFourPot, labelPlayerFourAction},
                        {labelPlayerFiveName, labelPlayerFivePot, labelPlayerFiveAction}};


        // Placeholders for the AI (based on their position). Shows their
        // cardbacks/no cards or
        // highlighted cards (AI-frame).
        this.collectionOfPots = new Label[6];

        this.collectionOfCardsAi = new ImageView[]{imgPlayerOneCards, imgPlayerTwoCards,
                imgPlayerThreeCards, imgPlayerFourCards, imgPlayerFiveCards};

        // Used to place AI-players into the right position depending on the
        // chosen number of AI:s.
        this.aiPositions = new int[][]{{2}, {0, 2, 4}, {0, 1, 2, 3, 4, 5}};

        // Table cards placeholders.
        this.collectionOfCardsTable =
                new ImageView[]{imgCard3, imgCard4, imgCard5, imgCard6, imgCard7};

        // Used by method: inactivateAllAiCardGlows and aiAction.
        this.prevPlayerActive = -1;

    }


    /**
     * Used to show labels and AI-frame.
     *
     * @param position Position on the screen (0-4).
     */
    public void setShowUIAiBar(int position) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                collectionOfLabelsAi[position][0].setVisible(true);
                collectionOfLabelsAi[position][1].setVisible(true);
                collectionOfLabelsAi[position][2].setVisible(true);
                collectionOfCardsAi[position].setVisible(true);
            }
        });

    }


    /**
     * Used to change AI-label "name" based on position.
     *
     * @param position Position on the screen (0-4).
     * @param name     The label for the AI's name.
     */
    public void setLabelUIAiBarName(int position, String name) {

        collectionOfLabelsAi[position][0].setText(name);
    }


    /**
     * Used to change AI-label "pot" based on position.
     *
     * @param position Position on the screen (0-4).
     * @param pot      The label for the AI's pot.
     */
    public void setLabelUIAiBarPot(int position, String pot) {

        collectionOfLabelsAi[position][1].setText(pot + "SEK");
    }


    /**
     * Used to change AI-label "action" based on position.
     *
     * @param position Position on the screen (0-4).
     * @param action   The label for the AI's action.
     */
    public void setLabelUIAiBarAction(int position, String action) {

        collectionOfLabelsAi[position][2].setText(action);

    }


    /**
     * Changes the AI-frame based on position and state.
     *
     * @param position Position on the screen (0-4).
     * @param state    The state can either be inactive (folded/lost), idle (waiting for it's turn),
     *                 active (currently it's turn).
     */
    public void setUIAiStatus(int position, String state) {

        String resource = "resources/images/"; // 122, 158
        Image hideCards = new Image(Paths.get(resource + "aiBarWithoutCards.png").toUri().toString(),
                122, 158, true, true);
        Image showCards = new Image(Paths.get(resource + "aiBarWithCards.png").toUri().toString(), 122,
                158, true, true);
        Image showActiveCards =
                new Image(Paths.get(resource + "aiBarWithCardsCurrentPlayer.png").toUri().toString(), 122,
                        158, true, true);

        switch (state) {
            case "inactive" -> collectionOfCardsAi[position].setImage(hideCards);
            case "idle" -> collectionOfCardsAi[position].setImage(showCards);
            case "active" -> collectionOfCardsAi[position].setImage(showActiveCards);
        }
    }


    /**
     * Sets the SPController for this gameController
     *
     * @param spController an instance of the class SPController
     */
    public void setSPController(SPController spController) {

        this.spController = spController;
        spController.setGameController(this);
    }


    public void setPlayerAction(ActionType actionType, int sum){
        disableButtons();
        if (sum != 0){
        lbPlayerAction.setText(actionType.getActionName() + sum + "SEK");
        } else {
            lbPlayerAction.setText(actionType.getActionName());
        }
        playerMadeDecision = true;
        updatePlayerValues(actionType.getActionName());

        //TODO: switch med actionType istället för separata metoder (rad 342-434)

    }

    /**
     * Disables all buttons and shows player-frame's action as check.
     */
    public void playerCheck() {
        disableButtons();
        lbPlayerAction.setText("check");
        playerMadeDecision = true;
        updatePlayerValues("Check");
        Sound.playSound("check");
    }


    /**
     * Disables all buttons and shows player-frame's action as fold.
     */
    public void playerFold() {

        disableButtons();
        lbPlayerAction.setText("fold");
        playerMadeDecision = true;
        updatePlayerValues("Fold");
        Sound.playSound("fold");
    }


    /**
     * Disables all buttons and shows player-frame's action as call, and the called amount. Calculates
     * and withdraws amount from player-pot.
     */
    public void playerCall() {

        disableButtons();
        /*
         * Player's pot - (Current maxbet - already paid (prev rounds)) THE PLAYER'S POT
         */
        this.playerPot -= (spController.getCurrentMaxBet() - alreadyPaid);
        /*
         * Already paid + (Current maxbet - already paid) = WHAT THE PLAYER HAS ALREADY PAID
         */
        this.alreadyPaid += (spController.getCurrentMaxBet() - alreadyPaid);
        this.decision = "call," + Integer.toString(alreadyPaid);
        playerMadeDecision = true;
        Sound.playSound("chipSingle");
        updatePlayerValues("Call, $" + Integer.toString(alreadyPaid));
    }


    /**
     * Disables all buttons and shows player-frame's action as raise, and the raised amount.
     * Calculates and withdraws amount from player-pot and adjusts already paid.
     */
    public void playerRaise() {

        disableButtons();
        /*
         * If the player hasn't matched the current maxbet
         */
        if (spController.getCurrentMaxBet() != alreadyPaid) {
        }

        int raisedBet = (int) (slider.getValue());
        this.playerPot -= raisedBet;
        /*
         * (raised amount + the amount the player has to match(if the player has to match)) = THE
         * PLAYER'S POT
         */

        this.decision = "raise," + (raisedBet + spController.getCurrentMaxBet()); // Chosen
        // raised
        // amount

        playerMadeDecision = true;
        Sound.playSound("chipMulti");

        updatePlayerValues("Raise, $" + raisedBet);

        try {
            if (playerPot == 0) { // Checks if the player has gone all in.
                updatePlayerValues("All-In, $" + raisedBet);
                this.decision = "allin," + (raisedBet) + "," + alreadyPaid;
                this.alreadyPaid += raisedBet;
                slider.setDisable(true);
                showAllIn();
                disableButtons();


            } else {
                updatePlayerValues("Raise, $" + raisedBet);
                this.alreadyPaid += raisedBet;

                /*
                 * Already paid + (raised amount + the amount the player has to match(if the player has to
                 * match)) = WHAT THE PLAYER HAS ALREADY PAID
                 */
            }
        } catch (Exception ignored) {
        }

    }


    /**
     * Updates player-frame's labels (action and player pot) based on action.
     *
     * @param action Call, Check, Raise or Fold
     */
    public void updatePlayerValues(String action) {
        //TODO: låt SP-conttroller skicka med summan som ska visas
        lbPotValue.setText("$" + (playerPot));
        lbPlayerAction.setText(action);
        setSliderValues();
    }


    /**
     * DEPRECATED. Never successfully implemented.
     */
    public void saveGame() {
    }


    /**
     * Sets the slider's min and max values based on the player-pot. Sets minimum sliderValue based on
     * BigBlind.
     */
    public void setSliderValues() {
        //TODO: låt SP-controller skicka med summan som ska visas

        int calcWithdraw = 0;
        if (spController.getCurrentMaxBet() != alreadyPaid) { // If the player hasn't matched the current max bet
            calcWithdraw = spController.getCurrentMaxBet() - alreadyPaid; // Calculates amount for the player to match max bet
        }

        slider.setMax(playerPot);
        if (calcWithdraw > spController.getBigBlind()) {
            slider.setMin(calcWithdraw);
        } else if (spController.getBigBlind() <= playerPot) { // Sets minimum value required in order to raise.
            slider.setMin(spController.getBigBlind());
        } else {
            slider.setMin(0);
        }

        if ((slider.getMax() - slider.getMin()) > 4) {
            slider.setMajorTickUnit((slider.getMax() - slider.getMin()) / 4);
        } else {
            slider.setMajorTickUnit(25);
        }
        slider.setMinorTickCount(4);
    }


    /**
     * Triggers when the player uses the slider to choose raise amount.
     */
    public void sliderChange() {

        slider.valueProperty().addListener(e -> {
            raiseLabel.setText(String.valueOf((int) slider.getValue()));

        });
    }


    /**
     * Creates a new ruleController.
     *
     * @throws IOException
     */


    /**
     * Method which returns the potValue for the table.
     *
     * @return tablePotValue the potValue for the table.
     */
    public double getPotValue() {
        //TODO: flytta till SP-controller

        return tablePot;
    }


    /**
     * Sets the player's name.
     *
     * @param name Used to sets the players name on the UI.
     */
    public void setUsername(String name) {
        userName.setText(name);
    }


    /**
     * Returns the players name
     *
     * @return userName the players name.
     */
    public String getUsername() {

        return userName.getText();
    }


    /**
     * Set Allin label visible
     */
    public void showAllIn() {

        lbAllIn.setVisible(true);
    }


    /**
     * Set Allin label deactive
     */
    public void hideAllIn() {

        lbAllIn.setVisible(false);
    }


    /**
     * Set slider active
     */
    public void activeSlider() {

        slider.setDisable(false);
    }


    /**
     * Clears AI action and updates the new and current AI-pot at the end of the round.
     *
     * @param ai Which AI to update values for.
     */
    public void endOfRound(int ai) {

        Platform.runLater(new Runnable() {

            private volatile boolean shutdown;


            @Override
            public void run() {

                while (!shutdown) {
                    //TODO: kalla på metod i SP-controller som återställer värdena på ai och skickar med dem som returer

                    setLabelUIAiBarPot(ai, Integer.toString(aiPlayers.get(ai).aiPot()));
                    setLabelUIAiBarAction(ai, "");
                    shutdown = true;
                }
            }
        });
    }


    /**
     * Sets the starting hand pre-flop for the player.
     *
     * @param playerHand The players hand from SPController
     */
    public void setStartingHand(Hand playerHand) {

        isReady = false;
        // Clears the table cards
        Platform.runLater(this::clearCommunityCards);

        Platform.runLater(() -> {
            for (int i = 0; i < 5; i++) { // Resets AI labels and removes all
                // previous glow-effects.
                setUIAiStatus(i, "idle");
                setLabelUIAiBarAction(i, "");
            }
        });

        isReady = true;
        updatePlayerCards();
        handHelp();
    }


    /**
     * Checks the player's hand and gives tips and highlights cards based on the method
     * getHighlightedCards (important during pre-flop situation).
     */
    public void updatePlayerCards() {

        Platform.runLater(() -> {
            Hand playerHand = spController.getPlayerHand();
            Card card1 = playerHand.getPersonalCards().get(0);
            Card card2 = playerHand.getPersonalCards().get(1);
            playerCardsArea.requestLayout();
            playerCardsArea.getChildren().clear();
            String cardOne =
                    "resources/images/" + card1.getCardValue() + card1.getCardSuit().charAt(0) + ".png";
            String cardTwo =
                    "resources/images/" + card2.getCardValue() + card2.getCardSuit().charAt(0) + ".png";

            if (playerHand.getHighlightedCards()
                    .contains(card1)) {
                cardOne =
                        "resources/images/" + card1.getCardValue() + card1.getCardSuit().charAt(0) + "O.png";
            }

            if (playerHand.getHighlightedCards()
                    .contains(card2)) {
                cardTwo =
                        "resources/images/" + card2.getCardValue() + card2.getCardSuit().charAt(0) + "O.png";
            }

            ImageView imgCard1;
            ImageView imgCard2;

            Image image = new Image(Paths.get(cardOne).toUri().toString(), 114, 148, true, true);
            imgCard1 = new ImageView(image);
            playerCardsArea.getChildren().add(imgCard1);
            imgCard1.setX(0);
            imgCard1.setY(0);

            image = new Image(Paths.get(cardTwo).toUri().toString(), 114, 148, true, true);
            imgCard2 = new ImageView(image);
            playerCardsArea.getChildren().add(imgCard2);
            imgCard2.setX(105);
            imgCard2.setY(0);
            updatePlayerValues("");
        });
    }


    /**
     * Uses the getHighlightedCards to highlight and show cards on the table.
     *
     * @param communityCards Set of cards shown on the table.
     */
    public void setCommunityCards(ArrayList<Card> communityCards) {
        Hand playerHand = spController.getPlayerHand();

        Platform.runLater(() -> {
            tableCardArea.getChildren().clear(); // Clears if there's cards on the table (UI)
            tableCardArea.requestLayout();

            int xCord = 0;
            for (int i = 0; i < communityCards.size(); i++) { // Loops through all cards and highlights the correct ones and
                // places them on the table (UI)
                String baseCard = "";
                if (playerHand.getHighlightedCards().contains(communityCards.get(i))) {
                    baseCard = "resources/images/" + communityCards.get(i).getCardValue()
                            + communityCards.get(i).getCardSuit().charAt(0) + "O.png";
                } else {
                    baseCard = "resources/images/" + communityCards.get(i).getCardValue()
                            + communityCards.get(i).getCardSuit().charAt(0) + ".png";
                }
                if (i == 1) {
                    xCord = 110; // First card
                } else if (i > 1) {
                    xCord += 105;
                }
                Image imageTemp = new Image(Paths.get(baseCard).toUri().toString(), 114, 148, true, true);

                collectionOfCardsTable[i] = new ImageView(imageTemp);
                tableCardArea.getChildren().add(collectionOfCardsTable[i]);
                collectionOfCardsTable[i].setX(xCord);
                collectionOfCardsTable[i].setY(0);
            }
        });
        handHelp();
        updatePlayerCards();
    }


    /**
     * Clears the cards on the table.
     */
    public void clearCommunityCards() {

        Platform.runLater(() -> {
            tableCardArea.getChildren().clear();
        });
    }


    /**
     * Method which makes the player the smallblind.
     */
    public void playerSmallBlind() {
        Platform.runLater(() -> {

            ivSmallBlind.relocate(520, 425);

        });
        updatePots(new int[1][0], spController.getPotSize());

    }


    /**
     * Method which makes the player the bigBlind
     *
     * @param i the amount to pay
     */
    public void playerBigBlind(int i) {
        //TODO: värdena regleras i SP-controller,  position i ai-objekt och i spelarens i SP-controller

        this.alreadyPaid += i;
        this.playerPot -= i;
        Platform.runLater(() -> {
            ivBigBlind.relocate(520, 425);

        });
        updatePots(new int[1][0], spController.getPotSize());
    }


    /**
     * Returns the amount of money that the player has already bet
     *
     * @return The amount of money that the player has already bet
     */
    public int getPlayerAlreadyPaid() {
        //TODO: flytta till SP-controller

        return this.alreadyPaid;
    }


    /**
     * Method which sets the player as dealer
     *
     * @param i not used.
     */
    public void playerIsDealer(int i) {
        //TODO: kolla upp vad int i är?
        if ((int) ivBigBlind.getLayoutX() == 520 || (int) ivSmallBlind.getLayoutX() == 520) {
            ivDealer.setLayoutX(500);
            ivDealer.setLayoutY(425);
        } else {
            ivDealer.setLayoutX(520);
            ivDealer.setLayoutY(425);
        }
    }


    /**
     * Method which fetches the advice for the player and displays it in the bottom left pane
     */
    public void handHelp() {
        Platform.runLater(() -> {
            HandValueAdvice advice = spController.getPlayerHand().handValueAdvice();
            int powerBarValue = advice.getPower();
            helpLabel.setText("Du har: " + advice.getHandValueName());
            adviceLabel.setText("Råd: \n" + advice.getAdvice());

            try {
                Image handStrengthImg;

                if (powerBarValue < 1) {
                    handStrengthImg = new Image(new FileInputStream("resources/images/weakHand.png"));
                } else if (powerBarValue == 2) {
                    handStrengthImg = new Image(new FileInputStream("resources/images/mediumWeakHand.png"));
                } else if (powerBarValue == 3) {
                    handStrengthImg = new Image(new FileInputStream("resources/images/mediumStrongHand.png"));
                } else if (powerBarValue == 4) {
                    handStrengthImg = new Image(new FileInputStream("resources/images/strongHand.png"));
                } else {
                    handStrengthImg = new Image(new FileInputStream("resources/images/weakHand.png"));
                }

                handStrengthImgArea.getChildren().remove(handStrengthImgView);
                handStrengthImgView = new ImageView(handStrengthImg);
                handStrengthImgView.setFitHeight(handStrengthImgArea.getHeight());
                handStrengthImgView.setFitWidth(handStrengthImgArea.getWidth());
                handStrengthImgArea.getChildren().add(handStrengthImgView);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        });

    }

    public void addLogMessage(String logMessage) {
        Platform.runLater(() -> {
            Text newLogText = new Text(logMessage + "\n");
            newLogText.setFont(Font.font("Tw Cen MT", FontWeight.SEMI_BOLD, 16));
            logTextFlow.getChildren().add(newLogText);
            logScrollPane.setVvalue(1.0);
        });
    }

    /**
     * Returns the players decision.
     *
     * @return The players decision.
     */
    public String getPlayerDecision() {
        //TODO: flytta till SP-controller

        return decision;
    }


    /**
     * Method which controls the players decision
     */
    public void askForPlayerDecision() throws InterruptedException {
        System.out.println("Current Thread" + Thread.currentThread().getName());
        handleButtons();
        playerMadeDecision = false;
        while (!playerMadeDecision) {
            spController.sleep(100);
        }
    }

    /**
     * Shows/hides player-buttons based on allowed actions.
     */
    public void handleButtons() {
        //TODO: kalla på från SP-controller och skicka med knappar som ska visas/döljas


        if (alreadyPaid == spController.getCurrentMaxBet()) {
            // show check, hide all other
            btCheck.setVisible(true);
            btCall.setVisible(false);
            btRaise.setVisible(true);
            btFold.setVisible(true);
        } else {
            if (alreadyPaid < spController.getCurrentMaxBet()
                    && (playerPot + alreadyPaid) >= spController.getCurrentMaxBet()) {
                // hide check, show call
                btCheck.setVisible(false);
                btCall.setVisible(true);
                btFold.setVisible(true);
            } else {
                // hide call, hide check
                btCheck.setVisible(false);
                btCall.setVisible(false);
                btFold.setVisible(true);

            }

            // show/hide raise
            btRaise.setVisible((spController.getCurrentMaxBet() - alreadyPaid) + spController.getBigBlind() <= playerPot
                    && playerPot != 0);
        }
        inactivateAllAiCardGlows();
    }


    /**
     * Disables all player-buttons.
     */
    public void disableButtons() {

        btCall.setVisible(false);
        btRaise.setVisible(false);
        btCheck.setVisible(false);
        btFold.setVisible(false);
    }


    /**
     * Method which returns the players pot
     *
     * @return the playerpot
     */
    public int getPlayerPot() {
        //TODO: flytta till SP-controller

        return playerPot;
    }


    /**
     * Method which dims an AI player
     *
     * @param AI an AI player
     */
    public void removeAiPlayer(int AI) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                collectionOfLabelsAi[AI][0].setVisible(false);
                collectionOfLabelsAi[AI][1].setVisible(false);
                collectionOfLabelsAi[AI][2].setVisible(false);
                collectionOfCardsAi[AI].setVisible(false);
            }
        });
    }


    /**
     * Places the AI-players in the correct position depending on chosen number of players.
     *
     * @param aiPlayers All the AI-players that are active.
     */
    public void setAiPlayers(ArrayList<Ai> aiPlayers) {
        for (int i = 0; i < 5; i++) {
            removeAiPlayer(i);
        }
        this.aiPlayers = aiPlayers;
        int numberOfAIs = aiPlayers.size();
        if (numberOfAIs == 1) {
            setShowUIAiBar(2);
            aiPlayers.get(0).setPosition(2);
        } else if (numberOfAIs == 3) {
            setShowUIAiBar(0);
            aiPlayers.get(0).setPosition(0);
            setShowUIAiBar(2);
            aiPlayers.get(1).setPosition(2);
            setShowUIAiBar(4);
            aiPlayers.get(2).setPosition(5);
        } else if (numberOfAIs == 5) {
            setShowUIAiBar(0);
            aiPlayers.get(0).setPosition(0);
            setShowUIAiBar(1);
            aiPlayers.get(1).setPosition(1);
            setShowUIAiBar(2);
            aiPlayers.get(2).setPosition(2);
            setShowUIAiBar(3);
            aiPlayers.get(3).setPosition(3);
            setShowUIAiBar(4);
            aiPlayers.get(4).setPosition(4);
        }

    }


    /**
     * Updates AI-frame based on aiIndex-position and decision with the method setUIAiStatus.
     *
     * @param aiIndex Chosen AI to update AI-frame
     */
    public void updateAiLabels(int aiIndex) {
        try {
            Ai ai = aiPlayers.get(aiIndex);
                setUIAiStatus(ai.getPosition(), "active");
                this.prevPlayerActive = ai.getPosition();
            Platform.runLater(new Runnable() {
                private volatile boolean shutdown;

                @Override
                public void run() {

                    /**
                     * Sets name, pot and action for the AI's (UI)
                     */
                    while (!shutdown) {
                        setLabelUIAiBarName(ai.getPosition(), ai.getName());
                        setLabelUIAiBarPot(ai.getPosition(), ai.aiPot() + " SEK");
                        setLabelUIAiBarAction(ai.getPosition(), ai.getActionString());
                        shutdown = true;
                    }
                }
            });
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error while processing. Probably cause: Shutdown of current game");
        }
    }


    /**
     * Formats action label for AI.
     *
     * @param decision fold/lost/check/call/raise/all-in/Dealer/SmallBlind/BigBlind
     * @return Formatted decision
     */
    public String getFormattedDecision(String decision) {
        //TODO: används inte, ActionType har ersatt

        String actionText = "Error";

        if (decision.contains("fold")) {
            actionText = "Fold";
        } else if (decision.contains("lost")) {
            actionText = "Lost";
        } else if (decision.contains("check")) {
            actionText = "Check";
        } else if (decision.contains("call")) {
            actionText = "Call";
        } else if (decision.contains("raise")) {
            String[] decisionAi = decision.split(",");
            actionText = "Raise, $" + decisionAi[1];
        } else if (decision.contains("all-in")) {
            actionText = "All-In";
        } else if (decision.contains("Dealer")) {
            actionText = "Dealer";
        } else if (decision.contains("SmallBlind")) {
            actionText = "Small Blind, $" + spController.getSmallBlind();
        } else if (decision.contains("BigBlind")) {
            actionText = "Big Blind, $" + spController.getBigBlind();
        }

        return actionText;
    }


    /**
     * This metod makes sure that during the players turn, the previous AI is considered idle
     */
    public void inactivateAllAiCardGlows() {

        if (prevPlayerActive != -1) {
            setUIAiStatus(prevPlayerActive, "idle");
            this.prevPlayerActive = -1;
        }
    }


    /**
     * Method which returns if the UI is ready
     *
     * @return isReady are we ready?
     */
    public boolean getIsReady() {

        return isReady;
    }


    /**
     * Method which creates a popup to inform the player that s/he lost.
     */
    public void playerLost() {
        //TODO: kalla på från SP-controller och skicka med vinnaren

        Platform.runLater(() -> {

            winnerBox = new WinnerBox();
            winnerBox.displayWinner("Förlust",
                    "Tyvärr, du förlorade och dina pengar är slut. Bättre lycka nästa gång!", 5,
                    winnerHand);

            SceneController.switchScene(Scenes.MainMenu);

        });
    }

    public void setBlindsMarker(int dealer, int smallBlindPlayer, int bigBlindPlayer) {
        //TODO: använd position-värden till varje spelare istället för markerPos

        int[][] markerPos = new int[5][2];
        Platform.runLater(() -> {

            // set MarkerPos TEST
            markerPos[0][0] = 300;
            markerPos[0][1] = 360;

            markerPos[1][0] = 375;
            markerPos[1][1] = 172;

            markerPos[2][0] = 745;
            markerPos[2][1] = 172;

            markerPos[3][0] = 1010;
            markerPos[3][1] = 220;

            markerPos[4][0] = 1010;
            markerPos[4][1] = 360;

            if (dealer <= 4) {
                ivDealer.relocate(markerPos[dealer][0], markerPos[dealer][1]);
            }
            if (smallBlindPlayer <= 4) {
                ivSmallBlind.relocate(markerPos[smallBlindPlayer][0], markerPos[smallBlindPlayer][1]);
            }
            if (bigBlindPlayer <= 4) {
                ivBigBlind.relocate(markerPos[bigBlindPlayer][0], markerPos[bigBlindPlayer][1]);
            }


        });
    }


    /**
     * Shows current round.
     *
     * @param round int between 0-3 ("roundPreFlop", "roundFlop", "roundTurn", "roundRiver").
     */
    public void roundStatus(int round) {

        String[] roundStatus = new String[]{"roundPreFlop", "roundFlop", "roundTurn", "roundRiver"};

        Platform.runLater(() -> {
            paneRounds.getChildren().remove(imgRoundStatus);
            Image tempImage =
                    new Image(Paths.get("resources/images/" + roundStatus[round] + ".png").toUri().toString(),
                            175, 56, true, true);
            imgRoundStatus = new ImageView(tempImage);
            imgRoundStatus.setImage(tempImage);
            imgRoundStatus.setPreserveRatio(false);
            paneRounds.getChildren().add(imgRoundStatus);
        });
    }


    /**
     * Creates a winnerWindow that displays the winner of the round.
     *
     * @param winner    Name of the winner from spController.
     * @param handValue Int number from spController that represent the value of the winning hand.
     */
    public void setWinnerLabel(String winner, HandValue handValue) {
        String handValueString = handValue.getHandValueName();

        if (!winner.equals(getUsername()) && (handValue.getHandValueRank() < 10)) {
            Platform.runLater(() -> {
                winnerBox = new WinnerBox();
                winnerBox.displayWinner("Rundans vinnare", winner, 2, handValueString);
            });
        } else if (winner.equals(getUsername()) && (handValue.getHandValueRank() < 10)) {
            Platform.runLater(() -> {
                Sound.playSound("coinSound");
                winnerBox = new WinnerBox();
                winnerBox.displayWinner("Rundans vinnare", winner, 1, handValueString);

            });
        } else if (winner.equals(getUsername()) && (handValue.getHandValueRank() > 10)) {
            Platform.runLater(() -> {
                Sound.playSound("coinSound");
                winnerBox = new WinnerBox();
                winnerBox.displayWinner("Rundans vinnare", winner, 3, handValueString);

            });
        } else if (!winner.equals(getUsername()) && (handValue.getHandValueRank() > 10)) {
            Platform.runLater(() -> {
                winnerBox = new WinnerBox();
                winnerBox.displayWinner("Rundans vinnare", winner, 4, handValueString);

            });
        }
    }


    /**
     * Method which returns the player viabilitylevel for potSplits
     *
     * @return AllInViability viabilityLevel
     */
    public int getAllInViability() {

        return AllInViability;
    }


    /**
     * Method which sets a viabilitylevel
     *
     * @param allInViability
     */
    public void setAllInViability(int allInViability) {

        if (allInViability < AllInViability) {
            AllInViability = allInViability;
        }
    }


    /**
     * Method which updates the various pots in the UI
     *
     * @param potSplits an Array of subPots during All-ins
     * @param tablePot  the main tablePot
     */
    public void updatePots(int[][] potSplits, int tablePot) {
        //TODO: delar upp den totala potten till spelarna? bättre att skriva en loop som hämtar varje spelares pot?

        if (spController.getFixedNumberOfAIs() == 5) {
            this.collectionOfPots =
                    new Label[]{subPotOne, subPotTwo, subPotThree, subPotFour, subPotFive, subPotSix};
        } else if (spController.getFixedNumberOfAIs() == 3) {
            this.collectionOfPots = new Label[]{subPotOne, subPotTwo, subPotThree, subPotFour};
        } else if (spController.getFixedNumberOfAIs() == 1) {
            this.collectionOfPots = new Label[]{subPotOne, subPotTwo};
        }
        Platform.runLater(() -> {
            String[] potOrder = {"Sub-Pot One: ", "Sub-Pot Two: ", "Sub-Pot Three: ", "Sub-Pot Four: ",
                    "Sub-Pot Five: ", "Sub-Pot Six: "};
            for (int i = 0; i < collectionOfPots.length; i++) {
                if (potSplits[i][0] > 0) {
                    collectionOfPots[i].setText(potOrder[i] + "$" + potSplits[i][0]);
                    collectionOfPots[i].setVisible(true);
                    collectionOfPots[i].setLayoutX(10);
                    collectionOfPots[i].setLayoutY(30 * (i + 1) + 70);
                } else {
                    collectionOfPots[i].setVisible(false);
                }
            }
            mainPot.setText("Table Pot: $" + tablePot);
            mainPot.setLayoutX(295.0);
            mainPot.setLayoutY(290.0);
            mainPot.setVisible(true);
        });
    }


}
