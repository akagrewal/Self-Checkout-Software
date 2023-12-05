package com.thelocalmarketplace.software.test.gui;

import static org.junit.Assert.*;

import java.awt.AWTEvent;
import java.math.BigDecimal;

import javax.swing.JPanel;

import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.database.CreateTestDatabases;
import com.thelocalmarketplace.software.gui.GUILogic;
import com.thelocalmarketplace.software.gui.HardwareActionSimulations;
import com.thelocalmarketplace.software.gui.HardwarePopups;
import com.thelocalmarketplace.software.gui.NumberPadDialog;
import com.thelocalmarketplace.software.gui.StationGUI;
import com.thelocalmarketplace.software.logic.AttendantLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import powerutility.PowerGrid;

import org.junit.Before;
import org.junit.Test;

	public class GUILogicTests {

	    private GUILogic guiLogic;
	    private CentralStationLogic centralStation;
	    private StationGUI mockDisplay;
	    private AbstractSelfCheckoutStation station1;
	    private PLUCodedItem pItem1;
	    private BarcodedItem bItem1;
	    private PLUCodedItem pItem2;
	    private BarcodedItem bItem2;
	    private PLUCodedItem pItem3;
	    private PriceLookUpCode pLU1;
	    private PriceLookUpCode pLU2;
	    private PriceLookUpCode pLU3;
	    private Barcode barcode;
	    private Barcode barcode2;
	    private Barcode barcode3;
	    private HardwareActionSimulations actionsFrame;
	    private HardwarePopups hardwareActions;
	    
	    

	    @Before
	    public void setUp() {
	        mockDisplay = new StationGUI(centralStation);
	        CreateTestDatabases.createDatabase();
			PowerGrid.engageUninterruptiblePowerSource();
			PowerGrid.instance().forcePowerRestore();
			AbstractSelfCheckoutStation.resetConfigurationToDefaults();			
	    	this.station1 = new SelfCheckoutStationGold();
	    	station1.plugIn(PowerGrid.instance());
			station1.turnOn();
	
	    	this.centralStation = new CentralStationLogic(station1);
	    	centralStation.setStationNumber(1);
			AttendantLogic attendantLogic = new AttendantLogic();
	        attendantLogic.registerStationLogic(centralStation);
	        HardwareActionSimulations actionsFrame = new HardwareActionSimulations(centralStation);
	       
	        actionsFrame.setVisible(true);
	        guiLogic = new GUILogic(centralStation,mockDisplay);
	        this.hardwareActions = new HardwarePopups(centralStation);
	        JPanel panel = new JPanel();
			guiLogic.addRemoveButtons(panel);
	        
	       
	       
	        
	        
	        // Creating Mock Items 
	        pLU1 = new PriceLookUpCode("1001");
	        pItem1 = new PLUCodedItem(pLU1, new Mass(1000000000));
	        pLU2 = new PriceLookUpCode("1002");
	        pItem2 = new PLUCodedItem(pLU1, new Mass(2000000000));
	        pLU3 = new PriceLookUpCode("1509");
	        pItem3 = new PLUCodedItem(pLU3, new Mass(100000000));
	        
	        barcode =  new Barcode(new Numeral[]{Numeral.one, Numeral.two, Numeral.three, Numeral.four});
	        bItem1 = new BarcodedItem(barcode, new Mass(100000));
	        
	        
	    }

	    @Test
	    public void testStartSessionButtonPressed() {
	        // This is a simple method, just checking if it loads the test databases
	        guiLogic.StartSessionButtonPressed();
	        // check if 150 of a product (apples) are in inventory
	        assertTrue(ProductDatabases.INVENTORY.containsValue(150));
	    }

	    @Test
	    public void testCustomerAddsItemPLUCode() {
	    	//Customer starts session
	    	guiLogic.StartSessionButtonPressed();
	    	//Adds PLU item to scanning area
	        station1.getScanningArea().addAnItem(pItem1); 
	        //True if PLU is valid and directly added to cart
	        assertTrue(guiLogic.checkPLU("1001"));
	    }
	    @Test
	    public void testCustomerAddsWrongItemPLUCode() {
	    	//Customer starts session
	    	guiLogic.StartSessionButtonPressed();
	    	//Adds PLU item to scanning area
	        station1.getScanningArea().addAnItem(pItem3); 
	        guiLogic.showExceptionMessage("WrongBarcode");
	        //Blocks Screan
	        guiLogic.blockGUI();
	        //True if PLU is valid and directly added to cart
	        assertFalse(guiLogic.checkPLU("1509"));
	    }
	    
	    @Test
	    public void testCustomerScansBarcodedProduct() {
	    	guiLogic.StartSessionButtonPressed();
	    	//Total before Item is added
	    	 BigDecimal oldTotal = centralStation.cartLogic.getBalanceOwed();
	        	
	 
	    	//Scans the Barcoded item 
	    	station1.getHandheldScanner().scan(bItem1);
	       
            //Total After Item is added
	    	 BigDecimal newTotal = centralStation.cartLogic.getBalanceOwed();
	    
	        assertFalse(newTotal.equals(oldTotal));
	    }

	    @Test
	    public void testCustomerAddsOwnBags() {
	        // Session starts
	    	guiLogic.StartSessionButtonPressed();
	    	
	        // Customer chooses the number of bag used
	       guiLogic.addPurchasableBag(1);
	       
	       //Cart will update with the number of bags
	       boolean CartSize = centralStation.cartLogic.getCart().isEmpty();
	       assertFalse(CartSize);
	    }

	   

	    @Test
	    public void testCustomerAddsMembershipDetails() {
	    	//Customer Starts session.
	    	guiLogic.StartSessionButtonPressed();
	    	//System checks for customer in system
	    	 Boolean isAMember = guiLogic.checkMembership("123456");
	    	
	        // Will return false if customer isn't registered
	        assertTrue(isAMember);	        
	    }

	   

	    
	}


