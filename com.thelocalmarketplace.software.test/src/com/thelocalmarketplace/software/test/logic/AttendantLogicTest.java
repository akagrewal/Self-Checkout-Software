package com.thelocalmarketplace.software.test.logic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

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
import com.thelocalmarketplace.hardware.CoinTray;
import com.thelocalmarketplace.software.gui.AttendantGUI;
import com.thelocalmarketplace.software.logic.*;

public class AttendantLogicTest {
	private AttendantLogic attendantLogic;
	private CentralStationLogicStub centralStationLogicStub;
	private AttendantGUIStub attendantGUIStub;
	
	@Before
	public void setUp() {
		attendantLogic = new AttendantLogic();
		attendantGUIStub = new AttendantGUIStub();
		attendantLogic.attendantGUI = attendantGUIStub;
		centralStationLogicStub = new CentralStationLogicStub(new SelfCheckoutStationBronze());
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
	}
	
	class CentralStationLogicStub extends CentralStationLogic {
		public CentralStationLogicStub(AbstractSelfCheckoutStation hardware) throws NullPointerException {
			super(hardware);
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
