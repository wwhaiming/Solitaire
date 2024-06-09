// creates a button label specifically for stock
    public static JPanel createStockLabel(int x, int y, Pile pile) {
        JPanel panel = new JPanel();
        LayoutManager overlay = new OverlayLayout(panel);
        panel.setLayout(overlay);
        JButton button = new JButton();
        button.setBackground(Color.GRAY);
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
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!stock.getPileContent().isEmpty()) {
                    Card card = stock.swapPile();
                    if (card != null) {
                        trash.addCard(card);
                        trash.unhideTop();
                        updateTrashLabel();
                        updateStockLabel();
                    }
                } else {
                    System.out.println("Stock is empty!");
                }
            }
        });
        panel.add(button);
        panel.setBounds(x, y, cardSizeX, cardSizeY);
        return panel;
    }