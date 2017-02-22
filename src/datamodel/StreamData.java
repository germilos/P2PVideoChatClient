package datamodel;

import datamodel.enums.EnumMessageType;
import java.io.Serializable;

/**
 *
 * @author Lazar Davidovic
 */
public class StreamData implements Serializable {

    private EnumMessageType type;
    private Object data;

    public StreamData(EnumMessageType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public EnumMessageType getType() {
        return type;
    }

    public void setType(EnumMessageType type) {
        this.type = type;
    }

}
