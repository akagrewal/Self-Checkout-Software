package com.thelocalmarketplace.software.gui;

import com.jjjwelectronics.Item;
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
    default void itemAddedToScale(GUILogic guiLogic, Item pkm) {}

    default void itemRemovedFromScale(GUILogic guiLogic, Item pkm) {}

    default void paidWithCredit(GUILogic guiLogic, String cardDetails) {}

    default void paidWithDebit(GUILogic guiLogic, String cardDetails) {}

    default void coinInserted(GUILogic guiLogic, int coinAmount) {}

    default void banknoteInserted(GUILogic guiLogic, int banknoteAmount) {}
}
