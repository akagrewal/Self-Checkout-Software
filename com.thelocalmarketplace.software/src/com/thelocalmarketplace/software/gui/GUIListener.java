package com.thelocalmarketplace.software.gui;

import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;

public interface GUIListener {

    default public void added(GUILogic GUILogic, PLUCodedProduct pk) {}

    default public void scanned(GUILogic GUILogic, BarcodedProduct pk) {}
}
