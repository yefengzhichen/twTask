package com.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.task.Product;

public class DataProcess {
	
	/**  
	 * Load product information from a file;
	 * @return map The map save all product information.
	 */
	public Map<String,Product> initProduct() throws NumberFormatException, IOException {
		Map<String,Product> map = new HashMap<String,Product>();
		InputStream is = this.getClass().getResourceAsStream("/product.data");
		InputStreamReader read = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(read);
		String lineTxt = null;
		while ((lineTxt = br.readLine()) != null) {
			String[] content = lineTxt.split(",");
			String barcode = content[0];
			String name= content[1];
			String unit= content[2];
			String category= content[3];
			String subCategory= content[4];
			double price=Double.parseDouble(content[5]);
			Product product = new Product(barcode,name,unit,category,subCategory,price);
			map.put(barcode, product);			
		}
		read.close();
		return map;		
	}
	
	/**  
	 * Add a product record, waiting for add.
	 */
	public void addProduct(Map<String,Product> map, String str){
		
	}
	
	
}
