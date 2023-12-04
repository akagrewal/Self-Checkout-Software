package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Numpad extends JPanel {
    private JFrame parentFrame;
    private GUILogic guiLogicInstance;
    private JTextField textField;
    // mode 0 = membership
    // mode 1 = weight
    private int mode;

    public Numpad(JFrame parentFrame, GUILogic guiLogicInstance, int mode) {
        this.mode = mode;
        this.parentFrame = parentFrame;
        this.guiLogicInstance = guiLogicInstance;
        setOpaque(true);
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(200, 250));

        textField = new JTextField("");
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER); // Center-align the text
        textField.setPreferredSize(new Dimension(150, 50)); // Set preferred size for the text field

        // Center the text field in the frame
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(textField);

        // Add the centered panel to the main panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 1;
        add(centerPanel, gbc);

        addNumPadButtons();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(128, 128, 128, 128));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }

    private void addNumPadButtons() {
        JButton button1 = new JButton("1");
        JButton button2 = new JButton("2");
        JButton button3 = new JButton("3");
        JButton button4 = new JButton("4");
        JButton button5 = new JButton("5");
        JButton button6 = new JButton("6");
        JButton button7 = new JButton("7");
        JButton button8 = new JButton("8");
        JButton button9 = new JButton("9");
        JButton buttonCLEAR = new JButton("CLEAR");
        JButton button0 = new JButton("0");
        JButton buttonENTER = new JButton("ENTER");

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(button1, gbc);
        gbc.gridx = 1;
        add(button2, gbc);
        gbc.gridx = 2;
        add(button3, gbc);
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(button4, gbc);
        gbc.gridx = 1;
        add(button5, gbc);
        gbc.gridx = 2;
        add(button6, gbc);
        gbc.gridy = 3;
        gbc.gridx = 0;
        add(button7, gbc);
        gbc.gridx = 1;
        add(button8, gbc);
        gbc.gridx = 2;
        add(button9, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        add(buttonCLEAR, gbc);
        gbc.gridx = 1;
        add(button0, gbc);
        gbc.gridx = 2;
        add(buttonENTER, gbc);

        button1.addActionListener(createNumButtonActionListener("1"));
        button2.addActionListener(createNumButtonActionListener("2"));
        button3.addActionListener(createNumButtonActionListener("3"));
        button4.addActionListener(createNumButtonActionListener("4"));
        button5.addActionListener(createNumButtonActionListener("5"));
        button6.addActionListener(createNumButtonActionListener("6"));
        button7.addActionListener(createNumButtonActionListener("7"));
        button8.addActionListener(createNumButtonActionListener("8"));
        button9.addActionListener(createNumButtonActionListener("9"));
        button0.addActionListener(createNumButtonActionListener("0"));

        buttonCLEAR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
            }
        });

        buttonENTER.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numpadEnter(textField.getText());
            }
        });
    }

    private ActionListener createNumButtonActionListener(String num) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText(textField.getText() + num);
            }
        };
    }

    protected void openNumPadPanel() {
        parentFrame.setGlassPane(this);
        this.setVisible(true);
    }

    protected void closeNumPadPanel() {
        parentFrame.setGlassPane(this);
        this.setVisible(false);
    }

    private void numpadEnter(String input) {
        this.closeNumPadPanel();

        if (mode == 0) {
            guiLogicInstance.checkMembership(input);
        } else if (mode == 1) {
            guiLogicInstance.checkPLU(input);
        }
    }
}
