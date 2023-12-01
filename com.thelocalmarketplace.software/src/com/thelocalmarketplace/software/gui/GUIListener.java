package com.thelocalmarketplace.software.gui;

import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;

public interface GUIListener {

    default void added(GUILogic GUILogic, PLUCodedProduct pk) {}

    default void scanned(GUILogic GUILogic, BarcodedProduct pk) {}
    
    default void ownBags(GUILogic GUILogic, boolean ownBags) {
        // ownBags = true means they want to use their own bags
        // ownBags = false means they want to buy store bags
    }

    // may need changes to this method
    default void changeLanguage(GUILogic GUILogic, String language) {}

    default void attendantCalled(GUILogic guiLogic) {}

    default void memberLogin(GUILogic guiLogic, String id) {}

    default void payOption(GUILogic guiLogic, int payOption) {
        // for this method, 1 = cash/coins, 2 = credit, 3 = debit, 4 = mixed (subject to change)
    }
}
