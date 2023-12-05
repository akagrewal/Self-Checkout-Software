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


package com.thelocalmarketplace.software.test.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.NoPowerException;
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
public class WeightDiscrepencyTests {
	
	SelfCheckoutStationBronze station;
	CentralStationLogic session;
	
	//stuff for database
	public ProductDatabases database_one_item;
	public Barcode barcode;
	public Numeral digits;
	
	public BarcodedItem bitem;
	public Mass itemMass;
	
	public BarcodedItem bitem2;
	public Mass itemMass2;
	public BarcodedItem bitem3;
	public Mass itemMass3;
	public Numeral[] barcode_numeral;
	public BarcodedProduct product;
	
	
	//the following function was taken mainly from Angelina's tests for bulkyitems
	public void scanUntilAdded(Product p, BarcodedItem b) {
		while(!session.cartLogic.getCart().containsKey(p)) {
			station.getHandheldScanner().scan(b);
		}
	}
	
	@Before public void setUp() {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		
		//d1 = new dummyProductDatabaseWithOneItem();
		//d2 = new dummyProductDatabaseWithNoItemsInInventory();
		station = new SelfCheckoutStationBronze();
		
		//initialize database
		barcode_numeral = new Numeral[]{Numeral.one,Numeral.two, Numeral.three};
		barcode = new Barcode(barcode_numeral);
		product = new BarcodedProduct(barcode, "some item",5,(double)300.0);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
		ProductDatabases.INVENTORY.clear();
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, product);
		ProductDatabases.INVENTORY.put(product, 1);


		//initialize barcoded item
		itemMass = new Mass((long) 1000000);
		bitem = new BarcodedItem(barcode, itemMass);
		itemMass2 = new Mass((double) 300.0);//300.0 grams
		bitem2 = new BarcodedItem(barcode, itemMass2);
		itemMass3 = new Mass((double) 300.0);//3.0 grams
		bitem3 = new BarcodedItem(barcode, itemMass3);
	}
	
	@After 
	public void tearDown() {
		PowerGrid.engageFaultyPowerSource();
	}
	
	/** Ensures failures do not occur from scanner failing to scan item, thus isolating test cases */
	public void scanUntilAdded(BarcodedItem item) {
		do {
			station.getHandheldScanner().scan(item);
		} while (!session.cartLogic.getCart().containsKey(product));
	}
	
	
	@Test (expected = NoPowerException.class) public void testWeightDiscrepencyWithNoPower() {
		station.plugIn(PowerGrid.instance());
		station.turnOff();
		session = new CentralStationLogic(station);
		session.startSession();
		this.scanUntilAdded(product, bitem);
		station.getHandheldScanner().scan(bitem);
		station.getBaggingArea().addAnItem(bitem);
		
	}
	@Test (expected = SimulationException.class)public void testWeightDiscrepencyWithoutPowerTurnOn() {
		station.turnOn();
		session = new CentralStationLogic(station);
		this.scanUntilAdded(product, bitem2);
		session.startSession();
		station.getBaggingArea().addAnItem(bitem2);
	}
	@Test public void testWeightDiscrepencyWithPowerTurnOnNoDiscrepency() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		session = new CentralStationLogic(station);
		session.startSession();
		this.scanUntilAdded(product, bitem3);
		station.getBaggingArea().addAnItem(bitem3);
		assertTrue("weight discrepency detected", !this.session.stateLogic.inState(States.BLOCKED));
	}

	
	@Test 
	public void testWeightDiscrepencyWithPowerTurnOnHasDiscrepencyDifferentThanItem() {

		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.startSession();
		
		this.scanUntilAdded(product, bitem2);
		station.getBaggingArea().addAnItem(bitem);

		assertTrue("weight discrepancy not detected", session.weightLogic.checkWeightDiscrepancy());
		assertTrue("station not blocked", this.session.stateLogic.inState(States.BLOCKED));
	}
	
	@Test public void testWeightDiscrepencyWithPowerTurnOnHasDiscrepencyNoItem() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.startSession();
		
		//station.scanner.scan(bitem2);
		station.getBaggingArea().addAnItem(bitem2);
		assertTrue("weight discrepency not detected", this.session.stateLogic.inState(States.BLOCKED));
	}@Test public void testWeightDiscrepencyWithPowerTurnOnNoDiscrepencyOnSensativity() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);;
		session.startSession();
		
		//sensativity of the scale is 100mg
		Mass  sensativity = station.getBaggingArea().getSensitivityLimit();
		Mass m = sensativity.sum(itemMass2);
		BarcodedItem i= new BarcodedItem(barcode, m);
		this.scanUntilAdded(product, bitem2);
		station.getBaggingArea().addAnItem(i);
		assertTrue("weight discrepency tedected", !this.session.stateLogic.inState(States.BLOCKED));
	}@Test public void testWeightDiscrepencyWithPowerTurnOnNoDiscrepencyWithinSensativity() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.startSession();
		
		//sensativity of the scale is 100mg
		Mass sensativity = station.getBaggingArea().getSensitivityLimit();
		Mass m = sensativity.sum(itemMass2.difference(new Mass(1)).abs());
		BarcodedItem i= new BarcodedItem(barcode, m);
		this.scanUntilAdded(product, bitem2);
		
		station.getBaggingArea().addAnItem(i);
		assertTrue("weight discrepency tedected", !this.session.stateLogic.inState(States.BLOCKED));
	}
	
	@Test
	public void testWeightDiscrepencyWithPowerTurnOnHasDiscrepency() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.startSession();
		
		this.scanUntilAdded(product, bitem2);
		station.getBaggingArea().addAnItem(bitem);

		assertTrue("weight discrepancy detected", this.session.stateLogic.inState(States.BLOCKED));
	}
	
	
	@Test public void testWeightDiscrepencyWithPowerTurnOnNoDiscrepencyItemPlacedBack() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.startSession();
		
		//station.scanner.scan(bitem2);
		station.getBaggingArea().addAnItem(bitem);
		station.getBaggingArea().removeAnItem(bitem);
		assertTrue("weight discrepency tedected", !this.session.stateLogic.inState(States.BLOCKED));

	}
	
	@Test 
	public void testWeightDiscrepencyWithPowerTurnOnNoDiscrepencyItemPlacedBackAfterOther() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.startSession();
		
		this.scanUntilAdded(product, bitem3);
		station.getBaggingArea().addAnItem(bitem);
		station.getBaggingArea().removeAnItem(bitem);
		station.getBaggingArea().addAnItem(bitem3);
		assertFalse("weight discrepancy detected when shouldn't be", this.session.stateLogic.inState(States.BLOCKED));
	}
	
	@Test public void testWeightDiscrepencyWithPowerTurnOnHasDiscrepencyRescan() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.startSession();
		
		this.scanUntilAdded(product, bitem);
		this.scanUntilAdded(product, bitem);
		
		assertEquals(1, session.cartLogic.getCart().size());
	}
	
	@Test(expected = SimulationException.class)
	public void testAddExpectedWeightNonExistentBarcode() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.startSession();
		
		this.session.weightLogic.addExpectedWeight(new Barcode(new Numeral[] {Numeral.one}));
	}
	
	@Test(expected = SimulationException.class)
	public void testRemoveExpectedWeightNonExistentBarcode() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.startSession();
		
		this.session.weightLogic.removeExpectedWeight(new Barcode(new Numeral[] {Numeral.one}));
	}
}