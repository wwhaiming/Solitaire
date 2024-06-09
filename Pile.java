/***
Pile.java
max wang
***/
package Solitaire;

import java.util.*;
import Solitaire.Card.Suit;

public class Pile {
   private ArrayList<Card> pile = new ArrayList<Card>();
   
   // default constructor
   public Pile() {
      // pile = new ArrayList<Card>(); // This is redundant
   }
   
   // get the topmost card
   public Card getTop() {
      if (pile.size() != 0) {
         return pile.get(pile.size() - 1);
      }
      return null;
   }
   
   public void unhideTop() { 
      // temporary card for top
      Card card = getTop();
      if (card != null) {
         try {
            card.show();
            pile.set(pile.size() - 1, card);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }
   
   // select and deselect card
   public void select(int index) {
      if (index >= 0 && index < pile.size()) {
         // temp card that is getting selected
         Card card = pile.get(index);
         card.select();
         pile.set(index, card);
      }
   }

   public void deselect(int index) {
      if (index >= 0 && index < pile.size()) {
         // temp card that is getting deselected
         Card card = pile.get(index);
         card.deselect();
         pile.set(index, card);
      }
   }
   
   // hides all cards
   public void hideAll() {
      ArrayList<Card> temp = new ArrayList<Card>();
      for (Card card : pile) {
         card.hide();
         temp.add(card);
      }
      pile.clear();
      pile.addAll(temp);
   }
   
   public Card swapPile() {
      if (!pile.isEmpty()) {
         return pile.remove(pile.size() - 1);
      }
      return null;
   }
   
   public Card swapPile(int index) {
      if (index >= 0 && index < pile.size()) {
         return pile.remove(index);
      }
      return null;
   }
   
   public void reverse() {
      Collections.reverse(pile);
   }
   
   public void addCard(Card card) {
      pile.add(card);
   }
   
   public void addCard(int index, Card card) {
      pile.add(index, card);
   }

   public void addCard(ArrayList<Card> cards) {
      pile.addAll(cards);
   }
   
   public Card indexCard(int i) {
      if (i >= 0 && i < pile.size()) {
         return pile.get(i);
      }
      return null;
   }
   
   public ArrayList<Card> getPileContent() {
      return pile;
   }
   
   public String toString() {
      StringBuilder result = new StringBuilder();
      result.append("There are ").append(pile.size()).append(" cards on this pile.\n");
      result.append("The cards include:\n");
      for (Card card : pile) {
         result.append(card).append(" ");
      }
      return result.toString();
   }
}
