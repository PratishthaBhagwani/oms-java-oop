package com.oms.model.product;

public class Clothing extends Product{
    private String size;


    public Clothing(String productId, String name,
                    double basePrice, int stockQuantity,
                    String size) {
        super(productId, name, basePrice, stockQuantity);
        this.size = size;
    }

    @Override
    public String getCategory() { return "Clothing"; }

    @Override
    public double calculateTax() {
        return getBasePrice() * TAX_RATE; // 18% GST
    }
    public String getSize() { return size; }
}


