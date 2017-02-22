package chat.client.video;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.net.InetSocketAddress;
import stream.agent.StreamServerAgent;

/**
 *
 * @author davidoviclazar
 */
public class StreamServer {

    InetSocketAddress adress;
    StreamServerAgent serverAgent;
    private Webcam webcam;

    public StreamServer(InetSocketAddress adress) {
        this.adress = adress;
    }

    public void run() {

        Webcam.setAutoOpenMode(true);
//        Webcam.setDriver(new GStreamerDriver());
        setWebcam(Webcam.getDefault());
        Dimension dimension = new Dimension(320, 240);
        getWebcam().setViewSize(dimension);

        serverAgent = new StreamServerAgent(getWebcam(), dimension);
        serverAgent.start(adress);

    }
    
    public void close() {
        System.out.println("Zatvaram stream server...");
        serverAgent.stop();
        getWebcam().close();
    }

    /**
     * @return the webcam
     */
    public Webcam getWebcam() {
        return webcam;
    }

    /**
     * @param webcam the webcam to set
     */
    public void setWebcam(Webcam webcam) {
        this.webcam = webcam;
    }
    
    
}
