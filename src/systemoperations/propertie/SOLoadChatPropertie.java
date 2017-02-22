package systemoperations.propertie;

import datamodel.Consts;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Lazar Davidovic
 */
public class SOLoadChatPropertie {

    private static InputStream input = null;

    private static Properties propertie = new Properties();

    public static Object[] execute() {
        String serverAdress = null;
        Integer serverPort = null;
        Object[] adressAndPort = new Object[2];

        try {
            if ((input = new FileInputStream("config.properties")) != null) {
                propertie.load(input);
                serverAdress = propertie.getProperty("serverAdress");
                serverPort = Integer.parseInt(propertie.getProperty("serverPort"));

            }
        } catch (Exception e) {
            //Default adress and port
            serverAdress = Consts.SERVER_ADRESS;
            serverPort = Consts.SERVER_PORT;
            e.printStackTrace();
        }
        adressAndPort[0] = serverAdress;
        adressAndPort[1] = serverPort;
        return adressAndPort;

    }

}
