package chat.client.audio;

import datamodel.pojo.SoundMessage;
import util.Utils;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import org.teleal.cling.UpnpService;
import org.teleal.cling.UpnpServiceImpl;
import org.teleal.cling.support.igd.PortMappingListener;
import org.teleal.cling.support.model.PortMapping;
import stream.audio.server.ClientAudioConnection;

/**
 *
 * @author Milos
 */
public class AudioServer {

    private ArrayList<SoundMessage> broadCastQueue = new ArrayList<SoundMessage>();
    private ArrayList<ClientAudioConnection> clients = new ArrayList<ClientAudioConnection>();
    private int port;
    private boolean exit = false;

    private UpnpService u; //when upnp is enabled, this points to the upnp service

    public void addToBroadcastQueue(SoundMessage m) { //add a message to the broadcast queue. this method is used by all ClientConnection instances
        try {
            broadCastQueue.add(m);
        } catch (Throwable t) {
            //mutex error, try again
            Utils.sleep(1);
            addToBroadcastQueue(m);
        }
    }
    private ServerSocket s;

    public AudioServer(int port, boolean upnp) throws Exception {
        this.port = port;
        if (upnp) {
//            Log.add("Setting up NAT Port Forwarding...");
            //first we need the address of this machine on the local network
            try {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            } catch (SocketException ex) {
//                Log.add("Network error");
                throw new Exception("Network error");
            }
            String ipAddress = null;
            Enumeration<NetworkInterface> net = null;
            try {
                net = NetworkInterface.getNetworkInterfaces();
            } catch (SocketException e) {
//                Log.add("Not connected to any network");
                throw new Exception("Network error");
            }

            while (net.hasMoreElements()) {
                NetworkInterface element = net.nextElement();
                Enumeration<InetAddress> addresses = element.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = addresses.nextElement();
                    if (ip instanceof Inet4Address) {
                        if (ip.isSiteLocalAddress()) {
                            ipAddress = ip.getHostAddress();
                            break;
                        }
                    }
                }
                if (ipAddress != null) {
                    break;
                }
            }
            if (ipAddress == null) {
//                Log.add("Not connected to any IPv4 network");
                throw new Exception("Network error");
            }
            u = new UpnpServiceImpl(new PortMappingListener(new PortMapping(port, ipAddress, PortMapping.Protocol.TCP)));
            u.getControlPoint().search();
        }
        try {
            s = new ServerSocket(port); //listen on specified port
//            Log.add("Port " + port + ": server started");
        } catch (IOException ex) {
//            Log.add("Server error " + ex + "(port " + port + ")");
            throw new Exception("Error " + ex);
        }
        while (!exit) {
            new BroadcastThread().start(); //create a BroadcastThread and start it
            while (!exit) { //accept all incoming connection
                try {
                    Socket c = s.accept();
                    ClientAudioConnection cc = new ClientAudioConnection(this, c); //create a ClientConnection thread
                    cc.start();
                    addToClients(cc);
//                Log.add("new client " + c.getInetAddress() + ":" + c.getPort() + " on port " + port);
                } catch (IOException ex) {
                }
            }
        }
    }

    private void addToClients(ClientAudioConnection cc) {
        try {
            clients.add(cc); //add the new connection to the list of connections
        } catch (Throwable t) {
            //mutex error, try again
            Utils.sleep(1);
            addToClients(cc);
        }
    }

    /**
     * @return the exit
     */
    public boolean isExit() {
        return exit;
    }

    /**
     * @param exit the exit to set
     */
    public void setExit(boolean exit) {
        this.exit = exit;
    }

    /**
     * broadcasts messages to each ClientConnection, and removes dead ones
     */
    private class BroadcastThread extends Thread {

        public BroadcastThread() {
        }

        @Override
        public void run() {
            for (;;) {
                try {
                    ArrayList<ClientAudioConnection> toRemove = new ArrayList<ClientAudioConnection>(); //create a list of dead connections
                    for (ClientAudioConnection cc : clients) {
                        if (!cc.isAlive()) { //connection is dead, need to be removed
//                            Log.add("dead connection closed: " + cc.getInetAddress() + ":" + cc.getPort() + " on port " + port);
                            toRemove.add(cc);
                        }
                    }
                    clients.removeAll(toRemove); //delete all dead connections
                    if (broadCastQueue.isEmpty()) { //nothing to send
                        Utils.sleep(10); //avoid busy wait
                        continue;
                    } else { //we got something to broadcast
                        SoundMessage m = broadCastQueue.get(0);
                        for (ClientAudioConnection cc : clients) { //broadcast the message
                            if (cc.getChId() != m.getChId()) {
                                cc.addToQueue(m);
                            }
                        }
                        broadCastQueue.remove(m); //remove it from the broadcast queue
                    }
                } catch (Throwable t) {
                    //mutex error, try again
                }
            }
        }
    }
}
