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


package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.software.AbstractStateTransitionListener;
import com.thelocalmarketplace.software.exceptions.InvalidStateTransitionException;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import powerutility.PowerGrid;

/** Tests StateLogic
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
public class StateLogicTests {
	
    private StateLogic stateLogic;
    private CentralStationLogic centralLogic;

    @Before
    public void setup() {
    	AbstractSelfCheckoutStation.resetConfigurationToDefaults();
    	SelfCheckoutStationGold station = new SelfCheckoutStationGold();
        centralLogic = new CentralStationLogic(station);
        stateLogic = new StateLogic(centralLogic);
        
        PowerGrid.engageUninterruptiblePowerSource();
        station.plugIn(PowerGrid.instance());
        station.turnOn();
    }
    
    @After
    public void teardown() {
    	PowerGrid.engageFaultyPowerSource();
    	AbstractSelfCheckoutStation.resetConfigurationToDefaults();
    }
    
    // - - - - - - - - - - Test Constructor - - - - - - - - - -

    @Test
    public void testInitialState() {
        assertEquals("Initial state should be NORMAL", States.NORMAL, stateLogic.getState());
    }
    
    // - - - - - - - - - - Test registerTransition - - - - - - - - - - 

    @Test(expected = NullPointerException.class)
    public void testRegisterTransitionWithNullInitial() {
        stateLogic.registerTransition(null, States.NORMAL);
    }
    
    @Test(expected = NullPointerException.class)
    public void testRegisterTransitionWithNullFinal() {
        stateLogic.registerTransition(States.NORMAL, null);
    }
    
    @Test
    public void testRegisterTransitionSuccessful() {
        stateLogic.registerTransition(States.NORMAL, States.BLOCKED);
        // verify the transition was added
        stateLogic.gotoState(States.BLOCKED);
        assertEquals("State should change to BLOCKED after registering the transition", States.BLOCKED, stateLogic.getState());
    }
    
    @Test
    public void testRegisterTransitionDuplicate() {
    	//TODO determine expected outcome 
        stateLogic.registerTransition(States.NORMAL, States.BLOCKED);
        stateLogic.registerTransition(States.NORMAL, States.BLOCKED);
    }

    
    //  - - - - - - - - - - Test registerListener - - - - - - - - - -

    @Test(expected = NullPointerException.class)
    public void testRegisterListenerWithNullState() {
        stateLogic.registerListener(null, new listenerStub());
    }
    
    @Test(expected = NullPointerException.class)
    public void testRegisterListenerWithNullListeners() {
        stateLogic.registerListener(States.NORMAL, null);
    }
    
    @Test
    public void testRegisterListenerValid() throws InvalidStateTransitionException {
        listenerStub listener = new listenerStub();
        stateLogic.registerListener(States.NORMAL, listener);

        stateLogic.gotoState(States.NORMAL);
        assertTrue("Listener should be called for NORMAL state", listener.onTransitionCalled);
    }
    
    @Test
    public void testRegisterListenerMultiple() throws InvalidStateTransitionException {
        listenerStub listener1 = new listenerStub();
        listenerStub listener2 = new listenerStub();
        stateLogic.registerListener(States.NORMAL, listener1);
        stateLogic.registerListener(States.NORMAL, listener2);

        stateLogic.gotoState(States.NORMAL);
        assertTrue("First listener should be called for NORMAL state", listener1.onTransitionCalled);
        assertTrue("Second listener should be called for NORMAL state", listener2.onTransitionCalled);
    }
    
    @Test
	public void testExactlyOneStateChangeEventTriggered() {
    	listenerStub listener = new listenerStub();
    	failListenerStub faiListener = new failListenerStub();
    	
    	stateLogic.registerTransition(States.NORMAL, States.BLOCKED);
    	
        stateLogic.registerListener(States.BLOCKED, listener);
        
        for (States s : States.values()) {
        	if (!s.equals(States.BLOCKED)) {
        		stateLogic.registerListener(s, faiListener);
        	}
        }
        
        stateLogic.gotoState(States.BLOCKED);
	}
    
    
    // - - - - - - - - - - Test gotoState - - - - - - - - - -

    @Test(expected = InvalidStateTransitionException.class)
    public void testInvalidTransition() throws InvalidStateTransitionException {
        stateLogic.gotoState(States.BLOCKED); // Allowed transition
        stateLogic.gotoState(States.SUSPENDED); // not allowed should throw error
        
    }

    @Test
    public void testValidTransition() throws InvalidStateTransitionException {
        stateLogic.registerTransition(States.NORMAL, States.BLOCKED);
    	stateLogic.gotoState(States.BLOCKED);
        assertTrue("State should be BLOCKED after valid transition", stateLogic.inState(States.BLOCKED));
    }

    @Test
    public void testEventListenerTriggeredOnTransition() throws InvalidStateTransitionException {
        stateLogic.registerTransition(States.NORMAL, States.BLOCKED);
    	listenerStub listener = new listenerStub();
        stateLogic.registerListener(States.BLOCKED, listener);

        stateLogic.gotoState(States.BLOCKED);
        assertTrue("Listener's onTransition should be called during the state transition", listener.onTransitionCalled);
    }
    

    // - - - - - - - - - - Helper classes for testing - - - - - - - - - -
    
    private class listenerStub extends AbstractStateTransitionListener {
        public boolean onTransitionCalled = false;

        public listenerStub() {
            super(centralLogic);
        }

        @Override
        public void onTransition() {
            onTransitionCalled = true;
        }
    }

    private class failListenerStub extends AbstractStateTransitionListener {
    	
        public failListenerStub() {
            super(centralLogic);
        }

        /**
         * Should not happen
         */
        @Override
        public void onTransition() {
            fail();
        }
    }
}
