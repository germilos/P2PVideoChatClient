/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.json.audio;

import java.io.Serializable;

/**
 *
 * @author Milos
 */
public class JSONAudioRequestFromServer implements Serializable {
    
    private static final long serialVersionUID = 14L;
    
    private JSONAudioRequestFromClient jsonAudioRequestFromClient;

    public JSONAudioRequestFromServer(JSONAudioRequestFromClient audioRequest) {
        this.jsonAudioRequestFromClient = audioRequest;
    }

    /**
     * @return the jsonAudioRequestFromClient
     */
    public JSONAudioRequestFromClient getJsonAudioRequestFromClient() {
        return jsonAudioRequestFromClient;
    }

    /**
     * @param jsonAudioRequestFromClient the jsonAudioRequestFromClient to set
     */
    public void setJsonAudioRequestFromClient(JSONAudioRequestFromClient jsonAudioRequestFromClient) {
        this.jsonAudioRequestFromClient = jsonAudioRequestFromClient;
    }

   
    
    
}
