package stream.audio.server;

/**
 *
 * @author Milos
 */
public class Log {

    private static String log = "";

    public static void add(String s) {
        log += s + "\n";
    }

    public static String get() {
        return log;
    }
}
