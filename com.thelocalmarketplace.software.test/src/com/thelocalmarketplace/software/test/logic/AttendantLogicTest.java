package com.thelocalmarketplace.software.test.logic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.CoinTray;
import com.thelocalmarketplace.software.gui.AttendantGUI;
import com.thelocalmarketplace.software.logic.*;

public class AttendantLogicTest {
	private AttendantLogic attendantLogic;
	private CentralStationLogicStub centralStationLogic;
	
	@Before
	public void setUp() {
		attendantLogic = new AttendantLogic();
		centralStationLogic = new CentralStationLogicStub();
	}
	
	@Test
	public void testRegisterStationLogic() {
		
	}
	
	class CentralStationLogicStub extends CentralStationLogic{

		public CentralStationLogicStub(AbstractSelfCheckoutStation hardware) throws NullPointerException {
			super(hardware);
		}
	}
	
	class AttendantGUIStub extends AttendantGUI {

	}
}
