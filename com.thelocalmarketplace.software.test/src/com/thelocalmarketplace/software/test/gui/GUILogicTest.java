package com.thelocalmarketplace.software.test.gui;

import static org.junit.Assert.*;

import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.gui.GUIListener;
import com.thelocalmarketplace.software.gui.GUILogic;
import org.junit.Before;
import org.junit.Test;

public class GUILogicTest {

    private GUILogic guiLogic;
    private GUIListenerMock listener;

    @Before
    public void setUp() {
        guiLogic = new GUILogic();
        // Mock a GUIListener
        listener = new GUIListenerMock();
        guiLogic.register(listener);
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
        int oldTotal = guiLogic.getTotal();

        // TODO: logic will need to be changed to reflect new GUI buttons
        guiLogic.buttonR7_CustomerAddsItem_PLUCode();

        // check if the listener's method was called
        assertTrue(listener.addedCalled);

        // TODO: check if total amount due was updated
        assertTrue(guiLogic.getTotal() > oldTotal);
    }

    @Test
    public void testCustomerScansBarcodedProduct() {
        int oldTotal = guiLogic.getTotal();

        // TODO: logic will need to be changed to reflect new GUI buttons
        guiLogic.buttonB1_CustomerScansBarcodedProduct_MainScanner();

        // check if the listener's method was called
        assertTrue(listener.scannedCalled);

        // TODO: check if total amount due was updated (might need to be done differently)
        assertTrue(guiLogic.getTotal() > oldTotal);
    }

    @Test
    public void testCustomerAddsOwnBags() {
        // TODO: logic will need to be changed to reflect add bag button (it doesnt exist yet)
        // guiLogic.(BAG BUTTON TO BE PRESSED)

        // check if the listener's method was called
        assertTrue(listener.ownBagsCalled);

        // may need to have a second step confirming that bags have been added? depends on the design by GUI designers
        // if so then it would be assertTrue(someMethodThatChecksIfBagsHaveBeenAdded);
    }

    @Test
    public void testCustomerCallsAttendant() {
        // TODO: logic will need to be changed to reflect call attendant button (it doesnt exist yet)
        // guiLogic.(CALL ATTENDANT BUTTON TO BE PRESSED)

        // check if the listener's method was called
        assertTrue(listener.attendantCalled);
    }

    @Test
    public void testCustomerAddsMembershipDetails() {
        // TODO: logic will need to be changed to reflect add membership button (it doesnt exist yet)
        // guiLogic.(ADD MEMBERSHIP BUTTON TO BE PRESSED)

        // check if the listener's method was called
        assertTrue(listener.membershipAdded);

        // some checks to see if membership is valid/acceptable? not sure if that needs to be implemented at this stage
    }

    @Test
    public void testCustomerSelectsPaymentOptionPhysical() {
        // TODO: logic will need to be changed to reflect payment option buttons (they dont exist yet)
        // guiLogic.(PAY WITH CASH/BILLS OPTION BUTTON TO BE PRESSED)

        // check if the listener's method was called correctly
        assertEquals(1, listener.paymentOptionSelected);
    }

    @Test
    public void testCustomerSelectsPaymentOptionCredit() {
        // TODO: logic will need to be changed to reflect payment option buttons (they dont exist yet)
        // guiLogic.(PAY BY CREDIT OPTION BUTTON TO BE PRESSED)

        // check if the listener's method was called correctly
        assertEquals(2, listener.paymentOptionSelected);
    }

    @Test
    public void testCustomerSelectsPaymentOptionDebit() {
        // TODO: logic will need to be changed to reflect payment option buttons (they dont exist yet)
        // guiLogic.(PAY BY DEBIT OPTION BUTTON TO BE PRESSED)

        // check if the listener's method was called correctly
        assertEquals(3, listener.paymentOptionSelected);
    }

    @Test
    public void testCustomerSelectsPaymentOptionMixed() {
        // TODO: logic will need to be changed to reflect payment option buttons (they dont exist yet)
        // guiLogic.(PAY WITH MIXED OPTION BUTTON TO BE PRESSED)

        // check if the listener's method was called correctly
        assertEquals(4, listener.paymentOptionSelected);
    }

    // Helper class for testing GUIListener methods
    private static class GUIListenerMock implements GUIListener {
        boolean addedCalled = false;
        boolean scannedCalled = false;
        boolean ownBagsCalled = false;
        boolean attendantCalled = false;
        boolean membershipAdded = false;
        int paymentOptionSelected = 0;

        @Override
        public void added(GUILogic GUILogic, PLUCodedProduct pk) {
            addedCalled = true;
        }

        @Override
        public void scanned(GUILogic GUILogic, BarcodedProduct pk) {
            scannedCalled = true;
        }

        @Override
        public void ownBags(GUILogic GUILogic, boolean ownBags) {
            ownBagsCalled = true;
        }

        @Override
        public void attendantCalled(GUILogic guiLogic) {
            attendantCalled = true;
        }

        @Override
        public void memberLogin(GUILogic guiLogic, String id) {
            membershipAdded = true;
        }

        @Override
        public void payOption(GUILogic guiLogic, int payOption) {
            paymentOptionSelected = payOption;
        }


        // Implement other GUIListener methods if needed...
    }
}
