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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thelocalmarketplace.software.Utilities;

/**
 * Handles all logical operations regarding money 
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
public class CurrencyLogic {
	
	/**
	 * List of possible denominations from one of the hardware references
	 */
	private List<BigDecimal> denominations;
	
	
	/**
	 * Base constructor
	 * @param denominations Is the denomination configuration to use
	 * @throws NullPointerException If denominations is null
	 */
	public CurrencyLogic(List<BigDecimal> denominations) throws NullPointerException {
		if (denominations == null) {
			throw new NullPointerException("Coin Denominations");
		}
		
		this.denominations = denominations;
		
		// Sort denominations from lowest to highest
		Collections.sort(this.denominations);
	}
	
	/**
	 * Constructor with array for denominations
	 * @param denominations Is the denomination configuration to use
	 * @throws NullPointerException If denominations is null
	 */
	public CurrencyLogic(BigDecimal[] denominations) throws NullPointerException {
		if (denominations == null) {
			throw new NullPointerException("Coin Denominations");
		}
		
		List<BigDecimal> d = new ArrayList<>();
		Collections.addAll(d, denominations);
		
		this.denominations = d;
		
		// Sort denominations from lowest to highest
		Collections.sort(this.denominations);
	}
	
	/**
	 * Gets the denominations associated with this currency logic as a list
	 * @return The list of denominations
	 */
	public List<BigDecimal> getDenominationsAsList() {
		return this.denominations;
	}
	
	/**
	 * Algorithm that calculates the coin change required to dispense
	 * @author Connell Reffo (10186960)
	 * 
	 * Uses a loop to initially calculate change denominations from what is available
	 * up until the total sum of change is just under the amount over paid
	 * 
	 * Then rounds up by adding another count of the lowest denomination until sum >= overpay
	 * if (total sum / over paid amount) > lowest denomination / 2
	 * 
	 * If higher denominations are available, they won't be used as that is bad for business
	 * 
	 * Counts of the lowest denomination will be added until required change is added if includeUnavailable is true
	 * 
	 * @param overpay Is the amount that was over paid
	 * @param availableCash Is a mapping of available denominations indexed by their denomination value that can be used as change
	 * @param includeUnavailable Should be true if unavailable change should be returned, false otherwise
	 * @return A map of denominations to an integer representing denomination count
	 */
	public Map<BigDecimal, Integer> calculateChange(BigDecimal overpay, Map<BigDecimal, Integer> availableCash, boolean includeUnavailable) {
		
		// Standardize over pay precision
		overpay = overpay.setScale(5, RoundingMode.HALF_DOWN);
		
		// Initialization
		Map<BigDecimal, Integer> change = new HashMap<>();
		BigDecimal sum = BigDecimal.ZERO;
		int rdi = this.denominations.size() - 1; // Reverse denomination index

		// Copy available map so it is not tampered with
		Map<BigDecimal, Integer> available = new HashMap<>();
		available.putAll(availableCash);
		
		// Main loop
		while (sum.compareTo(overpay) < 0 && rdi >= 0) {
			final BigDecimal denomination = this.denominations.get(rdi);
			
			// Attempt to start at highest possible denomination
			if (available.containsKey(denomination)) {			
				final BigDecimal preSum = sum.add(denomination);
				
				if (available.get(denomination) > 0 && denomination.compareTo(overpay) <= 0 && preSum.compareTo(overpay) <= 0) {
					sum = preSum;
					
					// Remove from available coins
					Utilities.modifyCountMapping(available, denomination, -1);
					
					// Add to change
					Utilities.modifyCountMapping(change, denomination, 1);
				}
				else {
					
					// Now check a lower denomination
					rdi--;
				}
			}
			else {
				
				// Initial denomination too high, go lower
				rdi--;
			}
		}
		
		final BigDecimal lowest = this.denominations.get(0);

		// Add to change until change is fulfilled
		while (overpay.compareTo(sum) > 0) {
			if (available.containsKey(lowest)) {
				if (available.get(lowest) > 0) {
					Utilities.modifyCountMapping(change, lowest, 1);
					
					sum = sum.add(lowest);
					
					continue;
				}
			}
			
			if (includeUnavailable) {
				Utilities.modifyCountMapping(change, lowest, 1);
				
				sum = sum.add(lowest);
			}
			else {
				break;
			}
		}
		
		return change;
	}
}
