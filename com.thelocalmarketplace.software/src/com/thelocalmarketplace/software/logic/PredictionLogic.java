package com.thelocalmarketplace.software.logic;

import com.thelocalmarketplace.software.AbstractLogicDependant;

/**
 * Handles all logical operations regarding money 
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

public class PredictionLogic  extends AbstractLogicDependant {
	
	
	 public PredictionLogic(CentralStationLogic logic) throws NullPointerException {
	        super(logic);
	        
	 }
	 
	/**
	 * Predict issue with low banknotes
	 * Assuming the store is small, low bank notes would be considered less than 50 banknotes
	 */
	 public boolean PredictLowBanknotes() {
		 
		 var currentBankNotes = logic.hardware.getBanknoteStorage().getBanknoteCount();
		 
		 if (currentBankNotes <= 50) {
			 
			 //notify attendant and disable customer station
			 predictionAction("WARNING: Banknote storage is almost empty.");
			 
			 return true;
			 
		 }
		 
		 return false;
		 
		 
	 }
	 
	/**
	 * Predict issue with full banknotes
	 */
	 public boolean PredictFullBanknotes() {
		 
		 var currentBankNotes = logic.hardware.getBanknoteStorage().getBanknoteCount();
		 var capacity = logic.hardware.getBanknoteStorage().getCapacity();
		 
		 if ((capacity - currentBankNotes) <= 50) {
			 
			 //notify attendant and disable customer station
			 predictionAction("WARNING: Banknote storage is almost full.");
		
		
		
			 return true;
			 
		 }
		 
		 return false;
		 
	 }
	 
	 
	 //method to turn off customer station and notify attendant with custom message
	 public void predictionAction(String message) {
	    logic.hardware.turnOff();
	    logic.attendantLogic.notifyAttendant(message);
	 }
	 
}
