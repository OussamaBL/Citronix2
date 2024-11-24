package com.citronix.citronix.exception.Sale;

public class SaleNotFoundException extends RuntimeException{
    public SaleNotFoundException(String msg){
        super(msg);
    }
}
