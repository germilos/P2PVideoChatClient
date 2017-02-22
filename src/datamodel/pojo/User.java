package datamodel.pojo;

import datamodel.enums.EnumGender;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 *
 * @author Lazar Davidovic
 */
public class User implements Serializable {

    static final long serialVersionUID = 1L;

    private String userName;
    private String firstName;
    private String lastName;
    private InetSocketAddress address;
    private EnumGender gender;

    public User(String userName, String firstName, String lastName, InetSocketAddress address, EnumGender gender) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public EnumGender getGender() {
        return gender;
    }

    public void setGender(EnumGender gender) {
        this.gender = gender;
    }

    /**
     * @return the address
     */
    public InetSocketAddress getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    
}
