package com.thelocalmarketplace.software.controllers.pay.cash;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteDispenserObserver;
import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.IBanknoteDispenser;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import powerutility.NoPowerException;

/**
 * Banknote Dispensing
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
public class BanknoteDispenserController extends AbstractLogicDependant implements BanknoteDispenserObserver {
	
	private List<Banknote> available;
	private IBanknoteDispenser dispenser;

	public BanknoteDispenserController(CentralStationLogic logic, BigDecimal denomination) throws NullPointerException {
		super(logic);
		
		if (denomination == null) {
			throw new NullPointerException("Denomination");
		}
		
		this.available = new ArrayList<>();
		
		// Attach self to specific dispenser corresponding to its denomination
		dispenser = this.logic.hardware.getBanknoteDispensers().get(denomination);
		dispenser.attach(this);
	}
	
	public List<Banknote> getAvailableBanknotes() {
        return this.available;
    }

	public void testBanknoteLevel() {
		BanknoteStorageUnit banknoteStorage = this.logic.hardware.getBanknoteStorage();
		if (!banknoteStorage.isActivated()) {  // This should not happen
			throw new NoPowerException();
		}
		if (!banknoteStorage.isDisabled()) {
			if (banknoteStorage.getBanknoteCount() < 20) {
				// TODO: Change GUI display message about low banknote level
				System.out.println("Banknote level too low. System shutting off.");

				logic.hardware.turnOff();
			}
			if (dispenser.size() < 5) {
				// TODO: Change GUI display message about low banknote level
				System.out.println("Banknote level too low. System shutting off.");

				logic.hardware.turnOff();
			}

			if (banknoteStorage.getBanknoteCount() - banknoteStorage.getCapacity() < 10) {
				// TODO: Change GUI display message about (almost) full banknotes
				System.out.println("Banknotes full or almost full. System shutting off.");

				logic.hardware.turnOff();
			}
			if (dispenser.size() - dispenser.getCapacity() < 3) {
				//TODO: Change GUI display message about (almost) full banknotes
				System.out.println("Banknotes full or almost full. System shutting off.");

				logic.hardware.turnOff();
			}
        }
	}

	@Override
	public void banknotesEmpty(IBanknoteDispenser dispenser) {
		this.available.clear();
		
	}
	@Override
	public void banknoteAdded(IBanknoteDispenser dispenser, Banknote banknote) {
		this.available.add(banknote);
		
	}
	
	@Override
	public void banknoteRemoved(IBanknoteDispenser dispenser, Banknote banknote) {
		this.available.remove(banknote);
		
	}
	@Override
	public void banknotesLoaded(IBanknoteDispenser dispenser, Banknote... banknotes) {
		for (Banknote b : banknotes) {
            this.available.add(b);
        }
		
	}
	@Override
	public void banknotesUnloaded(IBanknoteDispenser dispenser, Banknote... banknotes) {
		 for (Banknote b : banknotes) {
	            this.available.remove(b);
	        }
		
	}
	@Override
	public void moneyFull(IBanknoteDispenser dispenser) {
		// TODO: Change to GUI message about full dispenser
		System.out.println("Banknote Dispenser is full: " + dispenser);
		this.logic.hardware.turnOff();
	}

	// Test banknote level on startup in case of improper or missed maintenance
	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
		testBanknoteLevel();
	}

	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {
	}

	@Override
	// Test banknote level on startup in case of improper or missed maintenance
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
		testBanknoteLevel();
	}

	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
	}
}
