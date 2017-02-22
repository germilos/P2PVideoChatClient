package chat.client;

import com.google.gson.JsonSyntaxException;
import com.xuggle.mediatool.IMediaDebugListener;
import controller.ChatController;
import datamodel.SoundMessage;
import datamodel.StreamData;
import datamodel.enums.EnumMessageType;
import datamodel.json.JSONClientExit;
import datamodel.json.JSONClientUserData;
import datamodel.json.chat.JSONMessageFromClient;
import datamodel.json.chat.JSONMessageFromServer;
import datamodel.json.JSONUsersList;
import datamodel.json.audio.JSONAudioClientUserData;
import datamodel.json.audio.JSONAudioRequestFromClient;
import datamodel.json.audio.JSONAudioRequestFromServer;
import datamodel.json.audio.JSONAudioResponseFromClient;
import datamodel.json.audio.JSONAudioResponseFromServer;
import datamodel.json.stream.JSONStreamRequestFromClient;
import datamodel.json.stream.JSONStreamRequestFromServer;
import datamodel.json.stream.JSONStreamResponseFromClient;
import datamodel.json.stream.JSONStreamResponseFromServer;
import datamodel.pojo.User;
import datamodel.pojo.Utils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import systemoperations.SOInitializeConnectionTCP;

/**
 *
 * @author Lazar Davidovic
 */
public class ChatClient implements InterfaceChatClient, Runnable {

    static ObjectOutputStream clientOutputStreamTCP = null;
    static ObjectInputStream clientInputStreamTCP = null;

    //Universal
    private InetAddress serverIPAdress;
    private InetAddress clientIPAdress;
    private ArrayList<User> availableUsers;

    //TCP
    private int serverPortTCP;
    private Socket clientSocketTCP = null;
    StreamData dataTCP;

    //UDP
    private int clientPortUDP;
    DatagramSocket clientSocketUDP;
    byte[] dataFromServer = new byte[1024];
    DatagramPacket packetFromServer;
    StreamData dataUDP;

    //Support
    boolean endConnection;
    

    public ChatClient() {
    }

