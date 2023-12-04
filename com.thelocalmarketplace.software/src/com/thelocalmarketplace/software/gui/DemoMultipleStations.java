
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


public class DemoMultipleStations {

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



    //For Testing Purposes - to run GUI (main)
    public static void main(String[] args) {

        CreateTestDatabases.createDatabase();
        PowerGrid.engageUninterruptiblePowerSource();
        PowerGrid.instance().forcePowerRestore();
        AbstractSelfCheckoutStation.resetConfigurationToDefaults();

        SelfCheckoutStationGold station1 = new SelfCheckoutStationGold();
        SelfCheckoutStationGold station2 = new SelfCheckoutStationGold();
        SelfCheckoutStationGold station3 = new SelfCheckoutStationGold();
        station1.plugIn(PowerGrid.instance());
        station1.turnOn();
        station2.plugIn(PowerGrid.instance());
        station2.turnOn();
        station3.plugIn(PowerGrid.instance());
        station3.turnOn();

        CentralStationLogic stationLogic1 = new CentralStationLogic(station1);
        stationLogic1.setStationNumber(1);
        CentralStationLogic stationLogic2 = new CentralStationLogic(station2);
        stationLogic2.setStationNumber(2);
        CentralStationLogic stationLogic3 = new CentralStationLogic(station3);
        stationLogic3.setStationNumber(3);


        AttendantLogic attendantLogic = new AttendantLogic();
        attendantLogic.registerStationLogic(stationLogic1);
        attendantLogic.registerStationLogic(stationLogic2);
        attendantLogic.registerStationLogic(stationLogic3);
        HardwareActionSimulations actionsFrame1 = new HardwareActionSimulations(stationLogic1);
        HardwareActionSimulations actionsFrame2 = new HardwareActionSimulations(stationLogic2);
        HardwareActionSimulations actionsFrame3 = new HardwareActionSimulations(stationLogic2);
        actionsFrame1.setVisible(true);
        actionsFrame2.setVisible(true);
        actionsFrame3.setVisible(true);
    }
}
