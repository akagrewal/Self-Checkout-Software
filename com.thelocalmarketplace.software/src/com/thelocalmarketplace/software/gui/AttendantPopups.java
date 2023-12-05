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

import com.thelocalmarketplace.software.logic.AttendantLogic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

public class AttendantPopups {
	protected JFrame attendantFrame;

	public AttendantPopups(JFrame attendantFrame) {
		this.attendantFrame = attendantFrame;
	}

	public AttendantPopups(AttendantLogic attendantLogic) {
		this.attendantFrame = attendantLogic.attendantGUI.getAttendantFrame();
	}

	public void notifyPopUp(int stationNumber) {
		JFrame notifyPopUp = new JFrame("Assistance Requested: Station" + stationNumber);
		notifyPopUp.setSize(400, 300);
		notifyPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel popupPanel = createLabelPanel("Assistance Requested: Station " + stationNumber, 40, 50);
		JPanel buttonPanel = new JPanel();
		JButton confirmButton1 = new JButton("OK");
		confirmButton1.addActionListener(e -> notifyPopUp.dispose());
		buttonPanel.add(confirmButton1);
		notifyPopUp.add(popupPanel, BorderLayout.NORTH);
		notifyPopUp.add(buttonPanel, BorderLayout.CENTER);
		notifyPopUp.setLocationRelativeTo(attendantFrame);

		notifyPopUp.setVisible(true);

	}

	public void issuePredictedPopUp(String issueString) {
		JFrame issuePopup = new JFrame("Maintenance Required");
		issuePopup.setSize(400, 300);
		issuePopup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel popupPanel = createLabelPanel("Maintenance required for "+issueString, 40, 50);
		JPanel buttonPanel = new JPanel();
		JButton confirmButton1 = new JButton("Confirm");
		confirmButton1.addActionListener(e -> issuePopup.dispose());
		buttonPanel.add(confirmButton1);
		issuePopup.add(popupPanel, BorderLayout.NORTH);
		issuePopup.add(buttonPanel, BorderLayout.CENTER);

		issuePopup.setVisible(true);
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

	public void maintenceComplete(String message) {
		JFrame notifyPopUp = new JFrame("Maintenance Complete: " + message);
		notifyPopUp.setSize(400, 300);
		notifyPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel popupPanel = createLabelPanel("Maintenance Complete: " + message, 40, 50);
		JPanel buttonPanel = new JPanel();
		JButton confirmButton1 = new JButton("OK");
		confirmButton1.addActionListener(e -> notifyPopUp.dispose());
		buttonPanel.add(confirmButton1);
		notifyPopUp.add(popupPanel, BorderLayout.NORTH);
		notifyPopUp.add(buttonPanel, BorderLayout.CENTER);
		notifyPopUp.setLocationRelativeTo(attendantFrame);

		notifyPopUp.setVisible(true);

	}
}
