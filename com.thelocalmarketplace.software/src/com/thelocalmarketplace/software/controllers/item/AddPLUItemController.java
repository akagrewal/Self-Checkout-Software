package com.thelocalmarketplace.software.controllers.item;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;
import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic;



public class AddPLUItemController extends AbstractLogicDependant{

    /**
     * AddPLUProductController Constructor
     * @param logic A reference to the logic instance
     * @throws NullPointerException If logic is null
     */
    public AddPLUItemController(CentralStationLogic logic) throws NullPointerException {
        super(logic);        
        // registers to the bagging area as a listener       
    }
   

    /**
     * Adds a new PLU
     * If a weight discrepancy is detected, then station is blocked
     * @param plu The item to be added
     * @throws SimulationException If session not started
     * @throws SimulationException If station is blocked
     * @throws SimulationException If barcode is not registered in database
     * @throws NullPointerException If barcode is null
     */
    public void addPLU(PriceLookUpCode plu) throws SimulationException, NullPointerException {
        if (plu == null) {
            throw new NullPointerException("PLU is null");
        }
        else if (!this.logic.isSessionStarted()) {
            throw new InvalidStateSimulationException("The session has not been started");
        }
        else if (this.logic.stateLogic.inState(StateLogic.States.BLOCKED)) {
            throw new InvalidStateSimulationException("Station is blocked");
        }
        
        // Add product to cart first so when on scale the actual and expected weight are different in weightLogic to determine the weight
        // of the product and get its price to add to cart
        this.logic.cartLogic.addPLUCodedProductToCart(plu);
        // after added to cart, change the expected weight to the actual weight to account for the plu product
        this.logic.weightLogic.addExpectedWeight(plu);

        this.logic.stateLogic.gotoState(StateLogic.States.BLOCKED);
        logic.guiLogic.showInfoMessage("Item added to cart. Please place scanned item in bagging area");
        System.out.println("Item added to cart. Please place scanned item in bagging area");
    }


	

}


