package com.refix.main.utils.exceptions;


public class WrongPhoneNumberException extends Exception {
    public WrongPhoneNumberException(){
        super("Phone Number is wrong!");
    }
}
