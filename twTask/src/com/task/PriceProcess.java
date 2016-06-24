package com.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.task.*;
import com.exception.*;
import com.discount.*;

public class PriceProcess {
	// save product information

	public static final int WHOLESALE = 1;
	private static Map<String, Product> map = new HashMap<>();
	private Discount discount = new DiscountWholesale();

	/**
	 * load product information
	 */
	public PriceProcess() {
		DataProcess dp = new DataProcess();
		try {
			map = dp.initProduct();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Parse input string array.
	 * 
	 * @return map The map save all product barcode and numbers. The numbers'
	 *         type is Double because of the pound of some product
	 */
	public Map<String, Double> parseInput(List<String> input) {
		Map<String, Double> buy = new HashMap<>();
		for (String str : input) {
			String[] content = str.split("-");
			double num = 0.0;
			if (content.length == 1) {
				num = 1.0;
			} else if (content.length == 2) {
				num = Double.parseDouble(content[1]);
			} else {
				try {
					throw new InputBarcodeError(" ‰»ÎÃı–Œ¬Î¥ÌŒÛ£°");
				} catch (InputBarcodeError e) {
					e.printStackTrace();
				}
			}
			String key = content[0];
			if (buy.containsKey(key)) {
				num += buy.get(key);
				buy.put(key, num);
			} else {
				buy.put(key, num);
			}
		}
		return buy;
	}

	/**
	 * Calcaute total price and discount. Strategy Pattern, so the discount can
	 * change dynamically.
	 * 
	 * @return map The map save all product barcode and numbers.
	 */
	public String calcautePrice(List<String> input, Set<String> enjoyProduct){
		String result;
		Set<String> discountProduct = new HashSet<>();
		for(String str : input){
			discountProduct.add(str);
		}	
		Map<String, Double> buy = parseInput(input);
		result = discount.calculate(buy, map, enjoyProduct);
		return result;
	}
	

	

	
	/**
	 * Change discounts, waiting for add.
	 */
	public void changeDiscount(int type){
		if(type==WHOLESALE){
			discount=new DiscountWholesale();
		}		
	}
		
}
