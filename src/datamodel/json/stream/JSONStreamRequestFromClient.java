package datamodel.json.stream;

import datamodel.pojo.User;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author davidoviclazar
 */
public class JSONStreamRequestFromClient implements Serializable {

    static final long serialVersionUID = 8L;

    private JSONStreamClientUserData userData;
    private List<User> to;

    public JSONStreamRequestFromClient(JSONStreamClientUserData userData, List<User> to) {
        this.userData = userData;
        this.to = to;
    }

    public JSONStreamClientUserData getUserData() {
        return userData;
    }

    public void setUserData(JSONStreamClientUserData userData) {
        this.userData = userData;
    }

    public List<User> getTo() {
        return to;
    }

    public void setTo(List<User> to) {
        this.to = to;
    }

}
