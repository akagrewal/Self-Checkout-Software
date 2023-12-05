package com.thelocalmarketplace.software.test.controllers;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.OverloadedDevice;

import com.jjjwelectronics.scanner.Barcode;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;

import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;

import com.thelocalmarketplace.software.controllers.ReceiptPrintingController;
import com.thelocalmarketplace.software.logic.AttendantLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import powerutility.PowerGrid;

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
public class ReceiptPrintingTests {

    private CentralStationLogic session;
    private SelfCheckoutStationBronze station;
    private ReceiptPrintingController controller;
    

    @Before
    public void setUp() {
        PowerGrid.engageUninterruptiblePowerSource();
        PowerGrid.instance().forcePowerRestore();
        AbstractSelfCheckoutStation.resetConfigurationToDefaults();
        station = new SelfCheckoutStationBronze();
        station.plugIn(PowerGrid.instance());
        station.turnOn();
        session = new CentralStationLogic(station);
        session.startSession();
        controller = new ReceiptPrintingController(session);
    }

    @After
    public void tearDown() {
        PowerGrid.engageUninterruptiblePowerSource();
    }
    
    @Test
    public void testHandlePrintReceiptWithoutInk() throws OverloadedDevice {
    	Barcode barcode1 = new Barcode(new Numeral[] {Numeral.one});
        BarcodedProduct product1 = new BarcodedProduct(barcode1, "TestProduct", 1, 100.0);      
        session.cartLogic.addProductToCart(product1);      	
        station.getPrinter().addPaper(1000);
        controller.handlePrintReceipt(new BigDecimal(0));
        assertTrue(this.session.stateLogic.inState(States.SUSPENDED));
    }
    
    @Test
    public void testHandlePrintReceiptWithoutPaper() throws OverloadedDevice {
    	Barcode barcode1 = new Barcode(new Numeral[] {Numeral.one});
        BarcodedProduct product1 = new BarcodedProduct(barcode1, "TestProduct", 1, 100.0);      
        session.cartLogic.addProductToCart(product1);  
        station.getPrinter().addInk(1000);
        controller.handlePrintReceipt(new BigDecimal(0));
        
        assertTrue(this.session.stateLogic.inState(States.SUSPENDED));
    }
    
    @Test
    public void testPrintReceiptWithPaperandInk() throws OverloadedDevice {
    	Barcode barcode1 = new Barcode(new Numeral[] {Numeral.one});
        BarcodedProduct product1 = new BarcodedProduct(barcode1, "TestProduct", 1, 100.0);      
        session.cartLogic.addProductToCart(product1);  
        station.getPrinter().addInk(1000);
        station.getPrinter().addPaper(1000);
        controller.handlePrintReceipt(new BigDecimal(0));
        
        assertNotEquals(this.session.stateLogic.getState(), States.SUSPENDED);
    }
    
    @Test
    public void testNotifyOutofInk() throws OverloadedDevice {
    	Barcode barcode1 = new Barcode(new Numeral[] {Numeral.one});
        BarcodedProduct product1 = new BarcodedProduct(barcode1, "TestProduct", 1, 100.0);      
        session.cartLogic.addProductToCart(product1);  
        station.getPrinter().addInk(5);
        station.getPrinter().addPaper(5);
        controller.handlePrintReceipt(new BigDecimal(0));
        
        assertTrue(session.receiptPrintingController.getInkLow());
        
        assertEquals(this.session.stateLogic.getState(), States.OUTOFORDER);
        
        station.getPrinter().addInk(10);
        
        assertEquals(this.session.stateLogic.getState(), States.NORMAL);
    }
    
//    @Test
//    public void testNotifyOutofPaper() throws OverloadedDevice {
//    	Barcode barcode1 = new Barcode(new Numeral[] {Numeral.one});
//        BarcodedProduct product1 = new BarcodedProduct(barcode1, "TestProduct", 1, 100.0);      
//        session.cartLogic.addProductToCart(product1);  
//        station.getPrinter().addInk(1000);
//        station.getPrinter().addPaper(1);
//        controller.handlePrintReceipt(new BigDecimal(0));
//        
//        assertEquals(this.session.stateLogic.getState(), States.SUSPENDED);
//    }
    
    @Test
    public void testAttendantResolvingError() throws OverloadedDevice {
    	Barcode barcode1 = new Barcode(new Numeral[] {Numeral.one});
        BarcodedProduct product1 = new BarcodedProduct(barcode1, "TestProduct", 1, 100.0);      
        session.cartLogic.addProductToCart(product1);  
        station.getPrinter().addInk(1000);
        station.getPrinter().addPaper(1);
        controller.handlePrintReceipt(new BigDecimal(0));
        
        assertEquals(this.session.stateLogic.getState(), States.SUSPENDED);
        
        station.getPrinter().addPaper(100);
        AttendantLogic attendant = new AttendantLogic(session);
        attendant.printDuplicateReceipt();
        assertEquals(this.session.stateLogic.getState(), States.NORMAL);
    }
}
