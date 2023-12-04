
package com.thelocalmarketplace.software.gui;

import java.awt.CardLayout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

/*
 * This is where Project 3 Logic will be entered 
 * Please do not insert Logic into RunGUI (which should only contain
 * GUI code) and Panels
 */
public class GUILogic {
	CentralStationLogic centralLogic;
	CardLayout cardLayout;
	JPanel cardPanel;
	StationGUI guiDisplay;

	public GUILogic(CentralStationLogic logic, StationGUI stationGUI) {
		this.guiDisplay = stationGUI;
		this.cardLayout = stationGUI.cardLayout;
		this.cardPanel = stationGUI.cardPanel;
		this.centralLogic = logic;
	}

	public void switchPanels(String string) {
		cardLayout.show(cardPanel, string);
	}
	
	/** Call when a change is made to the cart; Will cause the required changes on the GUI side **/
	public void updateCartChanged() {
		System.out.println("Updated Cart Changed");
		updateItemsList();
		updateTotal();
	}

	/**
	 * Call when a change to the items in the cart is made to update the GUI display
	 * of the cart
	 *
	 */
	private void updateItemsList() {
		guiDisplay.itemListModel.clear();
		Map<Product, Float> cart = centralLogic.cartLogic.getCart();
		System.out.println("Size of cart: " + cart.size());
		for (Map.Entry<Product, Float> entry : cart.entrySet()) {
			Product product = entry.getKey();
			Float count = entry.getValue();
			long price = product.getPrice();
			String productName = "";
			if (product instanceof BarcodedProduct) {
				productName = ((BarcodedProduct) product).getDescription();
			}
			else if (product instanceof PLUCodedProduct) {
				productName = ((PLUCodedProduct) product).getDescription();
			}

			String displayString = String.format("%s - Price: %d, Count: %.0f", productName, price, count);
			guiDisplay.itemListModel.addElement(displayString);
		}
	}

	/** Call when a change to the price of items in the cart is made **/
	private void updateTotal() {
		BigDecimal owed = centralLogic.cartLogic.getBalanceOwed();
		guiDisplay.setTotal(owed);
	}

	public boolean checkPLU(String PLU) {
		boolean valid = false;

		try {
			PriceLookUpCode plu = new PriceLookUpCode(PLU);
			valid = ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(plu);
		} catch (Exception ignored) {}

		if (valid) {
			addProductPLU(PLU);
		}

		return valid;
	}

	public boolean checkMembership(String membershipID) {
		if (centralLogic.membershipLogic.isMembershipNumberValid(membershipID)) {
			centralLogic.membershipLogic.setMembershipNumber(membershipID);
		} else {
			return false;
		}
		return true;
	}


	// START BUTTON
	public void StartSessionButtonPressed() {
		if (centralLogic.stateLogic.getState() != States.OUTOFORDER) {
			System.out.println("Start Session");
			centralLogic.startSession();
			switchPanels("AddItemsPanel");
		}
	}
	
	public void SessionOver() {
		System.out.println("Start Session");
		centralLogic.stopSession();
		updateItemsList();
		updateTotal();
	}
	
	

	public boolean addProductPLU(String PLU){
		boolean productFound = false;

//		productFound = checkPLU(PLU);

		PriceLookUpCode pluCode = new PriceLookUpCode(PLU);


//		if (productFound){
		PLUCodedProduct product = ProductDatabases.PLU_PRODUCT_DATABASE.get(pluCode);
		System.out.println("This is the PLU code of the item being added: " + product.getPLUCode());
		centralLogic.addPLUProductController.addPLU(product.getPLUCode());
		updateCartChanged();

		return productFound;
	}
}
