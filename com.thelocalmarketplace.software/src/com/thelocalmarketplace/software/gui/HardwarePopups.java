package com.thelocalmarketplace.software.gui;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
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

    }
	
	public void showScanMainScannerPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Scan Main Scanner");
		JButton apple = new JButton("Apple");
		JButton orange = new JButton("Orange");
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        apple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarcodedItem Apple = new BarcodedItem(new Barcode(new Numeral[]{Numeral.one,Numeral.two, Numeral.three}), new Mass(10));
            	centralStationLogic.hardware.getMainScanner().scan(Apple);
            	//idk how to make the panel close
            	parentFrame.remove(panel);
            	}
        });
        orange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarcodedItem Orange = new BarcodedItem(new Barcode(new Numeral[]{Numeral.three,Numeral.two, Numeral.three}), new Mass(10));
            	centralStationLogic.hardware.getMainScanner().scan(Orange);
            }
        });
        
        dialog.add(panel);
        panel.add(apple);
        panel.add(orange);
		showDialog(dialog);
	}

	public void showScanHandheldScannerPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Scan Handheld Scanner");
		JButton apple = new JButton("Apple");
		JButton orange = new JButton("Orange");

        apple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarcodedItem Apple = new BarcodedItem(new Barcode(new Numeral[]{Numeral.one,Numeral.two, Numeral.three}), new Mass(10));
            	centralStationLogic.hardware.getHandheldScanner().scan(Apple);
            }
        });
        orange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarcodedItem Orange = new BarcodedItem(new Barcode(new Numeral[]{Numeral.three,Numeral.two, Numeral.three}), new Mass(10));
            	centralStationLogic.hardware.getHandheldScanner().scan(Orange);
            }
        });
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        dialog.add(panel);
        panel.add(apple);
        panel.add(orange);
		showDialog(dialog);
	}


	public void showAddItemToScalePopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Add an item to the scale");
		JButton apple = new JButton("Apple");
		JButton orange = new JButton("Orange");

        apple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarcodedItem Apple = new BarcodedItem(new Barcode(new Numeral[]{Numeral.one,Numeral.two, Numeral.three}), new Mass(10));
            	centralStationLogic.hardware.getBaggingArea().addAnItem(Apple);
            }
        });
        orange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarcodedItem Orange = new BarcodedItem(new Barcode(new Numeral[]{Numeral.three,Numeral.two, Numeral.three}), new Mass(10));
            	centralStationLogic.hardware.getBaggingArea().addAnItem(Orange);            
            }
        });
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        dialog.add(panel);
        panel.add(apple);
        panel.add(orange);
		showDialog(dialog);
	}

	public void showRemoveItemFromScalePopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Remove an item from the scale");
		JButton apple = new JButton("Apple");
		JButton orange = new JButton("Orange");

        apple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarcodedItem Apple = new BarcodedItem(new Barcode(new Numeral[]{Numeral.one,Numeral.two, Numeral.three}), new Mass(10));
            	centralStationLogic.hardware.getBaggingArea().removeAnItem(Apple);;
            }
        });
        orange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarcodedItem Orange = new BarcodedItem(new Barcode(new Numeral[]{Numeral.three,Numeral.two, Numeral.three}), new Mass(10));
            	centralStationLogic.hardware.getBaggingArea().removeAnItem(Orange);            
            }
        });
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        dialog.add(panel);
        panel.add(apple);
        panel.add(orange);
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
            	try {
					centralStationLogic.hardware.getCardReader().swipe(realCreditCard);
				} catch (IOException e1) {
					// POPUP FOR ERROR
				}
            }
        });
		swipeFake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					centralStationLogic.hardware.getCardReader().swipe(fakeCreditCard);
				} catch (IOException e1) {
					// POPUP FOR ERROR
				}
            }
        });
		
		insertReal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JDialog pinDialog = createDialog(parentFrame, "pin");
            	JTextField textField = addTextField(pinDialog, "Enter pin:");
        		Consumer<String> onSubmit = inputText -> {
					try {
						centralStationLogic.hardware.getCardReader().insert(realCreditCard, inputText);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						
					}

        		};
        		addSubmitButton(pinDialog, textField, onSubmit);
                showDialog(pinDialog);

            }
        });
		insertFake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JDialog pinDialog = createDialog(parentFrame, "pin");
            	JTextField textField = addTextField(pinDialog, "Enter pin:");
        		Consumer<String> onSubmit = inputText -> {
					try {
						centralStationLogic.hardware.getCardReader().insert(fakeCreditCard, inputText);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						
					}

                    //guiLogic.insertCoin(coinValue);
        		};
        		addSubmitButton(pinDialog, textField, onSubmit);
                showDialog(pinDialog);

            }
        });		
		tapReal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					centralStationLogic.hardware.getCardReader().tap(realCreditCard);
				} catch (IOException e1) {
					// POPUP FOR ERROR
				}
            }
        });
		tapFake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					centralStationLogic.hardware.getCardReader().tap(fakeCreditCard);
				} catch (IOException e1) {
					// POPUP FOR ERROR
				}
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
            	try {
					centralStationLogic.hardware.getCardReader().swipe(realDebitCard);
				} catch (IOException e1) {
					// POPUP FOR ERROR
				}
            }
        });
		swipeFake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					centralStationLogic.hardware.getCardReader().swipe(fakeDebitCard);
				} catch (IOException e1) {
					// POPUP FOR ERROR
				}
            }
        });
		
		// NEED INSERT
		insertReal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JDialog pinDialog = createDialog(parentFrame, "pin");
            	JTextField textField = addTextField(pinDialog, "Enter pin:");
        		Consumer<String> onSubmit = inputText -> {
					try {
						centralStationLogic.hardware.getCardReader().insert(realDebitCard, inputText);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						
					}

        		};
        		addSubmitButton(pinDialog, textField, onSubmit);
                showDialog(pinDialog);

            }
        });
		insertFake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JDialog pinDialog = createDialog(parentFrame, "pin");
            	JTextField textField = addTextField(pinDialog, "Enter pin:");
        		Consumer<String> onSubmit = inputText -> {
					try {
						centralStationLogic.hardware.getCardReader().insert(fakeDebitCard, inputText);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						
					}

                    //guiLogic.insertCoin(coinValue);
        		};
        		addSubmitButton(pinDialog, textField, onSubmit);
                showDialog(pinDialog);

            }
        });
		
		tapReal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					centralStationLogic.hardware.getCardReader().tap(realDebitCard);
				} catch (IOException e1) {
					// POPUP FOR ERROR
				}
            }
        });
		tapFake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					centralStationLogic.hardware.getCardReader().tap(fakeDebitCard);
				} catch (IOException e1) {
					// POPUP FOR ERROR
				}
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

	public static void showInsertCoinPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Insert Coin");
		JTextField textField = addTextField(dialog, "Enter coin value:");
		Consumer<String> onSubmit = inputText -> {
			int coinValue = Integer.parseInt(inputText);
            //guiLogic.insertCoin(coinValue);
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	public static void showInsertBanknotePopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Insert Banknote");
		JTextField textField = addTextField(dialog, "Enter banknote value:");
		Consumer<String> onSubmit = inputText -> {
            int banknoteValue = Integer.parseInt(inputText);
            //guiLogic.insertBanknote(banknoteValue);
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
