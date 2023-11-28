package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.software.Utilities;

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
public class UtilitiesTests {
	
	/**
	 * Map that will track the count of any object
	 */
	private Map<Object, Integer> testMap;
	
	private Object o1;
	private Object o2;
	
	
	@Before
	public void init() {
		this.testMap = new HashMap<>();
		
		// Initialize some arbitrary objects
		this.o1 = "str1";
		this.o2 = "str2";
		
		// Initialize their counts arbitrarily in the test map
		this.testMap.put(o1, 1);
		this.testMap.put(o2, 3);
	}
	
	@Test
	public void testAddAlreadyExistingInModifyCountMapping() {
		Utilities.modifyCountMapping(this.testMap, o1, 2);
		
		assertEquals(3, testMap.get(o1).intValue());
	}
	
	@Test
	public void testAddNotExistingInModifyCountMapping() {
		Object o = new Object();
		
		Utilities.modifyCountMapping(this.testMap, o, 2);
		
		assertEquals(2, testMap.get(o).intValue());
	}
	
	@Test
	public void testRemoveNotExistingInModifyCountMapping() {
		Object o = new Object();
		
		Utilities.modifyCountMapping(this.testMap, o, -3);
		
		assertFalse(this.testMap.containsKey(o));
	}
	
	@Test
	public void testRemoveAlreadyExistingInModifyCountMapping() {
		Utilities.modifyCountMapping(this.testMap, o2, -1);
		
		assertEquals(2, testMap.get(o2).intValue());
	}
	
	@Test
	public void testRemoveCompletelyAlreadyExistingInModifyCountMapping() {
		Utilities.modifyCountMapping(this.testMap, o1, -1);
		
		assertFalse(this.testMap.containsKey(o1));
	}
}
