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


package com.thelocalmarketplace.software.logic;


import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.IllegalDigitException;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.bag.IReusableBagDispenser;
import com.jjjwelectronics.bag.ReusableBag;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.software.AbstractLogicDependant;


import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import powerutility.NoPowerException;




public class PurchaseBagsLogic extends AbstractLogicDependant{
	IReusableBagDispenser iDispenser;
	ReusableBag bag;
	Mass bagsMass;
	IElectronicScale scale;
	
	public PurchaseBagsLogic(CentralStationLogic logic) {
		super(logic);
		this.bag = new ReusableBag();
		this.iDispenser = logic.hardware.getReusableBagDispenser();
		bagsMass = bag.getMass();
		this.scale = logic.hardware.getBaggingArea();
	}
	
	/**
	 * dispenses purchased bags and adds them to order
	 * @param int- number of bags to purchase. must be greater than 0
	 * @throws EmptyDevice
	 */
	public void dispensePurchasedBags(int numOfBags) throws EmptyDevice {
		
		if (!this.logic.isSessionStarted()) throw new InvalidStateSimulationException("Session not started");
		
		else if (!scale.isPoweredUp()) throw new NoPowerException();
	
		else if (numOfBags<0) throw new IllegalDigitException("Invalid Request. Cannot purchase negative amount bags");
		
		else if (numOfBags == 0) System.out.println("No bags added to order");//do nothing
		
		
		else if (numOfBags > 0){
			// add bag(s) to order
			// hardcoded price of bags into this method in cartLogic --> change this
			
			if(numOfBags < iDispenser.getQuantityRemaining()) {
				logic.cartLogic.addReusableBagToCart(numOfBags);
			}
			
			else {
				System.out.println("number of requested bags not available. dispensing number of available bags");
				logic.cartLogic.addReusableBagToCart(iDispenser.getQuantityRemaining());
		}
			
			int bagsDispensed = 0;
			while (bagsDispensed < numOfBags){	
				
				// do dispensing
				if (iDispenser.getQuantityRemaining() > 0){
					
					iDispenser.dispense();
					//adjust expected weight
					logic.weightLogic.addExpectedPurchasedBagWeight(bag);
					logic.weightLogic.updateActualWeight(logic.weightLogic.getActualWeight().sum(bagsMass));
					
					//detect weight change
					//logic.weightDiscrepancyController.theMassOnTheScaleHasChanged(scale, bagsMass);
					bagsDispensed ++;
				}
				
				else {
					System.out.println("No bags remaining in dispenser");
					break;
				}
			}
			// notify dispensed (indicate to customer)
		}
	}		
}

