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

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.software.AbstractStateTransitionListener;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import powerutility.PowerGrid;

/** Tests AbstractStateTransitionListener
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
public class AbstractStateTransitionListenerTests {


    // Creates listener stub for testing 
    private class StateTransitionListenerStub extends AbstractStateTransitionListener {
        public boolean onTransitionCalled = false;
    	public StateTransitionListenerStub(CentralStationLogic logic) {
            super(logic);
        }

        @Override
        public void onTransition() {
            onTransitionCalled = true;
        }
    }
    
    private StateLogic stateLogic;
    private StateTransitionListenerStub listener;
    
    @Before 
    public void setup() {
    	AbstractSelfCheckoutStation.resetConfigurationToDefaults();
    	SelfCheckoutStationGold station = new SelfCheckoutStationGold();
        CentralStationLogic logic = new CentralStationLogic(station);
        stateLogic = logic.stateLogic;
        listener = new StateTransitionListenerStub(logic);
        PowerGrid.engageUninterruptiblePowerSource();
        
        station.plugIn(PowerGrid.instance());
        station.turnOn();
        
        stateLogic.registerListener(States.BLOCKED, listener);
    }
    
    @After
    public void teardown() {
    	PowerGrid.engageFaultyPowerSource();
    	AbstractSelfCheckoutStation.resetConfigurationToDefaults();
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullLogic() {
        new StateTransitionListenerStub(null);
    }

    @Test
    public void testOnTransitionMethod() throws Exception {
        stateLogic.gotoState(States.BLOCKED);
        assertTrue("onTransition should be called during the state transition", listener.onTransitionCalled);
    }
    
    @Test
    public void testOnTransitionMethodSameState() throws Exception {
    	stateLogic.registerListener(States.NORMAL, listener);
        stateLogic.gotoState(States.NORMAL);
        assertTrue("onTransition should be called during the state transition", listener.onTransitionCalled);
    }
}
