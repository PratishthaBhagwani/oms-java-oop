package com.oms.model.product;

public class Grocery extends Product{
    public Grocery(String productId, String name,
                   double basePrice, int stockQuantity) {
        super(productId, name, basePrice, stockQuantity);
    }
    @Override
    public String getCategory() { return "Grocery"; }
    public double calculateTax()
    {
        return 0;//No tax on groceries
    }
}
