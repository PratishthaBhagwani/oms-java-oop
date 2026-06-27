package com.oms.exceptions;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String productName){
        super("Product '" + productName + "' is out of stock!");
    }
}
