package com.thelocalmarketplace.software.test.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
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
public class RemoveItemTests {

		SelfCheckoutStationBronze station;
		CentralStationLogic session;
		
		//stuff for database
		
		public Barcode barcode;
		public Barcode barcode2;	
		public Barcode barcode3;
		public Numeral digits;
		public Barcode nullBarcode;
		public PriceLookUpCode PLUcode;
		public PriceLookUpCode PLUcode2;
		public PriceLookUpCode PLUcode3;
		public PriceLookUpCode nullPLUcode;
		
		
		public BarcodedItem bitem;
		public BarcodedItem bitem2;	
		public BarcodedItem bitem3;
		public BarcodedItem bitem4;
		public BarcodedItem bitem5;				
		public Mass bItemMass;	
		public Mass bItemMass2;	
		public Mass bItemMass3;	
		public Mass bItemMass4;
		public Mass bItemMass5;
		
		public PLUCodedItem pItem;	
		public PLUCodedItem pItem2;	
		public Mass pItemMass;
		public Mass pItemMass1;
	
		public Numeral[] barcode_numeral;
		public Numeral[] barcode_numeral2;
		public Numeral[] barcode_numeral3;
		public Numeral[] PLU_numeral;
		public Numeral[] PLU_numeral2;
		public Numeral[] PLU_numeral3;
	
		public BarcodedProduct bProduct;
		public BarcodedProduct bProduct2;
		public BarcodedProduct bProduct3;
		public BarcodedProduct nullBProduct;
		public PLUCodedProduct  pProduct;
		public PLUCodedProduct  pProduct2;
		public PLUCodedProduct pProduct3;
		public PLUCodedProduct nullPLUProduct;
		
