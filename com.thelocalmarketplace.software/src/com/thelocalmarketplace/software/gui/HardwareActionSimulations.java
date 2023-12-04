package com.thelocalmarketplace.software.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.thelocalmarketplace.software.logic.CentralStationLogic;

public class HardwareActionSimulations extends JFrame {

    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 800;
    private static final int BUTTON_WIDTH = 225; // 75% of WINDOW_WIDTH
    private static final int BUTTON_HEIGHT = 50;
    
    HardwarePopups hardwarePopups;

    public HardwareActionSimulations(CentralStationLogic centralStationLogic) {
        // Initialize the JFrame
        setTitle("Hardware Action Simulations");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add Item Section
        add(createHeader("Add Item"));
        add(createButton("Scan Main Scanner", e -> hardwarePopups.showScanMainScannerPopup(this)));
        add(createButton("Scan Handheld Scanner", e -> hardwarePopups.showScanHandheldScannerPopup(this)));
        add(createButton("Visual Search", e -> HardwarePopups.showVisualSearchPopup(this)));
        add(createButton("PLU Code", e -> HardwarePopups.showPluCodePopup(this)));

        // Scale Section
        add(createHeader("Scale"));
        add(createButton("Add Item to Scale", e -> HardwarePopups.showAddItemToScalePopup(this)));
        add(createButton("Remove Item from Scale", e -> HardwarePopups.showRemoveItemFromScalePopup(this)));

        // Pay Section
        add(createHeader("Pay"));
        add(createButton("Pay with Credit (Real)", e -> HardwarePopups.showPayWithCreditPopup(this, true)));
        add(createButton("Pay with Credit (Fake)", e -> HardwarePopups.showPayWithCreditPopup(this, false)));
        add(createButton("Pay with Debit", e -> HardwarePopups.showPayWithDebitPopup(this)));
        add(createButton("Insert Coin", e -> HardwarePopups.showInsertCoinPopup(this)));
        add(createButton("Insert Banknote", e -> HardwarePopups.showInsertBanknotePopup(this)));

        setLocationRelativeTo(null); // Center the window on the screen
    	hardwarePopups = new HardwarePopups(centralStationLogic);

    }

    private JLabel createHeader(String text) {
        JLabel header = new JLabel(text);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        return header;
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener); // Add the action listener to the button
        return button;
    }

//    public static void main(String[] args) {
//        java.awt.EventQueue.invokeLater(() -> new HardwareActionSimulations().setVisible(true));
//    }
}
