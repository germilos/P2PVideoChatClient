package datamodel.json.stream;

import datamodel.pojo.User;
import java.io.Serializable;
import java.net.InetSocketAddress;

/**
 *
 * @author davidoviclazar
 */
public class JSONStreamClientUserData implements Serializable {

    static final long serialVersionUID = 12L;

    private User user;
    private InetSocketAddress adress;

    public JSONStreamClientUserData(User user, InetSocketAddress adress) {
        this.user = user;
        this.adress = adress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public InetSocketAddress getAdress() {
        return adress;
    }

    public void setAdress(InetSocketAddress adress) {
        this.adress = adress;
    }

}
