package com.thelocalmarketplace.software.gui;

import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;


import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.*;


public class AttendantFrame {
	HashMap<CentralStationLogic, JPanel> stationLogicsMap;
    Vector<String> itemsArray;

    public AttendantFrame() {
        stationLogicsMap = new HashMap<>();
        itemsArray = new Vector<> (Arrays.asList("Apricot", "Apricot Jam", "Apple", "Bagel", "Banana"));
    }

    public void registerStationLogic(CentralStationLogic logic) {
        stationLogicsMap.put(logic, null);
    }

    public void deregisterStationLogic(CentralStationLogic logic) {
        stationLogicsMap.remove(logic);
    }

    public void createAttendantFrame() {

        JFrame attendantFrame = new JFrame("Attendant Screen");
        attendantFrame.setSize(450, 600);
        attendantFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        attendantFrame.setLocation(1000, 0); // Adjust the coordinates as needed

        JPanel mainPanel = new JPanel(new GridLayout(12,3));

        int i = 0;
        for (CentralStationLogic stationLogic : stationLogicsMap.keySet()) {
            i++;
            JPanel tempPanel = new JPanel(new GridLayout(1,3));
            JButton buttonEnable = new JButton("Enable Station "+i);
            JButton buttonDisable = new JButton("DisableStation "+i);
            JComboBox<String> searchBox = new JComboBox<>(itemsArray);
            tempPanel.add(buttonEnable);
            tempPanel.add(buttonDisable);
            tempPanel.add(searchBox);
            stationLogicsMap.put(stationLogic, tempPanel);
            mainPanel.add(tempPanel);
        }

        attendantFrame.add(mainPanel);

        attendantFrame.setVisible(true);
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



    /*
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
     */
}
