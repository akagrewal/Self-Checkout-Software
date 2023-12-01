package com.thelocalmarketplace.software.logic;

import com.thelocalmarketplace.software.AbstractLogicDependant;

public class PredictionLogic extends AbstractLogicDependant {

	public PredictionLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}

	public boolean checkPaperPrediction() {
		int currentLinesRemaining = logic.hardware.getPrinter().paperRemaining();
		boolean isLow = false;
		
		// assume that 100 lines is required for a receipt, any less would constitute a low paper warning.
		if (currentLinesRemaining <= 100) {
			// low paper!!
			// predictionAction("WARNING: Low Paper!") (notify attendant)
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
			// predictionAction("WARNING: Low Ink!") (notify attendant)
			isLow = true;
		}
		return isLow;
	}
}
