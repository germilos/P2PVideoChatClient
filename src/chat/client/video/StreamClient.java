package chat.client.video;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stream.agent.StreamClientAgent;
import stream.agent.ui.SingleVideoDisplayWindow;
import stream.handler.StreamFrameListener;

/**
 *
 * @author davidoviclazar
 */
public class StreamClient {

    private Dimension dimension;
    private SingleVideoDisplayWindow displayWindow;
    protected final static Logger logger = LoggerFactory.getLogger(chat.client.video.StreamClient.class);
    StreamClientAgent clientAgent;

    InetSocketAddress adress;

    public StreamClient(Dimension dimension, SingleVideoDisplayWindow displayWindow, InetSocketAddress adress) {
        this.dimension = dimension;
        this.displayWindow = displayWindow;
        this.adress = adress;
    }

    public void run() {
        //setup the videoWindow
        getDisplayWindow().setVisible(true);

        //setup the connection
        logger.info("setup dimension :{}", dimension);
        clientAgent = new StreamClientAgent(new StreamFrameListenerIMPL(), dimension);
        clientAgent.connect(adress);
    }

    /**
     * @return the displayWindow
     */
    public SingleVideoDisplayWindow getDisplayWindow() {
        return displayWindow;
    }

    /**
     * @param displayWindow the displayWindow to set
     */
    public void setDisplayWindow(SingleVideoDisplayWindow displayWindow) {
        this.displayWindow = displayWindow;
    }

    protected class StreamFrameListenerIMPL implements StreamFrameListener {

        private volatile long count = 0;

        @Override
        public void onFrameReceived(BufferedImage image) {
            logger.info("frame received :{}", count++);
            getDisplayWindow().updateImage(image);
        }
    }
    public void close() {
        System.out.println("Zatvaram stream klijenta...");
        clientAgent.stop();
    }
    
    
}
