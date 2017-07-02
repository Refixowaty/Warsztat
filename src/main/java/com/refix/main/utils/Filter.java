package com.refix.main.utils;

import com.refix.main.entity.Customer;
import com.refix.main.entity.Part;

import java.util.Arrays;


public class Filter {

    public static boolean nameFilter(Part part, String fieldName){
        fieldName = fieldName.toLowerCase();
        String name = part.getName().toLowerCase();
        return match(name, fieldName);
    }

    public static boolean producerFilter(Part part, String fieldProducer){
        fieldProducer = fieldProducer.toLowerCase();
        String producer = part.getProducer().toLowerCase();
        return match(producer, fieldProducer);
    }

    public static boolean categoryFilter(Part part, String fieldCategory){
        fieldCategory = fieldCategory.toLowerCase();
        String category = part.getCategory().toLowerCase();
        return match(category, fieldCategory);
    }

    public static boolean customerNameAndLastNameFilter(Customer customer, String fieldNameAndLastName){
        fieldNameAndLastName = fieldNameAndLastName.toLowerCase();
        String name = customer.getName().toLowerCase();
        String lastName = customer.getLastName().toLowerCase();
        String fullName = name + " " + lastName;
        return match(fullName, fieldNameAndLastName);
    }

    private static boolean match(String originalText, String filterText){
        boolean match;
        if (hasSpace(originalText)) {
            match = matchIfHasSpace(originalText, filterText);
        }
        else {
            match = matchIfHasNotSpace(originalText, filterText);
        }
        if (hasSpace(originalText) && hasSpace(originalText)) {
            match = matchIfHasNotSpace(originalText, filterText);
        }
        return match;
    }

    private static boolean matchIfHasSpace(String originalText, String filterText){
        String[] splitText;
        splitText = originalText.split("\\s+");

        for (String s : Arrays.asList(splitText)) {
            if (s.contains(filterText))
                return true;
        }
        return false;
    }

    private static boolean matchIfHasNotSpace(String originalText, String filterText){
        if (originalText.contains(filterText))
            return true;
        else
            return false;
    }

    private static boolean hasSpace(String text){
        for (char singleLetter : text.toCharArray()) {
            if (Character.isWhitespace(singleLetter)) {
                return true;
            }
        }
        return false;
    }
}
