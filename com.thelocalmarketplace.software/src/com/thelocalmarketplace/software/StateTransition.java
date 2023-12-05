package com.thelocalmarketplace.software;

import com.thelocalmarketplace.software.logic.StateLogic.States;

public class StateTransition {

	/**
	 * Initial state
	 */
	private States initial;
	
	/**
	 * Final state
	 */
	private States end;
	
	/**
	 * Constructor for a registerable state transition
	 * @throws NullPointerException If any argument is null
	 */
	public StateTransition(States initial, States end) throws NullPointerException {
		this.setInitialState(initial);
		this.setFinalState(end);
	}

	public States getInitialState() {
		return initial;
	}

	public void setInitialState(States initial) throws NullPointerException {
		if (initial == null) {
			throw new NullPointerException("Initial");
		}
		
		this.initial = initial;
	}

	public States getFinalState() {
		return end;
	}

	public void setFinalState(States end) throws NullPointerException {
		if (end == null) {
			throw new NullPointerException("End");
		}
		
		this.end = end;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof StateTransition)) {
			return false;
		}
		
		StateTransition ob = (StateTransition) o;
		
		return (this.getInitialState().equals(ob.getInitialState()) && this.getFinalState().equals(ob.getFinalState()));
	}
}
