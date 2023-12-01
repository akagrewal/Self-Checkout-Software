package com.thelocalmarketplace.software.logic;


import com.jjjwelectronics.bag.IReusableBagDispenser;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;


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
public class PurchaseBagsLogic extends AbstractLogicDependant{
	IReusableBagDispenser iDispenser;
	
	public PurchaseBagsLogic(CentralStationLogic logic) {
		super(logic);
		this.iDispenser = logic.hardware.getReusableBagDispenser();
	}
	
	
	
	
	// customer indicates they want to purchase reusable bag(s) and indicates # of bags (?)
	
	// I add bag into the order 
		// needed new add item stuff to complete
	//I dispense # of bags orders
	
	// adjust expected weight to match purchased bag(s) weight
		// weight discrepancy --> updateBagMass(getTotalBagMass + mass of new bags)
	//weight change detected
		
	// indicate to customer that operation has been complete
		
}
