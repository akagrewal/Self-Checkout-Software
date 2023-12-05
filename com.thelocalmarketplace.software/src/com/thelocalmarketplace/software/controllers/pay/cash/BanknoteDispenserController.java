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


package com.thelocalmarketplace.software.controllers.pay.cash;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteDispenserObserver;
import com.tdc.banknote.IBanknoteDispenser;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

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

	public BanknoteDispenserController(CentralStationLogic logic, BigDecimal denomination) throws NullPointerException {
		super(logic);
		
		if (denomination == null) {
			throw new NullPointerException("Denomination");
		}
		
		this.available = new ArrayList<>();
		
		// Attach self to specific dispenser corresponding to its denomination
		this.logic.hardware.getBanknoteDispensers().get(denomination).attach(this);
	}
	
	public List<Banknote> getAvailableBanknotes() {
        return this.available;
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
		System.out.println("Banknote Dispenser is full: " + dispenser);
		
	}
	
	// ---- Unused ----
	
	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}
}
