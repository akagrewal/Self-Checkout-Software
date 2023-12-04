package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaggingAreaPopUp {
    StationGUI stationGUI;

    BaggingAreaPopUp(StationGUI stationGUI){
        this.stationGUI = stationGUI;
    }

    public JFrame createBaggingAreaPopUp() {
        JFrame popupFrame = new JFrame("Place Item on Scale");
        popupFrame.setSize(300, 200);
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Please place item on the scale.");
        label.setHorizontalAlignment(SwingConstants.CENTER);



        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupFrame.dispose();
                stationGUI.getLogicInstance().switchPanels("AddItemsPanel");
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(doneButton, BorderLayout.SOUTH);

        popupFrame.add(panel);
        popupFrame.setVisible(true);
        return popupFrame;
    }
}
