/***
Solitare.java
max wang
***/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Solitare {
   public static void main(String[] args) {
      // creates the objects
      Deck deck = new Deck();
      JFrame frame = new JFrame();
      
      // sets the deck(in order)
      deck.setDeck();
      
      // shuffles the deck
      deck.shuffleDeck();
      
      // returns the toString
      System.out.print(deck);         
      
      // frame.getContentPane().setLayout(new FlowLayout());
//       for (Card : 
   }
}