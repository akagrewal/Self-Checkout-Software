

package com.thelocalmarketplace.software.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AttendantFrame {
	 
	JLabel totalLabel;
	
    // Attendant Frame --------------------------------------BEGIN
	// It assumes that there is only one SelfCheckoutStation right now 
	
    public void createAttendantFrame() {
        JFrame attend_frame = new JFrame("Attendant Screen");
        attend_frame.setSize(450, 800);
        attend_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        attend_frame.setLocation(1000, 0); // Adjust the coordinates as needed

        JPanel mainPanel = new JPanel(new GridLayout(3,1));
        JPanel till1 = new JPanel(new GridLayout(2,2));
        JPanel till2 = new JPanel(new GridLayout(2, 2));
        JPanel till3 = new JPanel(new GridLayout(2, 2));

        till1.add(new JButton("activate till 1"));
        till2.add(new JButton("activate till 2"));
        till3.add(new JButton("activate till 3"));

        till1.add(new JButton("deactivate till 1"));
        till2.add(new JButton("deactivate till 2"));
        till3.add(new JButton("deactivate till 3"));


        till1.add(new JTextField());
        till2.add(new JTextField());
        till3.add(new JTextField());

        mainPanel.add(till1);
        mainPanel.add(till2);
        mainPanel.add(till3);

        attend_frame.add(mainPanel);

        attend_frame.setVisible(true);
    }



    private void handleButtonClick(int buttonNumber) {
        switch (buttonNumber) {
            case 1:
                System.out.println("Meow");
                //insert logic
                break;
            case 2:
                System.out.println("Button Clicked");
                //insert Logic
                break;
            case 3:
                System.out.println("Button Clicked");
                //insert Logic
                break;
            case 4:
                System.out.println("Button Clicked");
                //insert Logic
                break;
            case 5:
                System.out.println("Button Clicked");
                //insert Logic
                break;
            case 6:
                System.out.println("Button Clicked");
                //insert Logic
                break;
            case 7:
                System.out.println("Button Clicked");
                //insert Logic
                break;
            case 8:
                System.out.println("Button Clicked");
                //insert Logic
                break;
            case 9:
                System.out.println("Button Clicked");
                //insert Logic
                break;
            case 10:
                System.out.println("Button Clicked");
                //insert Logic
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
