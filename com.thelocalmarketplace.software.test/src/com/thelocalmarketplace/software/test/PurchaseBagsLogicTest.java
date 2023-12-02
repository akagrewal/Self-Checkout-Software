package com.thelocalmarketplace.software.test;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;

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
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.PurchaseBagsLogic;

import powerutility.PowerGrid;

public class PurchaseBagsLogicTest {
	
	SelfCheckoutStationBronze hardware;
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
		
		this.hardware = new SelfCheckoutStationBronze();
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
	}
	
	@Test
	public void testValidThreeBagsAddedToOrder() throws EmptyDevice {
		purchaseBagsLogic.dispensePurchasedBags(3);
		
		BigDecimal expectedOwed = new BigDecimal ("1.25");
		BigDecimal actualOwed = logic.cartLogic.getBalanceOwed();	
		BigDecimal zero = new BigDecimal("0");
		
		expectedOwed.compareTo(actualOwed);
		
	}
	
	@Test
	public void testValidThreeBagsNoWeightDiscrep() {
		
	}
	
	@Test
	public void testInvalidNegativeBagsNotAddedToOrder() {
		
	}
	
	
	
	@Test
	public void testZeroBagsNotAddedToOrder() {
		
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
