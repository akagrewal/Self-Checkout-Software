package com.thelocalmarketplace.software.test.logic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import com.jjjwelectronics.bag.IReusableBagDispenser;
import com.jjjwelectronics.card.ICardReader;
import com.jjjwelectronics.printer.IReceiptPrinter;
import com.jjjwelectronics.scale.IElectronicScale;
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

import powerutility.PowerGrid;

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
		
		station = new SelfCheckoutStationBronze();
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
	public void testApproveBaggingArea() {
		try {
			centralStationLogicStub.sessionStarted = true;
			centralStationLogicStub.stateLogic.gotoState(States.ADDBAGS);
			attendantLogic.approveBaggingArea(centralStationLogicStub);
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
	}
	
	class CentralStationLogicStub extends CentralStationLogic {
		boolean sessionStarted = false;
		
		public CentralStationLogicStub(AbstractSelfCheckoutStation hardware) throws NullPointerException {
			super(hardware);
		}
		
		public boolean isSessionStarted() {
			 return true;
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