    public ChatClient(InetAddress serverAdress, int serverPortTCP, int clientPortUDP) {
        try {
            this.clientIPAdress = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.serverIPAdress = serverAdress;
        this.serverPortTCP = serverPortTCP;
        this.clientPortUDP = clientPortUDP;
        availableUsers = new ArrayList<>();
    }

    @Override
    public void initializeConnection() {

        //Console info
        System.out.println("TCP port servera: " + serverPortTCP);
        System.out.println("UDP port klijenta: " + clientPortUDP);
        System.out.println("IP adresa servera: " + serverIPAdress.getHostAddress());

        //TCP
        clientSocketTCP = SOInitializeConnectionTCP.execute(serverIPAdress.getHostAddress(), serverPortTCP);

        //UDP
        try {
//            clientSocketUDP = new DatagramSocket(clientPortUDP, clientIPAdress);
             clientSocketUDP  = new DatagramSocket(clientPortUDP);
//        InetSocketAddress address = new InetSocketAddress(clientPortUDP);
//        clientSocketUDP.bind(address);

//            clientSocketUDP = new DatagramSocket();
//           InetSocketAddress address = new InetSocketAddress(serverIPAdress.getHostName(), clientPortUDP);
//            clientSocketUDP.bind(address);
            System.out.println("Izabran UDP port: " + clientSocketUDP.getLocalPort());
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

        try {
            clientOutputStreamTCP = new ObjectOutputStream(clientSocketTCP.getOutputStream());
            clientInputStreamTCP = new ObjectInputStream(clientSocketTCP.getInputStream());

//            //New Thread for UDP socket
//            new Thread(() -> {
//                listenUDPSocket();
//            }).start();
//            new Thread(new ChatClient()).start();
            new Thread(this).start();

            Thread t = new Thread() {
                public void run() {

                    listenUDPSocket();
                }
            };

            t.start();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        //Gson gson = new GsonBuilder().create();

        try {
            while (!endConnection) {
                
                //TCP
                if ((dataTCP = (StreamData) clientInputStreamTCP.readObject()) != null) {
                    switch (dataTCP.getType()) {
                        case SUCCESS_CONNECT:
//                            if (new Gson().fromJson((String) dataTCP.getData(), Boolean.class) == true) {
//                                System.out.println("Konekcija je uspesna!");
//                            }

                            if ((Boolean) dataTCP.getData() == true) {
                                System.out.println("Konekcija je uspesna!");

                            }
                            break;

                        case USERS_LIST:

                            forwardOnlineUsersList(dataTCP);

                            break;

                        case SUCCESS_FORWARD:
//                            if (new Gson().fromJson((String) dataTCP.getData(), Boolean.class) == true) {
//                                System.out.println("Poruka je uspesno prosledjena!");
//                            }

                            if ((Boolean) dataTCP.getData() == true) {
                                System.out.println("Poruka je uspesno prosledjena odabranim klijentima!");

                            }
                            break;

                        case TEXT_FROM_SERVER:
//                            JSONMessageFromServer jsonServerMessage = new Gson().fromJson((String) dataTCP.getData(), JSONMessageFromServer.class);
                            JSONMessageFromServer jsonServerMessage = (JSONMessageFromServer) dataTCP.getData();

                            System.out.println("Poruka od korisnika " + jsonServerMessage.getFrom().getUserName() + ": " + jsonServerMessage.getMessage().getText());

                            ChatController.fillHistory(jsonServerMessage);
                            break;

                        //UDP
//                        case USERS_LIST:
//                            break;
                        case AUDIO_REQUEST_FROM_SERVER:
                            System.out.println("Dobio request");
                            ChatController.processRequest((JSONAudioRequestFromServer) dataTCP.getData());
                            break;
                            
                        case AUDIO_RESPONSE_FROM_SERVER:
//                            JSONAudioResponseFromServer jsonAudioResponseFromServer = (JSONAudioResponseFromServer) dataTCP.getData();
//                            System.out.println("Dobio response");
//                            for (JSONAudioClientUserData user : jsonAudioResponseFromServer.getConfirmedClients() ) {
//                                String korisnik = user.getUser().getUserName();
//                                System.out.println("" + korisnik);
//                        }
//                            new AudioThread(jsonAudioResponseFromServer.getConfirmedClients()).start();
                            ChatController.processResponseAudio((JSONAudioResponseFromServer) dataTCP.getData());
                            break;
                        case VIDEO_STREAM_REQUEST_FROM_SERVER:
                            ChatController.processRequest((JSONStreamRequestFromServer) dataTCP.getData());
                            break;

                        case VIDEO_STREAM_RESPONSE_FROM_SERVER:
                            ChatController.processResponse((JSONStreamResponseFromServer) dataTCP.getData());
                            break;

                        case END:
//                            JSONClientExit jsonClientExit = new Gson().fromJson((String) dataTCP.getData(), JSONClientExit.class);
                            JSONClientExit jsonClientExit = (JSONClientExit) dataTCP.getData();

                            ChatController.loginAgain(jsonClientExit);

                            endConnection = true;
                            break;
                    }
                }

//                System.out.println(serverStreamMessage);
//                if (serverStreamMessage.indexOf("--end--") == 0) {
//                    endConnection = true;
//                    return;
//                }
            }
            clientSocketTCP.close();

        } catch (JsonSyntaxException | IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
   

    public void sendConnectionData(User user) {
        JSONClientUserData jsonNewUserData = new JSONClientUserData(user, user.getGender(), clientIPAdress, clientPortUDP);

        try {
            StreamData streamData = new StreamData(EnumMessageType.NEW_USER_DATA, jsonNewUserData);

            //to server
            clientOutputStreamTCP.writeObject(streamData);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void sendMessage(JSONMessageFromClient message) {
        if (!endConnection) {
//            StreamData messageToOther = new StreamData(EnumMessageType.TEXT_FROM_CLIENT, SOPackJSON.execute(message));
            StreamData messageToOther = new StreamData(EnumMessageType.TEXT_FROM_CLIENT, message);

            try {
                //to server
                clientOutputStreamTCP.writeObject(messageToOther);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Socket getClientSocketTCP() {
        return clientSocketTCP;
    }

    public void closeConnectionInfo(User user) {
        JSONClientExit exitClient = new JSONClientExit(user, new Date());
//        StreamData streamExit = new StreamData(EnumMessageType.END, SOPackJSON.execute(exitClient));
        StreamData streamExit = new StreamData(EnumMessageType.END, exitClient);

        try {
            clientOutputStreamTCP.writeObject(streamExit);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void forwardOnlineUsersList(StreamData data) {
//        JSONUsersList usersList = new Gson().fromJson((String) data.getData(), JSONUsersList.class);
        getAvailableUsers().clear();
        JSONUsersList usersList = (JSONUsersList) data.getData();

        getAvailableUsers().addAll(usersList.getUsers());
        System.out.println("Lista online korisnika: ");

        for (User onlineUser : getAvailableUsers()) {
            System.out.println(onlineUser.getUserName());
        }
        ChatController.fillUserList(getAvailableUsers());
    }

    private void listenUDPSocket() {
        try {
            while (true) {
                packetFromServer = new DatagramPacket(dataFromServer, dataFromServer.length);

                //UDP
                clientSocketUDP.receive(packetFromServer);
                dataFromServer = packetFromServer.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(dataFromServer);
                ObjectInputStream is = new ObjectInputStream(in);
                dataUDP = (StreamData) is.readObject();
                forwardOnlineUsersList(dataUDP);
            }

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void sendStreamResponseFromClient(JSONStreamResponseFromClient JSONStreamResponseFromClient) {
        if (!endConnection) {
            StreamData streamResponse = new StreamData(EnumMessageType.VIDEO_STREAM_RESPONSE_FROM_CLIENT, JSONStreamResponseFromClient);

            try {
                //to server
                clientOutputStreamTCP.writeObject(streamResponse);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendStreamRequestFromClient(JSONStreamRequestFromClient jSONStreamRequestFromClient) {
        if (!endConnection) {
            StreamData streamRequest = new StreamData(EnumMessageType.VIDEO_STREAM_REQUEST_FROM_CLIENT, jSONStreamRequestFromClient);

            try {
                //to server
                clientOutputStreamTCP.writeObject(streamRequest);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendAudioRequestFromClient(JSONAudioRequestFromClient jsonAudioRequest) {
        if (!endConnection) {
            StreamData audioRequest = new StreamData(EnumMessageType.AUDIO_REQUEST_FROM_CLIENT, jsonAudioRequest);

            try {
                clientOutputStreamTCP.writeObject(audioRequest);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendAudioResponseFromClient(JSONAudioResponseFromClient JSONAudioResponseFromClient) {
        if (!endConnection) {
            StreamData audioResponse = new StreamData(EnumMessageType.AUDIO_RESPONSE_FROM_CLIENT, JSONAudioResponseFromClient);

            try {
                //to server
                clientOutputStreamTCP.writeObject(audioResponse);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @return the availableUsers
     */
    public ArrayList<User> getAvailableUsers() {
        return availableUsers;
    }

    /**
     * @param availableUsers the availableUsers to set
     */
    public void setAvailableUsers(ArrayList<User> availableUsers) {
        this.availableUsers = availableUsers;
    }



}
