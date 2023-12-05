/** SENG300 Group Project
 * (Adapted from Project Iteration 2 - Group 5)
 *
 * Iteration 3 - Group 3
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


package com.thelocalmarketplace.software.logic;

import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.gui.AttendantPopups;
import com.thelocalmarketplace.software.gui.SessionBlockedPopUp;

public class PredictionLogic extends AbstractLogicDependant {
	final int MAXIMUM_PAPER = 1 << 10;
	final int MAXIMUM_INK = 1 << 20;
	private final CentralStationLogic logic;
	
	public PredictionLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
		this.logic = logic;
	}	
	
	public void runPredictions() {	// should be ran at the start/end of a session.	
		checkCoinsFullPrediction();
		checkLowCoinPrediction();
		PredictFullBanknotes();
		PredictLowBanknotes();
		checkInkPrediction();
		checkPaperPrediction();
	}
	
	public Boolean checkLowCoinPrediction() {
		var currentCoins = logic.hardware.getCoinStorage().getCoinCount();
		var capacity = logic.hardware.getCoinStorage().getCapacity();
    	var minCapacity = capacity * .15;
    	
      	if (currentCoins <= minCapacity) {
   		
    		//notify attendant and disable customer station
    		predictionAction("Warning: Coin storage is almost empty.");
		 
    		return true;
    		
    	}
    	return false;
	}
	
	public Boolean checkCoinsFullPrediction() {
		var currentCoins = logic.hardware.getCoinStorage().getCoinCount();
		var capacity = logic.hardware.getCoinStorage().getCapacity();
    	var maxCapacity = capacity * .75;
    	

      	if (currentCoins >= maxCapacity) {
   		
    		//notify attendant and disable customer station
    		predictionAction("Warning: Coin storage is almost full.");
		 
    		return true;
    		
    	}
    	return false;
	}
	
	public boolean checkPaperPrediction() {
        int currentLinesRemaining = 0;
        double lowPaperLevel = MAXIMUM_PAPER * 0.15;
        boolean isLow = false;
        
        try {
        	currentLinesRemaining = logic.hardware.getPrinter().paperRemaining();
        } catch (UnsupportedOperationException ex) {
        	// Bronze Printer, must be manually checked.
        	currentLinesRemaining = MAXIMUM_PAPER;
        }
        
        // Warning is triggered when 15% of the machines capacity is reached, should permit for 1-2 receipts before empty.
        if (currentLinesRemaining <= lowPaperLevel) {
            // low paper!!
            predictionAction("Warning: Low Paper!");
            isLow = true;
        }
        
        return isLow;
    }
    
    public boolean checkInkPrediction() {
        int currentInkRemaining = 0;
        double lowInkLevel = MAXIMUM_INK * 0.15;
        boolean isLow = false;

        try {
        	currentInkRemaining = logic.hardware.getPrinter().inkRemaining();
        } catch (UnsupportedOperationException e) {
        	// Bronze Printer, must be manually checked.
        	currentInkRemaining = MAXIMUM_INK;
        }
        
        // Warning is made when 15% of the machines capacity is reached, should permit for 1-2 more receipts before empty.
        if (currentInkRemaining <= lowInkLevel) {       	
            // low ink!!
            predictionAction("Warning: Low Ink!"); 
            isLow = true;
        } 
        return isLow;
    }
    
   /**
     * Predict issue with low banknotes
     * Prediction issues follow a range of 15% of capacity for both min and max 
     */
    public boolean PredictLowBanknotes() {
	 
    	var currentBankNotes = logic.hardware.getBanknoteStorage().getBanknoteCount();
    	var capacity = logic.hardware.getBanknoteStorage().getCapacity();
    	var minCapacity = capacity * .15;
	 
    	if (currentBankNotes <= minCapacity) {
		 
    		//notify attendant and disable customer station
    		predictionAction("Warning: Banknote storage is almost empty.");
		 
    		return true;
    		
    	}
    	return false;
    }
 
    /**
     * Predict issue with full banknotes
     * Prediction issues follow a range of 15% of capacity for both min and max 
     */
    public boolean PredictFullBanknotes() {
	 
    	var currentBankNotes = logic.getAvailableBanknotesInDispensers().size();
    	var capacity = logic.hardware.getBanknoteStorage().getCapacity();
    	var maxCapacity = capacity * .75;
	 
    	if (currentBankNotes >= maxCapacity) {
		 
    		//notify attendant and disable customer station
    		predictionAction("Warning: Banknote storage is almost full.");
	
	
    		return true;
		 
    	}
	 
    	return false;
	 
    }
	
	
	public void predictionAction(String message) {
		// notify attendant
		AttendantPopups attendantPopup = new AttendantPopups(logic.attendantLogic.attendantGUI.getAttendantFrame());
		attendantPopup.issuePredictedPopUp(message);
		// give 5s until station disabled
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// do nothing
		}
		// disable station
		logic.attendantLogic.disableCustomerStation(logic); 
	}	
}
