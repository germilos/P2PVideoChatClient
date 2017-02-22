/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.json.audio;

import datamodel.pojo.User;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Milos
 */
public class JSONAudioRequestFromClient implements Serializable {
    
    private static final long serialVersionUID = 54L;
    
    private JSONAudioClientUserData userData;
    private List<User> to;

    public JSONAudioRequestFromClient(JSONAudioClientUserData userData, List<User> to) {
        this.userData = userData;
        this.to = to;
    }

    
    /**
     * @return the userData
     */
    public JSONAudioClientUserData getUserData() {
        return userData;
    }

    /**
     * @param userData the userData to set
     */
    public void setUserData(JSONAudioClientUserData userData) {
        this.userData = userData;
    }

    /**
     * @return the to
     */
    public List<User> getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(List<User> to) {
        this.to = to;
    }
    
    
    
}
