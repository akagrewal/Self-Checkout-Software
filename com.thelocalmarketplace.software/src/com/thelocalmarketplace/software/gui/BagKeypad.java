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
