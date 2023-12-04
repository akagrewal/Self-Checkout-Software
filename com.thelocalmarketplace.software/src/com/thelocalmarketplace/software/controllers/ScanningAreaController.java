package com.thelocalmarketplace.software.controllers;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

public class ScanningAreaController extends AbstractLogicDependant implements ElectronicScaleListener {
    private Mass scanningAreaMass;

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
        scanningAreaMass = mass;
    }

    @Override public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {}
    @Override public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {}
    @Override public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {}
    @Override public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {}
    @Override public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {}
    @Override public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {}
}
