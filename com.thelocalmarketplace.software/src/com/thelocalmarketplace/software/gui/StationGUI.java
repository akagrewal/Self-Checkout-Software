package com.thelocalmarketplace.software.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;


import javax.swing.*;

import com.thelocalmarketplace.software.logic.CentralStationLogic;



public class StationGUI extends JFrame implements logicObserver {
    // Paneling on GUI
    public JPanel cardPanel;
    public CardLayout cardLayout;
    // For logic testing - delete after all GUI is done
    private int total;
    private JLabel totalLabel;

    public GUILogic guiLogicInstance;
    private final CentralStationLogic centralStationLogic;

    /** Stores the list of items being displayed on the screen (needs to be updated by GUI logic)**/
	public DefaultListModel<String> itemListModel = new DefaultListModel<>();
	public JList<String> itemList = new JList<>(itemListModel);

    private static final Insets insets = new Insets(0, 0, 0, 0);


    //For Testing Purposes - to run GUI
    public StationGUI(CentralStationLogic centralStationLogic) {
    	this.centralStationLogic = centralStationLogic;

        SelfCheckoutGUI();
        this.guiLogicInstance = new GUILogic(centralStationLogic,this);
    }

    /**
     * Main method that creates the GUI
     */
    private void SelfCheckoutGUI() {
        //Frame Size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocation(0,0);

        // Create and add panels to the card panel
        // When you add new panel, make sure to add one here too
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(StartSessionPanel(), "welcomePanel");
        cardPanel.add(createAddItemsPanel(), "AddItemsPanel");
        cardPanel.add(createVisual(), "visualCatalogue");
        cardPanel.add(createThankYouPanel(), "thankYouPanel");
        cardPanel.add(createPaymentPanel(), "paymentPanel");
        // cardPanel.add(createCashBillPanel(), "cashBillPanel");
        // cardPanel.add(createCashCoinPanel(), "cashCoinPanel");

//        cardPanel.add(createNumberPad(), "numpadPanel");
        add(cardPanel);

        // Show the welcome panel initially
        cardLayout.show(cardPanel, "welcomePanel");
        //Or use method guiLogicInstance.switchPanels("welcomePanel")

        setVisible(true);
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
        nextButton.addActionListener(e -> guiLogicInstance.StartSessionButtonPressed());

        JButton bags = new JButton("Have your own bags?");
        nextButton.addActionListener(e -> {
            // TODO: Implement
        });

        JButton help = new JButton("call for help");
        nextButton.addActionListener(e -> {
            //TODO: Implement
        });

        JButton membership = new JButton("Are you a member?");
        membership.addActionListener(e -> {
            Numpad membershipNumpad = new Numpad(StationGUI.this, guiLogicInstance, 0); // this may need changes
            membershipNumpad.openNumPadPanel();
        });



        JLabel welcomeLabel = new JLabel("Welcome to the UofC market!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));


        addComponent(panel,welcomeLabel,0, 0, 3, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(panel,new JLabel(""),0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(panel,nextButton,1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(panel,new JLabel(""),2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        JPanel bottomPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("MORE OPTIONS"));



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

    private JScrollPane createVisual() {
        JScrollPane vc = new VisualCatalouge(this).createVisualCatalouge();



        return vc;
    }
    
    public void setTotal(BigDecimal total) {
        totalLabel.setText("Total: $" + total.setScale(2, RoundingMode.HALF_UP).toString());
    }


    private JPanel createAddItemsPanel() {
        // Create main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Buttons panel on left side (all use case buttons)
        JPanel buttonsPanel = new JPanel(new GridLayout(9,1));

        // Create buttons
        JButton VCButton = new JButton("  Add an item using visual catalogue  ");
        VCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiLogicInstance.switchPanels("visualCatalogue");
            }
        });

        // Create buttons
        JButton PLUButton = new JButton("Add an item using PLU Code");
        PLUButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // goto panel with options to add an item
                Numpad pluNumpad = new Numpad(StationGUI.this, guiLogicInstance, 1); // this may need changes
                pluNumpad.openNumPadPanel();
            }
        });

