package com.oms.payment;

public class CashOnDelivery implements PaymentStrategy{
    @Override
    public boolean pay(double amount){
        System.out.printf("  COD selected — " + "Pay Rs.%.2f on delivery!%n", amount);
        return true;
    }
    @Override
    public String getMethodName() { return "COD"; }
}
