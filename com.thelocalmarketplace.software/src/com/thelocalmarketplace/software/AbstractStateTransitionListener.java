package com.thelocalmarketplace.software;

import com.thelocalmarketplace.software.logic.CentralStationLogic;

public abstract class AbstractStateTransitionListener extends AbstractLogicDependant {

	public AbstractStateTransitionListener(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}

	/**
	 * Triggered when a specific transition occurs
	 */
	public abstract void onTransition();
}
