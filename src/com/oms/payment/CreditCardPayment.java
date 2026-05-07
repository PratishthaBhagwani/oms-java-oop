package com.oms.payment;

public class CreditCardPayment implements PaymentStrategy{
    private String cardNumber;
    private String cardHolder;

    public CreditCardPayment(String cardNumber, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    @Override
    public boolean pay(double amount)
    {
        System.out.printf("  Paying Rs.%.2f via Credit Card: %s (Holder: %s)%n",
                amount, cardNumber, cardHolder);
        return true;
    }
    @Override
    public String getMethodName()
    {
        return "Credit Card";
    }
}
