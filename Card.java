/***
Card.java
max wang
card class holds information of car
***/
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Card {
   // value of card
   public int value;
   // suit of card
   public int suit;
   // if its red
   public boolean isRed;
   
   // boolean for hidden
   private boolean hidden;
   // for getting the value - same as imageLabel file name
   private String name;   
   private BufferedImage cardImage;
   private BufferedImage backImage;
   
   
   // default constructor
   public Card() {
      hidden = true;
      value = 0;
      cardImage = null;
      backImage = null;
   }
   
   // constructor
   public Card(String name, BufferedImage frontImage, boolean isHidden) {
      this.name = name;
      cardImage = frontImage;
      backImage = null;
      hidden = isHidden;
      value = getValue();
      isRed = isRed();
   }
   
   // returns the value of the card
   public int getValue() { // order is King(13) Queen(12) Jack(11) 10 9 8 7 6 5 4 3 2 Ace(1)
      int value = 0;
      
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
      
      return value;
   };
   // returns true of the card is red(depending on the file name)
   public boolean isRed() { if (name.contains("diamonds") || name.contains("hearts")) { return true; } return false; };
   
   public void hide() { hidden = true; }
   public void show() { hidden = false; }
   
   public String toString() {
      String result = "";
      result += "The card is a " + name + "\n";
      result += "The value of the card is " + value + "\n";
      result += "is the card red: " + isRed + "\n";
      
      return result;
   }
}