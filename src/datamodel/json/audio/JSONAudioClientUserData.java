/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.json.audio;

import datamodel.pojo.User;
import java.io.Serializable;
import java.net.InetSocketAddress;

/**
 *
 * @author Milos
 */
public class JSONAudioClientUserData implements Serializable {
    
    private static final long serialVersionUID = 11L;
    
    private User user;
    private InetSocketAddress adress;

    public JSONAudioClientUserData() {
    }

    public JSONAudioClientUserData(User user, InetSocketAddress adress) {
        this.user = user;
        this.adress = adress;
    }

    
    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the adress
     */
    public InetSocketAddress getAdress() {
        return adress;
    }

    /**
     * @param adress the adress to set
     */
    public void setAdress(InetSocketAddress adress) {
        this.adress = adress;
    }
    
    
    
}
