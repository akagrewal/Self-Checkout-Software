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


package com.thelocalmarketplace.software.controllers;

import java.util.ArrayList;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

public class ScanningAreaController extends AbstractLogicDependant implements ElectronicScaleListener {
    private Mass scanningAreaMass;
    public ArrayList<Item> itemsOnScale = new ArrayList<Item>();

    /**
     * Base constructor
     *
     * @param logic Reference to the central station logic
     * @throws NullPointerException If logic is null
     */
    public ScanningAreaController(CentralStationLogic logic) throws NullPointerException {
        super(logic);
        logic.hardware.getScanningArea().register(this);
        scanningAreaMass = Mass.ZERO;
    }

    public Mass getScanningAreaMass() {
        return scanningAreaMass;
    }

    @Override
    public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
    	System.out.println("The mass has changed for the Scanning Area; current mass: " + mass.toString());
        scanningAreaMass = mass;
                
    }

    @Override public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {}
    @Override public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {}
    @Override public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {}
    @Override public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {}
    @Override public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {}
    @Override public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {}
}
