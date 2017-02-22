package ui.validator;

import datamodel.enums.EnumFieldTypes;

/**
 *
 * @author Lazar Davidovic
 */
public interface InterfaceValidator {

    void validate(Object value, EnumFieldTypes type) throws Exception;
}
