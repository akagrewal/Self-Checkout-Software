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

import com.thelocalmarketplace.software.logic.CentralStationLogic;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.*;


public class AttendantGUI {
    Vector<String> itemsArray;
    public HashMap<CentralStationLogic, JPanel> stationToButtonMap;
    public ArrayList<CentralStationLogic> stationLogicsList;
    JFrame attendantFrame;

    public AttendantGUI() {
        this.stationToButtonMap = new HashMap<>();
        this.stationLogicsList = new ArrayList<>();
        // TODO: Find a way to incorporate `itemsArray` with `ProductDatabases`.
        itemsArray = new Vector<> (Arrays.asList("Banana", "Apple", "Soup Can", "Pickles Jar"));
    }

    public void createAttendantFrame() {
        attendantFrame = new JFrame("Attendant Screen");
        attendantFrame.setSize(800, 500);
        attendantFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        attendantFrame.setLocation(1000, 0); // Adjust the coordinates as needed

        JPanel mainPanel = new JPanel(new GridLayout(12, 4));

        int i = 0;

        for (CentralStationLogic stationLogic : stationLogicsList) {
            i++;
            JPanel tempPanel = new JPanel(new GridLayout());
            JButton buttonEnable = new JButton("Enable Station " + i);
            JButton buttonDisable = new JButton("DisableStation " + i);
            JComboBox<String> searchBox = new JComboBox<>(itemsArray);
            JButton buttonConfirmSearch = new JButton("Confirm search");
            stationLogic.attendantLogic.attendantGUI = this;
            tempPanel.add(buttonEnable);
            tempPanel.add(buttonDisable);
            tempPanel.add(searchBox);
            tempPanel.add(buttonConfirmSearch);

            buttonEnable.addActionListener(e -> stationLogic.attendantLogic.enableCustomerStation(stationLogic));
            buttonDisable.addActionListener(e -> {
                if(stationLogic.isSessionStarted()){
                    JOptionPane.showMessageDialog(attendantFrame, "Cannot disable an active session", "Information", JOptionPane.INFORMATION_MESSAGE);;
                } else{stationLogic.attendantLogic.disableCustomerStation(stationLogic);}
            });
            buttonConfirmSearch.addActionListener(e -> {
                	 String textSearch = (String)searchBox.getSelectedItem();
                     stationLogic.attendantLogic.AddItemByTextSearch(stationLogic, textSearch);
                });
            
            tempPanel.add(buttonEnable);
            tempPanel.add(buttonDisable);
            tempPanel.add(searchBox);
            tempPanel.add(buttonConfirmSearch);
            stationToButtonMap.put(stationLogic, tempPanel);

            mainPanel.add(tempPanel);
        }

        attendantFrame.add(mainPanel);

        attendantFrame.setVisible(true);
    }

    public JFrame getAttendantFrame() {
        return attendantFrame;
    }
}
