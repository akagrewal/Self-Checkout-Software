package com.thelocalmarketplace.software.controllers.pay;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.CardReaderListener;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;
import com.thelocalmarketplace.software.logic.StateLogic.States;

/**
 * Card Reader Controller
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
public class CardReaderController extends AbstractLogicDependant implements CardReaderListener{
    
	/**
     * Base constructor
     * @param logic Reference to the central station logic
     * @throws NullPointerException If logic is null
     */
    public CardReaderController(CentralStationLogic logic) throws NullPointerException {
        super(logic);
        this.logic.hardware.getCardReader().register(this);
    }


    //Ask for signature when card is swiped
    @Override
    public void aCardHasBeenSwiped() {
        System.out.println("A card has been swiped");
        this.logic.cardLogic.setDataRead(false);
    }
    
    //Ask for PIN when card is swiped
  	@Override
  	public void aCardHasBeenInserted() {
  		System.out.println("A card has been Inserted");
          this.logic.cardLogic.setDataRead(false);
  	}

  	//Ask for signature when card is tapped
  	@Override
  	public void aCardHasBeenTapped() {
  		System.out.println("A card has been swiped");
          this.logic.cardLogic.setDataRead(false);
  	}
  	
    @Override
    public void theDataFromACardHasBeenRead(CardData data) {
    	PaymentMethods t = this.logic.cardLogic.getCardPaymentType(data.getType());
   
        this.logic.cardLogic.setDataRead(true);

        if (!this.logic.isSessionStarted()) {
            throw new InvalidStateSimulationException("Session not started");
        }
        else if (!this.logic.stateLogic.inState(States.CHECKOUT)) {
            throw new InvalidStateSimulationException("Not ready for checkout");
        }
        else if (!       (  this.logic.getSelectedPaymentMethod().equals(t) || this.logic.getSelectedPaymentMethod().equals(PaymentMethods.MIXED)     ) ) {
        	throw new InvalidStateSimulationException("Pay by " + t.toString() + " not selected");
        }

        //check if transaction successful
        if(this.logic.cardLogic.approveTransaction(data.getNumber(),this.logic.cartLogic.getBalanceOwed().doubleValue())){

            //if successful reduce amount owed by customer otherwise do nothing
            this.logic.cartLogic.modifyBalance(logic.cartLogic.getBalanceOwed().negate());
            this.logic.guiLogic.switchPanels("thankYouPanel");

        }

        System.out.println("Total owed: " + this.logic.cartLogic.getBalanceOwed());
        

    }
    
    // ---- Unused Methods, we just care about the ones related to payment ----
    @Override
    public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
    }
    @Override
    public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
    }
    @Override
    public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
    }
    @Override
    public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
    }
	@Override
	public void theCardHasBeenRemoved() {
	}

	
}
