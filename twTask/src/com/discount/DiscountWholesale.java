package com.discount;

import java.text.DecimalFormat;
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
		for (Map.Entry<String, Double> entry : buy.entrySet()) {
			String key = entry.getKey();
			String sub = map.get(key).getSubCategory();		
			double value = subCategoryCount.get(sub);
			if (enjoyProduct.contains(key) && value > 10.0) {
				discountProduct.add(key);
			}
		}
		StringBuffer detailPrice = new StringBuffer("` *<没钱赚商店>购物清单*");
		StringBuffer discountPrice = new StringBuffer("");
		StringBuffer tatalPrice = new StringBuffer("");
		double totalSum = 0.0;
		double discountSum = 0.0;
		//小数位数
		DecimalFormat df = new DecimalFormat("######0.00");   
		for (Map.Entry<String, Double> entry : buy.entrySet()) {
			String key = entry.getKey();
			double value = entry.getValue();
			Product product = map.get(key);
			String name = product.getName();
			String unit = product.getUnit();
			double price = product.getPrice();
			String sub = product.getSubCategory();
			detailPrice.append(" 名称：" + name + ",数量：" + value + "" + unit + ",单价：" + df.format(price) + "(元)");
			if (discountProduct.contains(key)) {
				double subTotal = value * price * discountRatio;
				double dis = value * price - subTotal;
				totalSum += subTotal;
				discountSum += dis;
				detailPrice.append(",小计：" + df.format(subTotal) + "(元),优惠：" + df.format(dis) + "(元) ");
				if (discountPrice.length() < 1) {
					discountPrice.append("批发价出售商品");
				}
				discountPrice.append(" 名称：" + name + ",数量：" + value + "" + unit);
			} else {
				double subTotal = value * price;
				totalSum += subTotal;
				detailPrice.append(",小计：" + df.format(subTotal) + "(元) ");
			}
		}
		tatalPrice.append("总计：" + df.format(totalSum)+"(元)");
		if (discountPrice.length() > 1) {
			tatalPrice.append(" 节省：" + df.format(discountSum)+"(元)");
		}
		result = detailPrice.toString() + "\n\n" + discountPrice.toString() + "\n\n" + tatalPrice.toString();
		return result;
	}

}
