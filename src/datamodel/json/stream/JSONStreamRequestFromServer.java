package datamodel.json.stream;

import java.io.Serializable;

/**
 *
 * @author davidoviclazar
 */
public class JSONStreamRequestFromServer implements Serializable {

    static final long serialVersionUID = 9L;

    private JSONStreamRequestFromClient jsonStreamRequestFromClient;

    public JSONStreamRequestFromServer(JSONStreamRequestFromClient jsonStreamRequestFromClient) {
        this.jsonStreamRequestFromClient = jsonStreamRequestFromClient;
    }

    public JSONStreamRequestFromClient getJsonStreamRequestFromClient() {
        return jsonStreamRequestFromClient;
    }

    public void setJsonStreamRequestFromClient(JSONStreamRequestFromClient jsonStreamRequestFromClient) {
        this.jsonStreamRequestFromClient = jsonStreamRequestFromClient;
    }

}
