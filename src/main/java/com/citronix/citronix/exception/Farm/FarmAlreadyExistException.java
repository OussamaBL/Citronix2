package com.citronix.citronix.exception.Farm;

public class FarmAlreadyExistException extends RuntimeException{
    public FarmAlreadyExistException(String msg){
        super(msg);
    }
}
