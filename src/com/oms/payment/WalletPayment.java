package com.oms.payment;

public class WalletPayment implements PaymentStrategy{
    private String walletId;
    private double walletBalance;

    public WalletPayment(String walletId, double walletBalance)
    {
        this.walletId=walletId;
        this.walletBalance = walletBalance;
    }
    @Override
    public boolean pay(double amount)
    {
        if(walletBalance>=amount){
            walletBalance-=amount;
            System.out.printf("  Paying Rs.%.2f via Wallet: %s | " +
                            "Remaining Balance: Rs.%.2f%n",
                    amount, walletId, walletBalance);
            return true;
        }
        else {
            System.out.println(" Wallet balance insufficient!");
            return false;
        }
    }
    @Override
    public String getMethodName()
    {
        return "Wallet";
    }

}
