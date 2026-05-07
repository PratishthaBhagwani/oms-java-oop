package com.oms.model.product;

public class Electronics extends Product{


    private int warrantyMonths;
    public Electronics(String productId,String name,
                       double basePrice, int stockQuantity,
                       int warrantyMonths){
        super(productId,name,basePrice,stockQuantity);
        this.warrantyMonths=warrantyMonths;
    }
    @Override
    public String getCategory() { return "Electronics"; }
    @Override
    public double calculateTax()
    {
        return getBasePrice()*TAX_RATE; }
    public int getWarrantyMonths() { return warrantyMonths; }


}
