package com.refix.main.utils.exceptions;


public class EmptyFieldException extends Exception {
    public EmptyFieldException(){
        super("Field cannot be empty!");
    }
}
