package com.thelocalmarketplace.software.logic;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;

/**
 * Add Own Bags (Logic)
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
public class AddBagsLogic extends AbstractLogicDependant {
	
	/** Mass limit for bags when customer is adding own bags */
	public Mass bagWeightLimit = new Mass((double)20);
	
	/** tracks weather or not the attendant has approved the current bagging area*/
	public boolean approvedBagging;
	
	public AddBagsLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}
	
	/**Handles start of bagging state
	 * Notifies customer to place bags on scale
	 * Enables bagging area and disables scanners 
	 * @throws InvalidStateSimulationException if called when CentralStationLogic is not in ADDINGBAGS state 
	 * @throws InvalidStateSimulationException if session is not started */
	public void startAddBags() {
		//TODO GUI: please place bags on scale
		if (!logic.isSessionStarted()) throw new InvalidStateSimulationException("Session has not started");
		
		this.logic.stateLogic.gotoState(States.ADDBAGS);
		
		System.out.println("Please place bags on the scale");
		
	}
	
	
	/**When user has finished adding their bags to the scale
	 * Requires attendant verification if bags are too heavy (indicated by this.approvedBagging = true)
	 * Sets expected weight to the current weight of the bags on the scale 
	 * @throws Exception - when bags are too heavy 
	 * @throws InvalidStateSimulationException if called when CentralStationLogic is not in ADDINGBAGS state 
	 * @throws InvalidStateSimulationException if session is not started */
	public void endAddBags() {
		if (!logic.isSessionStarted()) throw new InvalidStateSimulationException("Session has not started");
		if (!logic.stateLogic.inState(States.ADDBAGS)) throw new InvalidStateSimulationException("Cannot end ADDBAGS state when not in ADDBAGS state");
		if (logic.weightLogic.getTotalBagMass().compareTo(bagWeightLimit) < 0 || this.approvedBagging) {
			// If bag weight is under the allowed weight
			this.logic.weightLogic.overrideDiscrepancy();
			this.approvedBagging = false;
			this.logic.attendantLogic.setBaggingDiscrepency(false);
			this.logic.stateLogic.gotoState(States.NORMAL);
		} else {
			//bags are too heavy 
			//TODO GUI: display waiting for attendant approval
			this.logic.attendantLogic.baggingDiscrepencyDetected();	
		}
	}

}
