package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;

public class SessionBlockedPopUp {

    private static JDialog popupDialog;

    public static void discrepancyDetected() {
        popupDialog = new JDialog();
        popupDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        popupDialog.setSize(800, 700);
        popupDialog.setLocationRelativeTo(null);

        JLabel label = new JLabel("Session Blocked. Attendant Notified.");

        // Set a larger font for the label
        Font font = new Font("Arial", Font.PLAIN, 24);
        label.setFont(font);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        popupDialog.add(label);

        popupDialog.setVisible(true);
    }
    
    public static void outOfOrder() {
        popupDialog = new JDialog();
        popupDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        popupDialog.setSize(800, 700);
        popupDialog.setLocationRelativeTo(null);

        JLabel label = new JLabel("OUT OF ORDER");

        // Set a larger font for the label
        Font font = new Font("Arial", Font.PLAIN, 24);
        label.setFont(font);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        popupDialog.add(label);

        popupDialog.setVisible(true);
    }

    public static void attendantOverride() {
        // Close the popup dialog
        popupDialog.dispose();
    }
    
    public static void issuePredicted(String message) {
    	popupDialog = new JDialog();
        popupDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        popupDialog.setSize(800, 700);
        popupDialog.setLocationRelativeTo(null);

        JLabel label = new JLabel(message);
        
        // Set a larger font for the label
        Font font = new Font("Arial", Font.PLAIN, 24);
        label.setFont(font);
        
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        popupDialog.add(label);

        popupDialog.setVisible(true);
    }
}
