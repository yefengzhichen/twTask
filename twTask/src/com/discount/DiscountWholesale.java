package com.discount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.task.*;

public class DiscountWholesale implements Discount {

	@Override
	public String calculate(Map<String, Double> buy, Map<String, Product> map, Set<String> enjoyProduct) {
		// TODO Auto-generated method stub
		String result;
		Set<String> discountProduct = new HashSet<>();
		Map<String, Double> subCategoryCount = new HashMap<>();
		double discountRatio = 0.95;
		for (Map.Entry<String, Double> entry : buy.entrySet()) {
			String key = entry.getKey();
			double value = entry.getValue();
			String subCategory = map.get(key).getSubCategory();
			if (subCategoryCount.containsKey(subCategory)) {
				double count = subCategoryCount.get(subCategory);
				count += value;
				subCategoryCount.put(subCategory, count);
			} else {
				subCategoryCount.put(subCategory, value);
			}
		}
		for (Map.Entry<String, Double> entry : subCategoryCount.entrySet()) {
			String key = entry.getKey();
			double value = entry.getValue();
			if (value > 10.0) {
				discountProduct.add(key);
			}
		}
		StringBuffer detailPrice = new StringBuffer("` *<ûǮ׬�̵�>�����嵥*");
		StringBuffer discountPrice = new StringBuffer("");
		StringBuffer tatalPrice = new StringBuffer("");
		double totalSum = 0.0;
		double discountSum = 0.0;
		for (Map.Entry<String, Double> entry : buy.entrySet()) {
			String key = entry.getKey();
			double value = entry.getValue();
			Product product = map.get(key);
			String name = product.getName();
			String unit = product.getUnit();
			double price = product.getPrice();
			detailPrice.append(" ���ƣ�" + name + ",������" + value + "" + unit + ",���ۣ�" + price + "(Ԫ)");
			if (discountProduct.contains(key)) {
				double subTotal = value * price * discountRatio;
				double dis = value * price - subTotal;
				totalSum += subTotal;
				discountSum += dis;
				detailPrice.append(",С�ƣ�" + subTotal + "(Ԫ),�Żݣ�" + dis + "(Ԫ) ");
				if (discountPrice.length() < 1) {
					discountPrice.append("�����۳�����Ʒ");
				}
				discountPrice.append(" ���ƣ�" + name + ",������" + value + "" + unit);
			} else {
				double subTotal = value * price;
				detailPrice.append(",С�ƣ�" + subTotal + "(Ԫ) ");
			}
		}
		tatalPrice.append("�ܼƣ�" + totalSum);
		if (discountPrice.length() > 1) {
			tatalPrice.append(" ��ʡ��" + discountSum);
		}
		result = detailPrice.toString() + "\n" + discountPrice.toString() + "\n" + tatalPrice.toString();
		return result;
	}

}
