package com.thelocalmarketplace.software.gui;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.software.logic.AttendantLogic;

public interface GUIListener {

    default void added(GUILogic GUILogic, PLUCodedProduct pk) {}

    default void scanned(GUILogic GUILogic, BarcodedProduct pk) {}
    
    default void ownBags(GUILogic GUILogic, boolean ownBags) {
        // ownBags = true means they want to use their own bags
        // ownBags = false means they want to buy store bags
    }

    default void attendantCalled(GUILogic guiLogic) {}

    default void memberLogin(GUILogic guiLogic, String id) {}

    default void payOption(GUILogic guiLogic, int payOption) {
        // for this method, 1 = cash/coins, 2 = credit, 3 = debit, 4 = mixed (subject to change)
    }

// HARDWARE FUNCTIONS
    default void itemAddedToScale(GUILogic guiLogic, Mass weight) {}

    default void itemRemovedFromScale(GUILogic guiLogic, Mass weight) {}

    default void paidWithCredit(GUILogic guiLogic, int cardNumber) {}

    default void paidWithDebit(GUILogic guiLogic, int cardNumber) {}

    default void coinInserted(GUILogic guiLogic, int coinAmount) {}

    default void banknoteInserted(GUILogic guiLogic, int banknoteAmount) {}
}
