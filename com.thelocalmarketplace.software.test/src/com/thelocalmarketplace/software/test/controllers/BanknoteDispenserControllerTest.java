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


package com.thelocalmarketplace.software.test.controllers;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tdc.banknote.Banknote;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.SelfCheckoutStationSilver;
import com.thelocalmarketplace.software.controllers.pay.cash.BanknoteDispenserController;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

/**
 * Adapted from Project Iteration 2 - Group 5
 *
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

public class BanknoteDispenserControllerTest {
	private BanknoteDispenserController inputBronze;
	private BanknoteDispenserController inputSilver;
	private BanknoteDispenserController inputGold;

	private CentralStationLogic logicBronze;
	private CentralStationLogic logicSilver;
	private CentralStationLogic logicGold;
	
	private BigDecimal tenDollar;
	private BigDecimal fiveDollar;
	private BigDecimal twoDollar;
	private BigDecimal dollar;
	private BigDecimal quarter;
	private BigDecimal halfDollar;
	private BigDecimal[] coinList;
	private BigDecimal[] noteList;
	private SelfCheckoutStationBronze stationBronze;
	private SelfCheckoutStationSilver stationSilver;
	private SelfCheckoutStationGold stationGold;
	private Currency cad;

	private Banknote twoNote;
	private Banknote fiveNote;
	private Banknote tenNote;
	private Banknote[] noteArray;
	private IBanknoteDispenserStub stub;
	
	@Before
	public void setup() {
		dollar = new BigDecimal(1);
		halfDollar = new BigDecimal(0.50);
		quarter = new BigDecimal(0.25);
		twoDollar = new BigDecimal(2);
		fiveDollar = new BigDecimal(5);
		tenDollar = new BigDecimal(10);
		coinList = new BigDecimal[] {quarter, halfDollar, dollar};
		noteList = new BigDecimal[] {twoDollar, fiveDollar, tenDollar};
		
		cad = Currency.getInstance("CAD"); 
		
		twoNote = new Banknote(cad, twoDollar);
		fiveNote = new Banknote(cad, fiveDollar);
		tenNote = new Banknote(cad, tenDollar);
		noteArray = new Banknote[] {twoNote, fiveNote, tenNote};
		
		stub = new IBanknoteDispenserStub();
		
		SelfCheckoutStationBronze.configureCurrency(cad);
		SelfCheckoutStationSilver.configureCurrency(cad);
		SelfCheckoutStationGold.configureCurrency(cad);
		
		SelfCheckoutStationBronze.configureCoinDenominations(coinList);
		SelfCheckoutStationSilver.configureCoinDenominations(coinList);
		SelfCheckoutStationGold.configureCoinDenominations(coinList);
		
		SelfCheckoutStationBronze.configureBanknoteDenominations(noteList);
		SelfCheckoutStationSilver.configureBanknoteDenominations(noteList);
		SelfCheckoutStationGold.configureBanknoteDenominations(noteList);
		
		SelfCheckoutStationBronze.configureBanknoteStorageUnitCapacity(10);
		SelfCheckoutStationSilver.configureBanknoteStorageUnitCapacity(10);
		SelfCheckoutStationGold.configureBanknoteStorageUnitCapacity(10);
		
		SelfCheckoutStationBronze.configureCoinStorageUnitCapacity(10);
		SelfCheckoutStationSilver.configureCoinStorageUnitCapacity(10);
		SelfCheckoutStationGold.configureCoinStorageUnitCapacity(10);
		
		SelfCheckoutStationBronze.configureCoinTrayCapacity(5);
		SelfCheckoutStationSilver.configureCoinTrayCapacity(5);
		SelfCheckoutStationGold.configureCoinTrayCapacity(5);
		
		SelfCheckoutStationBronze.configureCoinDispenserCapacity(5);
		SelfCheckoutStationSilver.configureCoinDispenserCapacity(5);
		SelfCheckoutStationGold.configureCoinDispenserCapacity(5);
		
		stationBronze = new SelfCheckoutStationBronze();
		stationSilver = new SelfCheckoutStationSilver();
		stationGold = new SelfCheckoutStationGold();
		logicBronze = new CentralStationLogic(stationBronze);
		logicSilver = new CentralStationLogic(stationSilver);
		logicGold = new CentralStationLogic(stationGold);
		inputBronze = new BanknoteDispenserController(logicBronze, twoDollar);
		inputSilver = new BanknoteDispenserController(logicSilver, twoDollar);
		inputGold = new BanknoteDispenserController(logicGold, twoDollar);
	}
	
	@Test (expected = NullPointerException.class)
	public void failedSetup() {
		new BanknoteDispenserController(logicBronze,null);
	}
	
	@Test
	public void getBanknotesTest() {
		List<Banknote> expected = new ArrayList<Banknote>();
		List<Banknote> actual1 = inputBronze.getAvailableBanknotes();
		List<Banknote> actual2 = inputSilver.getAvailableBanknotes();
		List<Banknote> actual3 = inputGold.getAvailableBanknotes();
		
		assertEquals(expected, actual1);
		assertEquals(expected, actual2);
		assertEquals(expected, actual3);
	}
	
	@Test
	public void banknoteAddedTest() {
		List<Banknote> expected = new ArrayList<Banknote>();
		expected.add(twoNote);
		
		inputBronze.banknoteAdded(stub, twoNote);
		inputSilver.banknoteAdded(stub, twoNote);
		inputGold.banknoteAdded(stub, twoNote);
		
		List<Banknote> actual1 = inputBronze.getAvailableBanknotes();
		List<Banknote> actual2 = inputSilver.getAvailableBanknotes();
		List<Banknote> actual3 = inputGold.getAvailableBanknotes();
		
		assertEquals(expected, actual1);
		assertEquals(expected, actual2);
		assertEquals(expected, actual3);
	}
	
	
	@Test
	public void banknotesEmptyTest() {
		List<Banknote> expected = new ArrayList<Banknote>();
		
		inputBronze.banknoteAdded(stub, twoNote);
		inputSilver.banknoteAdded(stub, twoNote);
		inputGold.banknoteAdded(stub, twoNote);
		
		inputBronze.banknotesEmpty(stub);
		inputSilver.banknotesEmpty(stub);
		inputGold.banknotesEmpty(stub);
		
		List<Banknote> actual1 = inputBronze.getAvailableBanknotes();
		List<Banknote> actual2 = inputSilver.getAvailableBanknotes();
		List<Banknote> actual3 = inputGold.getAvailableBanknotes();
		
		assertEquals(expected, actual1);
		assertEquals(expected, actual2);
		assertEquals(expected, actual3);
	}
	
	@Test
	public void banknoteRemovedTest() {
		List<Banknote> expected = new ArrayList<Banknote>();
		
		inputBronze.banknoteAdded(stub, twoNote);
		inputSilver.banknoteAdded(stub, twoNote);
		inputGold.banknoteAdded(stub, twoNote);
		
		inputBronze.banknoteRemoved(stub, twoNote);
		inputSilver.banknoteRemoved(stub, twoNote);
		inputGold.banknoteRemoved(stub, twoNote);
		
		List<Banknote> actual1 = inputBronze.getAvailableBanknotes();
		List<Banknote> actual2 = inputSilver.getAvailableBanknotes();
		List<Banknote> actual3 = inputGold.getAvailableBanknotes();
		
		assertEquals(expected, actual1);
		assertEquals(expected, actual2);
		assertEquals(expected, actual3);
	}
	
	@Test
	public void banknotesLoadedTest() {
		List<Banknote> expected = new ArrayList<Banknote>();
		expected.add(twoNote);
		expected.add(fiveNote);
		expected.add(tenNote);
		
		inputBronze.banknotesLoaded(stub, noteArray);
		inputSilver.banknotesLoaded(stub, noteArray);
		inputGold.banknotesLoaded(stub, noteArray);
		
		List<Banknote> actual1 = inputBronze.getAvailableBanknotes();
		List<Banknote> actual2 = inputSilver.getAvailableBanknotes();
		List<Banknote> actual3 = inputGold.getAvailableBanknotes();
		
		assertEquals(expected, actual1);
		assertEquals(expected, actual2);
		assertEquals(expected, actual3);
	}
	
	@Test
	public void banknotesUnloadedTest() {
		List<Banknote> expected = new ArrayList<Banknote>();
		expected.add(twoNote);
		
		inputBronze.banknotesLoaded(stub, noteArray);
		inputSilver.banknotesLoaded(stub, noteArray);
		inputGold.banknotesLoaded(stub, noteArray);
		
		inputBronze.banknotesUnloaded(stub, fiveNote, tenNote);
		inputSilver.banknotesUnloaded(stub, fiveNote, tenNote);
		inputGold.banknotesUnloaded(stub, fiveNote, tenNote);
		
		List<Banknote> actual1 = inputBronze.getAvailableBanknotes();
		List<Banknote> actual2 = inputSilver.getAvailableBanknotes();
		List<Banknote> actual3 = inputGold.getAvailableBanknotes();
		
		assertEquals(expected, actual1);
		assertEquals(expected, actual2);
		assertEquals(expected, actual3);
	}
	
	//Note: not sure what exactly to test here. Am I to use the IBanknoteDispenser somehow?
	@Test
	public void moneyFullTest() {
		inputBronze.moneyFull(stub);
		inputSilver.moneyFull(stub);
		inputGold.moneyFull(stub);
	}
}
