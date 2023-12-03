package com.thelocalmarketplace.software.test.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import powerutility.PowerGrid;

public class BagsTooHeavyTest {
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
	SelfCheckoutStationBronze station;
	CentralStationLogic logic;
	@Before public void setUp() {
	station = new SelfCheckoutStationBronze();
	station.plugIn(PowerGrid.instance());
	station.turnOn();
	logic = new CentralStationLogic(station);
	Mass bagWeight = new Mass(BigInteger.valueOf(25 * Mass.MICROGRAMS_PER_GRAM));
	this.logic.weightLogic.updateTotalBagMass(bagWeight);
}
	@Test public void testBaggingDiscrepancyDetected() {
		this.logic.addBagsLogic.approvedBagging = false;
		this.logic.attendantLogic.baggingDiscrepencyDetected();
		assertEquals(this.logic.stateLogic.getState(),States.BLOCKED);
	}
	@Test public void testBaggingDiscrepancyDetectedApproved() {
		this.logic.addBagsLogic.approvedBagging = true;
		this.logic.attendantLogic.baggingDiscrepencyDetected();
		assertEquals(this.logic.stateLogic.getState(),States.NORMAL);
	}
}