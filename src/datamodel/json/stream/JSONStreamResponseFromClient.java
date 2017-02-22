package datamodel.json.stream;

import java.io.Serializable;
import java.net.InetSocketAddress;

/**
 *
 * @author davidoviclazar
 */
public class JSONStreamResponseFromClient implements Serializable {

    static final long serialVersionUID = 10L;

    private boolean confirmation;
    private InetSocketAddress adress;

    public JSONStreamResponseFromClient(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public JSONStreamResponseFromClient(boolean confirmation, InetSocketAddress adress) {
        this.confirmation = confirmation;
        this.adress = adress;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public InetSocketAddress getAdress() {
        return adress;
    }

    public void setAdress(InetSocketAddress adress) {
        this.adress = adress;
    }

}
