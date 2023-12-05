package com.thelocalmarketplace.software;

import java.util.Map;

public class Utilities{

	/**
	 * Used for incrementing/decrementing a counting map
	 * @param <T> The type that is being counted
	 * @param map Is a reference to a map object of type: Map<T, Integer>
	 * @param item Is the item to increment/decrement
	 * @param amount Is the amount to increment/decrement by
	 */
	public static <T> void modifyCountMapping(Map<T, Integer> map, T item, int amount) {
		if (map.containsKey(item)) {
			map.put(item, map.get(item) + amount);
		}
		else {
			map.put(item, amount);
		}
		
		if (map.get(item) <= 0) {
			map.remove(item);
		}
	}
	public static <T> void modifyCountMapping(Map<T, Float> map, T item, float amount) {
		if (map.containsKey(item)) {
			map.put(item, map.get(item) + amount);
		}
		else {
			map.put(item, amount);
		}
		
		if (map.get(item) <= 0) {
			map.remove(item);
		}
	}
}