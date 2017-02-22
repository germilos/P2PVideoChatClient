package systemoperations;

import java.io.IOException;
import java.net.Socket;
import systemoperations.propertie.SOLoadChatPropertie;
import systemoperations.propertie.SOSaveChatPropertie;

/**
 *
 * @author Lazar Davidovic
 */
public class SOInitializeConnectionTCP {

    public static Socket execute(String serverAdress, int serverPort) {
        Object[] adressAndPort = null;
        Socket clientSocket = null;

        if (serverAdress != null && serverPort >= 1024) {
            SOSaveChatPropertie.execute(serverAdress, serverPort);
            adressAndPort = new Object[2];
            adressAndPort[0] = serverAdress;
            adressAndPort[1] = serverPort;
        } else {
            adressAndPort = SOLoadChatPropertie.execute();
        }

        try {
            //clientSocket = new Socket(serverAdress, serverPort);
            clientSocket = new Socket((String) adressAndPort[0], (int) adressAndPort[1]);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return clientSocket;

    }
}
