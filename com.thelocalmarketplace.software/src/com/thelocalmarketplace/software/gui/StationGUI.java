package com.thelocalmarketplace.software.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;


import javax.swing.*;

import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;
import com.thelocalmarketplace.software.logic.StateLogic;

public class StationGUI extends JFrame {
    // Paneling on GUI
    public JPanel cardPanel;
    public CardLayout cardLayout;
    // For logic testing - delete after all GUI is done
    private int total;
    private JLabel totalLabel;
    public JLabel BalanceLabel;

    public GUILogic guiLogicInstance;
    private final CentralStationLogic centralStationLogic;
    
    public JPanel blockingPanel;

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
        cardPanel.add(createPOSPanel(), "POS_Panel");
        cardPanel.add(createCashPaymentPanel(), "CashPaymentPanel");

//        cardPanel.add(createNumberPad(), "numpadPanel");
        add(cardPanel);

        // Show the welcome panel initially
        cardLayout.show(cardPanel, "welcomePanel");
        //Or use method guiLogicInstance.switchPanels("welcomePanel")

        setVisible(true);
        createBlockingPanel();
    }

    private static void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
        Insets insets = new Insets(0, 0, 0, 0);
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0, anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }
    
    private void createBlockingPanel() {
        blockingPanel = new JPanel(new GridBagLayout());
        blockingPanel.setBackground(Color.GRAY); 
        JLabel blockingLabel = new JLabel("Blocking");
        blockingLabel.setFont(new Font("Arial", Font.BOLD, 30));
        blockingPanel.add(blockingLabel);
        blockingPanel.setVisible(false); // Initially hidden
        this.add(blockingPanel, BorderLayout.CENTER);
    }

    // Customer Screen 1
    private JPanel StartSessionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#EFF0E5")); // beige
        GridBagConstraints gbc = new GridBagConstraints();

        JButton nextButton = new JButton("Start adding items to cart");
        nextButton.setFont(new Font("Arial", Font.BOLD, 26));
        nextButton.addActionListener(e -> guiLogicInstance.StartSessionButtonPressed());
        nextButton.setBackground(Color.decode("#9DAF99"));  //light green
        nextButton.setForeground(Color.decode("#40543D"));	// dark green
        nextButton.setFont(new Font("Serif", Font.BOLD, 42));
        
        JButton bags = new JButton("Have your own bags?");
        bags.setBackground(Color.decode("#B9AFCA"));  //light purple
        bags.setForeground(Color.decode("#4A3D54"));	// dark purple
        bags.setFont(new Font("Serif", Font.BOLD, 24));
        bags.addActionListener(e -> {
            // TODO: Implement
        });

        JButton help = new JButton("Call for assisstance");
        help.setBackground(Color.decode("#B9AFCA"));  //Navy
        help.setForeground(Color.decode("#4A3D54"));
        help.setFont(new Font("Serif", Font.BOLD, 24));
        help.addActionListener(e -> {
            centralStationLogic.attendantLogic.callAttendant(centralStationLogic.stationNumber);
        });

        JButton membership = new JButton("Enter Membership No.");
        membership.setBackground(Color.decode("#B9AFCA"));  //Navy
        membership.setForeground(Color.decode("#4A3D54"));
        membership.setFont(new Font("Serif", Font.BOLD, 24));
        membership.addActionListener(e -> {
            Numpad membershipNumpad = new Numpad(StationGUI.this, guiLogicInstance, 0); // this may need changes
            membershipNumpad.openNumPadPanel();
        });



        JLabel welcomeLabel = new JLabel("Welcome to the UofC market!");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 40));
        welcomeLabel.setForeground(Color.decode("#4A3D54"));




        addComponent(panel,welcomeLabel,0, 0, 3, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComponent(panel,new JLabel(""),0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(panel,nextButton,1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(panel,new JLabel(""),2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        JPanel bottomPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        bottomPanel.setBackground(Color.decode("#EFF0E5"));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("MORE OPTIONS"));



        bottomPanel.add(bags);
        bottomPanel.add(help);
        bottomPanel.add(membership);

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
        mainPanel.setBackground(Color.decode("#EFF0E5")); // beige

        GridBagConstraints gbc = new GridBagConstraints();

        // Buttons panel on left side (all use case buttons)
        JPanel buttonsPanel = new JPanel();
        GridLayout layout = new GridLayout(8, 1, 10, 10);
        buttonsPanel.setLayout(layout);
        buttonsPanel.setBackground(Color.decode("#EFF0E5")); // beige


        // Create buttons
        JButton VCButton = new JButton("  Add an item using visual catalogue  ");
        VCButton.setBackground(Color.decode("#9DAF99"));  //light green
        VCButton.setForeground(Color.decode("#40543D"));	// dark green
        VCButton.setFont(new Font("Serif", Font.BOLD, 15));
        VCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiLogicInstance.switchPanels("visualCatalogue");
            }
        });

        // Create buttons
        JButton PLUButton = new JButton("Add an item using PLU Code");
        PLUButton.setBackground(Color.decode("#9DAF99"));  //light green
        PLUButton.setForeground(Color.decode("#40543D"));	// dark green
        PLUButton.setFont(new Font("Serif", Font.BOLD, 15));
        PLUButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // goto panel with options to add an item
                Numpad pluNumpad = new Numpad(StationGUI.this, guiLogicInstance, 1); // this may need changes
                pluNumpad.openNumPadPanel();
            }
        });

        JButton removeItemButton = new JButton("Remove an Item");
        removeItemButton.setBackground(Color.decode("#9DAF99"));  //light green
        removeItemButton.setForeground(Color.decode("#40543D"));	// dark green
        removeItemButton.setFont(new Font("Serif", Font.BOLD, 15));
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.add(createRemoveItemPanel(), "removeItem");
                guiLogicInstance.switchPanels("removeItem");
            }
        });

        JButton payButton = new JButton("Finish and Pay");
        payButton.setBackground(Color.decode("#4A423E"));  //DARK BROWN
        payButton.setForeground(Color.decode("#EFF0E5"));	// beige
        payButton.setFont(new Font("Serif", Font.BOLD, 15)); 
       
        payButton.addActionListener(e -> {
            System.out.println(centralStationLogic.stateLogic.getState());
            centralStationLogic.stateLogic.gotoState(StateLogic.States.CHECKOUT);
            if (centralStationLogic.cartLogic.getBalanceOwed().compareTo(BigDecimal.ZERO) <= 0) {
                centralStationLogic.guiLogic.showExceptionMessage("No Items Added");
            } else {
                guiLogicInstance.switchPanels("paymentPanel");
            }
        });

        JButton buyBagsButton = new JButton("Purchase Bags");
        buyBagsButton.setBackground(Color.decode("#B9AFCA"));  //light purple
        buyBagsButton.setForeground(Color.decode("#4A3D54"));	// dark purple
        buyBagsButton.setFont(new Font("Serif", Font.BOLD, 15));
        buyBagsButton.addActionListener(e -> {
            new BagKeypad(StationGUI.this, centralStationLogic);
        });

        JButton ownBagsButton = new JButton("Have your own bags? ");
        ownBagsButton.setBackground(Color.decode("#B9AFCA"));  //light purple
        ownBagsButton.setForeground(Color.decode("#4A3D54"));	// dark purple
        ownBagsButton.setFont(new Font("Serif", Font.BOLD, 15));
        
        ownBagsButton.addActionListener(e -> {
            centralStationLogic.addBagsLogic.startAddBags();

            // Create a custom dialog
            JDialog dialog = new JDialog();
            dialog.setTitle("Add Own Bags");
            dialog.setModal(false); // This makes the dialog non-modal

            // Create a panel to hold the components
            JPanel panel = new JPanel();

            // Create the message
            JLabel messageLabel = new JLabel("Please press 'DONE' when done adding bags.");
            panel.add(messageLabel);

            // Create the buttons
            JButton doneButton = new JButton("DONE");
            JButton cancelButton = new JButton("Cancel");

            // Add action listeners to the buttons
            doneButton.addActionListener(e2 -> {
                centralStationLogic.addBagsLogic.endAddBags();
                if (centralStationLogic.stateLogic.inState(States.ADDBAGS)) {
                	BagsTooHeavyPopUp heavyBagPopUp = new BagsTooHeavyPopUp();
                	heavyBagPopUp.notifyBagHeavyPopUp();
                	SessionBlockedPopUp.discrepancyDetected(StationGUI.this, centralStationLogic.stationNumber);
                }
                dialog.dispose();
            });

            cancelButton.addActionListener(e2 -> dialog.dispose());

            // Add the buttons to the panel
            panel.add(doneButton);
            panel.add(cancelButton);

            // Add the panel to the dialog
            dialog.getContentPane().add(panel);

            // Set the location of the dialog to the location of the button
            Point location = ((Component) e.getSource()).getLocationOnScreen();
            dialog.setLocation(location);

            // Display the dialog
            dialog.pack();
            dialog.setVisible(true);

        });
        // Attach buttons
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(VCButton);
        buttonsPanel.add(PLUButton);
        buttonsPanel.add(removeItemButton);
        buttonsPanel.add(buyBagsButton);
        buttonsPanel.add(ownBagsButton);
        buttonsPanel.add(payButton);
        buttonsPanel.add(new JLabel(""));


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
        
        topMiddlePanel.setBackground(Color.decode("#EFF0E5")); // beige
        topMiddlePanel.setBorder(BorderFactory.createTitledBorder("Current Items: "));
        
        itemList.setVisibleRowCount(10); // visible row count
        itemList.setFixedCellHeight(20); // cell height

        // setup scroll pane
        JScrollPane scrollPane = new JScrollPane(itemList);
        itemList.setBackground(Color.decode("#DFDFD1")); //slightly darker beige
        itemList.setForeground(Color.decode("#4A3D54"));
        itemList.setFont(new Font("Serif", Font.BOLD, 24));

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

        
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Serif", Font.BOLD, 25));
        totalLabel.setForeground(Color.decode("#40543D")); // dark green
        totalPanel.setBackground(Color.decode("#9DAF99")); //slightly darker beige



        BalanceLabel = new JLabel("");
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

	private JPanel createRemoveItemPanel() {
        // create the panel
        JPanel mainPanel = new JPanel(new GridBagLayout());

        JPanel removeItemPanel = new JPanel();
        JLabel removeLabel = new JLabel("Please select an item to remove: ");
        removeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        JButton backButton = new JButton("Back");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiLogicInstance.switchPanels("AddItemsPanel");
            }
        });
        guiLogicInstance.addRemoveButtons(removeItemPanel);

        removeItemPanel.setBorder(BorderFactory.createTitledBorder("SELECT AN ITEM"));
        JScrollPane RMpanel = new JScrollPane(removeItemPanel);
        RMpanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        RMpanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        addComponent(mainPanel,removeLabel,0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(mainPanel,backButton,0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComponent(mainPanel,RMpanel,1, 0, 1, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        return mainPanel;
    }

	

    //Screen 3 Payment Panel
    private JPanel createPaymentPanel() {
        JPanel PaymentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton button_CreditPayment = new JButton("Credit");
        JButton button_DebitPayment = new JButton("Debit");
        
        JButton buttonCoinPayment = new JButton("Coins");
        JButton buttonCashPayment = new JButton("Banknotes");
        JButton buttonMixedPayment = new JButton("Mixed");
        JButton buttonLeaveWithoutPaying = new JButton("Leave Without Paying");
        JButton buttonBackToCheckout = new JButton("Back to Checkout");
        gbc.gridx = 1; gbc.gridy = 1;
        button_CreditPayment.setPreferredSize(new Dimension(200,123));
        PaymentPanel.add(button_CreditPayment, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        button_DebitPayment.setPreferredSize(new Dimension(200,123));
        PaymentPanel.add(button_DebitPayment, gbc);
        gbc.gridx = 2; gbc.gridy = 1;
        buttonCoinPayment.setPreferredSize(new Dimension(200,123));
        PaymentPanel.add(buttonCoinPayment, gbc);
        gbc.gridx = 3; gbc.gridy = 1;
        buttonCashPayment.setPreferredSize(new Dimension(200,123));
        PaymentPanel.add(buttonCashPayment, gbc);
        gbc.gridx = 4; gbc.gridy = 1;
        buttonMixedPayment.setPreferredSize(new Dimension(200,123));
        PaymentPanel.add(buttonMixedPayment, gbc);
        gbc.gridx = 2; gbc.gridy = 3;
        buttonLeaveWithoutPaying.setPreferredSize(new Dimension(200,50));
        PaymentPanel.add(buttonLeaveWithoutPaying, gbc);
        gbc.gridx = 4; gbc.gridy = 3;
        buttonBackToCheckout.setPreferredSize(new Dimension(200,50));
        PaymentPanel.add(buttonBackToCheckout, gbc);

        button_CreditPayment.addActionListener(e -> {
            centralStationLogic.selectPaymentMethod(PaymentMethods.CREDIT);
            guiLogicInstance.switchPanels("POS_Panel");
        });
        button_DebitPayment.addActionListener(e -> {
            centralStationLogic.selectPaymentMethod(PaymentMethods.DEBIT);
            guiLogicInstance.switchPanels("POS_Panel");
        });
        buttonCoinPayment.addActionListener(e -> {
            centralStationLogic.selectPaymentMethod(PaymentMethods.CASH);
            BalanceLabel.setText("Balance: " +  centralStationLogic.cartLogic.getBalanceOwed());
            guiLogicInstance.switchPanels("CashPaymentPanel");
        });
        buttonCashPayment.addActionListener(e -> {
            centralStationLogic.selectPaymentMethod(PaymentMethods.CASH);
            BalanceLabel.setText("Balance: " +  centralStationLogic.cartLogic.getBalanceOwed());
            guiLogicInstance.switchPanels("CashPaymentPanel");
        });
        buttonMixedPayment.addActionListener(e -> {
            centralStationLogic.selectPaymentMethod(PaymentMethods.MIXED);
            BalanceLabel.setText("Balance: " +  centralStationLogic.cartLogic.getBalanceOwed());
            guiLogicInstance.switchPanels("CashPaymentPanel");
        });

        buttonLeaveWithoutPaying.addActionListener(e -> {
        });
        buttonBackToCheckout.addActionListener(e -> {
            guiLogicInstance.switchPanels("AddItemsPanel");
        });


        return PaymentPanel;
    }

    private JPanel createPOSPanel()  {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton returnButton = new JButton("Cancel");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiLogicInstance.switchPanels("paymentPanel");
                guiLogicInstance.SessionOver();
            }
        });
        JLabel POSLabel = new JLabel("Please complete the payment at the POS");
        POSLabel.setFont(new Font("Arial", Font.BOLD, 26));
        gbc.gridy = 0;
        panel.add(POSLabel, gbc);
        gbc.gridy = 1;
        panel.add(returnButton,gbc);

        return panel;
    }

    private JPanel createCashPaymentPanel()  {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton returnButton = new JButton("Cancel");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiLogicInstance.switchPanels("paymentPanel");
                guiLogicInstance.SessionOver();
            }
        });
        BalanceLabel.setFont(new Font("Arial", Font.BOLD, 26));
        gbc.gridy = 0;
        panel.add(BalanceLabel, gbc);
        gbc.gridy = 1;
        panel.add(returnButton,gbc);

        return panel;
    }


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

    public GUILogic getLogicInstance(){
        return this.guiLogicInstance;
    }
}