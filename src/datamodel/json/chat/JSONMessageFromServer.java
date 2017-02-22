package datamodel.json.chat;

import datamodel.pojo.Message;
import datamodel.pojo.User;
import java.io.Serializable;

/**
 *
 * @author Lazar Davidovic
 */
public class JSONMessageFromServer implements Serializable {

    static final long serialVersionUID = 6L;

    private User from;
    private Message message;

    public JSONMessageFromServer(User from, Message message) {
        this.from = from;
        this.message = message;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
