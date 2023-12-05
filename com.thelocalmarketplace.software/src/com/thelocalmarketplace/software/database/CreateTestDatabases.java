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
	
	public static PLUCodedProduct bagsUnder;
	public static PLUCodedProduct bagsOver;

    public static void createDatabase() {
    	
    	PriceLookUpCode bagsUnderPLU = new PriceLookUpCode("9999");
    	bagsUnder = new PLUCodedProduct(bagsUnderPLU, "Bags", 15);
    	
    	PriceLookUpCode bagsOverPLU = new PriceLookUpCode("9998");
    	bagsOver = new PLUCodedProduct(bagsOverPLU, "Bags", 35);
    	
    	
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

        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(soupBarcode, soup);
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(picklesBarcode, pickles);

        // You can also update the inventory if needed
        ProductDatabases.INVENTORY.put(banana, 100);
        ProductDatabases.INVENTORY.put(apple, 150);
        ProductDatabases.INVENTORY.put(soup, 50);
        ProductDatabases.INVENTORY.put(pickles, 75);
        ProductDatabases.INVENTORY.put(bagsUnder, 1);
        ProductDatabases.INVENTORY.put(bagsOver, 1);
    }
}
