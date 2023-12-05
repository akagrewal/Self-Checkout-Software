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


package com.thelocalmarketplace.software.test.logic;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.IllegalDigitException;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Mass.MassDifference;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.bag.IReusableBagDispenser;
import com.jjjwelectronics.bag.ReusableBag;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.PurchaseBagsLogic;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import powerutility.NoPowerException;
import powerutility.PowerGrid;

public class PurchaseBagsLogicTest {
	
	SelfCheckoutStationGold hardware;
	PurchaseBagsLogic purchaseBagsLogic;
	CentralStationLogic logic;
	IReusableBagDispenser iDispenser;
	
	Mass bagsMass;
	IElectronicScale scale;
	boolean scaleOperational;
	
	ReusableBag bag1;
	ReusableBag bag2;
	ReusableBag bag3;
	ReusableBag bag4;
	ReusableBag bag5;
	
	ArrayList <ReusableBag> bags = new ArrayList <ReusableBag>() ;
	
	
	@Before
	public void setup() throws OverloadedDevice{
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		
		this.hardware = new SelfCheckoutStationGold();
		this.hardware.plugIn(PowerGrid.instance());
		this.hardware.turnOn();
		this.logic = new CentralStationLogic(hardware);
		
		this.iDispenser = logic.hardware.getReusableBagDispenser();
		this.scale = logic.hardware.getBaggingArea();
		this.purchaseBagsLogic = new PurchaseBagsLogic(logic);
		
		bag1 = new ReusableBag();
		bag2 = new ReusableBag();
		bag3 = new ReusableBag();
		bag4 = new ReusableBag();
		bag5 = new ReusableBag();
		
		bags.add(bag1);
		bags.add(bag2);
		bags.add(bag3);
		bags.add(bag4);
		bags.add(bag5);
		
		iDispenser.load(bag1, bag2, bag3, bag4, bag5);
		System.out.println( iDispenser.getQuantityRemaining());
		
	}
	
	@Test
	public void testValidThreeBagsAddedToOrder() throws EmptyDevice {
		logic.startSession();
		purchaseBagsLogic.dispensePurchasedBags(3);
		
		BigDecimal expectedOwed = new BigDecimal (3.75);
		BigDecimal actualOwed = logic.cartLogic.getBalanceOwed();	
		
		assertTrue(expectedOwed.compareTo(actualOwed) == 0);		
	}
	
	@Test
	public void testValidThreeBagsExpectedWeightUpdated() throws EmptyDevice {
		logic.startSession();
		Mass initialWeight = logic.weightLogic.getExpectedWeight();
		purchaseBagsLogic.dispensePurchasedBags(3);
		Mass afterWeight = logic.weightLogic.getExpectedWeight();
		MassDifference difference = afterWeight.difference(initialWeight) ;
		bagsMass = bag1.getMass().sum(bag2.getMass()).sum(bag3.getMass()) ;
		
		assertTrue(difference.compareTo(bagsMass)== 0);		
	}
	
	@Test
	public void testValidThreeBagsNoWeightDiscrepancy() throws EmptyDevice {
		logic.startSession();
		purchaseBagsLogic.dispensePurchasedBags(3);
		
		assertFalse(logic.weightLogic.checkWeightDiscrepancy());
	}
	
	@Test (expected = IllegalDigitException.class)
	public void testInvalidNegativeBagsNotAddedToOrderWhenOrderEmpty() throws EmptyDevice {
		logic.startSession();
		purchaseBagsLogic.dispensePurchasedBags(-3);
		
		BigDecimal expected = new BigDecimal("0");
		BigDecimal actual = logic.cartLogic.getBalanceOwed();
		
		assertTrue(expected.compareTo(actual) == 0);
	}
	
	@Test (expected = IllegalDigitException.class)
	public void testInvalidNegativeBagsWhenOrderNotEmpty() throws EmptyDevice {
		logic.startSession();
		purchaseBagsLogic.dispensePurchasedBags(1); // amount owed should be 1.25
		
		purchaseBagsLogic.dispensePurchasedBags(-3);
	}
	
	
	@Test
	public void testZeroBagsNotAddedToOrderWhenOrderEmpty() throws EmptyDevice {
		logic.startSession();
		purchaseBagsLogic.dispensePurchasedBags(0);
		BigDecimal expected = new BigDecimal(0);
		BigDecimal actual = logic.cartLogic.getBalanceOwed();
		
		assertTrue(expected.compareTo(actual)== 0);
	}
	
