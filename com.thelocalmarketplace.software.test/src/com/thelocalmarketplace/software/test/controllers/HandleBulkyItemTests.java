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
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.AttendantLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
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
public class HandleBulkyItemTests {
	
	private SelfCheckoutStationGold station;
	private CentralStationLogic session;
	private BarcodedItem barcodedItem;
	private BarcodedProduct product;
	
	
	/** Ensures failures do not occur from scanner failing to scan item, thus isolating test cases */
	public void scanUntilAdded() {
		do {
			station.getHandheldScanner().scan(barcodedItem);
		} while (!session.cartLogic.getCart().containsKey(product));
	}
	
	@Before
	public void setUp() {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		
		station = new SelfCheckoutStationGold();
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.startSession();
		
		Barcode barcode = new Barcode(new Numeral[] {Numeral.one});
		barcodedItem = new BarcodedItem(barcode, Mass.ONE_GRAM);
		product = new BarcodedProduct(barcode, "item", 5, barcodedItem.getMass().inGrams().doubleValue());
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
		ProductDatabases.INVENTORY.clear();
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, product);
		ProductDatabases.INVENTORY.put(product, 1);
	}
	
	@After 
	public void tearDown() {
		PowerGrid.engageFaultyPowerSource();
	}
	
	@Test 
	public void testSkipBaggingNotifiesAttendant() {
		AttendantLogicStub attendantLogic = new AttendantLogicStub(session);
		session.attendantLogic = attendantLogic;
		scanUntilAdded();
		session.weightLogic.skipBaggingRequest(barcodedItem.getBarcode());
		assertTrue(attendantLogic.requestApprovalCalled);
	}
	
	@Test
	public void testSkipBaggingBlocksStation() {
		scanUntilAdded();
		session.weightLogic.skipBaggingRequest(barcodedItem.getBarcode());
		assertTrue(this.session.stateLogic.inState(States.BLOCKED));
	}
	
	@Test (expected = InvalidArgumentSimulationException.class)
	public void testSkipBaggingNullBarcode() {
		session.weightLogic.skipBaggingRequest(null);
	}
	
	// Expected reaction not clear; we have therefore assumed unblocking when weight discrepancy removed is expected
	@Test 
	public void testSkipBaggingAddsAnyways() {
		scanUntilAdded();
		session.weightLogic.skipBaggingRequest(barcodedItem.getBarcode());
		station.getBaggingArea().addAnItem(barcodedItem);
		assertFalse(this.session.stateLogic.inState(States.BLOCKED));
	}
	
	@Test
	public void testAttendantApprovalReducesExceptedWeight() {
		scanUntilAdded();
		session.weightLogic.skipBaggingRequest(barcodedItem.getBarcode());
		session.attendantLogic.grantApprovalSkipBagging(this.session, barcodedItem.getBarcode());
		assertFalse(session.weightLogic.checkWeightDiscrepancy());
	}
	
	@Test 
	public void testAttendantApprovalUnblocksStation() {
		scanUntilAdded();
		session.weightLogic.skipBaggingRequest(barcodedItem.getBarcode());
		session.attendantLogic.grantApprovalSkipBagging(this.session, barcodedItem.getBarcode());
		assertFalse(this.session.stateLogic.inState(States.BLOCKED)); // Ensures no longer blocked
	}
	
	@Test 
	public void testAttendantApprovalStaysBlockedIfDiscrepancyRemains() {
		scanUntilAdded();
		session.weightLogic.skipBaggingRequest(barcodedItem.getBarcode());
		session.attendantLogic.grantApprovalSkipBagging(this.session, barcodedItem.getBarcode());
	}
	
	@Test (expected = InvalidArgumentSimulationException.class)
	public void testAttendantApprovalNullBarcode() {
		session.attendantLogic.grantApprovalSkipBagging(this.session, null);
	}

	
	private class AttendantLogicStub extends AttendantLogic {
		public boolean requestApprovalCalled = false;
		private CentralStationLogic logic;
		
		public AttendantLogicStub(CentralStationLogic l) {
			super();
			this.logic = l;
			this.registerStationLogic(l);
		}
		@Override 
		public void requestApprovalSkipBagging(CentralStationLogic logic, Barcode barcode) {
			requestApprovalCalled = true;
		}
		
		
	}
	

}
