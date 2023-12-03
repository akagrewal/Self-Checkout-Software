package com.thelocalmarketplace.software.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NotifyPopUp {
	
	public void notifyPopUp() {
		JFrame notifyPopUp = new JFrame("Assistance Requested");
		notifyPopUp.setSize(400, 125);
		notifyPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		notifyPopUp.setLocation(1025, 400);
    
		JPanel popupPanel = createLabelPanel("A Customer Has Requested Assistance", 40, 50);
		JPanel buttonPanel = new JPanel();
		JButton confirmButton1 = new JButton("Confirm");
		confirmButton1.addActionListener(e -> handleButtonClick(11));
		buttonPanel.add(confirmButton1);
		notifyPopUp.add(popupPanel, BorderLayout.NORTH);
		notifyPopUp.add(buttonPanel, BorderLayout.CENTER);
    
		notifyPopUp.setVisible(true);
	}
	
	private void handleButtonClick(int buttonNumber) {
        switch (buttonNumber) {
            case 11:
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
