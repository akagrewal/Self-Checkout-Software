package com.thelocalmarketplace.software.controllers.item;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;
import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic;

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
public class AddPLUItemController extends AbstractLogicDependant {

    /**
     * AddPLUProductController Constructor
     * @param logic A reference to the logic instance
     * @throws NullPointerException If logic is null
     */
    public AddPLUItemController(CentralStationLogic logic) throws NullPointerException {
        super(logic);
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
    public void addPLU(PriceLookUpCode plu ) throws SimulationException, NullPointerException {
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
        System.out.println("Item added to cart. Please place scanned item in bagging area");
    }


}


}
