package com.thelocalmarketplace.software;

import com.thelocalmarketplace.software.logic.StateLogic.States;

/**
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
