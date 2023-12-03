package com.thelocalmarketplace.software.logic;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;

/**
 * Logic class for the remove item use case functionality. Adapted from AddBarcodedItemTests
 * 
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
public class RemoveItemLogic extends AbstractLogicDependant{
	
	/**
	 * Base constructor
	 * @param logic Reference to central station logic
	 * @throws NullPointerException If logic is null
	 */
	public RemoveItemLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}
	
	/**
	 * Removes a barcoded item from the cart and updates states and expected weight.
	 * @param product - barcoded product to be removed
	 * @throws NullPointerException
	 *  Removal of other types of items added in next iteration
	 */
	public void removeBarcodedItem(BarcodedProduct product) throws NullPointerException{
    	if (product == null) {
            throw new NullPointerException("Barcode is null");
        }
    	
    	else if (!this.logic.isSessionStarted()) {
    		throw new InvalidStateSimulationException("The session has not been started");
    	}
    	
    	// When method is used to resolve weight discrepancies
    	else if (this.logic.stateLogic.inState(States.BLOCKED)) {
	    	this.logic.cartLogic.removeProductFromCart(product);
	    	this.logic.weightLogic.removeExpectedWeight(product.getBarcode());
	    	System.out.println("Item removed from cart.");
	    	logic.weightLogic.handleWeightDiscrepancy();
    	}
    	
    	// When method is used to remove unwanted items (without triggering a weight discrepancy
    	else {
    		this.logic.cartLogic.removeProductFromCart(product);
	    	this.logic.weightLogic.removeExpectedWeight(product.getBarcode());
	    	this.logic.stateLogic.gotoState(States.BLOCKED);
	    	System.out.println("Item removed from cart. Please remove the item from the bagging area");
    	}	}
    	
    public void removePLUCodedItem(PLUCodedProduct product)throws NullPointerException {
    	if (product == null) {
            throw new NullPointerException("PLU Code is null");
        }
    	else if (!this.logic.isSessionStarted()) {
    		throw new InvalidStateSimulationException("The session has not been started");
    	}
    	// When method is used to resolve weight discrepancies
    	else if (this.logic.stateLogic.inState(States.BLOCKED)) {
	    	this.logic.cartLogic.removeProductFromCart(product);
	    	this.logic.weightLogic.removeExpectedWeight(product.getPLUCode());
	    	System.out.println("Item removed from cart.");
	    	logic.weightLogic.handleWeightDiscrepancy();
    	}
    	// When method is used to remove unwanted items (without triggering a weight discrepancy
    	else {
    		this.logic.cartLogic.removeProductFromCart(product);
    		this.logic.weightLogic.removeExpectedWeight(product.getPLUCode());
	    	this.logic.stateLogic.gotoState(States.BLOCKED);
	    	System.out.println("Item removed from cart. Please remove the item from the bagging area");
    	}	    }
	
	
	
}
