package com.thelocalmarketplace.software.controllers.pay.cash;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Map.Entry;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.banknote.BanknoteValidator;
import com.tdc.banknote.BanknoteValidatorObserver;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;

/**
 * Pay with Cash
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
public class CashPaymentController extends AbstractLogicDependant implements BanknoteValidatorObserver {

	public CashPaymentController(CentralStationLogic logic) throws NullPointerException {
		super(logic);
		
		this.logic.hardware.getBanknoteValidator().attach(this);
		System.out.println("NEW CASH PAYMENT CONTROLLER");
	}
	
	/**
	 * Emits a combination of banknotes and coins to full fill change requirements
	 * @param overpay Is the change to give back
	 * @return The amount that failed to be returned
	 */
	public BigDecimal processCashChange(BigDecimal overpay) {
	    BigDecimal missed = BigDecimal.ZERO;
	    BigDecimal sum = BigDecimal.ZERO;
	    
	    if (overpay.compareTo(BigDecimal.ZERO) == 0) {
	        return missed;
	    }
	    
	    // Sanitize over pay
	    overpay = overpay.abs();
	    
		// Calculate required change in banknotes
	    Map<BigDecimal, Integer> changeInBanknotes = this.logic.banknoteCurrencyLogic.calculateChange(overpay, this.logic.getAvailableBanknotesInDispensers(), false);
		
		// Dispense banknotes
		for (Entry<BigDecimal, Integer> entry : changeInBanknotes.entrySet()) {
			BigDecimal denomination = entry.getKey();
			int count = entry.getValue();

			// Loop for each available banknote
			for (int i = 0; i < count; i++) {
				try {			
					this.logic.hardware.getBanknoteDispensers().get(denomination).emit();
					
					sum = sum.add(denomination);
				} catch (Exception e) {
					missed = missed.add(denomination);
					
					System.out.println("Failed to emit banknote");
				}
			}
		}
		
		// Dispense what is left as coins
		missed = missed.add(this.logic.coinPaymentController.processCoinChange(overpay.subtract(sum)));
	
	    return missed;
	}

	
	@Override
	public void goodBanknote(BanknoteValidator validator, Currency currency, BigDecimal denomination) {
		if (!this.logic.isSessionStarted()) {
			throw new InvalidStateSimulationException("Session not started");
		}
		else if (!this.logic.stateLogic.inState(States.CHECKOUT)) {
			throw new InvalidStateSimulationException("Not ready for checkout");
		}
		else if (!  (  this.logic.getSelectedPaymentMethod().equals(PaymentMethods.CASH) || this.logic.getSelectedPaymentMethod().equals(PaymentMethods.MIXED)  )) {
			throw new InvalidStateSimulationException("Payment by banknote not selected");
		}
		else if (this.logic.cartLogic.getBalanceOwed().compareTo(BigDecimal.ZERO) == 0) {
			throw new InvalidStateSimulationException("Balance already paid in full");
		}
		
		BigDecimal pay = this.logic.cartLogic.getBalanceOwed().subtract(denomination);
		
        this.logic.cartLogic.modifyBalance(denomination.negate());

        if (pay.compareTo(BigDecimal.ZERO) <= 0) {
        	pay = pay.abs();
        	
            BigDecimal missed = processCashChange(pay);
            
            if (missed.compareTo(BigDecimal.ZERO) > 0) {
            	System.out.println("Not enough change available: " + missed +  " is unavailable");
            }
            else {
                System.out.println("Payment complete. Change dispensed successfully");
                
                // Print receipt
				this.logic.receiptPrintingController.handlePrintReceipt(pay.subtract(missed));
            }
            this.logic.guiLogic.switchPanels("thankYouPanel");
        }
        else {
            System.out.println("Payment accepted. Remaining balance: " + pay);
            this.logic.stationGUI.BalanceLabel.setText("Balance: " + this.logic.cartLogic.getBalanceOwed());
        }
	}

	@Override
	public void badBanknote(BanknoteValidator validator) {
		 System.out.println("Invalid banknote detected. Please try another banknote.");
	}

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