	@Test
	public void testZeroBagsNotAddedToOrderWhenOrderNotEmpty() throws EmptyDevice {
		logic.startSession();
		purchaseBagsLogic.dispensePurchasedBags(4); // balance owed should equal 5.00
		
		purchaseBagsLogic.dispensePurchasedBags(0);
		BigDecimal expected = new BigDecimal(5.00);
		BigDecimal actual = logic.cartLogic.getBalanceOwed();
		
		assertTrue(expected.compareTo(actual) == 0);		
	}
	
	@Test
	public void testZeroBagsNoActualWeightChange() throws EmptyDevice {
		logic.startSession();
		Mass initialWeight = logic.weightLogic.getActualWeight();
		
		purchaseBagsLogic.dispensePurchasedBags(0);
		
		Mass afterWeight = logic.weightLogic.getActualWeight();
		
		assertTrue(initialWeight.compareTo(afterWeight) == 0);	
	}
	
	@Test
	public void testZeroBagsNoExpectedWeightChange() throws EmptyDevice {
		logic.startSession();
		
		Mass initialWeight = logic.weightLogic.getExpectedWeight();
		
		purchaseBagsLogic.dispensePurchasedBags(0);
		
		Mass afterWeight = logic.weightLogic.getExpectedWeight();
		
		assertTrue(initialWeight.compareTo(afterWeight) == 0);	
	}
	
	@Test 
	public void testPurchaseMoreBagsThanAvailable() throws EmptyDevice  {
		logic.startSession();
		purchaseBagsLogic.dispensePurchasedBags(6);
		
		BigDecimal expected= new BigDecimal(1.25 * 5); // multiply by 5 because that's hpw many bags are in the dispenser
		BigDecimal actual = logic.cartLogic.getBalanceOwed();
		
		assertTrue( expected.compareTo(actual) == 0);	
	}
	
	@Test (expected = NoPowerException.class)
	public void testPreconditonsWhenScaleNotOperational() throws EmptyDevice{
		logic.startSession();
	
		this.scale.turnOff();
		this.scale.disable();
		this.scale.unplug();
		
		purchaseBagsLogic.dispensePurchasedBags(2);	
	}	
	
	@Test (expected = InvalidStateSimulationException.class)
	public void testPreconditonsWhenSessionNotStartedAndScaleOperational() throws EmptyDevice{
		// do not start session
		purchaseBagsLogic.dispensePurchasedBags(3);
	}
	
	@Test 
	public void testWhenNoBagsAvailable() throws EmptyDevice{
		logic.startSession();
		iDispenser.unload();
		
		purchaseBagsLogic.dispensePurchasedBags(4);
		
		BigDecimal expected = new BigDecimal(0);
		BigDecimal actual = logic.cartLogic.getBalanceOwed();
		
		assertTrue(expected.compareTo(actual)== 0); // check no bags added to order
	}
	
	@Test 
	public void testWhenNoBagsAvailableNoActualWeightChange() throws EmptyDevice{
		logic.startSession();
		iDispenser.unload();
		
		Mass initialWeight = logic.weightLogic.getActualWeight();
		
		purchaseBagsLogic.dispensePurchasedBags(4);
		
		Mass afterWeight = logic.weightLogic.getActualWeight();
		
		
		assertTrue(initialWeight.compareTo(afterWeight)== 0); 
	}
	
	@Test 
	public void testWhenNoBagsAvailableNoExpectedWeightChange() throws EmptyDevice{
		logic.startSession();
		iDispenser.unload();
		
		Mass initialWeight = logic.weightLogic.getExpectedWeight();
		
		purchaseBagsLogic.dispensePurchasedBags(4);
		
		Mass afterWeight = logic.weightLogic.getExpectedWeight();
			
		assertTrue(initialWeight.compareTo(afterWeight)== 0); 
	}	
	
}
