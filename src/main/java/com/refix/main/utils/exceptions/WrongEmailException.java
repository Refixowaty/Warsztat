package com.refix.main.utils.exceptions;


public class WrongEmailException extends Exception {
    public WrongEmailException(){
        super("E-mail is wrong!");
    }
}
