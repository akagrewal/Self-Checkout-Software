
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
import com.thelocalmarketplace.software.database.CreateTestDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

/*
 * This is where Project 3 Logic will be entered 
 * Please do not insert Logic into RunGUI (which should only contain
 * GUI code) and Panels
 */
public class GUILogic {
	private final Set<GUIListener> listeners = new HashSet<>();
	CentralStationLogic centralLogic;
	CardLayout cardLayout;
	JPanel cardPanel;
	RunGUI guiDisplay;

	public GUILogic(CentralStationLogic logic, RunGUI runGUI) {
		this.guiDisplay = runGUI;
		this.cardLayout = runGUI.cardLayout;
		this.cardPanel = runGUI.cardPanel;
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

	public void checkMembership(String membershipID) {
		boolean valid = centralLogic.membershipLogic.isMembershipNumberValid(membershipID);
		// do something
	}




	/**
	 * Registers the given listener so that it will receive events from this
	 * communication facade.
	 *
	 * @param listener The listener to be registered. If it is already registered,
	 *                 this call has no effect.
	 */
	public void register(GUIListener listener) {
		listeners.add(listener);
	}

	// START BUTTON
	public void StartSessionButtonPressed() {
		System.out.println("Start Session");
		centralLogic.startSession();
	}
	
	public void SessionOver() {
		System.out.println("Start Session");
		centralLogic.stopSession();
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
	
	
	
	
// ENZOS CODE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
// LISTENER METHODS
	// add logic for when customer adds item via PLU/Visual Search
	protected void notifyItemAdded(PLUCodedProduct pk) {
		for (GUIListener listener : listeners)
			listener.added(this, pk);
	}

	// add logic for when customer scans a barcoded item
	protected void notifyItemScanned(BarcodedProduct pk) {
		for (GUIListener listener : listeners)
			listener.scanned(this, pk);
	}

	// add logic for when customer indicates to add their own bags
	protected void notifyOwnBags(boolean ownBags) {
		for (GUIListener listener : listeners)
			listener.ownBags(this, ownBags);
	}

	// add logic for when customer wants to call an attendant
	protected void callAttendant() {
		for (GUIListener listener : listeners)
			listener.attendantCalled(this);
	}

	// add logic for when customer indicates to add their membership card/id (?
	// might need to be changed)
	// string changed to whatever the membership identifier is
	protected void addMembership(String id) {
		for (GUIListener listener : listeners)
			listener.memberLogin(this, id);
	}

	// add logic for when customer chooses how they would like to pay
	protected void selectedPayOption(int payOption) {
		for (GUIListener listener : listeners)
			listener.payOption(this, payOption);
	}

// HARDWARE ACTION LISTENER METHODS

	// adding item to scale
	protected void addItemToScale(Mass weight) {
		for (GUIListener listener : listeners)
			listener.itemAddedToScale(this, weight);
	}

	// removing item from scale
	protected void removeItemFromScale(Mass weight) {
		for (GUIListener listener : listeners)
			listener.itemRemovedFromScale(this, weight);
	}

	protected void payWithCredit(int cardNumber) {
		for (GUIListener listener : listeners)
			listener.paidWithCredit(this, cardNumber);
	}

	protected void payWithDebit(int cardNumber) {
		for (GUIListener listener : listeners)
			listener.paidWithDebit(this, cardNumber);
	}

	protected void insertCoin(int coinAmount) {
		for (GUIListener listener : listeners)
			listener.coinInserted(this, coinAmount);
	}

	protected void insertBanknote(int banknoteAmount) {
		for (GUIListener listener : listeners)
			listener.banknoteInserted(this, banknoteAmount);
	}

//----------------------------------------------------------------
//Start Session Panel, 
	
	//when Customer presses [Start Session] 
//	public void StartSessionButtonPressed() {
//		System.out.println("Start Session");
//	}
	
//----------------------------------------------------------------
//Add Items Panel

	// RIGHT BUTTON PANEL
	public void buttonR1_AddMemberNoButton() {
		System.out.println("Add Member #");
		// Logic Here
	}

	public void buttonR2_SignUpForMembershipButton() {
		System.out.println("Sign Up For Membership");
		// Logic Here
	}

	public void buttonR3_CustomerCallsAttendant() {
		System.out.println("CustomerCallsAttendant");
		// Logic Here
	}

	public void buttonR4_CustomerAddsOwnBag() {
		System.out.println("buttonR4_CustomerAddsOwnBag");
		// Logic Here
	}

	public void buttonR5_CustomerWantstoRemoveItem() {
		System.out.println("buttonR5_CustomerWantstoRemoveItem");
		// Logic Here
	}

	public void buttonR6_BLANK() {
		System.out.println("buttonR6_BLANK");
		// Logic Here
	}

	/*
	 * return the string that will be displayed in GUI "Receipt"
	 */
	public String buttonR7_CustomerAddsItem_PLUCode() {
		System.out.println("buttonR7_CustomerAddsItem_PLUCode");
		// Logic Here
		// Example Code Here
		String addItemPLU_result = "New Item thru PLU Code";
		return addItemPLU_result;
	}

	/*
	 * return the string that will be displayed in GUI "Receipt"
	 */
	public String buttonR8_CustomerAddsItem_VisualCatalogue() {
		System.out.println("buttonR8_CustomerAddsItem_VisualCatalogue");
		// Logic Here
		// Example Code Here
		String addItemVC_result = "New Item thru Visual Catalogue";
		return addItemVC_result;
	}

	// This will switch to the Payment Panel
	public void buttonR9_CustomerWantsToPay() {
		System.out.println("buttonR9_CustomerWantsToPay!");
		// Logic Here
	}

//----------------------------------------------------------------
// Add Items Panel Pop Up 

	public String addItemPopUp_button1_CustomersAddsToBaggingArea() {
		System.out.println("addItemPopUp_button1_CustomersAddsToBaggingArea");
		// Example Code Here
		// Logic Here
		String addItemB1_result = "addItemPopUp_button1_CustomersAddsToBaggingArea";
		return addItemB1_result;
	}

	public String addItemPopUp_button2_CustomersDOESNOTAddsToBaggingArea() {
		System.out.println("addItemPopUp_button1_CustomersAddsToBaggingArea");
		// Example Code Here
		// Logic Here
		String addItemB1_result = "addItemPopUp_button1_CustomersAddsToBaggingArea";
		return addItemB1_result;
	}

	public String addItemPopUp_button3_BLANK() {
		System.out.println("Extra Button");
		// Example Code Here
		// Logic Here
		String addItemB1_result = "addItemPopUp_button3_BLANK - Extra Button";
		return addItemB1_result;
	}

//----------------------------------------------------------------
//Add Items Panel 	

	// BOTTOM PANEL
	public String buttonB1_CustomerScansBarcodedProduct_MainScanner() {
		System.out.println("buttonB1_CustomerScansBarcodedProduct_MainScanner");
		// Example Code Here
		// Logic Here
		String addItemB1_result = "New Barcoded Product thru Main Scanner";
		return addItemB1_result;
	}

	public String buttonB2_CustomerScansBarcodedProduct_HandheldScanner() {
		System.out.println("buttonB2_CustomerScansBarcodedProduct_HandheldScanner");
		// Example Code Here
		// Logic Here
		String addItemB2_result = "New Barcoded Product thru Handheld Scanner";
		return addItemB2_result;
	}

	public String buttonB3_CustomerScansBarcodedProduct_RFIDTag() {
		System.out.println("buttonB3_CustomerScansBarcodedProduct_RFIDTag");
		// Example Code Here
		// Logic Here
		String addItemB3_result = "New Barcoded Product thru RFID Tag";
		return addItemB3_result;
	}

//----------------------------------------------------------------
// FOR TESTING PURPOSES - DO NOT SUBMIT FOR PROJECT 3 

	public int total = 0;

	// Observer Design
	private List<logicObserver> observers = new ArrayList<>();

	// Observer Methods Below
	public void addObserver(logicObserver observer) {
		observers.add(observer);
	}

	public void removeObserver(logicObserver observer) {
		observers.remove(observer);
	}

	private void notifyObservers() {
		for (logicObserver observer : observers) {
			observer.updateTotal(total);
		}
	}

	// Logic Methods Below
	public void printMeow() {
		System.out.println("Meow");
	}

	public void initTotal() {
		total = 0;
		System.out.println("The total is " + total);
	}

	public int addOneTotal() {
		total = total + 1;
		System.out.println("The total is " + total);
		return total;
	}

	public int getTotal() {
		System.out.println("The total is " + total);
		return total;
	}

	public void addtoTotal(int meow) {
		total = total + meow;
		System.out.println("The total is " + total);
	}

	public void subtractTotal(int meow) {
		total = total - meow;
		System.out.println("The total is " + total);
	}
}
