package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;

public class HardwarePopups {

    public static void showScanMainScannerPopup(JFrame parentFrame) {
        JDialog dialog = createDialog(parentFrame, "Scan Main Scanner");
        addTextField(dialog, "Scan barcode using the main scanner:");
        showDialog(dialog);
    }

    public static void showScanHandheldScannerPopup(JFrame parentFrame) {
        JDialog dialog = createDialog(parentFrame, "Scan Handheld Scanner");
        addTextField(dialog, "Scan barcode using the handheld scanner:");
        showDialog(dialog);
    }

    public static void showVisualSearchPopup(JFrame parentFrame) {
        JDialog dialog = createDialog(parentFrame, "Visual Search");
        addTextField(dialog, "Enter item details for visual search:");
        showDialog(dialog);
    }

    public static void showPluCodePopup(JFrame parentFrame) {
        JDialog dialog = createDialog(parentFrame, "PLU Code");
        addTextField(dialog, "Enter PLU code:");
        showDialog(dialog);
    }

    public static void showAddItemToScalePopup(JFrame parentFrame) {
        JDialog dialog = createDialog(parentFrame, "Add Item to Scale");
        addTextField(dialog, "Place item on scale and enter weight:");
        showDialog(dialog);
    }

    public static void showRemoveItemFromScalePopup(JFrame parentFrame) {
        JDialog dialog = createDialog(parentFrame, "Remove Item from Scale");
        addTextField(dialog, "Remove item from scale:");
        showDialog(dialog);
    }
    
    public static void showPayWithCreditPopup(JFrame parentFrame, boolean real) {
        JDialog dialog = createDialog(parentFrame, "Pay With Credit");
        addTextField(dialog, "Pay With Credit:");
        showDialog(dialog);
    }
    
    public static void showPayWithDebitPopup(JFrame parentFrame) {
        JDialog dialog = createDialog(parentFrame, "Pay With Debit");
        addTextField(dialog, "Pay With Debit:");
        showDialog(dialog);
    }
    
    public static void showInsertCoinPopup(JFrame parentFrame) {
        JDialog dialog = createDialog(parentFrame, "Insert Coin");
        addTextField(dialog, "Insert Coin:");
        showDialog(dialog);
    }
    
    public static void showInsertBanknotePopup(JFrame parentFrame) {
        JDialog dialog = createDialog(parentFrame, "Insert Banknote");
        addTextField(dialog, "Insert Banknote:");
        showDialog(dialog);
    }
    
    


    // Pop-up creation logic 

    private static JDialog createDialog(JFrame parentFrame, String title) {
        JDialog dialog = new JDialog(parentFrame, title, true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(new Dimension(300, 200)); 
        dialog.setLocationRelativeTo(parentFrame);
        return dialog;
    }

    private static void addTextField(JDialog dialog, String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT); 
        JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField textField = new JTextField(20); 
        textFieldPanel.add(textField);
        panel.add(label);
        panel.add(Box.createVerticalStrut(5)); 
        panel.add(textFieldPanel); 
        dialog.add(panel, BorderLayout.CENTER);
    }

    private static void showDialog(JDialog dialog) {
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton, BorderLayout.PAGE_END);

        dialog.setVisible(true);
    }
}
