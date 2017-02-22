package ui.validator.impl;

import datamodel.enums.EnumFieldTypes;
import ui.validator.InterfaceValidator;

/**
 *
 * @author Lazar Davidovic
 */
public class Validator implements InterfaceValidator {

    @Override
    public void validate(Object value, EnumFieldTypes type) throws Exception {
        switch (type) {
            case SERVER_ADRESS:
                checkServerAdress(value);
                break;
            case SERVER_PORT:
                checkServerPort(value);
                break;
            case USER_NAME:
                checkUserName(value);
                break;
            case GENDER:
                checkGender(value);
                break;

        }
    }

    private void checkServerAdress(Object value) throws Exception {
        String s = (String) value;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '^') {
                throw new Exception("(" + s.charAt(i) + ") nije podrzan karakter!");
            }

        }

    }

    private void checkServerPort(Object value) throws Exception {
        String s;
        if ((s = (String) value).length() <= 4) {
            for (int i = 0; i < 4; i++) {
                if (!Character.isDigit(s.charAt(i))) {
                    throw new Exception("(" + s.charAt(i) + ") nije cifra!");
                }

            }
        }

    }

    private void checkUserName(Object value) throws Exception {

    }

    private void checkGender(Object value) throws Exception {

    }

}
