/***
Deck.java
Max Wang
holds all 52 cards
***/
package Solitaire;

import java.util.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class Deck {
   // list of all the cards in the deck
   private ArrayList<Card> deck = new ArrayList<Card>();
   private int cards;
   
   // directory for image folder
   static File imageFolder = new File("Solitaire/Cards");
   
   // constructor for deck
   public Deck() {
      deck = new ArrayList<Card>();
   }
   
   // image filter for function set deck
   static FilenameFilter IMAGE_FILTER = new FilenameFilter() {
      public boolean accept(File dir, String name) {
         if (name.endsWith(".png") && name.startsWith(".") == false) {
            return (true);
         }
         return (false);
      } 
   };
   
   // loads all the cards in the deck
   public void setDeck() {
      if (imageFolder.isDirectory()) {
         for(File card : imageFolder.listFiles(IMAGE_FILTER)) {
            try {
               deck.add(new Card(card.getName(), ImageIO.read(card), true));
            } catch (IOException e) {
            
            }
         }
      }
   }
   
   // shuffles all the cards in the deck
   public void shuffleDeck() {
      Collections.shuffle(deck);
   }
   
   public Card swapPile() {
      Card temp = null;
      try {
         temp = deck.get(deck.size() - 1);
         deck.remove(deck.size() - 1);
         return temp;
      } catch (Exception e) {
         return temp;
      }
   }
   
   public ArrayList getPileContent() {
      return deck;
   }
   
   // toString
   public String toString() {
      String result = ""; 
      result += "There are " + deck.size() + " cards on this deck. ";
      result += "The cards include:\n";
      for (int i = 0; i < deck.size(); i++) {
         result += deck.get(i) + " ";
      }
      
      return result;
   }
}