package com.thelocalmarketplace.software.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jjjwelectronics.EmptyDevice;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BagKeypad {
    private JTextField bagsTextField;
    private CentralStationLogic station;
    private JFrame parent;

    public BagKeypad(JFrame parent, CentralStationLogic station) {
        this.station = station;
        this.parent = parent;
        showNumberInputDialog(parent, "Enter the number of bags:");
    }

    private void showNumberInputDialog(JFrame parent, String message) {
        bagsTextField = new JTextField(10);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(bagsTextField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 3));
        for (int i = 1; i <= 9; i++) {
            buttonPanel.add(createNumberButton(String.valueOf(i)));
        }
        buttonPanel.add(createNumberButton("0"));
        buttonPanel.add(createClearButton());
        panel.add(buttonPanel, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(parent, panel, message, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int bags = Integer.parseInt(bagsTextField.getText());
                station.purchaseBagsLogic.dispensePurchasedBags(bags);
                station.guiLogic.addPurchasableBag(bags);
            } catch (Exception ignored) {}
        }
    }

    private JButton createNumberButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bagsTextField.setText(bagsTextField.getText() + label);
            }
        });
        return button;
    }

    private JButton createClearButton() {
        JButton clearButton = new JButton("C");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bagsTextField.setText("");
            }
        });
        return clearButton;
    }
}
