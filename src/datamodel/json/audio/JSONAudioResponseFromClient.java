/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.json.audio;

import java.io.Serializable;
import java.net.InetSocketAddress;

/**
 *
 * @author Milos
 */
public class JSONAudioResponseFromClient implements Serializable {
    
    private static final long serialVersionUID = 15L;
    
    private boolean confirmation;
    private InetSocketAddress adress;

    public JSONAudioResponseFromClient(boolean confirmation) {
        this.confirmation = confirmation;
    }

    
    public JSONAudioResponseFromClient(boolean confirmation, InetSocketAddress adress) {
        this.confirmation = confirmation;
        this.adress = adress;
    }

    /**
     * @return the confirmation
     */
    public boolean isConfirmation() {
        return confirmation;
    }

    /**
     * @param confirmation the confirmation to set
     */
    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
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
