package testClasses;
import controller.SPController;
import controller.gameControllers.CorrectHandCalc;
import model.Card;
import model.Suit;

import javax.swing.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class JUnitTest {





    @org.junit.jupiter.api.Test

    void Testhandcalc(){

        model.Card one = new Card(Suit.HEARTS, 10, new ImageIcon());
        model.Card two = new Card(Suit.HEARTS, 9, new ImageIcon());
        model.Card three = new Card(Suit.DIAMONDS, 10, new ImageIcon());
        model.Card four = new Card(Suit.SPADES, 10, new ImageIcon());
        model.Card five = new Card(Suit.CLUBS, 10, new ImageIcon());


        ArrayList<Card> array = new ArrayList<>();

        array.add(one);
        array.add(two);
        array.add(three);
        array.add(four);
        array.add(five);


        CorrectHandCalc c = new CorrectHandCalc(array);

        assertEquals("FOUR OF A KIND", c.calculateHand());



    }


}
