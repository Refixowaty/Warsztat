package com.refix.main.utils.validation;

import com.refix.main.utils.exceptions.*;
import com.vaadin.ui.*;


public class ExceptionThrower {
    public static void checkIfFieldsAreNotEmpty(TextField... textFields) throws EmptyFieldException {
        if (!Validator.areNotEmptyFields(textFields))
            throw new EmptyFieldException();
    }

    public static void checkIfAreaIsNotEmpty(TextArea textArea) throws EmptyFieldException {
        if (!Validator.isNotEmptyArea(textArea))
            throw new EmptyFieldException();
    }

    public static void checkEmail(TextField emailField) throws WrongEmailException{
        if (!Validator.isEmailFormat(emailField))
            throw new WrongEmailException();
    }

    public static void checkPhoneNumber(TextField phoneField) throws WrongPhoneNumberException{
        if (!Validator.phoneNumberIsValid(phoneField))
            throw new WrongPhoneNumberException();
    }

    public static void checkYear(TextField yearField) throws WrongYearFormatException{
        if (!Validator.isYear(yearField))
            throw new WrongYearFormatException();
    }

    public static void checkIfHasAnySelected(Component component) throws NotSelectedException{
        if (!Validator.hasSelected(component))
            throw new NotSelectedException();
    }

    public static void checkIfHasAnyChosen(ComboBox comboBox) throws NotChosenException{
        if(!Validator.hasChosenComboBox(comboBox))
            throw new NotChosenException();
    }

    public static void checkCostFormat(TextField costField) throws WrongCostFormatException{
        if(!Validator.isNumberFormat(costField))
            throw new WrongCostFormatException();
    }

    public static void checkNamesFormat(TextField... textFields) throws WrongNamesFormatException{
        if(!Validator.isStringFormat(textFields))
            throw new WrongNamesFormatException();
    }

    public static void checkIfHasAnySelectedRadioButton(RadioButtonGroup radioButtonGroup) throws NotChosenException{
        if(!Validator.hasChosenRadioButtonGroup(radioButtonGroup))
            throw new NotChosenException();
    }


}
