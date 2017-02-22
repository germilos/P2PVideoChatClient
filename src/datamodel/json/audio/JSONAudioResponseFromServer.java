/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.json.audio;

import datamodel.json.stream.JSONStreamClientUserData;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Milos
 */
public class JSONAudioResponseFromServer implements Serializable {
    
    private static final long serialVersionUID = 12L;
    
    private List<JSONAudioClientUserData> confirmedClients;

    public JSONAudioResponseFromServer(List<JSONAudioClientUserData> confirmedClients) {
        this.confirmedClients = confirmedClients;
    }

    /**
     * @return the confirmedClients
     */
    public List<JSONAudioClientUserData> getConfirmedClients() {
        return confirmedClients;
    }

    /**
     * @param confirmedClients the confirmedClients to set
     */
    public void setConfirmedClients(List<JSONAudioClientUserData> confirmedClients) {
        this.confirmedClients = confirmedClients;
    }
    
    
}