		@Before public void setUp() {
			PowerGrid.engageUninterruptiblePowerSource();
			PowerGrid.instance().forcePowerRestore();			
			AbstractSelfCheckoutStation.resetConfigurationToDefaults();
			this.station = new SelfCheckoutStationBronze();
			
			//Setting Up Barcoded Products
			barcode_numeral = new Numeral[] {Numeral.one, Numeral.two, Numeral.three};
			barcode_numeral2 = new Numeral[] {Numeral.three, Numeral.two, Numeral.three};
			barcode_numeral3 = new Numeral[] {Numeral.three, Numeral.three, Numeral.three};
			barcode = new Barcode(barcode_numeral);
			barcode2 = new Barcode(barcode_numeral2);
			barcode3 = new Barcode(barcode_numeral3);	
			bProduct = new BarcodedProduct(barcode, "some item",(long)5.99,(double)400.0);
			bProduct2 = new BarcodedProduct(barcode2, "some item 2",(long)1.00,(double)300.0);
			
			//Setting up PLUcoded Products
			PLUcode = new PriceLookUpCode("1111");
			PLUcode2 = new PriceLookUpCode("2222");
			PLUcode3 = new PriceLookUpCode("5555");
			pProduct = new PLUCodedProduct(PLUcode, "Apples", 2);
			pProduct2 = new PLUCodedProduct(PLUcode2, "Bananas", 3);
			pProduct3 = new PLUCodedProduct (PLUcode3, "Oranges", 4);
			
            // Populating Databases
			ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
			ProductDatabases.INVENTORY.clear();			
			ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, bProduct);
			ProductDatabases.INVENTORY.put(bProduct, 1);
			ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode2, bProduct2);
			ProductDatabases.INVENTORY.put(bProduct2, 1);	
			
			ProductDatabases.PLU_PRODUCT_DATABASE.clear();
			ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUcode, pProduct);
			ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUcode2, pProduct2);
			ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUcode3, pProduct3);			
			ProductDatabases.INVENTORY.put(pProduct, 10);
			ProductDatabases.INVENTORY.put(pProduct2, 10);
			ProductDatabases.INVENTORY.put(pProduct3, 10);

       // Setting up Items
			bItemMass = new Mass((double) 400.0);
			bitem = new BarcodedItem(barcode, bItemMass);
			bItemMass2 = new Mass((double) 300.0);//300.0 grams
			bitem2 = new BarcodedItem(barcode2, bItemMass2);
			bItemMass3 = new Mass((double) 300.0);//3.0 grams
			bitem3 = new BarcodedItem(barcode3, bItemMass3);
			//bitem4 = new BarcodedItem(b_test, itemMass3);
			bItemMass5 = new Mass((double) 300.0);
			bitem5 = new BarcodedItem(barcode2,bItemMass5);
			
			pItemMass = new Mass(1000000000);// 1kg
			pItem = new PLUCodedItem(PLUcode, pItemMass);
			pItemMass1 = new Mass(2000000000); // 2kg
			pItem2 = new PLUCodedItem(PLUcode2, pItemMass1);
			
			
			//Initialize station
			station.plugIn(PowerGrid.instance());
			station.turnOn();	
			session = new CentralStationLogic(station);
			session.startSession();
		}
		@Rule
		public TestName name = new TestName();
		
		@After
		public void displayTest() {
			System.out.println(name.getMethodName() + " has completed. \n");
		}
		
		//the following function was taken mainly from Angelina's tests for bulkyitems
		public void scanUntilAdded(Product p, BarcodedItem b) {
			int cnt = 0;
			
			while(cnt< 10000 && !session.cartLogic.getCart().containsKey(p)) {
				station.getHandheldScanner().scan(b);
				cnt++;
			}
		}
		
		/** Tests if the method actually removes a Barcoded item from the cart when called
		 * 
		 */
		@Test
		public void testSuccessfulRemovalBarcodedItem() {
			this.scanUntilAdded(bProduct, bitem);
			assertTrue(session.cartLogic.getCart().size() == 1);			
			station.getBaggingArea().addAnItem(bitem);			
			session.removeItemLogic.removeBarcodedItem(bProduct);
			assertEquals(0, session.cartLogic.getCart().size());			
			
		}
		/**Tests if the method actually removes a PLUcoded item from cart when called
		 * 
		 */
		@Test
		public void testSuccesfulRemovalPLUCodedItem() {
		   station.getBaggingArea().addAnItem(pItem); 
		   session.cartLogic.addPLUCodedProductToCart(PLUcode);
		   session.weightLogic.addExpectedWeight(PLUcode);
		   assertTrue(session.cartLogic.getCart().size() == 1);		   
		   session.removeItemLogic.removePLUCodedItem(pProduct);
		   assertEquals(0, session.cartLogic.getCart().size());
		}
		
				
		
		/** Tests if the station is blocked while the correct Barcoded item isn't removed, and unblocked when it is removed
		 * 
		 */
		@Test
		public void testPostRemovalBlockBarcode() {
			this.scanUntilAdded(bProduct, bitem);
			station.getBaggingArea().addAnItem(bitem);
			
			session.removeItemLogic.removeBarcodedItem(bProduct);
			assertTrue(session.stateLogic.getState() == States.BLOCKED);
			
			station.getBaggingArea().removeAnItem(bitem);
			assertTrue(session.stateLogic.getState() == States.NORMAL);
			
		}
		/** Tests if the station is blocked while the correct PLUcoded item isn't removed, and unblocked when it is removed
		 * 
		 */
		@Test
		public void testPostRemovalBlockPLU() {
			station.getBaggingArea().addAnItem(pItem);
			session.cartLogic.addPLUCodedProductToCart(PLUcode);
			session.weightLogic.addExpectedWeight(PLUcode);	
			
			session.removeItemLogic.removePLUCodedItem(pProduct);
			assertTrue(session.stateLogic.getState() == States.BLOCKED);
			
			station.getBaggingArea().removeAnItem(pItem);
			assertTrue(session.stateLogic.getState() == States.NORMAL);
			
		}
		
		
		
		
		
		/**
		 * Tests if removing the wrong item from bagging area causes a weight discrepancy
		 */
		@Test
		public void testIncorrectRemoval() {
			this.scanUntilAdded(bProduct, bitem);
			station.getBaggingArea().addAnItem(bitem);
					
			this.scanUntilAdded(bProduct2, bitem2);
			station.getBaggingArea().addAnItem(bitem2);
			
			session.removeItemLogic.removeBarcodedItem(bProduct);
			
			station.getBaggingArea().removeAnItem(bitem2);
			assertTrue(session.stateLogic.getState() == States.BLOCKED);
			
			station.getBaggingArea().addAnItem(bitem2);
			station.getBaggingArea().removeAnItem(bitem);
			assertTrue(session.stateLogic.getState() == States.NORMAL);	
			
		}	
		
		@Test //Might have a bug 
		public void testIncorrectRemovalPLU() {
						
			session.cartLogic.addPLUCodedProductToCart(PLUcode);	
			station.getBaggingArea().addAnItem(pItem);		
			session.weightLogic.addExpectedWeight(PLUcode);			
			station.getBaggingArea().addAnItem(bitem2);
		    session.cartLogic.addPLUCodedProductToCart(PLUcode2);
			session.weightLogic.addExpectedWeight(PLUcode2);		
			
			session.removeItemLogic.removePLUCodedItem(pProduct);
			station.getBaggingArea().removeAnItem(pItem2);
			assertTrue(session.stateLogic.getState() == States.BLOCKED);			
			
		}
		
		
		
		
		/**
		 * Tests if method fails on a null item.
		 */
		
		@Test (expected = NullPointerException.class)
		public void failOnBarcodeNullItem() {
			session.removeItemLogic.removeBarcodedItem(null);						
		}
		@Test (expected = NullPointerException.class)
		public void failOnPLUCodedNullItem() {
			session.removeItemLogic.removePLUCodedItem(null);				
		}
		
		/**
		 * Tests if method fails on removal request of an item not in cart.
		 */
		@Test (expected = InvalidStateSimulationException.class)
		public void failOnBarcodedItemNotInCart() {
			session.removeItemLogic.removeBarcodedItem(bProduct);
			}		 
		@Test (expected = InvalidStateSimulationException.class)
		public void failOnPLUCodedItemNotInCart() {
				session.removeItemLogic.removePLUCodedItem(pProduct);
		     }
		
		
		/**
		 * Tests if method can resolve a weight discrepancy event caused by not putting an added item into the bagging area
		 */
		@Test
		public void resolveWeightDescrepancyByRemovalBarcode() {
			this.scanUntilAdded(bProduct, bitem);
			assertTrue(session.stateLogic.getState() == States.BLOCKED);

			session.removeItemLogic.removeBarcodedItem(bProduct);
			assertTrue(session.stateLogic.getState() == States.NORMAL);	
			
			System.out.println("Test 6 end\n");
			
		}
		@Test // Needs more work
		public void resolveWeightDescrepencyByRemovalPLU() {
		 session.cartLogic.addPLUCodedProductToCart(PLUcode);		
		 assertTrue(session.stateLogic.getState() == States.BLOCKED);
		 session.removeItemLogic.removePLUCodedItem(pProduct);
		 assertTrue(session.stateLogic.getState() == States.NORMAL);				
		}
		
		/**
		 * Tests if the method fails when a session hasn't been started
		 */
		@Test (expected = InvalidStateSimulationException.class)
		public void failOnNullSessionBarcode() {
			session.stopSession();
			session.removeItemLogic.removeBarcodedItem(bProduct);
		}
		@Test (expected = InvalidStateSimulationException.class)
		public void failOnNullSessionPLU() {
			session.stopSession();
			session.removeItemLogic.removePLUCodedItem(pProduct);;
		}
		
}
