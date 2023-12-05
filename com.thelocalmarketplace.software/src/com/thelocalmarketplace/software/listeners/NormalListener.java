package com.thelocalmarketplace.software.listeners;

import com.thelocalmarketplace.software.AbstractStateTransitionListener;
import com.thelocalmarketplace.software.logic.CentralStationLogic;



public class NormalListener extends AbstractStateTransitionListener {

	public NormalListener(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}

	@Override
	public void onTransition() {
		this.logic.hardware.getBaggingArea().enable();
		
		this.logic.hardware.getHandheldScanner().enable();
		this.logic.hardware.getMainScanner().enable();
		
		this.logic.hardware.getCoinSlot().enable();
		this.logic.hardware.getCoinValidator().enable();
		
		this.logic.hardware.getBanknoteInput().enable();
		this.logic.hardware.getBanknoteValidator().enable();
	}
}
