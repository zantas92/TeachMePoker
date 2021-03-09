package model;

import java.util.ArrayList;

public class CommunityCards {
    private boolean flop;
    private boolean turn;
    private boolean river;
    private ArrayList<Card> communityCards;

    public CommunityCards(){
        communityCards = new ArrayList<>();
        flop = false;
        turn = false;
        river = false;
    }

    public void setFlopCards(ArrayList<Card> flopCards) {
        communityCards.addAll(flopCards);
        flop = true;
    }

    public void setRiver(Card riverCard) {
        communityCards.add(riverCard);
        river = true;
    }

    public void setTurn(Card turnCard) {
        communityCards.add(turnCard);
        turn = true;
    }

    public ArrayList<Card> getCommunityCards() {
        return communityCards;
    }

    public int getRound(){
        if (river){
            return 4;
        } else if (turn){
            return 3;
        } else if (flop){
            return 2;
        } else {
            return 1;
        }
    }
}
