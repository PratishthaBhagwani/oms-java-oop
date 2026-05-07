package com.oms.factory;
import com.oms.model.user.*;
import java.util.UUID;

public class UserFactory {
    private UserFactory() {}
    public static Customer createCustomer(String name,String email){
        String id=generateId("CUST");
        return new Customer(id, name, email);
    }
    public static Admin createAdmin(String name, String email){
        String id=generateId("ADMN");
        return new Admin(id,name,email);
    }
    public static Seller createSeller(String name, String email, String storeName){
        String id=generateId("SELR");
        return new Seller(id,name,email,storeName);
    }
    private static String generateId(String prefix){
        return prefix+"-"+UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }
}
