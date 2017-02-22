package datamodel.json;

import datamodel.pojo.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lazar Davidovic
 */
public class JSONUsersList implements Serializable {

    static final long serialVersionUID = 7L;

    private List<User> users;
    private Date date;

    public JSONUsersList(List<User> users, Date date) {
        this.users = users;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        String onlineUsers = null;
        for (User user : users) {
            onlineUsers += user.getUserName() + '\n';
        }
        return onlineUsers;

    }

}
