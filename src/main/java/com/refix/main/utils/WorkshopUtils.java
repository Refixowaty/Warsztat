package com.refix.main.utils;

import com.refix.main.entity.Customer;
import com.refix.main.entity.Worker;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class WorkshopUtils {

    public static String showWorkerNameLastName(Worker worker){
        String result = worker.getName() + " " + worker.getLastName();
        return result;
    }

    public static String showCustomerNameLastName(Customer customer){
        String result = customer.getName() + " " + customer.getLastName();
        return result;
    }

    public static String getFormattedDate(Timestamp date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        String newDate = simpleDateFormat.format(date);
        return newDate;
    }

    public static boolean convertPartValueToBoolean(String value){
        return value.equals("New");
    }

    public static String convertPartBooleanToValue(boolean partIsNew){
        if (partIsNew)
            return "New";
        else
            return "Used";
    }

}
