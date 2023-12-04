

package com.thelocalmarketplace.software.gui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.card.ICardReader;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.tdc.banknote.BanknoteValidator;
import com.tdc.coin.CoinValidator;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.database.CreateTestDatabases;
import com.thelocalmarketplace.software.logic.AttendantLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

import powerutility.PowerGrid;

public class DemoHere {

    public IElectronicScale baggingAreaScale;
    public IBarcodeScanner handHeldScanner;
    public IBarcodeScanner mainScanner;
    public BanknoteValidator banknoteValidator;
    public CoinValidator coinValidator;
    public ICardReader cardReader;
    // add instances of your class here then initialize below
//    public WeightDiscrepancy weightDiscrepancy;
//    public TouchScreen touchScreen;
//    public Attendant attendant;
//    public PayByBanknote payByBanknote;
//
//    public UpdateCart updateCart;

    public Mass allowableBagWeight;

	private ArrayList<BarcodedProduct> barcodedProductsInOrder;
	private ArrayList<BarcodedProduct> baggedProducts;

	private static Currency currency = 	Currency.getInstance("CAD");

	private BigDecimal[] billDenominations;

	private static BigDecimal[] coindenominations = new BigDecimal[] {
			new BigDecimal("0.05"),
			new BigDecimal("0.10"),
			new BigDecimal("0.25"),
			new BigDecimal("1.00"),
			new BigDecimal("2.00")
	};

    //For Testing Purposes - to run GUI (main)
    public static void main(String[] args) {

		CreateTestDatabases.createDatabase();
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		AbstractSelfCheckoutStation.configureCoinDenominations(coindenominations);
		AbstractSelfCheckoutStation.configureCoinDispenserCapacity(10);
		AbstractSelfCheckoutStation.configureCoinStorageUnitCapacity(10);
		AbstractSelfCheckoutStation.configureCoinTrayCapacity(10);
		AbstractSelfCheckoutStation.configureCurrency(currency);
		
    	SelfCheckoutStationGold station1 = new SelfCheckoutStationGold();
    	station1.plugIn(PowerGrid.instance());
		station1.turnOn();

    	CentralStationLogic stationLogic1 = new CentralStationLogic(station1);
    	stationLogic1.setStationNumber(1);


		AttendantLogic attendantLogic = new AttendantLogic();
        attendantLogic.registerStationLogic(stationLogic1);
        HardwareActionSimulations actionsFrame = new HardwareActionSimulations(stationLogic1);
        actionsFrame.setVisible(true);
    }
}
