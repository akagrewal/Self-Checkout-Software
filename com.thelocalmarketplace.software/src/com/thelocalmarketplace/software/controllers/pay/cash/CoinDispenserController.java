package com.thelocalmarketplace.software.controllers.pay.cash;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinDispenserObserver;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import powerutility.NoPowerException;

/**
 * Represents an object that will control a coin dispenser of a specific coin denomination
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
public class CoinDispenserController extends AbstractLogicDependant implements CoinDispenserObserver {
	
	/**
	 * List of available coins of this denomination
	 */
	private List<Coin> available;
	private ICoinDispenser dispenser;
	
	/**
	 * Base constructor
	 * @param logic Is the reference to the logic
	 * @param denomination Is the denomination this controller will dispense
	 * @throws NullPointerException If any argument is null
	 */
	public CoinDispenserController(CentralStationLogic logic, BigDecimal denomination) throws NullPointerException {
		super(logic);
		
		if (denomination == null) {
			throw new NullPointerException("Denomination");
		}
		
		this.available = new ArrayList<>();
		
		// Attach self to specific dispenser corresponding to its denomination
		dispenser = this.logic.hardware.getCoinDispensers().get(denomination);
		dispenser.attach(this);
	}
	
	/**
	 * Gets a list of coins of corresponding denomination that are available as change
	 * @return The list of coins
	 */
	public List<Coin> getAvailableChange() {
		return this.available;
	}

	public void testCoinLevel() {
		CoinStorageUnit coinStorage = this.logic.hardware.getCoinStorage();
		if (!coinStorage.isActivated()) {  // This should not happen
			throw new NoPowerException();
		}
		if (!coinStorage.isDisabled()) {
			if (coinStorage.getCoinCount() < 20) {
				// TODO: Change GUI display message about low banknote level
				System.out.println("Coin level too low. System shutting off.");

				logic.hardware.turnOff();
			}
			if (dispenser.size() < 5) {
				// TODO: Change GUI display message about low banknote level
				System.out.println("Coin level too low. System shutting off.");

				logic.hardware.turnOff();
			}

			if (coinStorage.getCoinCount() - coinStorage.getCapacity() < 10) {
				// TODO: Change GUI display message about (almost) full banknotes
				System.out.println("Coins full or almost full. System shutting off.");

				logic.hardware.turnOff();
			}
			if (dispenser.size() - dispenser.getCapacity() < 3) {
				//TODO: Change GUI display message about (almost) full banknotes
				System.out.println("Coins full or almost full. System shutting off.");

				logic.hardware.turnOff();
			}
		}
	}

	@Override
	public void coinsEmpty(ICoinDispenser dispenser) {
		this.available.clear();
	}

	@Override
	public void coinAdded(ICoinDispenser dispenser, Coin coin) {
		this.available.add(coin);
	}

	@Override
	public void coinRemoved(ICoinDispenser dispenser, Coin coin) {
		this.available.remove(coin);
	}
	
	@Override
	public void coinsLoaded(ICoinDispenser dispenser, Coin... coins) {
		for (Coin c : coins) {
			this.available.add(c);
		}
	}

	@Override
	public void coinsUnloaded(ICoinDispenser dispenser, Coin... coins) {
		for (Coin c : coins) {
			this.available.remove(c);
		}
	}

	// Test coin level on startup in case of improper or missed maintenance
	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
		testCoinLevel();
	}

	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {

	}

	// Test coin level on startup in case of improper or missed maintenance
	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
		testCoinLevel();
	}

	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
	}

	@Override
	public void coinsFull(ICoinDispenser dispenser) {
	}
}
