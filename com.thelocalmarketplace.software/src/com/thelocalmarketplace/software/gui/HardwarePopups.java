package com.thelocalmarketplace.software.gui;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HardwarePopups {

    private static GUILogic guiLogic;

    public HardwarePopups(GUILogic guiLogic) {
        HardwarePopups.guiLogic = guiLogic;
    }

	public static void showScanMainScannerPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Scan Main Scanner");
		JTextField textField = addTextField(dialog, "Scan barcode using the main scanner:");
		Consumer<String> onSubmit = inputText -> {
			//TODO
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	public static void showScanHandheldScannerPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Scan Handheld Scanner");
		JTextField textField = addTextField(dialog, "Scan barcode using the handheld scanner:");
		Consumer<String> onSubmit = inputText -> {
			//TODO 
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	public static void showVisualSearchPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Visual Search");
		JTextField textField = addTextField(dialog, "Enter item details for visual search:");
		Consumer<String> onSubmit = inputText -> {
            PriceLookUpCode priceLookUpCode = new PriceLookUpCode(inputText);
            PLUCodedProduct pk = ProductDatabases.PLU_PRODUCT_DATABASE.get(priceLookUpCode);
            guiLogic.notifyItemAdded(pk);
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	public static void showPluCodePopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "PLU Code");
		JTextField textField = addTextField(dialog, "Enter PLU code:");
		Consumer<String> onSubmit = inputText -> {
            PriceLookUpCode priceLookUpCode = new PriceLookUpCode(inputText);
            PLUCodedProduct pk = ProductDatabases.PLU_PRODUCT_DATABASE.get(priceLookUpCode);
            guiLogic.notifyItemAdded(pk);
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	public static void showAddItemToScalePopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Add Item to Scale");
		JTextField textField = addTextField(dialog, "Place item on scale and enter weight of item:");
		Consumer<String> onSubmit = inputText -> {
			Mass weight = new Mass(Double.parseDouble(inputText));
            guiLogic.addItemToScale(weight);
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	public static void showRemoveItemFromScalePopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Remove Item from Scale");
		JTextField textField = addTextField(dialog, "Remove item from scale:");
		Consumer<String> onSubmit = inputText -> {
            Mass weight = new Mass(Double.parseDouble(inputText));
            guiLogic.removeItemFromScale(weight);
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	public static void showPayWithCreditPopup(JFrame parentFrame, boolean real) {
		JDialog dialog = createDialog(parentFrame, real ? "Pay With Real Credit" : "Pay With Fake Credit");
		JTextField textField = addTextField(dialog, "Enter credit card details:");
		Consumer<String> onSubmit = inputText -> {
			int cardNumber = Integer.parseInt(inputText);
			if (real) {
				//TODO 
			} else {
				//TODO: can we just have an error message here?
			}
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	public static void showPayWithDebitPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Pay With Debit");
		JTextField textField = addTextField(dialog, "Enter debit card details:");
		Consumer<String> onSubmit = inputText -> {
			//TODO 
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	public static void showInsertCoinPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Insert Coin");
		JTextField textField = addTextField(dialog, "Enter coin value:");
		Consumer<String> onSubmit = inputText -> {
			//TODO 
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	public static void showInsertBanknotePopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Insert Banknote");
		JTextField textField = addTextField(dialog, "Enter banknote value:");
		Consumer<String> onSubmit = inputText -> {
			//TODO 
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	// Pop-up creation logic
	
	private static void addSubmitButton(JDialog dialog, JTextField textField, Consumer<String> onSubmit) {
	    JButton submitButton = new JButton("Submit");
	    submitButton.addActionListener(e -> {
	        String inputText = textField.getText();
	        onSubmit.accept(inputText); // Calls the passed consumer function with the text as input
	        dialog.dispose();
	    });
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    buttonPanel.add(submitButton);
	    dialog.add(buttonPanel, BorderLayout.PAGE_END);
	}

	private static JDialog createDialog(JFrame parentFrame, String title) {
		JDialog dialog = new JDialog(parentFrame, title, true);
		dialog.setLayout(new BorderLayout());
		dialog.setSize(new Dimension(300, 200));
		dialog.setLocationRelativeTo(parentFrame);
		return dialog;
	}

    private static JTextField addTextField(JDialog dialog, String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT); 
        JTextField textField = new JTextField(20);
        panel.add(label);
        panel.add(Box.createVerticalStrut(5)); 
        panel.add(textField); 
        dialog.add(panel, BorderLayout.CENTER);
        return textField;
    }

	private static void showDialog(JDialog dialog) {
		dialog.setVisible(true);
	}
}
