package com.thelocalmarketplace.software.test;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.bag.ReusableBag;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.PurchaseBagsLogic;

public class PurchaseBagsLogicTest {
	PurchaseBagsLogic purchaseBagsLogic;
	CentralStationLogic logic;
	ReusableBag bag;
	Mass bagsMass;
	IElectronicScale scale;
	boolean scaleOperational;
	
	
	@Before
	public void setup(){
		
		
	}
	
	@Test
	public void testValidThreeBags() {
		
	}
	
	@Test
	public void testInvalidNegativeBags() {
		
	}
	@Test
	public void testZeroBags() {
		
	}
	@Test
	public void testNoWeightDiscrepancy() {
		
	}
	
	
	
	
}
