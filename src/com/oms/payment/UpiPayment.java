package com.oms.payment;

public class UpiPayment implements PaymentStrategy {
    private String upiId;

    public UpiPayment(String upiId) {
        this.upiId = upiId;
    }
    @Override
    public boolean pay(double amount) {
        System.out.printf("  Paying Rs.%.2f via UPI ID : %s%n", amount, upiId);
        return true;
    }
    @Override
    public String getMethodName()
    {
        return "UPI";
    }
}
