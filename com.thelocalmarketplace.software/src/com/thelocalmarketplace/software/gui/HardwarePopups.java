package com.thelocalmarketplace.software.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.coin.Coin;
import com.tdc.banknote.*;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.software.database.CreateTestDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

public class HardwarePopups {

	private BarcodedItem soup = new BarcodedItem(CreateTestDatabases.soup.getBarcode(), new Mass(CreateTestDatabases.soup.getExpectedWeight()));
	private BarcodedItem pickles = new BarcodedItem(CreateTestDatabases.pickles.getBarcode(), new Mass(CreateTestDatabases.pickles.getExpectedWeight()));
	private PLUCodedItem apples = new PLUCodedItem(CreateTestDatabases.apple.getPLUCode(), new Mass((double) 300.0));
	private PLUCodedItem bananas = new PLUCodedItem(CreateTestDatabases.banana.getPLUCode(), new Mass((double) 500.0));
	private PLUCodedItem bagsUnder = new PLUCodedItem(CreateTestDatabases.bagsUnder.getPLUCode(), new Mass((double) 15.0));
	private PLUCodedItem bagsOver = new PLUCodedItem(CreateTestDatabases.bagsOver.getPLUCode(), new Mass((double) 35.0));

	private CentralStationLogic centralStationLogic;

	// creating cards
	CardIssuer bank= new CardIssuer("Scotia Bank",3);

	Card realCreditCard = new Card("CREDIT","123456789","Jane","329", "1234", true, true);
	Card fakeCreditCard = new Card("CREDIT","123456788","John","328", "1233", true, true);

	Card realDebitCard = new Card("DEBIT","123456787","John","327", "1232", true, true);
	Card fakeDebitCard = new Card("DEBIT","123456786","Jane","326", "1231", true, true);



