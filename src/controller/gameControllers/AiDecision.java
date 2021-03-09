package controller.gameControllers;

import controller.aiControllers.AiDecide;

public class AiDecision {
    private AiDecide aiDecide;
    private ActionType aiActionType;
    private int amountToBet;
    private int playTurn;
    private int minimumBet;
    private HandValueAdvice handValueAdvice;


    public AiDecision(){


    }

    public void calculateDecision(ActionType actionType, int minimumBet){
        this.aiActionType = actionType;
        this.minimumBet = minimumBet;

        //TODO: implementera och glöm ej amountToBet och uppdatera actionType

        aiDecide = new AiDecide();

    }

    public void setHandValueAdvice(HandValueAdvice handValueAdvice) {
        this.handValueAdvice = handValueAdvice;
    }

    public void setAiActionType(ActionType aiActionType) {
        this.aiActionType = aiActionType;
    }

    public ActionType getAiActionType() {
        return aiActionType;
    }

    public void setAmountToBet(int amountToBet) {
        this.amountToBet = amountToBet;
    }

    public int getAmountToBet() {
        return amountToBet;
    }

    public void setPlayTurn(int playTurn) {
        this.playTurn = playTurn;
    }

    public int getPlayTurn() {
        return playTurn;
    }
}
