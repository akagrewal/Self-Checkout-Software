package com.thelocalmarketplace.software.controllers;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

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
public class WeightDiscrepancyController extends AbstractLogicDependant implements ElectronicScaleListener {
	
	/**
	 * WeightDiscrepancyController Constructor
	 * @param logic A reference to the logic instance
	 * @throws NullPointerException If logic is null
	 **/
	public WeightDiscrepancyController(CentralStationLogic logic) throws NullPointerException {
		super(logic);
		
		// Register self to bagging area scale
		this.logic.hardware.getBaggingArea().register(this);
	}
	
	/** Triggered when mass on bagging area scale is changed
	 * If a weight discrepancy is detected, system is updated accordingly */
	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
		
		// Weight discrepancies are ignored when in ADDBAGS state
		if (!this.logic.stateLogic.inState(States.ADDBAGS)) {
			this.logic.weightLogic.updateActualWeight(mass);
			this.logic.weightLogic.handleWeightDiscrepancy();	
		} else {
			
			// The actual mass now is whatever was on the scale before this change
			Mass one_bag = mass.difference(this.logic.weightLogic.getActualWeight()).abs();
			
			if (mass.compareTo(this.logic.weightLogic.getActualWeight()) > 0) {
				
				// Add the bag to the bag mass
				this.logic.weightLogic.updateTotalBagMass(this.logic.weightLogic.getTotalBagMass().sum(one_bag));

			} else {
				
				// Remove the bag from the bag mass
				this.logic.weightLogic.updateTotalBagMass(this.logic.weightLogic.getTotalBagMass().difference(one_bag).abs());
			}
			
			// Update actual weight of the scale
			this.logic.weightLogic.updateActualWeight(mass);
		}
	}
	
	/** Triggered when actual weight is over expected weight */
	public void notifyOverload() {
		System.out.println("Weight discrepancy detected. Please remove item(s)");
	}
	
	/** Triggered when actual weight is under expected weight */
	public void notifyUnderload() {
		System.out.println("Weight discrepancy detected. Please add item(s)");
	}
	
	@Override
	public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {
		this.logic.weightLogic.scaleOperational = false;
	}

	@Override
	public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {
		this.logic.weightLogic.scaleOperational = true;
	}
	
	// ---- Unused -----

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