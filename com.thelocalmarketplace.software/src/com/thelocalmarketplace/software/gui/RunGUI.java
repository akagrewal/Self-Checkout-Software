package com.thelocalmarketplace.software.gui;
import com.thelocalmarketplace.software.logic.AttendantLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class RunGUI extends JFrame implements logicObserver {
    // for receipt building on GUI
    private List<JLabel> labelList = new ArrayList<>();
    // Paneling on GUI
    private JPanel leftPanel;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    // For logic testing - delete after all GUI is done
    private int total;
    private JLabel totalLabel;

    //This is what allows Logic to happen when I click a button
    private GUILogic guiLogicInstance;

    private static final Insets insets = new Insets(0, 0, 0, 0);


    //For Testing Purposes - to run GUI
    public RunGUI() {
        SelfCheckoutGUI();
    }


    // Constructor to initialize GUILogic
    public RunGUI(GUILogic guiLogicInstance) {
        this.guiLogicInstance = guiLogicInstance;
    }

    /**
     * Main method that creates the GUI
     */
    private void SelfCheckoutGUI() {
        //Frame Size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocation(0,0);

        guiLogicInstance = new GUILogic();

        // Create and add panels to the card panel
        // When you add new panel, make sure to add one here too
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(StartSessionPanel(), "welcomePanel");
        cardPanel.add(createAddItemsPanel(), "AddItemsPanel");
        cardPanel.add(createThankYouPanel(), "thankYouPanel");
        cardPanel.add(createPaymentPanel(), "paymentPanel");
        cardPanel.add(createCashBillPanel(), "cashBillPanel");
        cardPanel.add(createCashCoinPanel(), "cashCoinPanel");
//        cardPanel.add(createNumberPad(), "numpadPanel");
        add(cardPanel);

        // Show the welcome panel initially
        cardLayout.show(cardPanel, "welcomePanel");
        //Or use method switchPanels("welcomePanel")

        setVisible(true);

        // Open Attendant Frame beside the Self CheckOut
        AttendantFrame attendantFrame = new AttendantFrame();
        attendantFrame.AttendantFrame();

        HardwareActionSimulations actionsFrame = new HardwareActionSimulations();
        actionsFrame.setVisible(true);

    }

    private static void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
        Insets insets = new Insets(0, 0, 0, 0);
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0, anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }

    // Customer Screen 1
    private JPanel StartSessionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton nextButton = new JButton("Start adding items to cart");
        nextButton.setFont(new Font("Arial", Font.BOLD, 26));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiLogicInstance.StartSessionButtonPressed();
                switchPanels("AddItemsPanel");
            }
        });

        JButton frenchButton = new JButton("Français");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // change all text to french when button pressed. not implemented yet
            }
        });

        JButton bags = new JButton("Have your own bags?");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // change all text to french when button pressed. not implemented yet
            }
        });

        JButton help = new JButton("call for help");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // change all text to french when button pressed. not implemented yet
            }
        });

        JButton membership = new JButton("Are you a member?");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // change all text to french when button pressed. not implemented yet
            }
        });



        JLabel welcomeLabel = new JLabel("Welcome to the UofC market!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));


        addComponent(panel,welcomeLabel,0, 0, 3, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(panel,new JLabel(""),0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(panel,nextButton,1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(panel,new JLabel(""),2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("MORE OPTIONS"));



        bottomPanel.add(frenchButton);
        bottomPanel.add(bags);
        bottomPanel.add(help);
        bottomPanel.add(membership);

        bottomPanel.add(new JLabel(""));
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(new JLabel(""));


        addComponent(panel,bottomPanel,0, 2, 3, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);



        return panel;
    }

    /*
     * The Panel for Checkout (MAIN)
     * There are additional parts that made this layout
     * Part 1
     */
    private JPanel createAddItemsPanel() {
        // Create main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Buttons panel on left side (all use case buttons)
        JPanel buttonsPanel = new JPanel(new GridLayout(9,1));

        // Create buttons
        JButton addItemButton = new JButton("Add an item");
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // goto panel with options to add an item
            }
        });

        JButton removeItemButton = new JButton("Remove an Item");
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // goto panel to remove an item
            }
        });

        JButton payButton = new JButton("Finish and Pay");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("paymentPanel");
            }
        });


        // Attach buttons
        buttonsPanel.add(addItemButton);
        buttonsPanel.add(removeItemButton);
        buttonsPanel.add(payButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.3; // 30% horizontal weight
        gbc.weighty = 1; // full vertical length
        mainPanel.add(buttonsPanel, gbc);

        // current items panel
        JPanel topMiddlePanel = new JPanel();
        topMiddlePanel.setBorder(BorderFactory.createTitledBorder("Current Items: "));

        // setup scroll pane
        JScrollPane CurrentItemsPanel = new JScrollPane(topMiddlePanel);
        CurrentItemsPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        CurrentItemsPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        CurrentItemsPanel.getVerticalScrollBar().setUnitIncrement(16);
//        addComponent(mainPanel,CurrentItemsPanel,1, 0, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.7; // 70% horizontal
        gbc.weighty = 0.7; // 70% vertical
        mainPanel.add(CurrentItemsPanel, gbc);

        JPanel totalPanel = new JPanel();
        totalPanel.setBorder(BorderFactory.createTitledBorder(" "));
        addComponent(totalPanel,new JLabel("Total:"),0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.7; // 70% horizontal
        gbc.weighty = 0.3; // 30% vertical
        mainPanel.add(totalPanel, gbc);
//        addComponent(mainPanel,totalPanel,1, 2, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

        return mainPanel;
    }
    private JPanel createThankYouPanel()  {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton returnButton = new JButton("Please press this button to continue.");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("welcomePanel");
            }
        });
        JLabel TY_receiptLabel = new JLabel("Thank you for Shopping, Please take your receipt.");
        TY_receiptLabel.setFont(new Font("Arial", Font.BOLD, 26));
        gbc.gridy = 0;
        panel.add(TY_receiptLabel, gbc);
        gbc.gridy = 1;
        panel.add(returnButton,gbc);


        return panel;
    }

    //Screen 3 Payment Panel
    private JPanel createPaymentPanel() {
        JPanel PaymentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);


        JButton payment_button1 = new JButton("DEBIT (Swipe)");
        payment_button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("thankYouPanel");
            }
        });
        JButton payment_button2 = new JButton("DEBIT (Tap)");
        payment_button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("thankYouPanel");
            }
        });
        JButton payment_button3 = new JButton("DEBIT (Insert Card)");
        payment_button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("thankYouPanel");
            }
        });
        JButton payment_button4 = new JButton("CREDIT (Swipe)");
        payment_button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("thankYouPanel");
            }
        });
        JButton payment_button5 = new JButton("CREDIT (Tap)");
        payment_button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("thankYouPanel");
            }
        });
        JButton payment_button6 = new JButton("CREDIT (Insert Card)");
        payment_button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("thankYouPanel");
            }
        });
        JButton payment_button7 = new JButton("Cash (Bills)");
        payment_button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("cashBillPanel");
            }
        });
        JButton payment_button8 = new JButton("Cash (Coins)");
        payment_button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("cashCoinPanel");
            }
        });
        JButton payment_button9 = new JButton("Leave Without Paying");
        payment_button9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("thankYouPanel");
            }
        });
        JButton BacktoCheckoutButton = new JButton("Back to Checkout");
        BacktoCheckoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("AddItemsPanel");
            }
        });
        gbc.gridx = 1; gbc.gridy = 1;
        payment_button1.setPreferredSize(new Dimension(150,150));
        PaymentPanel.add(payment_button1, gbc);


        gbc.gridx = 1; gbc.gridy = 2;
        payment_button2.setPreferredSize(new Dimension(150,150));
        PaymentPanel.add(payment_button2, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        payment_button3.setPreferredSize(new Dimension(150,150));
        PaymentPanel.add(payment_button3, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        payment_button4.setPreferredSize(new Dimension(150,150));
        PaymentPanel.add(payment_button4, gbc);

        gbc.gridx = 2; gbc.gridy = 2;
        payment_button5.setPreferredSize(new Dimension(150,150));
        PaymentPanel.add(payment_button5, gbc);

        gbc.gridx = 2; gbc.gridy = 3;
        payment_button6.setPreferredSize(new Dimension(150,150));
        PaymentPanel.add(payment_button6, gbc);

        gbc.gridx = 3; gbc.gridy = 1;
        payment_button7.setPreferredSize(new Dimension(150,150));
        PaymentPanel.add(payment_button7, gbc);

        gbc.gridx = 3; gbc.gridy = 2;
        payment_button8.setPreferredSize(new Dimension(150,150));
        PaymentPanel.add(payment_button8, gbc);

        gbc.gridx = 3; gbc.gridy = 3;
        payment_button9.setPreferredSize(new Dimension(150,150));
        PaymentPanel.add(payment_button9, gbc);

        gbc.gridx = 2; gbc.gridy = 4;
        BacktoCheckoutButton.setPreferredSize(new Dimension(150,50));
        PaymentPanel.add(BacktoCheckoutButton, gbc);

        return PaymentPanel;
    }



    //Screen 3.B Payment Panel (Coin Bill)
    private JPanel createCashBillPanel() {
        JPanel CoinBillPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);


        JButton payment_button1 = new JButton("$5.00");
        payment_button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("5.00");
            }
        });
        JButton payment_button2 = new JButton("$10.00");
        payment_button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("10.00");
            }
        });
        JButton payment_button3 = new JButton("$20.00");
        payment_button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("20.00");
            }
        });
        JButton payment_button4 = new JButton("$50.00");
        payment_button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("50.00");
            }
        });
        JButton payment_button5 = new JButton("100.00");
        payment_button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("10.00");
            }
        });
        JButton payment_button6 = new JButton("Pay for Order");
        payment_button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("thankYouPanel");
            }
        });
        JButton payment_button7 = new JButton("Back to Checkout/Add More Items");
        payment_button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("AddItemsPanel");
            }
        });
        gbc.gridx = 0; gbc.gridy = 0;
        payment_button1.setPreferredSize(new Dimension(150,150));
        CoinBillPanel.add(payment_button1, gbc);


        gbc.gridx = 0; gbc.gridy = 1;
        payment_button2.setPreferredSize(new Dimension(150,150));
        CoinBillPanel.add(payment_button2, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        payment_button3.setPreferredSize(new Dimension(150,150));
        CoinBillPanel.add(payment_button3, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        payment_button4.setPreferredSize(new Dimension(150,150));
        CoinBillPanel.add(payment_button4, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        payment_button5.setPreferredSize(new Dimension(150,150));
        CoinBillPanel.add(payment_button5, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        payment_button6.setPreferredSize(new Dimension(150,150));
        CoinBillPanel.add(payment_button6, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        payment_button7.setPreferredSize(new Dimension(150,150));
        CoinBillPanel.add(payment_button7, gbc);

        return CoinBillPanel;
    }

    //Screen 3 Payment Panel (Coin Coin)
    private JPanel createCashCoinPanel() {
        JPanel CoinBillPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);


        JButton payment_button1 = new JButton("$0.01");
        payment_button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("0.01");
            }
        });
        JButton payment_button2 = new JButton("$0.05");
        payment_button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("0.05");
            }
        });
        JButton payment_button3 = new JButton("$0.10");
        payment_button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("0.10");
            }
        });
        JButton payment_button4 = new JButton("$0.25");
        payment_button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("0.25");
            }
        });
        JButton payment_button5 = new JButton("$1.00");
        payment_button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("1.00");
            }
        });
        JButton payment_button6 = new JButton("Pay for Order");
        payment_button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("thankYouPanel");
            }
        });
        JButton payment_button7 = new JButton("Back to Checkout/Add More Items");
        payment_button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels("AddItemsPanel");
            }
        });
        gbc.gridx = 0; gbc.gridy = 0;
        payment_button1.setPreferredSize(new Dimension(150,150));
        CoinBillPanel.add(payment_button1, gbc);


        gbc.gridx = 0; gbc.gridy = 1;
        payment_button2.setPreferredSize(new Dimension(150,150));
        CoinBillPanel.add(payment_button2, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        payment_button3.setPreferredSize(new Dimension(150,150));
        CoinBillPanel.add(payment_button3, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        payment_button4.setPreferredSize(new Dimension(150,150));
        CoinBillPanel.add(payment_button4, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        payment_button5.setPreferredSize(new Dimension(150,150));
        CoinBillPanel.add(payment_button5, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        payment_button6.setPreferredSize(new Dimension(150,150));
        CoinBillPanel.add(payment_button6, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        payment_button7.setPreferredSize(new Dimension(150,150));
        CoinBillPanel.add(payment_button7, gbc);

        return CoinBillPanel;
    }


    //Screen 3 Payment Panel (Coin Coin)


    /*
     * 3-part Method that causes a Numberpad
     */
    class TransparentNumpadPanel extends JPanel {
        private JFrame parentFrame;


        public TransparentNumpadPanel(JFrame parentFrame) {
            this.parentFrame = parentFrame;
            setOpaque(true); // Make the panel transparent
            setLayout(new GridBagLayout());
            setPreferredSize(new Dimension(400, 400)); // Set the preferred size to 400x400
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);


            // Draw a semi-transparent background
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(128, 128, 128, 128)); // 128, 128, 128 is grey, 128 is the alpha value
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }


        public void addNumPadButtons() {
            // Create buttons and add them to the center of the panel
            JButton button1 = new JButton("1");
            JButton button2 = new JButton("2");
            JButton button3 = new JButton("3");
            JButton button4 = new JButton("1");
            JButton button5 = new JButton("2");
            JButton button6 = new JButton("3");
            JButton button7 = new JButton("1");
            JButton button8 = new JButton("2");
            JButton button9 = new JButton("3");
            JButton buttonCLEAR = new JButton("CLEAR");
            JButton button0 = new JButton("0");
            JButton buttonENTER = new JButton("ENTER");


            GridBagConstraints gbc = new GridBagConstraints();
            //Placements of Button (indentation for readability)
            gbc.gridy = 0;
            gbc.gridx = 0;
            add(button1, gbc);
            gbc.gridx = 1;
            add(button2, gbc);
            gbc.gridx = 2;
            add(button3, gbc);
            gbc.gridx = 1;
            gbc.gridx = 0;
            add(button4, gbc);
            gbc.gridx = 1;
            add(button5, gbc);
            gbc.gridx = 2;
            add(button6, gbc);





            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guiLogicInstance.addItemPopUp_button1_CustomersAddsToBaggingArea();
                    closeNumPadPanel();
                }
            });
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guiLogicInstance.addItemPopUp_button2_CustomersDOESNOTAddsToBaggingArea();
                    closeNumPadPanel();
                }
            });
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guiLogicInstance.addItemPopUp_button3_BLANK();
                    closeNumPadPanel();
                }
            });
        }
    }
    /*
     * NumberPad Pop Up Part 2/3
     * What causes the Overlay to show up
     *
     * If needed, copy paste this code for each Add Item
     */
    private void openNumPadPanel() {
        // Create a transparent overlay panel
        TransparentNumpadPanel numpadPanel = new TransparentNumpadPanel(this);


        // Add buttons to the overlay panel
        numpadPanel.addNumPadButtons();


        // Add the overlay panel to the main frame
        setGlassPane(numpadPanel);
        numpadPanel.setVisible(true);
    }

    /*
     * NumberPad Pop Up Part 3/3
     * What causes the Overlay to disappear
     */
    private void closeNumPadPanel() {
        TransparentNumpadPanel numpadPanel = new TransparentNumpadPanel(this);
        numpadPanel.addNumPadButtons();
        setGlassPane(numpadPanel);
        numpadPanel.setVisible(false);
    }



    //-----------------------------------------
    /*
     * Add Item Pop Up Part 1/3
     *
     * When Customer adds any Item, it will cause GUI to show up and ask if
     * Customer would place item into Bagging Area or Not
     * 3rd Option is Extra Button
     */
    class TransparentOverlayPanel extends JPanel {
        private JFrame parentFrame;


        public TransparentOverlayPanel(JFrame parentFrame) {
            this.parentFrame = parentFrame;
            setOpaque(true); // Make the panel transparent
            setLayout(new GridBagLayout());
            setPreferredSize(new Dimension(400, 400)); // Set the preferred size to 400x400
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);


            // Draw a semi-transparent background
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(128, 128, 128, 128)); // 128, 128, 128 is grey, 128 is the alpha value
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }


        public void addCenteredButtons() {
            // Create buttons and add them to the center of the panel
            JButton button1 = new JButton("Button 1");
            JButton button2 = new JButton("Button 2");
            JButton button3 = new JButton("Button 3");


            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(button1, gbc);


            gbc.gridy = 1;
            add(button2, gbc);


            gbc.gridy = 2;
            add(button3, gbc);


            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guiLogicInstance.addItemPopUp_button1_CustomersAddsToBaggingArea();
                    closeOverlayPanel();
                }
            });
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guiLogicInstance.addItemPopUp_button2_CustomersDOESNOTAddsToBaggingArea();
                    closeOverlayPanel();
                }
            });
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guiLogicInstance.addItemPopUp_button3_BLANK();
                    closeOverlayPanel();
                }
            });
        }
    }
    /*
     * Add Item Pop Up Part 2/3
     * What causes the Overlay to show up
     *
     * If needed, copy paste this code for each Add Item
     */
    private void openOverlayPanel() {
        // Create a transparent overlay panel
        TransparentOverlayPanel overlayPanel = new TransparentOverlayPanel(this);


        // Add buttons to the overlay panel
        overlayPanel.addCenteredButtons();


        // Add the overlay panel to the main frame
        setGlassPane(overlayPanel);
        overlayPanel.setVisible(true);
    }

    /*
     * Add Item Pop Up Part 3/3
     * What causes the Overlay to disappear
     */
    private void closeOverlayPanel() {
        TransparentOverlayPanel overlayPanel = new TransparentOverlayPanel(this);
        overlayPanel.addCenteredButtons();
        setGlassPane(overlayPanel);
        overlayPanel.setVisible(false);
    }
    public void switchPanels(String string) {
        cardLayout.show(cardPanel, string);
    }
    @Override
    public void updateTotal(int total) {
        totalLabel.setText("Total: "+ total);
    }
}
