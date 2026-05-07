package com.oms.model.product;

public abstract class Product implements Taxable {
    private String productId;
    private String name;
    private double basePrice;
    private int stockQuantity;

    public Product(String productId, String name, double basePrice, int stockQuantity) {
        this.productId = productId;
        this.name = name;
        this.basePrice = basePrice;
        this.stockQuantity = stockQuantity;
    }

    public abstract String getCategory();

    public double getFinalPrice() {
        return basePrice + calculateTax();
    }

    public String getProductId()  { return productId; }
    public String getName()       { return name; }
    public double getBasePrice()  { return basePrice; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int qty) {
        this.stockQuantity = qty;
    }
    @Override
    public String toString(){
        return String.format("[%s] %s | ID: %s | Price : %.2f | Tax : %.2f | Stock %d", getCategory(), name,productId, basePrice, calculateTax(), stockQuantity);
    }
}
