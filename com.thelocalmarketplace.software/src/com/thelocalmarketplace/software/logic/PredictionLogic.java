package com.thelocalmarketplace.software.logic;

import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;

public class PredictionLogic extends AbstractLogicDependant {
	
	public Boolean checkLowCoinPrediction() {
		if (logic.hardware.getCoinStorage().getCoinCount() <= 10) {
			predictionAction("WARING: Low Coins!");
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean checkCoinsFullPrediction() {
		if (logic.hardware.getCoinStorage().getCoinCount() <= logic.hardware.getCoinStorage().getCapacity() - 10) {
			predictionAction("WARING: Coins Full!");
			return false;
		} else {
			return true;
		}
	}
	
	public void predictionAction(String message) {
		logic.hardware.turnOff();
		logic.attendantLogic.notifyAttendant(message);
	}
	
	public PredictionLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}

		
}