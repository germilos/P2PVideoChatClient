package datamodel.json;

import datamodel.enums.EnumGender;
import datamodel.pojo.User;
import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author Lazar Davidovic
 */
public class JSONClientUserData implements Serializable {

    static final long serialVersionUID = 3L;

    private User user;
    private EnumGender gender;
    private InetAddress IPAdress;
    private int portUDP;

    public JSONClientUserData(User user, EnumGender gender, InetAddress IPAdress, int port) {
        this.user = user;
        this.gender = gender;
        this.IPAdress = IPAdress;
        this.portUDP = port;
    }

    public EnumGender getGender() {
        return gender;
    }

    public void setGender(EnumGender gender) {
        this.gender = gender;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public InetAddress getIPAdress() {
        return IPAdress;
    }

    public int getPortUDP() {
        return portUDP;
    }
}
