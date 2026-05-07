package com.oms.model.user;

public class Seller extends User{
    private String storeName;

    public Seller(String userId, String name, String email, String storeName) {
        super(userId, name, email);
        this.storeName = storeName;
    }
    @Override
    public String getRole() {
        return "SELLER";
    }
    public String getStoreName() { return storeName; }
}
