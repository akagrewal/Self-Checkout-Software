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


package com.thelocalmarketplace.software.database;

import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

public class CreateTestDatabases {
	
	public static PLUCodedProduct banana;
	public static PLUCodedProduct apple;
	
	public static BarcodedProduct soup;
	public static BarcodedProduct pickles;

    public static BarcodedProduct bagsPurchasable;
	public static PLUCodedProduct bagsUnder;
	public static PLUCodedProduct bagsOver;

    public static void createDatabase() {
        Barcode purchasableBag = new Barcode(new Numeral[]{Numeral.nine, Numeral.nine, Numeral.nine, Numeral.zero});
        bagsPurchasable = new BarcodedProduct(purchasableBag, "TheLocalMarketplace Purchasable Bag", 3, 0.00000001);
    	
    	PriceLookUpCode bagsUnderPLU = new PriceLookUpCode("72701");
    	bagsUnder = new PLUCodedProduct(bagsUnderPLU, "USER BAG: LIGHT", 15);
    	
    	PriceLookUpCode bagsOverPLU = new PriceLookUpCode("72702");
    	bagsOver = new PLUCodedProduct(bagsOverPLU, "USER BAG: TOO HEAVY", 35);
    	
    	
        // Populate PLU-coded products (fruits)
        PriceLookUpCode bananaPLU = new PriceLookUpCode("1001");
        banana = new PLUCodedProduct(bananaPLU, "Banana", 150);
        // price here is per kilogram

        PriceLookUpCode applePLU = new PriceLookUpCode("1002");
        apple = new PLUCodedProduct(applePLU, "Apple", 120);
        // price here is per kilogram

        // Populate barcoded products (canned goods)
        Barcode soupBarcode = new Barcode(new Numeral[]{Numeral.one, Numeral.two, Numeral.three, Numeral.four});
        soup = new BarcodedProduct(soupBarcode, "Soup Can", (long)2.50, 400);
        // price here is per unit

        Barcode picklesBarcode = new Barcode(new Numeral[]{Numeral.five, Numeral.six, Numeral.seven, Numeral.eight});
        pickles = new BarcodedProduct(picklesBarcode, "Pickles Jar", (long)1.80, 600);
        // price here is per unit

        // Add products to the databases
        ProductDatabases.PLU_PRODUCT_DATABASE.put(bananaPLU, banana);
        ProductDatabases.PLU_PRODUCT_DATABASE.put(applePLU, apple);
        ProductDatabases.PLU_PRODUCT_DATABASE.put(bagsUnderPLU, bagsUnder);
        ProductDatabases.PLU_PRODUCT_DATABASE.put(bagsOverPLU, bagsOver);

        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(purchasableBag, bagsPurchasable);
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(soupBarcode, soup);
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(picklesBarcode, pickles);

        // You can also update the inventory if needed
        ProductDatabases.INVENTORY.put(banana, 100);
        ProductDatabases.INVENTORY.put(apple, 150);
        ProductDatabases.INVENTORY.put(soup, 50);
        ProductDatabases.INVENTORY.put(pickles, 75);
        ProductDatabases.INVENTORY.put(bagsUnder, 1);
        ProductDatabases.INVENTORY.put(bagsOver, 1);
        ProductDatabases.INVENTORY.put(bagsPurchasable, 750);
    }
}
