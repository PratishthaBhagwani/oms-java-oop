package com.oms.factory;
import com.oms.model.product.*;
import java.util.UUID;

public class ProductFactory {
    private ProductFactory() {
    }

    public static Electronics createElectronics(String name, double basePrice, int stockQuantity, int warrantyMonths) {
        String id = generateId("ELEC");
        return new Electronics(id, name, basePrice, stockQuantity, warrantyMonths);
    }

    public static Clothing createClothing(String name, double basePrice, int stockQuantity, String size) {
        String id = generateId("CLTH");
        return new Clothing(id, name, basePrice, stockQuantity, size);
    }

    public static Grocery createGrocery(String name, double basePrice, int stockQuantity) {
    String id=generateId("GROC");
    return new Grocery(id, name, basePrice, stockQuantity);
    }
    private static String generateId(String prefix){
        return prefix+"-"+UUID.randomUUID().toString().substring(0,8).toUpperCase();

    }
}