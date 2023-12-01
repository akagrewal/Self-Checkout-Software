package com.thelocalmarketplace.software.test.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
		
		
		
		
		
		//Populating Databases
		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
		ProductDatabases.INVENTORY.clear();
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, bProduct);
		ProductDatabases.INVENTORY.put(bProduct, 1);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode2, bProduct2);
		ProductDatabases.INVENTORY.put(bProduct2, 1);
		
		station = new SelfCheckoutStationBronze();
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		logic = new CentralStationLogic(station);
	}
	@Test public void updatePriceOfCartTest() {
		BigDecimal price1 = new BigDecimal(50.0);
		logic.cartLogic.updateBalance(price1);
		assertTrue("price of cart was not updated correctly when adding to it", logic.cartLogic.getBalanceOwed().equals(price1));
	}@Test public void updateNegativePriceOfCartTest() {
		BigDecimal price1 = new BigDecimal(-50.0);
		logic.cartLogic.updateBalance(price1);
		assertTrue("price of cart was not updated correctly when subtracting from it", logic.cartLogic.getBalanceOwed().equals(price1));
	}@Test public void addProductToCartTestCheckPrice() {
		logic.cartLogic.addBarcodedProductToCart(barcode);
		assertTrue("price of cart was not updated correctly after adding to cart", logic.cartLogic.getBalanceOwed().equals(new BigDecimal(5)));
	}@Test public void addMultipleProductToCartTestCheckPrice() {
		BigDecimal price1 = new BigDecimal((long)5);
		BigDecimal price2 = new BigDecimal((long)1.00);
		BigDecimal expected = price1.add(price2);
		logic.cartLogic.addBarcodedProductToCart(barcode);
		logic.cartLogic.addBarcodedProductToCart(barcode2);
		assertTrue("price of cart was not updated correctly after adding to cart", logic.cartLogic.getBalanceOwed().equals(expected));
	}
	@Test public void addMultipleProductToCartTestGetTotalPrice() {
		BigDecimal price1 = new BigDecimal(5);
		BigDecimal price2 = new BigDecimal((long)1.00);
		BigDecimal expected = price1.add(price2);
		logic.cartLogic.addBarcodedProductToCart(barcode);
		logic.cartLogic.addBarcodedProductToCart(barcode2);
		assertTrue("price of cart was not calculated correctly", logic.cartLogic.calculateTotalCost().equals(expected));
	}
	
	@Test
	public void testRemoveProductFromCart() {
		logic.cartLogic.addBarcodedProductToCart(barcode);
		assertEquals(1, logic.cartLogic.getCart().size());
		logic.cartLogic.removeProductFromCart(bProduct);
		assertEquals(0, logic.cartLogic.getCart().size());
	}
	
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
	public void testAddBarcodeNotInInventory2() {
		Barcode b = new Barcode(new Numeral[] {Numeral.two});
		BarcodedProduct p = new BarcodedProduct(b, "some item", 5, 4.0);
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b, p);
		
		logic.cartLogic.addBarcodedProductToCart(b);
	}
	
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