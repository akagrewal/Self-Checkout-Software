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

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SessionBlockedPopUp {

    private static JDialog popupDialog;
    private static HashMap<Integer, JDialog> popUps = new HashMap<Integer, JDialog>();
    
    /** popup for customer */
    public static void customerDiscrepancyDetected(GUILogic guiLogic) {
        /* popupDialog = new JDialog(parentFrame);
        popUps.put(stationNumber, popupDialog);
        popupDialog.setUndecorated(true);
        popupDialog.setSize(800, 700);
        popupDialog.setLocationRelativeTo(parentFrame);

        JLabel label = new JLabel("Session Blocked. Attendant Notified.");

        // Set a larger font for the label
        Font font = new Font("Arial", Font.PLAIN, 24);
        label.setFont(font);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        popupDialog.add(label);

        popupDialog.setVisible(true);*/
        guiLogic.blockGUI();
    }
    
    /** popup for attendant */
    public static void discrepancyDetected(JFrame parentFrame, int stationNumber) {
        popupDialog = new JDialog(parentFrame);
        popupDialog.setSize(300, 200);
        popupDialog.setLocationRelativeTo(parentFrame);

        JLabel label = new JLabel("Weight issue at Station " + stationNumber);
        label.setFont(new Font("Sans", Font.BOLD, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        popupDialog.add(label);

        popupDialog.setVisible(true);
    }
    
    public static void outOfOrder(JPanel parentPanel, int stationNumber) {
        popupDialog = new JDialog();
        popUps.put(stationNumber, popupDialog);
        popupDialog.setUndecorated(true);
        popupDialog.setSize(800, 700);
        popupDialog.setLocationRelativeTo(parentPanel);

        JLabel label = new JLabel("OUT OF ORDER");

        // Set a larger font for the label
        Font font = new Font("Arial", Font.PLAIN, 24);
        label.setFont(font);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        popupDialog.add(label);

        popupDialog.setVisible(true);
    }

    public static void removeOutOfOrder(int stationNumber) {
        popUps.get(stationNumber).dispose();
        popUps.remove(stationNumber);
    }


    public static void maintenanceRequired(JFrame parentFrame, String issue) {
        popupDialog = new JDialog(parentFrame);
        popupDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        popupDialog.setSize(800, 700);
        popupDialog.setLocationRelativeTo(parentFrame);

        JLabel label = new JLabel("MAINTENANCE REQUIRED FOR "+issue);
        label.setFont(new Font("Arial", Font.PLAIN, 24));

        popupDialog.add(label);

        popupDialog.setVisible(true);
    }

}
