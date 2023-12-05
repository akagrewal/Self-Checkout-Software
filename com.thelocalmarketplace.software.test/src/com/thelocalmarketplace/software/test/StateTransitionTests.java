package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.thelocalmarketplace.software.StateTransition;
import com.thelocalmarketplace.software.logic.StateLogic.States;

/** Tests StateTransition
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
public class StateTransitionTests {

	// - - - - - - - - - - Constructor tests - - - - - - - - - -
	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullInitial() {
		new StateTransition(null, States.NORMAL);
	}

	
	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullFinal() {
		new StateTransition(States.NORMAL, null);
	}

	
	@Test
	public void testValidConstructor() {
		StateTransition transition = new StateTransition(States.NORMAL, States.BLOCKED);
		assertNotNull("The StateTransition object should not be null", transition);
		assertEquals("Initial state should be NORMAL", States.NORMAL, transition.getInitialState());
		assertEquals("Final state should be BLOCKED", States.BLOCKED, transition.getFinalState());
	}

	// - - - - - - - - - - Equals tests - - - - - - - - - -

	@Test
	public void testEqualsWithSameObject() {
		StateTransition transition = new StateTransition(States.NORMAL, States.BLOCKED);
		assertTrue("A StateTransition object should be equal to itself", transition.equals(transition));
	}

	
	@Test
	public void testEqualsWithEqualStates() {
		StateTransition st1 = new StateTransition(States.NORMAL, States.BLOCKED);
		StateTransition st2 = new StateTransition(States.NORMAL, States.BLOCKED);
		assertTrue("Two StateTransition objects with the same states should be equal", st1.equals(st2));
	}

	
	@Test
	public void testEqualsWithDifferentStates() {
		StateTransition st1 = new StateTransition(States.NORMAL, States.BLOCKED);
		StateTransition st3 = new StateTransition(States.BLOCKED, States.NORMAL);
		assertFalse("Two StateTransition objects with different states should not be equal", st1.equals(st3));
	}

	
	@Test
	public void testEqualsWithNonStateTransitionObject() {
		StateTransition st1 = new StateTransition(States.NORMAL, States.BLOCKED);
		Object obj = new Object();
		assertFalse("A StateTransition object should not be equal to a non-StateTransition object", st1.equals(obj));
	}
}
