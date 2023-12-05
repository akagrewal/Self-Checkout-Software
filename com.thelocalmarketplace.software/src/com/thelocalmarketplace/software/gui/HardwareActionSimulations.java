package com.thelocalmarketplace.software.gui;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.thelocalmarketplace.software.logic.CentralStationLogic;

public class HardwareActionSimulations extends JFrame {

    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 500;
    private static final int BUTTON_WIDTH = 225; // 75% of WINDOW_WIDTH
    private static final int BUTTON_HEIGHT = 50;
    
    HardwarePopups hardwarePopups;

    public HardwareActionSimulations(CentralStationLogic centralStationLogic) {
        // Initialize the JFrame
        setTitle("Hardware Action Simulations");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setLayout(new GridLayout(1, 3));

        // Add Item Section
        JPanel addItemPanel = new JPanel();
        addItemPanel.add(createHeader("Add item"));
        addItemPanel.add(createButton("Scan with main scanner", e -> hardwarePopups.showScanMainScannerPopup(this)));
        addItemPanel.add(createButton("Scan with handheld scanner", e -> hardwarePopups.showScanHandheldScannerPopup(this)));
        add(addItemPanel);

        // Scale Section
        JPanel scalePanel = new JPanel();
        scalePanel.add(createHeader("Scale"));
        scalePanel.add(createButton("Add item to bagging area", e -> hardwarePopups.showAddItemToScalePopup(this)));
        scalePanel.add(createButton("Remove item from bagging area", e -> hardwarePopups.showRemoveItemFromScalePopup(this)));
        scalePanel.add(createButton("Place items on scanner scale", e -> hardwarePopups.showMeasureItemsOnPLUScalePopup(this)));
        add(scalePanel);

        // Pay Section
        JPanel payPanel = new JPanel();
        payPanel.add(createHeader("Pay"));
        payPanel.add(createButton("Pay with credit", e -> hardwarePopups.showPayWithCreditPopup(this)));
        payPanel.add(createButton("Pay with debit", e -> hardwarePopups.showPayWithDebitPopup(this)));
        payPanel.add(createButton("Insert coin", e -> hardwarePopups.showInsertCoinPopup(this)));
        payPanel.add(createButton("Insert banknote", e -> hardwarePopups.showInsertBanknotePopup(this)));
        add(payPanel);

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