        JButton removeItemButton = new JButton("Remove an Item");
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // goto panel to remove an item
            }
        });

        JButton payButton = new JButton("Finish and Pay");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiLogicInstance.switchPanels("paymentPanel");
            }
        });

        JButton buyBagsButton = new JButton("Purchase Bags");
        buyBagsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiLogicInstance.switchPanels("paymentPanel");
            }
        });

        JButton ownBagsButton = new JButton("Have your own bags? ");
        ownBagsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	centralStationLogic.addBagsLogic.startAddBags();
            	//opens dialog to end Add Bags
            	int choice = JOptionPane.showOptionDialog(ownBagsButton,
                        "Please press 'DONE' when done adding bags.",
                        "Add Own Bags",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"DONE", "Cancel"},
                        "DONE");
            	
            	if (choice == JOptionPane.YES_OPTION) {
                    centralStationLogic.addBagsLogic.endAddBags();
                }

                guiLogicInstance.switchPanels("paymentPanel");
            }
        });
        // Attach buttons
        buttonsPanel.add(VCButton);
        buttonsPanel.add(PLUButton);
        buttonsPanel.add(removeItemButton);
        buttonsPanel.add(buyBagsButton);
        buttonsPanel.add(ownBagsButton);
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
        JPanel topMiddlePanel = new JPanel(new BorderLayout());
        topMiddlePanel.setBorder(BorderFactory.createTitledBorder("Current Items: "));
        
        itemList.setVisibleRowCount(10); // visible row count
        itemList.setFixedCellHeight(20); // cell height

        // setup scroll pane
        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        topMiddlePanel.add(scrollPane, BorderLayout.CENTER);
//        addComponent(mainPanel,CurrentItemsPanel,1, 0, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.7; // 70% horizontal
        gbc.weighty = 0.7; // 70% vertical
        mainPanel.add(topMiddlePanel, gbc);

        JPanel totalPanel = new JPanel();
        totalPanel.setBorder(BorderFactory.createTitledBorder(" "));
        totalLabel = new JLabel("Total: $0.00");
        addComponent(totalPanel, totalLabel,0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

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


    //Screen 3 Payment Panel
    private JPanel createPaymentPanel() {
        JPanel PaymentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton button_CardPayment = new JButton("Credit/Debit");
        JButton buttonCoinPayment = new JButton("Coins");
        JButton buttonCashPayment = new JButton("Banknotes");
        JButton buttonMixedPayment = new JButton("Mixed");
        JButton buttonLeaveWithoutPaying = new JButton("Leave Without Paying");
        JButton buttonBackToCheckout = new JButton("Back to Checkout");
        gbc.gridx = 1; gbc.gridy = 1;
        button_CardPayment.setPreferredSize(new Dimension(200,123));
        PaymentPanel.add(button_CardPayment, gbc);
        gbc.gridx = 2; gbc.gridy = 1;
        buttonCoinPayment.setPreferredSize(new Dimension(200,123));
        PaymentPanel.add(buttonCoinPayment, gbc);
        gbc.gridx = 3; gbc.gridy = 1;
        buttonCashPayment.setPreferredSize(new Dimension(200,123));
        PaymentPanel.add(buttonCashPayment, gbc);
        gbc.gridx = 4; gbc.gridy = 1;
        buttonMixedPayment.setPreferredSize(new Dimension(200,123));
        PaymentPanel.add(buttonMixedPayment, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        buttonLeaveWithoutPaying.setPreferredSize(new Dimension(200,50));
        PaymentPanel.add(buttonLeaveWithoutPaying, gbc);
        gbc.gridx = 4; gbc.gridy = 3;
        buttonBackToCheckout.setPreferredSize(new Dimension(200,50));
        PaymentPanel.add(buttonBackToCheckout, gbc);

        button_CardPayment.addActionListener(e -> guiLogicInstance.switchPanels("thankYouPanel"));
        buttonCoinPayment.addActionListener(e -> guiLogicInstance.switchPanels("thankYouPanel"));
        buttonCashPayment.addActionListener(e -> guiLogicInstance.switchPanels("thankYouPanel"));
        buttonMixedPayment.addActionListener(e -> guiLogicInstance.switchPanels("thankYouPanel"));
        buttonLeaveWithoutPaying.addActionListener(e -> guiLogicInstance.switchPanels("thankYouPanel"));
        buttonBackToCheckout.addActionListener(e -> guiLogicInstance.switchPanels("AddItemsPanel"));

        return PaymentPanel;
    }


/*
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
                guiLogicInstance.switchPanels("thankYouPanel");
            }
        });
        JButton payment_button7 = new JButton("Back to Checkout/Add More Items");
        payment_button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiLogicInstance.switchPanels("AddItemsPanel");
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

 */

    /*
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
                guiLogicInstance.switchPanels("thankYouPanel");
            }
        });
        JButton payment_button7 = new JButton("Back to Checkout/Add More Items");
        payment_button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiLogicInstance.switchPanels("AddItemsPanel");
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

     */


    private JPanel createThankYouPanel()  {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton returnButton = new JButton("Please press this button to continue.");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiLogicInstance.switchPanels("welcomePanel");
                guiLogicInstance.SessionOver();
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

    @Override
    public void updateTotal(int total) {
        totalLabel.setText("Total: "+ total);
    }


    public GUILogic getLogicInstance(){
        return this.guiLogicInstance;
    }
}