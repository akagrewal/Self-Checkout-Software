package com.thelocalmarketplace.software.gui;

import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;


import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.*;


public class AttendantFrame {
    public HashMap<CentralStationLogic, JPanel> stationLogicsMap;
    Vector<String> itemsArray;

    public AttendantFrame() {
        stationLogicsMap = new HashMap<>();
        // TODO: Find a way to incorporate `itemsArray` with `ProductDatabases`.
        itemsArray = new Vector<> (Arrays.asList("Banana", "Apple", "Soup Can", "Pickles Jar"));
    }

    public void registerStationLogic(CentralStationLogic logic) {
        stationLogicsMap.put(logic, null);
    }

    public void deregisterStationLogic(CentralStationLogic logic) {
        stationLogicsMap.remove(logic);
    }

    public void createAttendantFrame() {

        JFrame attendantFrame = new JFrame("Attendant Screen");
        attendantFrame.setSize(800, 500);
        attendantFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        attendantFrame.setLocation(1000, 0); // Adjust the coordinates as needed

        JPanel mainPanel = new JPanel(new GridLayout(12, 4));

        int i = 0;
        for (CentralStationLogic stationLogic : stationLogicsMap.keySet()) {
            i++;
            JPanel tempPanel = new JPanel(new GridLayout());

            JButton buttonEnable = new JButton("Enable Station " + i);
            JButton buttonDisable = new JButton("DisableStation " + i);
            JComboBox<String> searchBox = new JComboBox<>(itemsArray);
            JButton buttonConfirmSearch = new JButton("Confirm search");
            tempPanel.add(buttonEnable);
            tempPanel.add(buttonDisable);
            tempPanel.add(searchBox);
            tempPanel.add(buttonConfirmSearch);

            buttonEnable.addActionListener(e -> stationLogic.attendantLogic.enableCustomerStation());
            buttonDisable.addActionListener(e -> stationLogic.attendantLogic.disableCustomerStation());
            // buttonConfirmSearch.addActionListener(e -> textsearch());

            tempPanel.add(buttonEnable);
            tempPanel.add(buttonDisable);
            tempPanel.add(searchBox);
            tempPanel.add(buttonConfirmSearch);
            stationLogicsMap.put(stationLogic, tempPanel);

            mainPanel.add(tempPanel);
        }

        attendantFrame.add(mainPanel);

        attendantFrame.setVisible(true);
    }
}
