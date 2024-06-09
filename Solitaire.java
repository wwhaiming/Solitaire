/***
Solitare.java
max wang
***/
package Solitaire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import Solitaire.Card.Suit;

public class Solitaire {
   public static boolean resizable = true;
   public static JLabel notifications = new JLabel();

   public static int cardSizeX = 500 / 6; // card size(x)
   public static int cardSizeY = 726 / 6; // card size(y)
   public static int tableauIntX = 150; // tableau integration (like how many pixels in between each pile)
   public static int tableauPosY = 250; // position of tableau (in terms of y)

   // creates the objects
   public static Deck deck = new Deck();

   // stock piles
   public static Pile stock = new Pile(); // stock
   public static Pile trash = new Pile(); // trash
   
   // suit piles
   public static Pile spades = new Pile();
   public static Pile clubs = new Pile();
   public static Pile hearts = new Pile();
   public static Pile diamonds = new Pile();

   // piles for tableau
   public static Pile[] piles = new Pile[7];

   // arraylist of all the cards that are selected
   public static ArrayList<Card> selected = new ArrayList<Card>();

   public static JFrame frame = new JFrame("Solitaire");

   public static JPanel panel = new JPanel(null);

   public static void Game() {   
      // creates the frame
      frame.setSize(new Dimension(1600, 900));
      frame.setPreferredSize(new Dimension(1600, 900));
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
      // main panel setup
      panel.setLayout(null);
   
      for (int i = 0; i < 7; i++) {
         piles[i] = new Pile();
      }
      
      // sets up the notification stuff
      notifications.setPreferredSize (new Dimension(300, 75));
      notifications.setForeground(Color.BLACK);
      notifications.setFont(new Font("Serif", Font.PLAIN, 40));
      notifications.setBounds(500, 10, 1000, 75);
   
      // sets the deck(in an order)
      deck.setDeck();
   
      // shuffles the deck
      deck.shuffleDeck();
      
      // setting up the game
      piles[0].addCard(deck.swapPile());
      for (int i = 0; i <= 1; i++) piles[1].addCard(deck.swapPile());
      for (int i = 0; i <= 2; i++) piles[2].addCard(deck.swapPile());
      for (int i = 0; i <= 3; i++) piles[3].addCard(deck.swapPile());
      for (int i = 0; i <= 4; i++) piles[4].addCard(deck.swapPile());
      for (int i = 0; i <= 5; i++) piles[5].addCard(deck.swapPile());
      for (int i = 0; i <= 6; i++) piles[6].addCard(deck.swapPile());
      
      for (int i = 0; i < 23; i++) stock.addCard(deck.swapPile());
   
      // sets the topmost cards to be shown as the game starts
      unhideUpdate();
   
      // adding tableau piles to the main panel
      for (int i = 0; i < 7; i++) {
         panel.add(createTableauPilePanel(piles[i], 300 + tableauIntX * i, tableauPosY));
         panel.add(createButtonsUnder(300 + tableauIntX * i, tableauPosY, i));
      }
   
      try {
         // stock
         panel.add(createStockLabel(300, 110, stock));
         // trash
         panel.add(createTrashLabel(400, 110, trash));
      } catch (Exception e) {
         e.printStackTrace();
      }
   
      try {
         // spades
         spades.addCard(new Card(ImageIO.read(new File("Solitaire/Cards/.Spades_Suit.png")), Suit.Spades));
         panel.add(createSuitLabel(600, 110, spades));
         // clubs
         clubs.addCard(new Card(ImageIO.read(new File("Solitaire/Cards/.Clubs_Suit.png")), Suit.Clubs));
         panel.add(createSuitLabel(800, 110, clubs));
         // diamonds
         diamonds.addCard(new Card(ImageIO.read(new File("Solitaire/Cards/.Diamonds_Suit.png")), Suit.Diamonds));
         panel.add(createSuitLabel(1000, 110, diamonds));
         // hearts
         hearts.addCard(new Card(ImageIO.read(new File("Solitaire/Cards/.Hearts_Suit.png")), Suit.Hearts));
         panel.add(createSuitLabel(1200, 110, hearts));
      } catch (Exception e) {
         e.printStackTrace();
      }
   
      // Start a timer to call unhideUpdate and updateMainPanel every delay ms
      int delay = 500;
      javax.swing.Timer timer = new javax.swing.Timer(delay, 
         new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               unhideUpdate();
               updateTableauPanels();
               updateTrashLabel();
               updateSuitPiles();
               panel.add(notifications);
               if (haveYouWonYet()) {
                  if (again()) {
                     frame.dispose();
                     Game();
                  }
               }
            }
         });
      timer.start();
     
       
      frame.add(panel);
      frame.pack();
      frame.setResizable(resizable);
      frame.setVisible(true);
   }

   // logic stuff //
   // finds the pile of the card
   public static Pile findPile(Card card, Pile[] piles) {
      for (Pile pile : piles) {
         if (pile.getPileContent().contains(card)) {
            return pile;
         }
      }
      return null;
   }

   public static int pileNum(Pile pile, Pile[] piles) {
      for (int i = 0; i < piles.length; i++) {
         if (pile == piles[i]) {
            return i;
         }
      }
      return -1;
   }

   // function for moving cards(tableau)
   public static void move(Card card1, Card card2, Pile[] piles) {
      if (card1.isRed() != card2.isRed() && card1.getValue() == card2.getValue() - 1 && !card1.isHidden() && !card2.isHidden()) {
         Pile sourcePile = findPile(card1, piles);
         Pile destinationPile = findPile(card2, piles);
      
         if (sourcePile != null && destinationPile != null) {
            Pile tempPile = new Pile();
            int startIndex = sourcePile.getPileContent().indexOf(card1);
         
            for (int i = sourcePile.getPileContent().size() - 1; i >= startIndex; i--) {
               tempPile.addCard(sourcePile.swapPile());
            }
         
            while (!tempPile.getPileContent().isEmpty()) {
               destinationPile.addCard(tempPile.swapPile());
            }
            updateTableauPanels();
         }
      } else {
         notifications.setText("cannot move card here!!");
      }
   }

   // function for moving cards(trash)
   public static void move(Card card2, Pile[] piles) {
      if (trash.getTop().isRed() != card2.isRed() && trash.getTop().getValue() == card2.getValue() - 1 && !trash.getTop().isHidden() && !card2.isHidden()) {
         int destinationPile = pileNum(findPile(card2, piles), piles);
         piles[destinationPile].addCard(trash.swapPile());
         updateTableauPanels();
         updateTrashLabel();
      } else {
         notifications.setText("cannot move card here!!");
      }
   }
   
   // ui stuff //
   // create each suit pile
   public static JPanel createSuitLabel(int x, int y, Pile pile) {
      JPanel panel = new JPanel();
      LayoutManager overlay = new OverlayLayout(panel);
      panel.setLayout(overlay);
      JButton button = new JButton();
      panel.setLayout(overlay);
      button.setMaximumSize(new Dimension(cardSizeX, cardSizeY));
      if (!pile.getPileContent().isEmpty()) {
         try {
            ImageIcon img = new ImageIcon(pile.getTop().getImage().getScaledInstance(cardSizeX, cardSizeY, Image.SCALE_SMOOTH));
            button.setIcon(img);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      button.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               selected.add(pile.getTop());
               if (selected.size() == 2) {
               // conditions for moving a card to a suit pile: card must have a value 1 greater; they must have the same suit
                  if (pile.getTop().getValue() + 1 == selected.get(0).getValue() && pile.getTop().getSuit() == selected.get(0).getSuit()) {
                     if (trash.getPileContent().contains(selected.get(0))) {
                        switch (pile.getTop().getSuit()) {
                           case Spades:
                              spades.addCard(trash.swapPile());
                              break;
                           case Clubs:
                              clubs.addCard(trash.swapPile());
                              break;
                           case Diamonds:
                              diamonds.addCard(trash.swapPile());
                              break;
                           case Hearts:
                              hearts.addCard(trash.swapPile());
                              break;
                        }
                     } else {
                        switch (pile.getTop().getSuit()) {
                           case Spades:
                              spades.addCard(piles[pileNum(findPile(selected.get(0), piles), piles)].swapPile());
                              break;
                           case Clubs:
                              clubs.addCard(piles[pileNum(findPile(selected.get(0), piles), piles)].swapPile());
                              break;
                           case Diamonds:
                              diamonds.addCard(piles[pileNum(findPile(selected.get(0), piles), piles)].swapPile());
                              break;
                           case Hearts:
                              hearts.addCard(piles[pileNum(findPile(selected.get(0), piles), piles)].swapPile());
                              break;
                        }
                     }
                     notifications.setText("moved");
                  } else {
                     notifications.setText("card does not meet requirements");
                  }                  
               }
            }
         });
      panel.setBorder(BorderFactory.createLineBorder(Color.black));
      panel.add(button);
      panel.setBounds(x, y, cardSizeX, cardSizeY);
      return panel;
   }
   
   // create each tableau pile
   public static JLayeredPane createTableauPilePanel(Pile pile, int x, int y) {
      JLayeredPane layeredPane = new JLayeredPane();
      layeredPane.setBounds(x, y, cardSizeX, cardSizeY * pile.getPileContent().size());
      int j = 0;
      for (Card card : (ArrayList<Card>)pile.getPileContent()) {
         if (!pile.getPileContent().isEmpty()) {
            JPanel panel = createCardPanel(0, 35 * j, card);
            panel.setBounds(0, 35 * j, cardSizeX, cardSizeY);
            layeredPane.add(panel, Integer.valueOf(j));
         }
         j++;
      }
      return layeredPane;
   }

   // creates each panel using the card labels
   public static JPanel createCardPanel(int x, int y, Card card) {
      JPanel panel = new JPanel();
      LayoutManager overlay = new OverlayLayout(panel);
      panel.setLayout(overlay);
      panel.add(createCardButton(card));
      panel.setBounds(x, y, cardSizeX, cardSizeY);
      return panel;
   }

   // creates each button using the cards(for tableau)
   public static JButton createCardButton(Card card) {
      ImageIcon img = null;
      try {
         img = new ImageIcon(card.getImage().getScaledInstance(cardSizeX, cardSizeY, Image.SCALE_SMOOTH));
      } catch (Exception e) {
         e.printStackTrace();
      }
      JButton button = new JButton(img);
      button.setBackground(Color.GRAY);
      button.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.0f)));
      button.setMaximumSize(new Dimension(cardSizeX, cardSizeY));
      button.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if (selected.contains(card)) {
                  selected.remove(card);
                  button.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.0f)));
                  notifications.setText("deselected");
               } else {
                  selected.add(card);
                  button.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
                  notifications.setText("selected");
               }
               updateSelected();
               if (selected.size() == 2) {
                  handleCardMove();
               }
            }
         });
   
      if (card.isSelected()) {
         button.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
      }
      return button;
   }


  // creates a button label specifically for stock
   public static JPanel createStockLabel(int x, int y, Pile pile) {
      JPanel panel = new JPanel();
      LayoutManager overlay = new OverlayLayout(panel);
      panel.setLayout(overlay);
      JButton button = new JButton();
      button.setBackground(Color.GRAY);
      panel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.0f)));
      panel.setLayout(overlay);
      button.setMaximumSize(new Dimension(cardSizeX, cardSizeY));
      if (!pile.getPileContent().isEmpty()) {
         try {
            ImageIcon img = new ImageIcon(pile.getTop().getImage().getScaledInstance(cardSizeX, cardSizeY, Image.SCALE_SMOOTH));
            button.setIcon(img);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      button.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if (!stock.getPileContent().isEmpty()) {
                  Card card = stock.swapPile();
                  if (card != null) {
                     trash.addCard(card);
                     notifications.setText("moved to trash");
                     trash.unhideTop();
                     updateTrashLabel();
                     updateStockLabel();
                  }
               } else {
                  Pile temp = new Pile();
                  temp = trash;
                  trash = new Pile();
                  stock.addCard(temp.getPileContent());
                  stock.reverse();
                  stock.hideAll();
                  updateTrashLabel();
                  updateStockLabel();
               }
            }
         });
      panel.add(button);
      panel.setBounds(x, y, cardSizeX, cardSizeY);
      return panel;
   }
   
   // creates a jpanel specifically for the buttons underneath tableau
   public static JPanel createButtonsUnder(int x, int y, int pileNum) {
      JPanel panel = new JPanel();
      LayoutManager overlay = new OverlayLayout(panel);
      panel.setLayout(overlay);
      JButton button = new JButton();
      button.setBackground(Color.GRAY);
      panel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.0f)));
      button.setMaximumSize(new Dimension(cardSizeX, cardSizeY));
      button.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if (selected.size() == 1) {
                  if (selected.get(0).getValue() == 13) {
                     if (trash.getPileContent().contains(selected.get(0))) {
                        piles[pileNum].addCard(trash.swapPile());
                     } else {
                        final int altPile = pileNum(findPile(selected.get(0), piles), piles); // pile number of the pile where cards are being taken from
                        System.out.println(altPile);
                        final int altIndex = piles[altPile].getPileContent().indexOf(selected.get(0)); // card index of the card thats being moved
                        for (int i = piles[altPile].getPileContent().size() - 1; i >= altIndex; i--) {
                           piles[pileNum].addCard(piles[altPile].swapPile(altIndex));
                        }
                     }
                  } else {
                     notifications.setText("this card isnt a king!!");
                  }
                  selected.clear();
               } else {
                  notifications.setText("you cant select this card!!");
               }
            }
         });
      panel.add(button);
      panel.setBounds(x, y, cardSizeX, cardSizeY);
      return panel;
   }

   // creates a jpanel specifically for trash
   public static JPanel createTrashLabel(int x, int y, Pile pile) {
      JPanel panel = new JPanel();
      LayoutManager overlay = new OverlayLayout(panel);
      panel.setLayout(overlay);
      JButton button = new JButton();
      try {
         button = new JButton(new ImageIcon(pile.getTop().getImage().getScaledInstance(cardSizeX, cardSizeY, Image.SCALE_SMOOTH)));
      } catch (Exception e) {
        // Catch NullPointerException and other exceptions
         System.out.println("Trash is empty.");
      }
      button.setBackground(Color.GRAY);
      panel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.0f)));
      button.setMaximumSize(new Dimension(cardSizeX, cardSizeY));
      button.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               Card topCard = pile.getTop();
               if (topCard != null) {
                  if (selected.contains(topCard)) {
                     selected.remove(topCard);
                     notifications.setText("deselected");
                  } else {
                     selected.add(topCard);
                     notifications.setText("selected");
                  }
                  updateSelected();
               } else {
                  notifications.setText("trash pile is empty!");
               }
            }
         });
   
      if (pile.getTop() != null && pile.getTop().isSelected()) {
         panel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
      } else {
         panel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.0f)));
      }
      panel.add(button);
      panel.setBounds(x, y, cardSizeX, cardSizeY);
      return panel;
   }

   // Method to update the main panel
   public static void updateMainPanel() {
      // Only remove and re-add stock and trash labels(for better efficency
      updateStockLabel();
      updateTrashLabel();
      updateTableauPanels();
      updateSelected();
   
      panel.revalidate();
      panel.repaint();
   }

   public static void updateTableauPanels() {
      for (int i = 0; i < 7; i++) {
         final int index = i;
         // gets all components currently added to the main panel
         Component[] components = panel.getComponents();
      
         for (Component component : components) {
            // Check if the component is an instance of JLayeredPane (which is used for tableau piles)
            if (component instanceof JLayeredPane) {
               JLayeredPane layeredPane = (JLayeredPane) component;
               if (layeredPane.getBounds().x == 300 + tableauIntX * index && layeredPane.getBounds().y == tableauPosY) { // check if the layeredPanes bounds match the 
                                                                                                                        // position of the tableau pile at 'index'
                  panel.remove(layeredPane);
                  panel.add(createTableauPilePanel(piles[index], 300 + tableauIntX * index, tableauPosY));
               }               
            }
            
            if (component instanceof JPanel) {
               JPanel underPanel = (JPanel) component;
               if (underPanel.getBounds().x == 300 + tableauIntX * index && underPanel.getBounds().y == tableauPosY) {
                  panel.remove(underPanel);
                  panel.add(createButtonsUnder(300 + tableauIntX * i, tableauPosY, i));
               }               
            }
            
            // System.out.println(component);
            
         }
      }
   }
   
   // updates the trash label when stock is pressed
   public static void updateTrashLabel() {
      for (Component component : panel.getComponents()) {
         if (component instanceof JPanel) {
            JPanel jPanel = (JPanel) component;
            if (jPanel.getBounds().x == 400 && jPanel.getBounds().y == 110) {
               panel.remove(jPanel);
               panel.add(createTrashLabel(400, 110, trash));
               break;
            }
         }
      }
   
      panel.revalidate();
      panel.repaint();
   }
   
   // Update the stock label when the stock pile changes
   public static void updateStockLabel() {
      for (Component component : panel.getComponents()) {
         if (component instanceof JPanel) {
            JPanel jPanel = (JPanel) component;
            if (jPanel.getBounds().x == 300 && jPanel.getBounds().y == 110) {
               panel.remove(jPanel);
               panel.add(createStockLabel(300, 110, stock));
               break;
            }
         }
      }
      panel.revalidate();
      panel.repaint();
   }
   
   // pretty much same thing as tableau update function but with suits referenced
   public static void updateSuitPiles() {
      int[] suitPilePositions = {600, 800, 1000, 1200};
      Pile[] suitPiles = {spades, clubs, diamonds, hearts};
   
      for (int i = 0; i < 4; i++) {
         final int x = suitPilePositions[i];
         final Pile pile = suitPiles[i];
         Component[] components = panel.getComponents();
      
         for (Component component : components) {
            if (component instanceof JPanel) {
               JPanel jPanel = (JPanel) component;
               if (jPanel.getBounds().x == x && jPanel.getBounds().y == 110) {
                  panel.remove(jPanel);
                  panel.add(createSuitLabel(x, 110, pile));
                  break;
               }
            }
         }
      }
      panel.revalidate();
      panel.repaint();
   }
   
   // updates what cards are selected
   public static void updateSelected() {
      try {
         System.out.println("updateSelected() called");
         if (selected.size() >= 1) {
            System.out.println("Selected size: " + selected.size());
            Card selectedCard = selected.get(0);
         
            // Check and handle null selectedCard
            if (selectedCard == null) {
               System.out.println("Selected card is null");
               return;
            }
         
            for (int i = 0; i < 7; i++) {
               ArrayList<Card> pileContent = piles[i].getPileContent();
               for (int j = 0; j < pileContent.size(); j++) {
                  Card currentCard = pileContent.get(j);
                  if (selectedCard == currentCard) {
                     System.out.println("Selecting card at pile " + i + ", index " + j);
                     piles[i].select(i);
                  } else {
                     piles[i].deselect(i);
                  }
               }
            }
            
            Card topTrashCard = trash.getTop();
            if (topTrashCard != null) {
               if (selectedCard == topTrashCard) {
                  System.out.println("selecting card from trash");
                  trash.select(trash.getPileContent().size() - 1);
               } else {
                  trash.deselect(trash.getPileContent().size() - 1);
               }
            }
         } else {
            System.out.println("No cards selected");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   
   // Unhides card if it is on top of the pile
   public static void unhideUpdate() {
      for (int i = 0; i < 7; i++) {
         piles[i].unhideTop();
      }
   }
   
   // function that checks for wins
   public static boolean haveYouWonYet() {
      if (spades.getTop().value == 13 && clubs.getTop().value == 13 && diamonds.getTop().value == 13 && hearts.getTop().value == 13) {
         return true;
      }
      return false;
   }
   
   // asks user if they wanna play again
   public static boolean again() {
      int response = JOptionPane.showConfirmDialog(null, "congrats!!\nplay again?", "again?", JOptionPane.YES_NO_OPTION);
      return response == JOptionPane.YES_OPTION;
   }
   
   public static void handleCardMove() {
      if (selected.size() == 2) {
         Card card1 = selected.get(0);
         Card card2 = selected.get(1);
      
         if (findPile(card1, piles) == findPile(card2, piles)) {
            selected.clear();
            notifications.setText("you can't select from the same pile!!");
         } else {
            try {
               if (card1.isRed() != card2.isRed() && card1.getValue() == card2.getValue() - 1 && !card1.isHidden() && !card2.isHidden()) {
                  if (trash.getPileContent().contains(card1)) { // if the card is moving from the trash pile
                     move(card2, piles);
                  } else {
                     move(card1, card2, piles);
                  }
                  selected.clear();
                  updateMainPanel();
                  notifications.setText("moved");
               } else {
                  notifications.setText("you can't move that card there");
                  selected.clear();
               }
            } catch (Exception ex) {
               notifications.setText("move failed: " + ex.getMessage());
               selected.clear();
            }
         }
      }
   }


   public static void main(String[] args) {
      try {
         Game();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
