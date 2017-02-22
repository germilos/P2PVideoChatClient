package datamodel.json;

import datamodel.pojo.User;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Lazar Davidovic
 */
public class JSONClientExit implements Serializable {

    static final long serialVersionUID = 4L;

    private User user;
    private Date date;

    public JSONClientExit(User user, Date date) {
        this.user = user;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
