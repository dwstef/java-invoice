package pl.edu.agh.mwo.invoice;

import java.util.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
	private final int NUMBER = Math.abs(new Random().nextInt());
	private Map<Product, Integer> products = new HashMap<Product, Integer>();

	public void addProduct(Product product) {
		addProduct(product, 1);
	}

	public void addProduct(Product product, Integer quantity) {
		if (product == null || quantity <= 0) {
			throw new IllegalArgumentException();
		}
		
		if(products.containsKey(product)) {
			products.put(product, products.get(product) + quantity);
		} else products.put(product, quantity);

	}

	public BigDecimal getNetTotal() {
		BigDecimal totalNet = BigDecimal.ZERO;
		for (Product product : products.keySet()) {
			BigDecimal quantity = new BigDecimal(products.get(product));
			totalNet = totalNet.add(product.getPrice().multiply(quantity));
		}
		return totalNet;
	}

	public BigDecimal getTaxTotal() {
		return getGrossTotal().subtract(getNetTotal());
	}

	public BigDecimal getGrossTotal() {
		BigDecimal totalGross = BigDecimal.ZERO;
		for (Product product : products.keySet()) {
			BigDecimal quantity = new BigDecimal(products.get(product));
			totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
		}
		return totalGross;
	}

	public int getNumber() {
		// TODO Auto-generated method stub
		return NUMBER;
	}

	public void getProductsWithPrices() {
		for (Product product : products.keySet()) {
			BigDecimal quantity = new BigDecimal(products.get(product));
			System.out.println("Product: " + product.getName() + " || Quantity: " + quantity + " || Total price: "
					+ product.getPriceWithTax().multiply(quantity));
		}
	}

	public Map<Product, Integer> getProductsList() {
		return products;
	}

	public int getSummary() {
		int numberOfProducts = 0;
		;
		for (Product product : products.keySet()) {
			numberOfProducts += 1;
		}
		return numberOfProducts;
	};

	public void printInvoice() {
		System.out.println("Invoice number: " + this.getNumber());
		System.out.println("===============");
		this.getProductsWithPrices();
		System.out.println("===============");
		System.out.println("Liczba pozycji: " + this.getSummary());
	}

}
