package systemoperations.propertie;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Lazar Davidovic
 */
public class SOSaveChatPropertie {

    private static OutputStream output = null;

    private static Properties propertie = new Properties();

    public static void execute(String serverAdress, int serverPort) {
        try {
            File file = new File("config.properties");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            output = new FileOutputStream("config.properties");
            propertie.clear();
            propertie.setProperty("serverAdress", serverAdress);
            propertie.setProperty("serverPort", Integer.toString(serverPort));

            propertie.store(output, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
