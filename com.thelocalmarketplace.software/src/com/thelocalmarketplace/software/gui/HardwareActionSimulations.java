/** SENG300 Group Project
 * (Adapted from Project Iteration 2 - Group 5)
 *
 * Iteration 3 - Group 3
 * @author Jaimie Marchuk - 30112841
 * @author Wyatt Deichert - 30174611
 * @author Jane Magai - 30180119
 * @author Enzo Mutiso - 30182555
 * @author Mauricio Murillo - 30180713
 * @author Ahmed Ibrahim Mohamed Seifeldin Hassan - 30174024
 * @author Aryaman Sandhu - 30017164
 * @author Nikki Kim - 30189188
 * @author Jayden Ma - 30184996
 * @author Braden Beler - 30084941
 * @author Danish Sharma - 30172600
 * @author Angelina Rochon - 30087177
 * @author Amira Wishah - 30182579
 * @author Walija Ihsan - 30172565
 * @author Hannah Pohl - 30173027
 * @author Akashdeep Grewal - 30179657
 * @author Rhett Bramfield - 30170520
 * @author Arthur Huan - 30197354
 * @author Jaden Myers - 30152504
 * @author Jincheng Li - 30172907
 * @author Anandita Mahika - 30097559
 */


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
        
        // Maintain Section
        JPanel maintainPanel = new JPanel();
        payPanel.add(createHeader("Maintain"));
        payPanel.add(createButton("Maintain Actions", e -> hardwarePopups.showMaintainActions(this)));

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
