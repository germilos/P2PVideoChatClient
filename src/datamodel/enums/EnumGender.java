package datamodel.enums;

/**
 *
 * @author Lazar Davidovic
 */
public enum EnumGender {
    MALE,
    FEMALE;

    @Override
    public String toString() {
        return name();
    }
}
