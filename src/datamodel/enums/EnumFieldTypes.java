package datamodel.enums;

/**
 *
 * @author Lazar Davidovic
 */
public enum EnumFieldTypes {
    SERVER_ADRESS,
    SERVER_PORT,
    USER_NAME,
    GENDER;

    @Override
    public String toString() {
        return name();
    }
}
