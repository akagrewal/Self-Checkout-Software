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



package com.thelocalmarketplace.software.logic;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.Utilities;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;

/**
 * Handles all logical operations on the customer's cart
 *
 * Adapted from Project Iteration 2 - Group 5
 * 
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
public class CartLogic extends AbstractLogicDependant {
	/**
	 * Tracks all the products that are in the customer's cart Includes products
	 * without barcodes Maps a product to its count If product is by weight, count
	 * is weight in kg
	 */
	protected Map<Product, Float> cart;

	/**
	 * Tracks how much money the customer owes
	 */
	private BigDecimal balanceOwed;

	/**
	 * Constructor for a new CartLogic instance
	 */

	public CartLogic(CentralStationLogic logic) {
		super(logic);
		// Initialization
		this.cart = new HashMap<>();
		this.balanceOwed = BigDecimal.ZERO;
	}

	/**
	 * Adds a PLUCodedproduct to customer's cart. Calculates the price and updates the balance owed by the customer.
	 * @param product The PLUCodedproduct added
	 * @throws SimulationException If the product is not in the cart
	 */
	public void addProductToCart(Product product) {
		if (product.isPerUnit()) {
			Utilities.modifyCountMapping(cart, product, 1);
			BigDecimal newPrice = this.balanceOwed.add(new BigDecimal(product.getPrice()));
			this.updateBalance(newPrice);
		} else {
			Mass weightOnScanningArea = logic.scanningAreaController.getScanningAreaMass();
			double weightValue = weightOnScanningArea.inGrams().doubleValue() / 1000;

			Utilities.modifyCountMapping(cart, product, 1);
			double Price = product.getPrice() * weightValue;
			// Update balance owed
			BigDecimal newPrice = this.balanceOwed.add(new BigDecimal(Price));
			this.updateBalance(newPrice);
		}
		logic.guiLogic.updateCartChanged();
	}

	/**
	 * Adds purchased bags to the total cost
	 * not actually added to cart 
	 * @param numOfBags the number of bags to add
	 */
	public void addReusableBagToCart(int numOfBags) {
		double cost = 1.25 * numOfBags;
		BigDecimal bagPrice = new BigDecimal(cost);
		BigDecimal newPrice = this.balanceOwed.add(bagPrice);
		
		this.updateBalance(newPrice);
		System.out.println("balance owed:" + balanceOwed);
	}
	
	/**
	 * Removes a product from customer's cart
	 * 
	 * @param product The product to remove
	 * @throws SimulationException If the product is not in the cart
	 */
	public void removeProductFromCart(Product product) throws SimulationException {
		if (!this.getCart().containsKey(product)) {
			throw new InvalidStateSimulationException("Product not in cart");
		}
		// Update balance owed
		if (product.isPerUnit()) {
			BigDecimal newPrice = this.balanceOwed.subtract(new BigDecimal(product.getPrice()));
			this.updateBalance(newPrice);
			Utilities.modifyCountMapping(cart, product, -1);

		} else {
			// Get weight from cart hashmap using product key
			float productWeight = cart.get(product);
			// Get total price of item by multiplying weight and price/kg
			long productPrice = (product.getPrice() * (long) productWeight);
			BigDecimal newPrice = this.balanceOwed.subtract(new BigDecimal(productPrice));
			this.updateBalance(newPrice);
			Utilities.modifyCountMapping(cart, product, -(productWeight));
		}
		if (product instanceof PLUCodedProduct) {
			logic.weightLogic.removeExpectedWeight(((PLUCodedProduct) product).getPLUCode());
		} else {
			logic.weightLogic.removeExpectedWeight(((BarcodedProduct) product).getBarcode());
		}
		logic.weightLogic.handleWeightDiscrepancy();
		logic.guiLogic.updateCartChanged();
	}

	/**
	 * Takes a barcode, looks it up in product database, then adds it to customer
	 * cart
	 * 
	 * @param barcode The barcode to use
	 * @throws SimulationException If barcode is not registered to product database
	 * @throws SimulationException If barcode is not registered in available
	 *                             inventory
	 */
	public void addBarcodedProductToCart(Barcode barcode) throws SimulationException {
		BarcodedProduct toadd = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);

		if (!ProductDatabases.BARCODED_PRODUCT_DATABASE.containsKey(barcode)) {
			throw new InvalidStateSimulationException("Barcode not registered to product database");
		} else if (!ProductDatabases.INVENTORY.containsKey(toadd) || ProductDatabases.INVENTORY.get(toadd) < 1) {
			throw new InvalidStateSimulationException("No items of this type are in inventory");
		}

		this.addProductToCart(toadd);
		logic.guiLogic.updateCartChanged();
	}

	/**
	 * Takes a PLU code, looks it up in product database, then adds it to customer
	 * cart
	 * 
	 * @param pluCode, The PLU code to use
	 * @throws SimulationException If PLU code is not registered to product database
	 * @throws SimulationException If PLU code is not registered in available
	 *                             inventory
	 */
	public void addPLUCodedProductToCart(PriceLookUpCode pluCode) throws SimulationException {
		PLUCodedProduct toadd = ProductDatabases.PLU_PRODUCT_DATABASE.get(pluCode);

		if (!ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(pluCode)) {
			throw new InvalidStateSimulationException("PLU Code not registered to product database");
		} else if (!ProductDatabases.INVENTORY.containsKey(toadd) || ProductDatabases.INVENTORY.get(toadd) < 1) {
			throw new InvalidStateSimulationException("No items of this type are in inventory");
		}
		this.addProductToCart(toadd);
		logic.guiLogic.updateCartChanged();
	}

	/**
	 * Gets the customer's cart
	 * 
	 * @return A list of products that represent the cart
	 */
	public Map<Product, Float> getCart() {
		return this.cart;
	}

	/**
	 * Calculates the balance owed based on the products added to customer's cart
	 * 
	 * @return The balance owed
	 */
	public BigDecimal calculateTotalCost() {
		long balance = 0;

		for (Entry<Product, Float> productAndCount : this.getCart().entrySet()) {
			Product product = productAndCount.getKey();
			Float count = productAndCount.getValue();

			balance += product.getPrice() * count;
		}

		return new BigDecimal(balance);
	}

	/**
	 * Gets the balance owed by the customer
	 * 
	 * @return The balance owed
	 */
	public BigDecimal getBalanceOwed() {
		return this.balanceOwed;
	}

	/**
	 * Increments/Decrements the customer's balance
	 * 
	 * @param amount Is the amount to increment/decrement by
	 */
	public void modifyBalance(BigDecimal amount) {
		this.balanceOwed = this.balanceOwed.add(amount);

		if (this.balanceOwed.compareTo(BigDecimal.ZERO) < 0) {
			this.balanceOwed = BigDecimal.ZERO;
		}
	}

	/**
	 * Sets the customer's balance
	 * 
	 * @param balance The new balance value
	 */
	public void updateBalance(BigDecimal balance) {
		this.balanceOwed = balance;
		logic.guiLogic.updateCartChanged();
	}

}
