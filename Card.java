/***
Card.java
max wang
card class holds information of car
***/
package Solitaire;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Card {
   // value of card
   public int value;
   // suit of card
   public Suit suit;
   // if its red
   public boolean isRed;
   
   // if its moveable
   public boolean moveable;
   // boolean for hidden
   public boolean hidden;
   // boolean for selected
   public boolean selected = false;
   
   // for getting the value - same as imageLabel file name
   private String name;   
   private BufferedImage cardImage;
   private BufferedImage backImage;
   
   // default constructor
   public Card(String name, BufferedImage frontImage, boolean isHidden) {
      this.name = name;
      cardImage = frontImage;
      try {
         backImage = ImageIO.read(new File("Solitaire/Cards/.card_back.png"));
      } catch (Exception e) {
      
      }
      hidden = isHidden;
      value = getValue();
      moveable = true;
      isRed = isRed();
      suit = getSuit();
      selected = isSelected();
   }
   
   // immovable constructor(for label)
   public Card(BufferedImage frontImage, Suit suit) {
      cardImage = frontImage;
      value = 0;
      this.suit = suit;
      moveable = false;
      selected = isSelected();
      hidden = false;
      value = 0;
   }
   
   
   // enum for storing suits
   public enum Suit {
      Spades,
      Hearts,
      Diamonds,
      Clubs;
   }
   
   // returns moveable
   public boolean moveable() { return moveable; };
   
   // tells you if the card is selected
   public boolean isSelected() {
      return selected;
   }
   // select and deselect card
   public void select() {
      selected = true;
   }
   public void deselect() {
      selected = false;
   }
   
   public Suit getSuit() {
      try {
         if (name.contains("spades")) {
            return Suit.Spades;
         } else if (name.contains("hearts")) {
            return Suit.Hearts;
         } else if (name.contains("diamonds")) {
            return Suit.Diamonds;
         } else if (name.contains("clubs")) {
            return Suit.Clubs;
         }
      } catch (Exception e) {
         return suit;
      }
      return null;
   }
   
   // takes the bufferedimage of the card and turns it into an image(while returning the image)
   public Image getImage() {
      BufferedImage bufferedImage;
      if (hidden) {
         bufferedImage = backImage;
      } else {
         bufferedImage = cardImage;
      }      
      Image image = new ImageIcon(bufferedImage).getImage();
      
      return image;
   }
   
   // returns the value of the card
   public int getValue() { // order is King(13) Queen(12) Jack(11) 10 9 8 7 6 5 4 3 2 Ace(1)
      int value = 0;
      
      try {
         switch(name.charAt(0)) {
            case 'a':
               value = 1;
            break;
    
            case '2':
               value = 2;
            break;
            
            case '3':
               value = 3;
            break;
            
            case '4':
               value = 4;
            break;
            
            case '5':
               value = 5;
            break;
            
            case '6':
               value = 6;
            break;
            
            case '7':
               value = 7;
            break;
            
            case '8':
               value = 8;
            break;
            
            case '9':
               value = 9;
            break;
            
            case '1':
               value = 10;
            break;
            
            case 'j':
               value = 11;
            break;
            
            case 'q':
               value = 12;
            break;
            
            case 'k':
               value = 13;
            break;
            
         }
      } catch (Exception e) {
         value = 0;
      }
      
      return value;
   };
   // returns true of the card is red(depending on the file name)
   public boolean isRed() { if (name.contains("diamonds") || name.contains("hearts")) { return true; } return false; };
   
   // for hidden and stuff
   public boolean isHidden() {
      if (hidden == true) {
         return true;
      } else if (hidden == false) {
         return false;
      }
      return false;
   }
   
   public void show() { hidden = false; }
   public void hide() { hidden = true; }
   
   public String toString() {
      String result = "";
      result += "The card is a " + name + "\n";
      result += "The value of the card is " + value + "\n";
      result += "is the card red: " + isRed + "\n";
      result += "its suit is: " + suit + "\n";
      
      return result;
   }
}