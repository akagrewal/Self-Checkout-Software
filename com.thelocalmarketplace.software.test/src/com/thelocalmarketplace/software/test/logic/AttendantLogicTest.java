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

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.bag.IReusableBagDispenser;
import com.jjjwelectronics.card.ICardReader;
import com.jjjwelectronics.printer.IReceiptPrinter;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.jjjwelectronics.screen.ITouchScreen;
import com.tdc.banknote.BanknoteDispensationSlot;
import com.tdc.banknote.BanknoteInsertionSlot;
import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.BanknoteValidator;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.CoinSlot;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.CoinValidator;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.*;
import com.thelocalmarketplace.software.gui.AttendantGUI;
import com.thelocalmarketplace.software.logic.*;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import powerutility.PowerGrid;

/**
 * 
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
public class AttendantLogicTest {
	private AttendantLogic attendantLogic;
	private CentralStationLogicStub centralStationLogicStub;
	private AttendantGUIStub attendantGUIStub;
	private SelfCheckoutStationBronze station = new SelfCheckoutStationBronze();
	
	@Before
	public void setUp() {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		attendantLogic = new AttendantLogic();
		attendantGUIStub = new AttendantGUIStub();
		attendantLogic.attendantGUI = attendantGUIStub;
		centralStationLogicStub = new CentralStationLogicStub(station);
		station.plugIn(PowerGrid.instance());
		station.turnOn();
	}
	
	@Test
	public void testRegisterStationLogic() {
		attendantLogic.registerStationLogic(centralStationLogicStub);
		assertTrue(attendantGUIStub.createAttendantFrameCalled == 1);
	}
	
	@Test
	public void testDeregisterStationLogic() {
		attendantLogic.registerStationLogic(centralStationLogicStub);
		attendantLogic.deregisterStationLogic(centralStationLogicStub);
		
		// the station should have been removed from the buttons
		assertTrue(attendantGUIStub.stationToButtonMap.get(centralStationLogicStub) == null);
		
		// the station should have been removed from the logic list
		assertTrue(attendantGUIStub.stationLogicsList.indexOf(centralStationLogicStub) == -1);
	}
	
	@Test
	public void testUpdateAttendantGUIWhenFrameNotNull() {
		JFrameStub frameStub = new JFrameStub();
		attendantGUIStub.attendantFrame = frameStub;
		attendantLogic.updateAttendantGUI();
		
		// the frame should have been disposed
		assertTrue(frameStub.disposed == 1);
	}
	
	@Test
	public void testUpdateAttendantGUIWhenFrameNull() {
		attendantLogic.updateAttendantGUI();
		
		// the frame should have been created
		assertTrue(attendantGUIStub.createAttendantFrameCalled == 1);
	}
	
	@Test
	public void testApproveBaggingAreaWhenNotApproved() {
		try {
			centralStationLogicStub.sessionStarted = true;
			centralStationLogicStub.stateLogic.gotoState(States.ADDBAGS);
			attendantLogic.approveBaggingArea(centralStationLogicStub);
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
	}
	
	@Test
	public void testBaggingDiscrepencyDetectedWhenNotApproved() {
		try {
			centralStationLogicStub.addBagsLogic.approvedBagging = false;
			attendantLogic.baggingDiscrepencyDetected(centralStationLogicStub);
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
	}
	
	@Test
	public void testBaggingDiscrepencyDetectedWhenApproved() {
		try {
			centralStationLogicStub.addBagsLogic.approvedBagging = true;
			attendantLogic.baggingDiscrepencyDetected(centralStationLogicStub);
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
	}
	
	@Test
	public void testRequestApprovalSkipBaggingWhenBarcodeNotNullNotBlocked() {
		try {
			attendantLogic.requestApprovalSkipBagging(centralStationLogicStub, new Barcode(new Numeral[]{Numeral.one}));
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof InvalidStateSimulationException);
		}
	}
	
	@Test
	public void testRequestApprovalSkipBaggingWhenBarcodeNotNullBlocked() {
		try {
			centralStationLogicStub.stateLogic.gotoState(States.BLOCKED);
			attendantLogic.requestApprovalSkipBagging(centralStationLogicStub, new Barcode(new Numeral[]{Numeral.one}));
		} catch (InvalidStateSimulationException e) {
			fail();
		}
	}
	
	@Test
	public void testRequestApprovalSkipBaggingWhenBarcodeNull() {
		try {
			attendantLogic.requestApprovalSkipBagging(centralStationLogicStub, null);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof InvalidArgumentSimulationException);
		}
	}
	
	@Test
	public void testGrantApprovalSkipBaggingWhenBarcodeNull() {
		try {
			attendantLogic.grantApprovalSkipBagging(centralStationLogicStub, null);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof InvalidArgumentSimulationException);
		}
	}
	
	@Test
	public void testGrantApprovalSkipBaggingWhenBarcodeNotNull() {
		try {
			attendantLogic.grantApprovalSkipBagging(centralStationLogicStub, new Barcode(new Numeral[]{Numeral.one}));
		} catch (Exception e) {
			if (!(e instanceof InvalidStateSimulationException))
				fail();
		}
	}
	
	@Test
	public void notifySessionEndedNotWaiting() {
		try {
			attendantLogic.notifySessionEnded(centralStationLogicStub);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void notifySessionEndedWaiting() {
		try {
			centralStationLogicStub.sessionStarted = true;
			attendantLogic.disableCustomerStation(centralStationLogicStub);
			attendantLogic.notifySessionEnded(centralStationLogicStub);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testDisableCustomerStationSessionStarted() {
		attendantLogic.disableCustomerStation(centralStationLogicStub);
	}
	
	@Test
	public void testCallAttendant() {
		try {
			attendantLogic.callAttendant(0);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testEnableCustomerStationWhenNotOutOfOrder() {
		attendantLogic.enableCustomerStation(centralStationLogicStub);
		assertTrue(centralStationLogicStub.stateLogic.getState() == States.NORMAL);
	}
	
	@Test
	public void testEnableCustomerStationWhenOutOfOrder() {
		try {
			centralStationLogicStub.stateLogic.gotoState(States.OUTOFORDER);
			attendantLogic.enableCustomerStation(centralStationLogicStub);
		} catch (Exception e) {
			if (!(e instanceof NullPointerException))
				fail();
		}
	}
	
	@Test
	public void testWeightDiscrepancy() {
		try {
			attendantLogic.weightDiscrepancy(centralStationLogicStub);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testAddItemByTextSearch() {
		try {
			attendantLogic.AddItemByTextSearch(centralStationLogicStub, "Apple");
		} catch (Exception e) {
			fail();
		}
	}
	
	class CentralStationLogicStub extends CentralStationLogic {
		boolean sessionStarted = false;
		
		public CentralStationLogicStub(AbstractSelfCheckoutStation hardware) throws NullPointerException {
			super(hardware);
		}
		
		public boolean isSessionStarted() {
			 return sessionStarted;
		}
	}
	
	class AttendantGUIStub extends AttendantGUI {
		int createAttendantFrameCalled = 0;
		int getAttendantFrameCalled = 0;
		JFrame attendantFrame = null;
		
		public void createAttendantFrame() {
			createAttendantFrameCalled++;
		}
		
		public JFrame getAttendantFrame() {
			getAttendantFrameCalled++;
			return attendantFrame;
		}
	}
	
	class JFrameStub extends JFrame {
		int disposed = 0;
		
		public void dispose() {
			disposed++;
		}
	}
}