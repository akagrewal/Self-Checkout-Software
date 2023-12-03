package com.thelocalmarketplace.software.gui;

import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.software.logic.AttendantLogic;

public interface AttendantFrameListener {

    default void confirmed(AttendantLogic attendantLogic, GUILogic guiLogic) {}

    // blockType subject to change
    default void override(AttendantLogic attendantLogic, GUILogic guiLogic, String blockType) {}


}
