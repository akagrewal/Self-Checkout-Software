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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import powerutility.PowerGrid;

public class AddPLUItemTests {
	SelfCheckoutStationBronze station;
	CentralStationLogic session;

	public Numeral[] PLU_numeral;
	public Numeral[] PLU_numeral2;
	public Numeral[] PLU_numeral3;
	public PriceLookUpCode PLU1;
	public PriceLookUpCode PLU2;
	public PriceLookUpCode PLU3;
	public PLUCodedProduct  pProduct;
	public PLUCodedProduct  pProduct2;
	public PLUCodedProduct pProduct3;

	public PLUCodedItem pItem;	
	public PLUCodedItem pItem2;	
	public Mass pItemMass;
	public Mass pItemMass2;
	
	@Before
	public void setUp() {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();		
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		this.station = new SelfCheckoutStationBronze();	
		
		//Setting up PLUCoded Products
		PLU1 = new PriceLookUpCode("1111");
		PLU2 = new PriceLookUpCode("2222");
		PLU3 = new PriceLookUpCode("5555");
		pProduct = new PLUCodedProduct(PLU1, "Apples", 2);
		pProduct2 = new PLUCodedProduct(PLU2, "Bananas", 3);
		pProduct3 = new PLUCodedProduct (PLU3, "Oranges", 4);
		
		//Populating Database
		ProductDatabases.PLU_PRODUCT_DATABASE.clear();
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLU1, pProduct);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLU2, pProduct2);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLU3, pProduct3);			
		ProductDatabases.INVENTORY.put(pProduct, 10);
		ProductDatabases.INVENTORY.put(pProduct2, 10);
		ProductDatabases.INVENTORY.put(pProduct3, 10);
		
		//Setting up Items
		pItemMass = new Mass(1000000000);// 1kg
		pItem = new PLUCodedItem(PLU1, pItemMass);
		pItemMass2 = new Mass(2000000000); // 2kg
		pItem2 = new PLUCodedItem(PLU2, pItemMass2);
		
		//Initiliaze station
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		session = new CentralStationLogic(station);
		session.startSession();
				
	}
	@After
	public void tearDown() {
		PowerGrid.engageUninterruptiblePowerSource();
	}
	//When PLU is null
	@Test(expected = NullPointerException.class)
	public void testAddNullPLUCode(){
		session.addPLUProductController.addPLU(null);	
	}
	//When session isn't started
	@Test(expected = InvalidStateSimulationException.class)
	public void testSessionNotstarted(){
		session.stopSession();
		session.addPLUProductController.addPLU(PLU1);
	}
	//When an item has been succesfully added
	@Test 
	public void testSuccesfulAddition() {
		station.getScanningArea().addAnItem(pItem);
		session.addPLUProductController.addPLU(PLU1);
		assertEquals(session.cartLogic.getCart().size(),1);
	}
	// Trying to add an item when station is blocked
	@Test(expected = InvalidStateSimulationException.class)
	public void testStateBlocked(){
		 this.session.stateLogic.gotoState(StateLogic.States.BLOCKED);
		 session.addPLUProductController.addPLU(PLU2);
    //Adding a bulky
		
	}
	
	
}
	