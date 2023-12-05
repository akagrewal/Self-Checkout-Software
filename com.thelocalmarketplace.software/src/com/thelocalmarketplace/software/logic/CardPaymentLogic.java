package com.thelocalmarketplace.software.logic;

import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;

/**
 * Card Swipe Logic
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
public class CardPaymentLogic extends AbstractLogicDependant {

    public String signature;
    public CardIssuer bank;
    boolean dataRead;

    
    public CardPaymentLogic(CentralStationLogic logic, CardIssuer bank) throws NullPointerException {
    	super(logic);
    	
        this.bank = bank;
    }

    //approve the transaction
    public boolean approveTransaction(String debitCardNumber, double chargeAmount) {
        Long holdNumber = this.bank.authorizeHold(debitCardNumber, chargeAmount);
        
        if (holdNumber != -1) {
            return this.bank.postTransaction(debitCardNumber, holdNumber, chargeAmount);
        }
        
        return false;
    }


    //keeps track of whether data was read or not
    public void setDataRead(boolean read){
        dataRead = read;
    }

    //returns if data is read or not
    public boolean isDataRead(){
        return dataRead;
    }
    
    public PaymentMethods getCardPaymentType(String type) {
    	String t = type.toLowerCase();
    	
    	if (t.contains("debit")) {
    		return PaymentMethods.DEBIT;
    	}
    	else if (t.contains("credit")) {
    		return PaymentMethods.CREDIT;
    	}
    	
    	return PaymentMethods.NONE;
    }
}
