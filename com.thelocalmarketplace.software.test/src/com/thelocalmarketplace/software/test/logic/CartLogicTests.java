package com.thelocalmarketplace.software.test.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.jjjwelectronics.Mass;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.PowerGrid;

/**
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
public class CartLogicTests {
	
	SelfCheckoutStationBronze station;
	CentralStationLogic logic;
	
	public Barcode barcode;
	public PriceLookUpCode PLUcode;
	public Numeral digits;
	
	public BarcodedItem bitem;
	public PLUCodedItem pItem;

	public Numeral[] barcode_numeral;
	public Numeral[] barcode_numeral2;
	public Numeral[] barcode_numeral3;
	public Barcode b_test;
	public Barcode barcode2;
	public BarcodedProduct bProduct;
	public BarcodedProduct bProduct2;
	public BarcodedProduct bProduct3;
	
	public Numeral[] PLU_numeral;
	public Numeral[] PLU_numeral2;
	public Numeral[] PLU_numeral3;
	public PriceLookUpCode PLU1;
	public PriceLookUpCode PLU2;
	public PLUCodedProduct  pProduct;
	public PLUCodedProduct  pProduct2;
	
	
	
	@Before public void setUp() {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();		
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		
		//Setting up Barcoded Products
		barcode_numeral = new Numeral[]{Numeral.one,Numeral.two, Numeral.three};
		barcode_numeral2 = new Numeral[]{Numeral.three,Numeral.two, Numeral.three};
		barcode_numeral3 = new Numeral[]{Numeral.three,Numeral.three, Numeral.three};
		barcode = new Barcode(barcode_numeral);
		barcode2 = new Barcode(barcode_numeral2);
		b_test = new Barcode(barcode_numeral3);
		bProduct = new BarcodedProduct(barcode, "some item",(long)5,(double)3.0);
		bProduct2 = new BarcodedProduct(barcode2, "some item 2",(long)1.00,(double)300.0);
		bProduct3 = new BarcodedProduct(b_test, "some item 3",(long)1.00,(double)3.0);
		
		//Setting up PLU Products

		PLU1 = new PriceLookUpCode("1111");
		PLU2 = new PriceLookUpCode("2222");
		pProduct = new PLUCodedProduct(PLU1, "Apples", 2);
		pProduct2 = new PLUCodedProduct(PLU2, "Bananas", 3);
		
		//Populating Databases
		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
		ProductDatabases.INVENTORY.clear();
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, bProduct);
		ProductDatabases.INVENTORY.put(bProduct, 1);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode2, bProduct2);
		ProductDatabases.INVENTORY.put(bProduct2, 1);

		ProductDatabases.PLU_PRODUCT_DATABASE.clear();
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLU1, pProduct);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLU2, pProduct2);
		ProductDatabases.INVENTORY.put(pProduct, 10);
		ProductDatabases.INVENTORY.put(pProduct2, 10);
		
		station = new SelfCheckoutStationBronze();
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		logic = new CentralStationLogic(station);
	}
	
//Testing method cartLogic.updateBalance(Int)	
	@Test
	public void updatePriceOfCartTest() {
		BigDecimal price1 = new BigDecimal(50.0);
		logic.cartLogic.updateBalance(price1);
		assertTrue("price of cart was not updated correctly when adding to it", logic.cartLogic.getBalanceOwed().equals(price1));
	}
	@Test
	public void updateNegativePriceOfCartTest() {
		BigDecimal price1 = new BigDecimal(-50.0);
		logic.cartLogic.updateBalance(price1);
		assertTrue("price of cart was not updated correctly when subtracting from it", logic.cartLogic.getBalanceOwed().equals(price1));
	}
	
//Tests for adding Barcoded and PLU products as well as when they're added together
	@Test
	public void addBarcodeProductToCartTestCheckPrice() {
		logic.cartLogic.addBarcodedProductToCart(barcode);
		assertTrue("price of cart was not updated correctly after adding to cart", logic.cartLogic.getBalanceOwed().equals(new BigDecimal(5)));
	}
	@Test 
	public void addPLUProductToCartTestCheckPrice() {
		logic.weightLogic.updateActualWeight(new Mass(1000000000));
		logic.cartLogic.addPLUCodedProductToCart(PLU1);
		assertTrue("price of cart was not updated correctly after adding to cart", logic.cartLogic.getBalanceOwed().equals(new BigDecimal(2)));
	}
	@Test 
	public void addMultipleBarcodedProductToCartTestCheckPrice() {
		BigDecimal price1 = new BigDecimal((long)5);
		BigDecimal price2 = new BigDecimal((long)1.00);
		BigDecimal expected = price1.add(price2);
		logic.cartLogic.addBarcodedProductToCart(barcode);
		logic.cartLogic.addBarcodedProductToCart(barcode2);
		assertTrue("price of cart was not updated correctly after adding to cart", logic.cartLogic.getBalanceOwed().equals(expected));
	}
	@Test
	public void addMultiplePLUProductsToCartTestCheckPrice() {
	logic.weightLogic.updateActualWeight(new Mass(1000000000));
	logic.cartLogic.addPLUCodedProductToCart(PLU1);
	logic.weightLogic.updateActualWeight(new Mass(1000000000));
	logic.cartLogic.addPLUCodedProductToCart(PLU2);
	assertTrue("price of cart was not updated correctly after adding to cart", logic.cartLogic.getBalanceOwed().equals(new BigDecimal(5)));
	}
	@Test
	public void addMultipleBarcodedProductsToCartTestGetTotalPrice() {
		BigDecimal price1 = new BigDecimal(5);
		BigDecimal price2 = new BigDecimal((long)1.00);
		BigDecimal expected = price1.add(price2);
		logic.cartLogic.addBarcodedProductToCart(barcode);
		logic.cartLogic.addBarcodedProductToCart(barcode2);
		assertTrue("price of cart was not calculated correctly", logic.cartLogic.calculateTotalCost().equals(expected));
	}
	@Test
	public void addMultiplePLUProductToCartTestGetTotalPrice() {
		logic.weightLogic.updateActualWeight(new Mass(1000000000));
		logic.cartLogic.addPLUCodedProductToCart(PLU1);
		logic.weightLogic.updateActualWeight(new Mass(1000000000));
		logic.cartLogic.addPLUCodedProductToCart(PLU2);

		assertTrue("price of cart was not updated correctly after adding to cart", logic.cartLogic.calculateTotalCost().equals(new BigDecimal(5)));
	}
	@Test
	public void addMultipleProductsToCastTestGetTotalPrice() {
		logic.weightLogic.updateActualWeight(new Mass(1000000000));
		logic.cartLogic.addPLUCodedProductToCart(PLU1);	
		BigDecimal price1 = new BigDecimal(5);
		logic.cartLogic.addBarcodedProductToCart(barcode);
		BigDecimal expected = price1.add(new BigDecimal(pProduct.getPrice()));
		assertTrue("price of cart was not calculated correctly", logic.cartLogic.calculateTotalCost().equals(expected));		
	}
	
//Tests for removing a product once it's in the cart
	@Test
	public void testRemoveBarcodedProductFromCart() {
		logic.cartLogic.addBarcodedProductToCart(barcode);
		assertEquals(1, logic.cartLogic.getCart().size());
		logic.cartLogic.removeProductFromCart(bProduct);
		assertEquals(0, logic.cartLogic.getCart().size());
	}

	@Test
	public void testRemovePLUProductFromCart() {
		logic.weightLogic.updateActualWeight(new Mass(1000000000));
		logic.cartLogic.addPLUCodedProductToCart(PLU1);
		assertEquals(1, logic.cartLogic.getCart().size());
		logic.cartLogic.removeProductFromCart(pProduct);
		assertEquals(0, logic.cartLogic.getCart().size());
	}
	@Test
	public void testRemoveProductFromCart() {
		logic.weightLogic.updateActualWeight(new Mass(1000000000));
		logic.cartLogic.addPLUCodedProductToCart(PLU2);	
		BigDecimal price1 = new BigDecimal(5);
		logic.cartLogic.addBarcodedProductToCart(barcode);
		assertEquals(2, logic.cartLogic.getCart().size());
		logic.cartLogic.removeProductFromCart(pProduct2);
		assertEquals(1, logic.cartLogic.getCart().size());
		logic.cartLogic.removeProductFromCart(bProduct);
		assertEquals(0, logic.cartLogic.getCart().size());	
		
	}
	
//Test for removing a product from cart and adding it back again.
	@Test
	public void testRemoveProductThenAddToCartAgain() {
		logic.weightLogic.updateActualWeight(new Mass(1000000000));
		logic.cartLogic.addPLUCodedProductToCart(PLU2);	
		BigDecimal price1 = new BigDecimal(5);
		logic.cartLogic.addBarcodedProductToCart(barcode);
		assertEquals(2, logic.cartLogic.getCart().size());
		logic.cartLogic.removeProductFromCart(pProduct2);
		logic.cartLogic.addPLUCodedProductToCart(PLU2);
		assertEquals(2, logic.cartLogic.getCart().size());
				
	}
	
//Tests for Simulation Exceptions	
	@Test(expected = SimulationException.class)
	public void testRemoveNonExistentProductFromCart() {
		logic.cartLogic.removeProductFromCart(bProduct);
	}
	
	@Test(expected = SimulationException.class)
	public void testAddBarcodeNotInDatabase() {
		Barcode b = new Barcode(new Numeral[] {Numeral.one});
		
		logic.cartLogic.addBarcodedProductToCart(b);
	}

	@Test(expected = SimulationException.class)
	public void testAddPLUNotInDatabase() {
		PriceLookUpCode p = new PriceLookUpCode("1234");

		logic.cartLogic.addPLUCodedProductToCart(p);
	}
	
	@Test(expected = SimulationException.class)
	public void testAddBarcodeNotInInventory2() {
		Barcode b = new Barcode(new Numeral[] {Numeral.two});
		BarcodedProduct p = new BarcodedProduct(b, "some item", 5, 4.0);
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b, p);
		
		logic.cartLogic.addBarcodedProductToCart(b);
	}

	@Test(expected = SimulationException.class)
	public void testAddPLUNotInInventory2() {
		PriceLookUpCode p = new PriceLookUpCode("1234");
		PLUCodedProduct p1 = new PLUCodedProduct(p, "some item", 5);

		ProductDatabases.PLU_PRODUCT_DATABASE.put(p, p1);

		logic.cartLogic.addPLUCodedProductToCart(p);
	}
	
//Tests for cartLogic.modifyBalance() method
	@Test
	public void testModifyBalanceAdd() {
		logic.cartLogic.modifyBalance(new BigDecimal(5));
		logic.cartLogic.modifyBalance(new BigDecimal(3));
		
		assertEquals(new BigDecimal(8).setScale(5, RoundingMode.HALF_DOWN), logic.cartLogic.getBalanceOwed().setScale(5, RoundingMode.HALF_DOWN));
	}
	
	@Test
	public void testModifyBalanceSubtract() {
		logic.cartLogic.modifyBalance(new BigDecimal(3));
		logic.cartLogic.modifyBalance(new BigDecimal(-5));
		
		assertEquals(new BigDecimal(0).setScale(5, RoundingMode.HALF_DOWN), logic.cartLogic.getBalanceOwed().setScale(5, RoundingMode.HALF_DOWN));
	}
}