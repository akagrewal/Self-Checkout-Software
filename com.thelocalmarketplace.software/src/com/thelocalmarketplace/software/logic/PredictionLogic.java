package com.thelocalmarketplace.software.logic;

import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;

public class PredictionLogic extends AbstractLogicDependant {
	
	public Boolean checkLowCoinPrediction() {
		if (logic.hardware.getCoinStorage().getCoinCount() <= 100) {
			predictionAction("WARING: Low Coins!");
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean checkCoinsFullPrediction() {
		if (logic.hardware.getCoinStorage().getCoinCount() <= logic.hardware.getCoinStorage().getCapacity() - 100) {
			predictionAction("WARING: Coins Full!");
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkPaperPrediction() {
        int currentLinesRemaining = logic.hardware.getPrinter().paperRemaining();
        boolean isLow = false;
        
        // assume that 100 lines is required for a receipt, any less would constitute a low paper warning.
        if (currentLinesRemaining <= 100) {
            // low paper!!
            predictionAction("WARNING: Low Paper!");
            isLow = true;
        }
        
        return isLow;
    }
    
    public boolean checkInkPrediction() {
        int currentInkRemaining = logic.hardware.getPrinter().inkRemaining();
        boolean isLow = false;

        // assume that 100 chars is required for a receipt, any less would constitute a low ink warning.
        if (currentInkRemaining <= 20) {
            // low ink!!
            predictionAction("WARNING: Low Ink!"); 
            isLow = true;
        }
        return isLow;
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
	
	public void predictionAction(String message) {
		logic.hardware.turnOff();
		logic.attendantLogic.notifyAttendant(message);
	}
	
	public PredictionLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}		
}