package com.citronix.citronix.exception.Farm;

public class FarmNotFoundException extends RuntimeException{
    public FarmNotFoundException(String msg){
        super(msg);
    }
}
