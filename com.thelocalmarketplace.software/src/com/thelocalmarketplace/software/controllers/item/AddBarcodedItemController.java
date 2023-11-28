package com.thelocalmarketplace.software.controllers.item;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;

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
public class AddBarcodedItemController extends AbstractLogicDependant implements BarcodeScannerListener {    
    
    /**
     * AddBarcodedProductController Constructor
     * @param logic A reference to the logic instance
     * @throws NullPointerException If logic is null
     */
    public AddBarcodedItemController(CentralStationLogic logic) throws NullPointerException {
    	super(logic);
        
        // Register self to main and hand held barcode scanners
        this.logic.hardware.getMainScanner().register(this);
        this.logic.hardware.getHandheldScanner().register(this);
    }
    
    /**
     * Adds a new barcode
     * If a weight discrepancy is detected, then station is blocked
     * @param barcodedItem The item to be scanned and added
     * @throws SimulationException If session not started
     * @throws SimulationException If station is blocked
     * @throws SimulationException If barcode is not registered in database
     * @throws NullPointerException If barcode is null
     */
    public void addBarcode(Barcode barcode) throws SimulationException, NullPointerException {
    	if (barcode == null) {
            throw new NullPointerException("Barcode is null");
        }
    	else if (!this.logic.isSessionStarted()) {
    		throw new InvalidStateSimulationException("The session has not been started");
    	}
    	else if (this.logic.stateLogic.inState(States.BLOCKED)) {
    		throw new InvalidStateSimulationException("Station is blocked");
    	}
    	
    	this.logic.cartLogic.addBarcodedProductToCart(barcode);
    	this.logic.weightLogic.addExpectedWeight(barcode);
    	
		this.logic.stateLogic.gotoState(States.BLOCKED);
		System.out.println("Item added to cart. Please place scanned item in bagging area");
    }
    
    @Override
	public void aBarcodeHasBeenScanned(IBarcodeScanner barcodeScanner, Barcode barcode) throws SimulationException, NullPointerException {
    	System.out.println("A barcoded item has been scanned");
    	
    	this.addBarcode(barcode);
    	
    	System.out.println("Place item in bagging area");
	}
    
    // ---- Unused ----

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}
}