package com.oms.exceptions;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException(String email){
        super("Invalid email format: " + email);
    }
}
