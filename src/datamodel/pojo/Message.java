package datamodel.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Lazar Davidovic
 */
public class Message implements Serializable {

    static final long serialVersionUID = 2L;

    private String text;
    private Date sendDate;

    public Message(String text, Date sendDate) {
        this.text = text;
        this.sendDate = sendDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

}
