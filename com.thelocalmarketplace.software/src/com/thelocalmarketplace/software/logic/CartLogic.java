package com.thelocalmarketplace.software.logic;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.Utilities;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;

/**
 * Handles all logical operations on the customer's cart
 * 
 * Adapted from Project Iteration 2 - Group 5
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
public class CartLogic {
	
	/**
	 * Tracks all of the products that are in the customer's cart
	 * Includes products without barcodes
	 * Maps a product to its count
	 */
	private Map<Product, Integer> cart;
	
	/**
	 * Tracks how much money the customer owes
	 */
	private BigDecimal balanceOwed;
	
	/**
	 * Constructor for a new CartLogic instance
	 */
	public CartLogic() {
		
		// Initialization
		this.cart = new HashMap<Product, Integer>();
		
		this.balanceOwed = BigDecimal.ZERO;
	}
	
	
	public void addProductToCart(BarcodedProduct product) {
		Utilities.modifyCountMapping(cart, product, 1);
		
		// Update balance owed
		//if (product.isPerUnit()) {
		BigDecimal newPrice = this.balanceOwed.add(new BigDecimal(product.getPrice()));
		this.updateBalance(newPrice);
		//} else {
			
		//}
	}
	
	/**
	 * Adds purchased bags to the customer's cart
	 * @param int the number of bags to add
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
	 * @param product The product to remove
	 * @throws SimulationException If the product is not in the cart
	 */
	public void removeProductFromCart(BarcodedProduct product) throws SimulationException {
		if (!this.getCart().containsKey(product)) {
			throw new InvalidStateSimulationException("Product not in cart");
		}
		
		Utilities.modifyCountMapping(cart, product, -1);
		
		// Update balance owed
		//if (product.isPerUnit()) {
		BigDecimal newPrice = this.balanceOwed.subtract(new BigDecimal(product.getPrice()));
		this.updateBalance(newPrice);
		//} else {
			
		//}
	}
	
	/**
	 * Takes a barcode, looks it up in product database, then adds it to customer cart
	 * @param barcode The barcode to use
	 * @throws SimulationException If barcode is not registered to product database
	 * @throws SimulationException If barcode is not registered in available inventory
	 */
	public void addBarcodedProductToCart(Barcode barcode) throws SimulationException {
		BarcodedProduct toadd = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
		
		if (!ProductDatabases.BARCODED_PRODUCT_DATABASE.containsKey(barcode)) {
			throw new InvalidStateSimulationException("Barcode not registered to product database");
		}
		else if (!ProductDatabases.INVENTORY.containsKey(toadd) || ProductDatabases.INVENTORY.get(toadd) < 1) {
			throw new InvalidStateSimulationException("No items of this type are in inventory");
		}
		
		this.addProductToCart(toadd);
	}
	
	/**
	 * Gets the customer's cart
	 * @return A list of products that represent the cart
	 */
	public Map<Product, Integer> getCart() {
		return this.cart;
	}
	
	/**
	 * Calculates the balance owed based on the products added to customer's cart
	 * @return The balance owed
	 */
	public BigDecimal calculateTotalCost() {
		long balance = 0;
		
		for (Entry<Product, Integer> productAndCount : this.getCart().entrySet()) {
			Product product = productAndCount.getKey();
			int count = productAndCount.getValue();
			
			balance += product.getPrice() * count;
		}
		
		return new BigDecimal(balance);
	}
	
	/**
	 * Gets the balance owed by the customer
	 * @return The balance owed
	 */
	public BigDecimal getBalanceOwed() {
		return this.balanceOwed;
	}
	
	/**
   * Increments/Decrements the customer's balance
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
	 * @param balance The new balance value
	 */
	public void updateBalance(BigDecimal balance) {
		this.balanceOwed = balance;
	}
}