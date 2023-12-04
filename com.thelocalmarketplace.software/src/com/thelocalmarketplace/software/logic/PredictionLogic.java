package com.thelocalmarketplace.software.logic;

import com.thelocalmarketplace.software.AbstractLogicDependant;

public class PredictionLogic extends AbstractLogicDependant {
	
	public PredictionLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}	
	
	public Boolean checkLowCoinPrediction() {
		if (logic.hardware.getCoinStorage().getCoinCount() <= 100) {
			predictionAction("Warning: Low Coins!");
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean checkCoinsFullPrediction() {
		if (logic.hardware.getCoinStorage().getCoinCount() <= logic.hardware.getCoinStorage().getCapacity() - 100) {
			predictionAction("Warning: Coins Full!");
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkPaperPrediction() {
        int currentLinesRemaining = logic.hardware.getPrinter().paperRemaining();
        boolean isLow = false;
        
        // Warning is triggered at 175 lines remaining, should permit for 1-2 receipts before empty.
        if (currentLinesRemaining <= 175) {
            // low paper!!
            predictionAction("Warning: Low Paper!");
            isLow = true;
        }
        
        return isLow;
    }
    
    public boolean checkInkPrediction() {
        int currentInkRemaining = logic.hardware.getPrinter().inkRemaining();
        boolean isLow = false;

        // Warning is made when 20 units of ink remain in the machine, should permit for 1-2 more receipts before empty.
        if (currentInkRemaining <= 20) {       	
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
	 
    	var currentBankNotes = logic.hardware.getBanknoteStorage().getBanknoteCount();
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
		// need to notify attendant.
		logic.attendantLogic.disableCustomerStation(); 
	}	
}
