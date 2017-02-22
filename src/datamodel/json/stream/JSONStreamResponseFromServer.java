package datamodel.json.stream;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author davidoviclazar
 */
public class JSONStreamResponseFromServer implements Serializable {

    static final long serialVersionUID = 11L;

    private List<JSONStreamClientUserData> confirmedClients;

    public JSONStreamResponseFromServer(List<JSONStreamClientUserData> confirmedClients) {
        this.confirmedClients = confirmedClients;
    }

    public List<JSONStreamClientUserData> getConfirmedClients() {
        return confirmedClients;
    }

    public void setConfirmedClients(List<JSONStreamClientUserData> confirmedClients) {
        this.confirmedClients = confirmedClients;
    }

}
