package com.thelocalmarketplace.software.logic;

import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.software.gui.*;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;

import java.util.HashSet;
import java.util.Set;

import static com.thelocalmarketplace.software.gui.SessionBlockedPopUp.*;

/**
 * Attendant Logic
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
public class AttendantLogic implements GUIListener{
	/** tracks the station logic being monitored */
	private CentralStationLogic logic;
	
	/** tracks weather or not a bagging discrepency has been found */
	private boolean inBaggingDiscrepency;
	
	private boolean waitingToDisable = false;

// listener stuff
private final Set<AttendantFrameListener> listeners = new HashSet<>();

	/**
	 * Registers the given listener so that it will receive events from this
	 * communication facade.
	 *
	 * @param listener The listener to be registered. If it is already registered, this
	 *                 call has no effect.
	 */
	public void register(AttendantFrameListener listener) {
		listeners.add(listener);
	}

	// LOGIC PANEL
	// add logic for when attendant confirms call from customer
	private void confirmCall(GUILogic guiLogic) {
		for (AttendantFrameListener listener : listeners)
			listener.confirmed(this, guiLogic);
	}

	// add logic for when attendant overrides block on specific station
	private void override(GUILogic guiLogic, String blockType) {
		for (AttendantFrameListener listener : listeners)
			listener.override(this, guiLogic, blockType);
	}
// listener end

	public AttendantLogic(CentralStationLogic l) {
		this.logic = l;
	}



	
	/** simulates attendant signifying they approve the bagging area 
	 * @throws Exception if the add bags state cannot be exited*/
	public void approveBaggingArea() throws Exception {
		this.logic.weightLogic.overrideDiscrepancy();
		this.logic.addBagsLogic.approvedBagging = true;
		this.logic.addBagsLogic.endAddBags();
	}
	
	/** simulates attendant being notified that a bagging discrepancy has occurred 
	 * The only way for customer to transition out of ADDBAGS state is for attendant to call
	 * approveBaggingArea() */
	public void baggingDiscrepencyDetected() {
		//TODO GUI: display that customer is awaiting approval to attendant
		discrepancyDetected();

		this.inBaggingDiscrepency = true;
		this.logic.stateLogic.gotoState(States.BLOCKED);
		if(this.logic.addBagsLogic.approvedBagging) {
			this.logic.weightLogic.overrideDiscrepancy();
			this.logic.stateLogic.gotoState(States.NORMAL);
		}
	}
	
	/** setter for in baggingDiscrepency */
	
	public void setBaggingDiscrepency(boolean b){
		this.inBaggingDiscrepency = b;
	}
	
/** getter for in baggingDiscrepency for testing*/
	
	public boolean getBaggingDiscrepency() {
		return this.inBaggingDiscrepency;
	}
	
	/** Notifies attendant of request to skip bagging a particular product with given barcode 
	 * @param barcode - barcode of item the customer is requesting to skip bagging 
	 * @throws InvalidArgumentSimulationException if barcode is null */
	public void requestApprovalSkipBagging(Barcode barcode) {
		if (barcode == null) throw new InvalidArgumentSimulationException("Cannot skip bagging with null barcode");
		if (!this.logic.stateLogic.inState(States.BLOCKED)) throw new InvalidStateSimulationException("Skip bagging request should only occur in a blocked state");
		///TODO GUI: set alert for customer to wait for attendant approval
	}
	
	/** Attendant approval to skip bagging of item with specified barcode
	 * removes the expected weight of the requested barcode
	 * unblocks station only if no weight discrepancy remains after expected weight is removed
	 * @param barcode - barcode of item that the attendant is approving to skip bagging 
	 * @throws InvalidArgumentSimulationException if barcode is null */
	public void grantApprovalSkipBagging(Barcode barcode) {
		if (barcode == null) throw new InvalidArgumentSimulationException("Cannot skip bagging with null barcode");
		logic.weightLogic.removeExpectedWeight(barcode);
		logic.weightLogic.handleWeightDiscrepancy();
	}
	
	public void printDuplicateReceipt() {
		this.logic.receiptPrintingController.printDuplicateReceipt();
	}
	
	/**
	 * Notify the attendant their aid is needed
	 */
	@ Override
	public void attendantCalled(GUILogic guiLogic) {
    	// set visible (or open) NotifyPopUp
		NotifyPopUp notify = new NotifyPopUp();
        notify.notifyPopUp();
    }
	
	/** Method to notify the attendant station that the current session has ended */
	public void notifySessionEnded() {
		if (waitingToDisable) {
			disableCustomerStation();
			waitingToDisable = false;
		}
	}
	
	/** Method to disable a customer station for maintenance and display out of order */
	public void disableCustomerStation() {
		//TODO: change the logic do be able to disable only a specific customer station
		//TODO GUI: GUI should display out of order when disabled for maintenance
		
		if (!logic.isSessionStarted()) {
			// once the station is out of the session
			logic.stateLogic.gotoState(States.OUTOFORDER);
			
			outOfOrder();
		} else {
			waitingToDisable = true;
		}
		
	}
	
	/** Method to take a customer station out of maintenance mode */
	public void enableCustomerStation() {
		//TODO: change the logic do be able to enable only a specific customer station


		//TODO GUI: GUI should go back to normal if it was previously disabled
		attendantOverride();
		
		logic.stateLogic.gotoState(States.NORMAL);
	}
}
