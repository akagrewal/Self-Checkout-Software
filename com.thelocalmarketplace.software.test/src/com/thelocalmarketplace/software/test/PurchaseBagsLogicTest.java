package com.thelocalmarketplace.software.test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.bag.IReusableBagDispenser;
import com.jjjwelectronics.bag.ReusableBag;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.PurchaseBagsLogic;

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
		logic.startSession();
	}
	
	@After
	public void teardown() {
		
	}
	
	@Test
	public void testValidThreeBagsAddedToOrder() throws EmptyDevice {
		
		purchaseBagsLogic.dispensePurchasedBags(3);
		
		BigDecimal expectedOwed = new BigDecimal (3.75);
		BigDecimal actualOwed = logic.cartLogic.getBalanceOwed();	
		
		assertTrue(expectedOwed.compareTo(actualOwed) == 0);		
	}
	
	@Test
	public void testValidThreeBagsNoWeightDiscrep() throws EmptyDevice {
		
		purchaseBagsLogic.dispensePurchasedBags(3);
		
		assertFalse(logic.weightLogic.checkWeightDiscrepancy());
	}
	
	@Test
	public void testInvalidNegativeBagsNotAddedToOrderWhenOrderEmpty() throws EmptyDevice {
		purchaseBagsLogic.dispensePurchasedBags(-3);
		
		BigDecimal expected = new BigDecimal("0");
		BigDecimal actual = logic.cartLogic.getBalanceOwed();
		
		assertTrue(expected.compareTo(actual) == 0);
	}
	
	public void testInvalidNegativeBagsNotAddedToOrderWhenOrderNotEmpty() throws EmptyDevice {
		purchaseBagsLogic.dispensePurchasedBags(1); // amount owed should be 1.25
		
		purchaseBagsLogic.dispensePurchasedBags(-3);
		
		BigDecimal expected = new BigDecimal(1.25);
		BigDecimal actual = logic.cartLogic.getBalanceOwed();
		
		System.out.println("invalid e" + expected);
		System.out.println("invalid a" + actual);
		
		assertTrue(expected.compareTo(actual) == 0) ;
	}
	
	
	@Test
	public void testZeroBagsNotAddedToOrderWhenOrderEmpty() throws EmptyDevice {
		purchaseBagsLogic.dispensePurchasedBags(0);
		BigDecimal expected = new BigDecimal(0);
		BigDecimal actual = logic.cartLogic.getBalanceOwed();
		
		assertTrue(expected.compareTo(actual)== 0);
	}
	
	@Test
	public void testZeroBagsNotAddedToOrderWhenOrderNotEmpty() throws EmptyDevice {
		purchaseBagsLogic.dispensePurchasedBags(4); // balance owed should equal 5.00
		
		purchaseBagsLogic.dispensePurchasedBags(0);
		BigDecimal expected = new BigDecimal(5.00);
		BigDecimal actual = logic.cartLogic.getBalanceOwed();
		
		assertTrue(expected.compareTo(actual) == 0);
		
		
		
		
	}
	@Test
	public void testZeroBagsNoWeightChange() {
		
	}
	
	@Test 
	public void testPurchaseMoreBagsThanAvailable() {
		
	}
	
	@Test
	public void testNull() {
		
	}
	
	
	
}
