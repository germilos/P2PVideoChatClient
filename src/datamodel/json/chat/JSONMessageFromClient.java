package datamodel.json.chat;

import datamodel.pojo.Message;
import datamodel.pojo.User;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Lazar Davidovic
 */
public class JSONMessageFromClient implements Serializable {

    static final long serialVersionUID = 5L;

    private User from;
    private List<User> to;
    private Message message;

    public JSONMessageFromClient(User from, List<User> to, Message message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public List<User> getTo() {
        return to;
    }

    public void setTo(List<User> to) {
        this.to = to;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
