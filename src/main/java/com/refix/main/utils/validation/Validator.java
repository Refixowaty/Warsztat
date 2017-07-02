package com.refix.main.utils.validation;


import com.vaadin.ui.*;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validator {
    public static boolean areNotEmptyFields(TextField... textFields){
        boolean valid = true;
        for (TextField textField : textFields) {
            if (textField.isEmpty()) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    public static boolean isNotEmptyArea(TextArea textArea){
        return !textArea.isEmpty();
    }

    public static boolean phoneNumberIsValid(TextField phoneField){
        String number = phoneField.getValue();
        return isNumber(number);
    }

    public static boolean isYear(TextField yearField){
        String year = yearField.getValue();
        return isNumber(year) && year.length() == 4;
    }

            private static boolean isNumber(String text){
                boolean result = true;
                char[] chars = text.toCharArray();
                for (char ch : chars) {
                    if (!Character.isDigit(ch)) {
                        result = false;
                        break;
                    }
                }
                return result;
            }

            private static boolean containsDigit(String text){
                boolean result = false;
                char[] chars = text.toCharArray();
                for (char ch : chars) {
                    if (Character.isDigit(ch)) {
                        result = true;
                        break;
                    }
                }
                return result;
            }




    public static boolean hasSelected(Component component){
        Set selectedItems = getItemsFromComponent(component);
        return !selectedItems.isEmpty();
    }

            private static Set getItemsFromComponent(Component component){
                Set selectedItems = null;
                if(component instanceof Grid)
                    selectedItems = ((Grid) component).getSelectedItems();
                if(component instanceof ListSelect)
                    selectedItems = ((ListSelect) component).getSelectedItems();
                return selectedItems;
            }

    public static boolean hasChosenComboBox(ComboBox comboBox){
        return !comboBox.isEmpty();
    }

    public static boolean hasChosenRadioButtonGroup(RadioButtonGroup radioButtonGroup){
        return !radioButtonGroup.isEmpty();
    }

    public static boolean isNumberFormat(TextField textField){
        String text = textField.getValue();
        return isNumber(text);
    }

    public static boolean isStringFormat(TextField... textFields){
        boolean valid = true;
        for (TextField textField : textFields) {
            if (containsDigit(textField.getValue())) {
                valid = false;
                break;
            }
        }
        return valid;
    }


    public static boolean isEmailFormat(TextField textField) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(textField.getValue());
        return matcher.find();
    }

}
