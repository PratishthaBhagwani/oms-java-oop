package com.oms.model.user;

public class Customer extends User {
    private double walletBalance;

    public Customer(String userId, String name, String email)
    {
        super(userId,name,email);
        this.walletBalance=0.0;
    }
    @Override
    public String getRole()
    {
        return "CUSTOMER";
    }
    public double getWalletBalance()
    {
        return walletBalance;
    }
    public void topUpWallet(double amount)
    {
        walletBalance+= amount;
    }
}
