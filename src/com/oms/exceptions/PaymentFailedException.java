package com.oms.exceptions;

public class PaymentFailedException extends RuntimeException{
    public PaymentFailedException(String paymentMethod){
        super("Payment failed via " + paymentMethod + "!");
    }
    }


