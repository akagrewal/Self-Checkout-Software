package com.thelocalmarketplace.software.test.controllers;

import com.jjjwelectronics.card.Card;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import powerutility.PowerGrid;

import ca.ucalgary.seng300.simulation.SimulationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
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
public class PayByTapTests {

    SelfCheckoutStationGold station;
    CentralStationLogic session;

    CardIssuer bank;

    Card card;

    @Before
    public void setup() {

        PowerGrid.engageUninterruptiblePowerSource();
        PowerGrid.instance().forcePowerRestore();

        AbstractSelfCheckoutStation.resetConfigurationToDefaults();

        station=new SelfCheckoutStationGold();
        station.plugIn(PowerGrid.instance());
        station.turnOn();


        session = new CentralStationLogic(station);
        session.startSession();

        //set up bank details
        CardIssuer bank= new CardIssuer("Scotia Bank",3);
        session.setupBankDetails(bank);
        this.card = new Card("DEBIT","123456789","John","329", "1234", true, true);
        Calendar expiry = Calendar.getInstance();
        expiry.set(2025,Calendar.JANUARY,24);
        bank.addCardData("123456789", "John",expiry,"329",32.00);


        this.session.selectPaymentMethod(PaymentMethods.DEBIT);
    }

    @After
    public void tearDown() {
        PowerGrid.engageFaultyPowerSource();
    }

    @Test(expected=SimulationException.class)
    public void testInValidState() throws IOException {
        session.cartLogic.updateBalance(BigDecimal.valueOf(10.00));
        session.hardware.getCardReader().enable();
        session.hardware.getCardReader().tap(this.card);
    }

    @Test
    public void testValidTransaction() throws IOException {
        session.cartLogic.updateBalance(BigDecimal.valueOf(17.00));
        session.hardware.getCardReader().enable();
        session.stateLogic.gotoState(States.CHECKOUT);
        session.hardware.getCardReader().tap(this.card);
        assertEquals(BigDecimal.valueOf(0.0),session.cartLogic.getBalanceOwed());

    }

    @Test(expected=SimulationException.class)
    public void testSessionNotStartedTap() throws IOException{
        session.stopSession();
        session.hardware.getCardReader().enable();
        session.stateLogic.gotoState(States.CHECKOUT);
        session.hardware.getCardReader().tap(this.card);
    }

    @Test
    public void testDeclinedTransaction() throws IOException {
        session.cartLogic.updateBalance(BigDecimal.valueOf(33.00));
        session.hardware.getCardReader().enable();
        session.stateLogic.gotoState(States.CHECKOUT);
        session.hardware.getCardReader().tap(this.card);
        assertEquals(BigDecimal.valueOf(33.0),session.cartLogic.getBalanceOwed());
    }
    
    @Test(expected=SimulationException.class)
    public void testWrongTapMethodSelected() throws IOException {
        this.session.selectPaymentMethod(PaymentMethods.CREDIT);
        session.cartLogic.updateBalance(BigDecimal.valueOf(14.00));
        session.hardware.getCardReader().enable();
        session.stateLogic.gotoState(States.CHECKOUT);
        session.hardware.getCardReader().tap(this.card);
    }

    @Test(expected=SimulationException.class)
    public void testStationBlockedTap()throws IOException{
        session.cartLogic.updateBalance(BigDecimal.valueOf(12.00));
        session.stateLogic.gotoState(States.BLOCKED);
        session.hardware.getCardReader().enable();
        session.hardware.getCardReader().tap(this.card);
    }

    @Test(expected=SimulationException.class)
    public void tesSessionNotStartedTap() throws IOException {
        session.cartLogic.updateBalance(BigDecimal.valueOf(14.00));
        session.hardware.getCardReader().enable();
        session.hardware.getCardReader().tap(this.card);
    }
    
    @Test
    public void testGetCardPaymentTypeDebit() {
    	Card c = new Card("deBiT","123456789","John","329", "1234", true, true);
    	
    	assertEquals(PaymentMethods.DEBIT, session.cardLogic.getCardPaymentType(c.kind));
    }
    
    @Test
    public void testGetCardPaymentTypeCredit() {
    	Card c = new Card("CreDIt","123456789","John","329", "1234", true, true);
    	
    	assertEquals(PaymentMethods.CREDIT, session.cardLogic.getCardPaymentType(c.kind));
    }
    
    @Test
    public void testGetCardPaymentTypeNone() {
    	Card c = new Card("fdsgds","123456789","John","329", "1234", true, true);
    	
    	assertEquals(PaymentMethods.NONE, session.cardLogic.getCardPaymentType(c.kind));
    }
}
