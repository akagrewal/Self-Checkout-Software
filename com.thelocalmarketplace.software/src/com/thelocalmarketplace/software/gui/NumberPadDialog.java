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
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberPadDialog extends JDialog {
    private JTextField targetTextField;

    public NumberPadDialog(JFrame parentFrame, JTextField targetTextField) {
        super(parentFrame, "Number Pad", true); // true for modal

        this.targetTextField = targetTextField;

        // Create number pad buttons
        JPanel numberPadPanel = createNumberPadPanel();

        // Add the number pad to the dialog content pane
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(numberPadPanel, BorderLayout.CENTER);

        // Set dialog properties
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(parentFrame);
    }

    private JPanel createNumberPadPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 3, 5, 5));

        for (int i = 1; i <= 9; i++) {
            addButton(panel, String.valueOf(i));
        }

        addButton(panel, "0");
        addButton(panel, "Clear");
        addButton(panel, "Close");

        return panel;
    }

    private void addButton(JPanel panel, String label) {
        JButton button = new JButton(label);
        button.addActionListener(new NumberPadButtonListener());
        panel.add(button);
    }

    private class NumberPadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            String buttonText = sourceButton.getText();

            if (buttonText.equals("Clear")) {
                targetTextField.setText("");
            } else if (buttonText.equals("Close")) {
                dispose(); // Close the dialog
            } else {
                targetTextField.setText(targetTextField.getText() + buttonText);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Frame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JTextField textField = new JTextField();

            JButton openButton = new JButton("Open Number Pad");
            openButton.addActionListener(e -> {
                NumberPadDialog dialog = new NumberPadDialog(frame, textField);
                dialog.setVisible(true);
            });

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(textField, BorderLayout.NORTH);
            mainPanel.add(openButton, BorderLayout.SOUTH);

            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }
}
