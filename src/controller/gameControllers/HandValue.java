package controller.gameControllers;

public enum HandValue {
    HIGH_CARD(1, "Högt kort"),
    ONE_PAIR(2, "Ett par"),
    TWO_PAIR(3, "Två par"),
    THREE_OF_A_KIND(4, "Triss"),
    FLUSH(5, "Färg"),
    STRAIGHT(6, "Stege"),
    FULL_HOUSE(7, "Kåk"),
    FOUR_OF_A_KIND(8, "Fyrtal"),
    STRAIGHT_FLUSH(9, "Färgstege"),
    ROYAL_STRAIGHT_FLUSH(10, "Royal Straight Flush"),
    ;

    private final int handValueRank;
    private final String handValueName;

    HandValue(int handValueRank, String handValueName){
        this.handValueRank = handValueRank;
        this.handValueName = handValueName;
    }

    public int getHandValueRank() {
        return handValueRank;
    }

    public String getHandValueName() {
        return handValueName;
    }
}

