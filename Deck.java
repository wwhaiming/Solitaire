/***
Deck.java
Max Wang
holds all 52 cards
***/
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class Deck {
   // list of all the cards in the deck
   private ArrayList<Card> deck = new ArrayList<Card>();
   private int cards;
   
   // directory for image folder
   static File imageFolder = new File("Cards");
   
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
               deck.add(new Card(card.getName(), ImageIO.read(card), false));
            } catch (IOException e) {
            
            }
         }
      }
   }
   
   // shuffles all the cards in the deck
   public void shuffleDeck() {
      Collections.shuffle(deck);
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