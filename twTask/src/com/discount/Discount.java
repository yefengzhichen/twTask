package com.discount;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.task.Product;

/**
 * All the discount activities implement this interface.
 * Strategy Pattern.
 */
public interface Discount {
	String calculate(Map<String, Double> buy, Map<String, Product> map, Set<String> discountProduct);
}
