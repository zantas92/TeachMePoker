package controller.gameControllers;

public enum ActionType {
    DEALER("Givare"),
    SMALL_BLIND("Liten mörk"),
    BIG_BLIND("Stor mörk"),
    TO_BE_DECIDED("Inget drag än"),
    BET("Satsade"),
    CALL("Synade"),
    CHECK("Passade"),
    RAISE("Höjde"),
    ALL_IN("All-in"),
    FOLD("La sig"),
    LOST("Förlorade")
    ;

    private final String actionName;

    ActionType(String actionName){
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }

}
