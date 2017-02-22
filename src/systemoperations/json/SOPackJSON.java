package systemoperations.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Lazar Davidovic
 */
public class SOPackJSON {

    public static String execute(Object dataToJson) {

        // Gson create
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Pretty print
        String JSONMessage = gson.toJson(dataToJson);

        return JSONMessage;

    }

}
