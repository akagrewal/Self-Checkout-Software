package com.thelocalmarketplace.software.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BagsTooHeavyPopUp {
	
	public void notifyPopUp() {
		JFrame bagsPopUp = new JFrame("Bags Too Heavy");
		bagsPopUp.setSize(400, 125);
		bagsPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bagsPopUp.setLocation(1025, 400);
    
		JPanel popupPanel = createLabelPanel("A Customer's Bags are Too Heavy", 40, 50);
		JPanel buttonPanel = new JPanel();
		JButton confirmButton2 = new JButton("Confirm");
		confirmButton2.addActionListener(e -> handleButtonClick(12));
		buttonPanel.add(confirmButton2);
		bagsPopUp.add(popupPanel, BorderLayout.NORTH);
		bagsPopUp.add(buttonPanel, BorderLayout.CENTER);
    
		bagsPopUp.setVisible(true);
	}
	
	private void handleButtonClick(int buttonNumber) {
        switch (buttonNumber) {
            case 12:
                //NotifyPopUp.notifyPopUp.setVisible(false);
                break;
        }
	}
	
	private JPanel createLabelPanel(String labelText, int width, int height) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(width, height));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, gbc);
        return panel;
    }
}