	public HardwarePopups(CentralStationLogic centralStationLogic) {

		this.centralStationLogic = centralStationLogic;

		this.centralStationLogic.setupBankDetails(bank);
		Calendar expiry = Calendar.getInstance();
		expiry.set(2025,Calendar.JANUARY,24);
		// adding REAL cards' data so bank's database
		bank.addCardData("123456789", "Jane",expiry,"329",32.00);
		bank.addCardData("123456787", "John",expiry,"327",32.00);

		AbstractSelfCheckoutStation.resetConfigurationToDefaults();

	}

	
	//pop up for show change
	public void showChangeDispensed(String message) {
		JFrame changeFrame = new JFrame();
	    JLabel label = new JLabel("Change dispensed: $" + message);
	    Font font = new Font("Arial", Font.PLAIN, 22);
	    label.setFont(font);
	    
	
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 15, 10, 15); 
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 2); 
        Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, paddingBorder);
        
    
        label.setBorder(compoundBorder);
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(label);

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.add(centerPanel, BorderLayout.CENTER);

        changeFrame.setTitle("Change");
        changeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        changeFrame.getContentPane().add(outerPanel); 
        changeFrame.pack();
  
		changeFrame.setSize(new Dimension(300, 300));
		

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

   
        int screenWidth = screenSize.width;
        int frameWidth = changeFrame.getWidth();
        int xCoordinate = screenWidth - frameWidth;
        int yCoordinate = 0; 

    
        changeFrame.setLocation(xCoordinate, yCoordinate);
	

		changeFrame.setVisible(true);
	}
	
	
	public void showScanMainScannerPopup(JFrame parentFrame) {
		JFrame selectionFrame = new JFrame();
		selectionFrame.setTitle("Scan Main Scanner");
		selectionFrame.setSize(new Dimension(200, 300));
		selectionFrame.setLayout(new FlowLayout());

		JButton soupButton = new JButton("Soup");
		JButton picklesButton = new JButton("Pickle Jar");
		soupButton.addActionListener(e -> {
			if(!centralStationLogic.isSessionStarted()){
				centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
			} else {
			centralStationLogic.hardware.getMainScanner().scan(soup);
			selectionFrame.dispose();}
		});
		picklesButton.addActionListener(e -> {
			if(!centralStationLogic.isSessionStarted()){
				centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
			} else {
			centralStationLogic.hardware.getMainScanner().scan(pickles);
			selectionFrame.dispose();}
		});

		selectionFrame.add(soupButton);
		selectionFrame.add(picklesButton);
		selectionFrame.setLocationRelativeTo(parentFrame);
		selectionFrame.setVisible(true);
	}

	public void showScanHandheldScannerPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Scan Handheld Scanner");
		JButton soupButton = new JButton("Soup");
		JButton picklesButton = new JButton("Pickle Jar");

		soupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				centralStationLogic.hardware.getHandheldScanner().scan(soup);
			}}
		});
		picklesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				centralStationLogic.hardware.getHandheldScanner().scan(pickles);
			}}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		dialog.add(panel);
		panel.add(soupButton);
		panel.add(picklesButton);
		showDialog(dialog);
	}



	public void showAddItemToScalePopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Item to add to bagging area: ");


		JButton appleButton = new JButton("Apples");
		JButton bananaButton = new JButton("Bananas");

		appleButton.addActionListener(e -> {
			JDialog scaleDialog = createDialog(parentFrame, "Number of items");
			JTextField textField = addTextField(scaleDialog, "Total weight of items:");
			Consumer<String> onSubmit = inputText -> {
				double massOfItems = Integer.parseInt(inputText);
				PLUCodedItem currentItem = new PLUCodedItem(CreateTestDatabases.apple.getPLUCode(), new Mass(massOfItems));
				centralStationLogic.hardware.getBaggingArea().addAnItem(currentItem);
				centralStationLogic.scanningAreaController.itemsOnScale.add(currentItem);
			};
			addSubmitButton(scaleDialog, textField, onSubmit);
			showDialog(scaleDialog);
		});
		bananaButton.addActionListener(e -> {
			JDialog scaleDialog = createDialog(parentFrame, "Number of items");
			JTextField textField = addTextField(scaleDialog, "Total weight of items:");
			Consumer<String> onSubmit = inputText -> {
				double massOfItems = Integer.parseInt(inputText);
				PLUCodedItem currentItem = new PLUCodedItem(CreateTestDatabases.banana.getPLUCode(), new Mass(massOfItems));
				centralStationLogic.hardware.getBaggingArea().addAnItem(currentItem);
				centralStationLogic.scanningAreaController.itemsOnScale.add(currentItem);
			};
			addSubmitButton(scaleDialog, textField, onSubmit);
			showDialog(scaleDialog);
		});

		JButton soupButton = new JButton("Soup");
		JButton pickleButton = new JButton("Pickle Jar");
		JButton bagsUnderButton = new JButton("Bags (under weight limit)");
		JButton bagsOverButton = new JButton("Bags (over weight limit)");

		soupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
					centralStationLogic.hardware.getBaggingArea().addAnItem(soup);
				}}
		});
		pickleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
					centralStationLogic.hardware.getBaggingArea().addAnItem(pickles);
				}}
		});

		bagsUnderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
					centralStationLogic.hardware.getBaggingArea().addAnItem(bagsUnder);
				}}
		});
		bagsOverButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
					centralStationLogic.hardware.getBaggingArea().addAnItem(bagsOver);
				}}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		dialog.add(panel);
		panel.add(soupButton);
		panel.add(pickleButton);
		panel.add(appleButton);
		panel.add(bananaButton);
		panel.add(bagsUnderButton);
		panel.add(bagsOverButton);
		showDialog(dialog);
	}


	public void showRemoveItemFromScalePopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Remove an item from the scale");
		JButton soupButton = new JButton("Soup");
		JButton pickleButton = new JButton("Pickle Jar");

		soupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				centralStationLogic.hardware.getBaggingArea().removeAnItem(soup);;
			}}
		});
		pickleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				centralStationLogic.hardware.getBaggingArea().removeAnItem(pickles);
			}}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		dialog.add(panel);
		panel.add(soupButton);
		panel.add(pickleButton);
		showDialog(dialog);
	}
	public void showMeasureItemsOnPLUScalePopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Please place items onto scale");
		JButton apple = new JButton("Apples");
		JButton banana = new JButton("Bananas");

		apple.addActionListener(e -> {
            JDialog scaleDialog = createDialog(parentFrame, "Number of items");
            JTextField textField = addTextField(scaleDialog, "Total weight of items:");
            Consumer<String> onSubmit = inputText -> {
                double massOfItems = Integer.parseInt(inputText);
                PLUCodedItem currentItem = new PLUCodedItem(CreateTestDatabases.apple.getPLUCode(), new Mass(massOfItems));
                centralStationLogic.hardware.getScanningArea().addAnItem(currentItem);
                centralStationLogic.scanningAreaController.itemsOnScale.add(currentItem);
            };
            addSubmitButton(scaleDialog, textField, onSubmit);
            showDialog(scaleDialog);
        });
		banana.addActionListener(e -> {
            JDialog scaleDialog = createDialog(parentFrame, "Number of items");
            JTextField textField = addTextField(scaleDialog, "Total weight of items:");
            Consumer<String> onSubmit = inputText -> {
                double massOfItems = Integer.parseInt(inputText);
                PLUCodedItem currentItem = new PLUCodedItem(CreateTestDatabases.banana.getPLUCode(), new Mass(massOfItems));
                centralStationLogic.hardware.getScanningArea().addAnItem(currentItem);
                centralStationLogic.scanningAreaController.itemsOnScale.add(currentItem);
            };
            addSubmitButton(scaleDialog, textField, onSubmit);
            showDialog(scaleDialog);
        });

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		dialog.add(panel);
		panel.add(apple);
		panel.add(banana);
		showDialog(dialog);
	}

	public void showPayWithCreditPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Pay With Credit");
		JButton swipeReal = new JButton("Swipe valid credit card");
		JButton swipeFake = new JButton("Swipe invalid credit card");
		JButton insertReal = new JButton("Insert valid credit card");
		JButton insertFake = new JButton("Insert invalid credit card");
		JButton tapReal = new JButton("Tap valid credit card");
		JButton tapFake = new JButton("Tap invalid credit card");

		swipeReal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				try {
					centralStationLogic.hardware.getCardReader().swipe(realCreditCard);
				} catch (IOException e1) {
					centralStationLogic.guiLogic.showExceptionMessage("IOException, Please Request Attendance");
				} }
			}
		});
		swipeFake.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {

				try {
					centralStationLogic.hardware.getCardReader().swipe(fakeCreditCard);
				} catch (IOException e1) {
					centralStationLogic.guiLogic.showExceptionMessage("IOException, Please Request Attendance");
				}}
			}
		});

		insertReal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				JDialog pinDialog = createDialog(parentFrame, "pin");
				JTextField textField = addTextField(pinDialog, "Enter pin:");
				Consumer<String> onSubmit = inputText -> {
					try {
						centralStationLogic.hardware.getCardReader().insert(realCreditCard, inputText);
					} catch (IOException e1) {
						centralStationLogic.guiLogic.showExceptionMessage("IOException, Please Request Attendance");
						e1.printStackTrace();

					}

				};
				addSubmitButton(pinDialog, textField, onSubmit);
				showDialog(pinDialog);}

			}
		});
		insertFake.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				JDialog pinDialog = createDialog(parentFrame, "pin");
				JTextField textField = addTextField(pinDialog, "Enter pin:");
				Consumer<String> onSubmit = inputText -> {
					try {
						centralStationLogic.hardware.getCardReader().insert(fakeCreditCard, inputText);
					} catch (IOException e1) {
						centralStationLogic.guiLogic.showExceptionMessage("IOException, Please Request Attendance");
						e1.printStackTrace();

					}

					//guiLogic.insertCoin(coinValue);
				};
				addSubmitButton(pinDialog, textField, onSubmit);
				showDialog(pinDialog);}

			}
		});
		tapReal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				try {
					centralStationLogic.hardware.getCardReader().tap(realCreditCard);
				} catch (IOException e1) {
					centralStationLogic.guiLogic.showExceptionMessage("IOException, Please Request Attendance");
				}}
			}
		});
		tapFake.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				try {
					centralStationLogic.hardware.getCardReader().tap(fakeCreditCard);
				} catch (IOException e1) {
					centralStationLogic.guiLogic.showExceptionMessage("IOException, Please Request Attendance");
				}}
			}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		dialog.add(panel);
		panel.add(swipeReal);
		panel.add(swipeFake);
		panel.add(insertReal);
		panel.add(insertFake);
		panel.add(tapReal);
		panel.add(tapFake);
		showDialog(dialog);
	}

	public void showPayWithDebitPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Pay With Debit");
		JButton swipeReal = new JButton("Swipe valid debit card");
		JButton swipeFake = new JButton("Swipe invalid debit card");
		JButton insertReal = new JButton("Insert valid debit card");
		JButton insertFake = new JButton("Insert invalid debit card");
		JButton tapReal = new JButton("Tap valid debit card");
		JButton tapFake = new JButton("Tap invalid debit card");

		swipeReal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				try {
					centralStationLogic.hardware.getCardReader().swipe(realDebitCard);
				} catch (IOException e1) {
					centralStationLogic.guiLogic.showExceptionMessage("IOException, Please Request Attendance");
				}}
			}
		});
		swipeFake.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				try {
					centralStationLogic.hardware.getCardReader().swipe(fakeDebitCard);
				} catch (IOException e1) {
					centralStationLogic.guiLogic.showExceptionMessage("IOException, Please Request Attendance");
				}}
			}
		});

		// NEED INSERT
		insertReal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				JDialog pinDialog = createDialog(parentFrame, "pin");
				JTextField textField = addTextField(pinDialog, "Enter pin:");
				Consumer<String> onSubmit = inputText -> {
					try {
						centralStationLogic.hardware.getCardReader().insert(realDebitCard, inputText);
					} catch (IOException e1) {
						centralStationLogic.guiLogic.showExceptionMessage("IOException, Please Request Attendance");
						e1.printStackTrace();

					}

				};
				addSubmitButton(pinDialog, textField, onSubmit);
				showDialog(pinDialog);}

			}
		});
		insertFake.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				JDialog pinDialog = createDialog(parentFrame, "pin");
				JTextField textField = addTextField(pinDialog, "Enter pin:");
				Consumer<String> onSubmit = inputText -> {
					try {
						centralStationLogic.hardware.getCardReader().insert(fakeDebitCard, inputText);
					} catch (IOException e1) {
						centralStationLogic.guiLogic.showExceptionMessage("IOException, Please Request Attendance");
						e1.printStackTrace();

					}

					//guiLogic.insertCoin(coinValue);
				};
				addSubmitButton(pinDialog, textField, onSubmit);
				showDialog(pinDialog);}

			}
		});

		tapReal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				try {
					centralStationLogic.hardware.getCardReader().tap(realDebitCard);
				} catch (IOException e1) {
					centralStationLogic.guiLogic.showExceptionMessage("IOException, Please Request Attendance");
				}
			}}
		});
		tapFake.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!centralStationLogic.isSessionStarted()){
					centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
				} else {
				try {
					centralStationLogic.hardware.getCardReader().tap(fakeDebitCard);
				} catch (IOException e1) {
					centralStationLogic.guiLogic.showExceptionMessage("IOException, Please Request Attendance");
				}}
			}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		dialog.add(panel);
		panel.add(swipeReal);
		panel.add(swipeFake);
		panel.add(insertReal);
		panel.add(insertFake);
		panel.add(tapReal);
		panel.add(tapFake);
		showDialog(dialog);
	}

	public void showInsertCoinPopup(JFrame parentFrame) {

		JFrame selectionFrame = new JFrame();
		selectionFrame.setTitle("Enter a coin");
		selectionFrame.setSize(new Dimension(200, 300));
		selectionFrame.setLayout(new FlowLayout());

		JButton nikle = new JButton("Nickle ($0.05)");
		JButton dime = new JButton("Dime ($0.10)");
		JButton quarter = new JButton("Quarter ($0.25)");
		JButton loonie = new JButton("Loonie ($1.00)");
		JButton toonie = new JButton("Toonie ($2.00)");

		nikle.addActionListener(e -> {
			if(!centralStationLogic.isSessionStarted()){
				centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
			} else {
			Coin nickleCoin = new Coin(Currency.getInstance("CAD"), new BigDecimal("0.05"));
			try {
				centralStationLogic.hardware.getCoinSlot().sink.receive(nickleCoin);
			} catch (DisabledException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CashOverloadException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			selectionFrame.dispose();}
		});
		dime.addActionListener(e -> {
			if(!centralStationLogic.isSessionStarted()){
				centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
			} else {
			Coin dimeCoin = new Coin(Currency.getInstance("CAD"), new BigDecimal("0.10"));
			try {
				centralStationLogic.hardware.getCoinSlot().receive(dimeCoin);
			} catch (DisabledException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CashOverloadException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			selectionFrame.dispose();}
		});
		quarter.addActionListener(e -> {
			if(!centralStationLogic.isSessionStarted()){
				centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
			} else {
			Coin quarterCoin = new Coin(Currency.getInstance("CAD"), new BigDecimal("0.25"));
			try {
				centralStationLogic.hardware.getCoinSlot().receive(quarterCoin);
			} catch (DisabledException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CashOverloadException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			selectionFrame.dispose();}
		});
		loonie.addActionListener(e -> {
			if(!centralStationLogic.isSessionStarted()){
				centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
			} else {
			Coin loonieCoin = new Coin(Currency.getInstance("CAD"), new BigDecimal("1.00"));
			try {
				centralStationLogic.hardware.getCoinSlot().receive(loonieCoin);
			} catch (DisabledException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CashOverloadException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			selectionFrame.dispose();}
		});
		toonie.addActionListener(e -> {
			if(!centralStationLogic.isSessionStarted()){
				centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
			} else {
			Coin toonieCoin = new Coin(Currency.getInstance("CAD"), new BigDecimal("2.00"));
			try {
				centralStationLogic.hardware.getCoinSlot().receive(toonieCoin);
			} catch (DisabledException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CashOverloadException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			selectionFrame.dispose();}
		});

		selectionFrame.add(nikle);
		selectionFrame.add(dime);
		selectionFrame.add(quarter);
		selectionFrame.add(loonie);
		selectionFrame.add(toonie);

		selectionFrame.setLocationRelativeTo(parentFrame);
		selectionFrame.setVisible(true);
	}

public void showInsertBanknotePopup(JFrame parentFrame) {
		
		// Create a Frame
		JFrame selectionFrame = new JFrame();
		selectionFrame.setTitle("Insert Banknote");
		selectionFrame.setSize(new Dimension(200, 300));
		selectionFrame.setLayout(new FlowLayout());
		
		// Initialize the Bank Notes
		JButton five = new JButton("$5");
		JButton ten = new JButton("$10");
		JButton twenty = new JButton("$20");
		JButton fifty = new JButton("$50");
		JButton hundred = new JButton("$100");
		
		// Action Listeners for Inserting Banknote
		five.addActionListener(e -> {
					if(!centralStationLogic.isSessionStarted()){
						centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
					} else {
			Banknote banknote = new Banknote( Currency.getInstance("CAD"),BigDecimal.valueOf(5));
			try {
				centralStationLogic.hardware.getBanknoteInput().receive(banknote);
			} catch (DisabledException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CashOverloadException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			selectionFrame.dispose();}
		});
		ten.addActionListener(e -> {
			if(!centralStationLogic.isSessionStarted()){
						centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
			} else {
			Banknote banknote = new Banknote( Currency.getInstance("CAD"),BigDecimal.valueOf(10));
			try {
				centralStationLogic.hardware.getBanknoteInput().receive(banknote);
			} catch (DisabledException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CashOverloadException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			selectionFrame.dispose();}
		});
		twenty.addActionListener(e -> {
			if(!centralStationLogic.isSessionStarted()){
						centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
			} else {
				Banknote banknote = new Banknote( Currency.getInstance("CAD"),BigDecimal.valueOf(20));
			try {
				centralStationLogic.hardware.getBanknoteInput().receive(banknote);
			} catch (DisabledException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CashOverloadException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			selectionFrame.dispose();}
		});
		fifty.addActionListener(e -> {
			if(!centralStationLogic.isSessionStarted()){
						centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
					} else {
			Banknote banknote = new Banknote( Currency.getInstance("CAD"),BigDecimal.valueOf(50));
			try {
				centralStationLogic.hardware.getBanknoteInput().receive(banknote);
			} catch (DisabledException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CashOverloadException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			selectionFrame.dispose();}
		});
		hundred.addActionListener(e -> {
			if(!centralStationLogic.isSessionStarted()){
						centralStationLogic.guiLogic.showExceptionMessage("Session not started!");
					} else {
			Banknote banknote = new Banknote( Currency.getInstance("CAD"),BigDecimal.valueOf(100));
			try {
				centralStationLogic.hardware.getBanknoteInput().receive(banknote);
			} catch (DisabledException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CashOverloadException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			selectionFrame.dispose();}
		});
		
		// Add Buttons to Frame
		selectionFrame.add(five);
		selectionFrame.add(ten);
		selectionFrame.add(twenty);
		selectionFrame.add(fifty);
		selectionFrame.add(hundred);
		
		// Set Location of Frame and make Frame Visible
		selectionFrame.setLocationRelativeTo(parentFrame);
		selectionFrame.setVisible(true);
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