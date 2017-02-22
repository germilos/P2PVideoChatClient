package controller;

import chat.client.ChatClient;
import chat.client.audio.c.AudioClient;
import chat.client.audio.s.AudioServer;
import chat.client.video.StreamClient;
import chat.client.video.StreamServer;
import datamodel.enums.EnumGender;
import datamodel.enums.EnumMessageType;
import datamodel.json.JSONClientExit;
import datamodel.json.audio.*;
import datamodel.json.chat.JSONMessageFromClient;
import datamodel.json.chat.JSONMessageFromServer;
import datamodel.json.stream.JSONStreamClientUserData;
import datamodel.json.stream.JSONStreamRequestFromClient;
import datamodel.json.stream.JSONStreamRequestFromServer;
import datamodel.json.stream.JSONStreamResponseFromClient;
import datamodel.json.stream.JSONStreamResponseFromServer;
import datamodel.pojo.Message;
import datamodel.pojo.User;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import stream.agent.ui.SingleVideoDisplayWindow;
import ui.components.panels.JPanelChat;
import ui.dialogs.JDialogConnection;
import ui.forms.JFrameMain;

/**
 *
 * @author Lazar Davidovic
 */
public class ChatController {

    private static JDialogConnection dialogConnection;
    private static JFrameMain mainForm;

    private static User user;
    private static Message message;

    private static ChatClient client;
    private static StreamClient sc = null;
    private static ArrayList<StreamClient> clients = null;
    private static StreamServer ss;
    private static AudioServer as = null;
    private static AudioClient ac = null;
    
    //Datagram socket
//    private static DatagramSocket socketUDP = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                showMainForm();
                showLoginDialog();

                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    public void run() {
                        try {
                            client.closeConnectionInfo(user);

                            client.getClientSocketTCP().close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }));

            }
        });
    }

    private static void createChatClient(InetAddress serverAdress, int serverPortTCP, int clientPortUDP) {

        client = new ChatClient(serverAdress, serverPortTCP, clientPortUDP);
        client.initializeConnection();

    }

    private static void showLoginDialog() {
        dialogConnection = new JDialogConnection(mainForm, true);
        dialogConnection.setLocationRelativeTo(mainForm.getContentPane());
        dialogConnection.setVisible(true);

    }

    private static void showMainForm() {
        mainForm = new JFrameMain(user);
        mainForm.setVisible(true);
        mainForm.setLocationRelativeTo(null);
    }

    public static void sendMessage(List<String> to, String userMessage) {

        Message message = new Message(userMessage, new Date());

        List<User> selectedUsers = new ArrayList<>();

        for (String userName : to) {
            selectedUsers.add(new User(userName, "", "", null, user.getGender()));
        }
        JSONMessageFromClient jsonUserMassage = new JSONMessageFromClient(user, selectedUsers, message);
        client.sendMessage(jsonUserMassage);
    }

    public static void offApplication() {
        int option = JOptionPane.showConfirmDialog(mainForm.getContentPane(),
                "Da li ZAISTA zelite da izadjete iz aplikacije?", "Izlazak", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                client.closeConnectionInfo(user);

                client.getClientSocketTCP().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }

    public static void showAboutWindow() {
        JOptionPane.showMessageDialog(mainForm.getContentPane(), "Autori: Lazar Davidović i Miloš Gerun, Verzija 1.0",
                "O programu ChatClient", JOptionPane.INFORMATION_MESSAGE);
    }

    // 1.
    public static void fillData() {
        try {
            InetSocketAddress clientIPAddress = new InetSocketAddress(InetAddress.getLocalHost(), 5555);
            String serverAdress = dialogConnection.getjPanelServer().getjTextFieldServerAdress().getText().trim();
            int serverPortTCP = Integer.parseInt(dialogConnection.getjPanelServer().getjTextFieldServerPortTCP().getText().trim());
            int clientPortUDP = Integer.parseInt(dialogConnection.getjPanelServer().getjTextFieldServerPortUDP().getText().trim());

            createChatClient(InetAddress.getByName(serverAdress), serverPortTCP, clientPortUDP);

            String userName = dialogConnection.getjPanelLogin().getUserNameValue();
            EnumGender gender = dialogConnection.getjPanelLogin().getRadioButtonsValue();

            createUser(userName, clientIPAddress, gender);

            //client.sendConnectionData(SOPackJSON.execute(newUserData));
            client.sendConnectionData(user);

            mainForm.setTitle(user.getUserName());

            dialogConnection.dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void createUser(String userName, InetSocketAddress address, EnumGender gender) {
        user = new User(userName, "", "", address, gender);

    }

    public static void fillUserList(List<User> users) {
        mainForm.fillList(users);
    }

    public static void fillHistory(JSONMessageFromServer jsonServerMessage) {
        User from = jsonServerMessage.getFrom();
        Message message = jsonServerMessage.getMessage();

        mainForm.fillHistory(EnumMessageType.TEXT_FROM_SERVER, from.getUserName(), user.getUserName(), message.getText());
    }

    public static void loginAgain(JSONClientExit jsonClientExit) {
        int option = JOptionPane.showConfirmDialog(mainForm.getContentPane(),
                "Da li zelite ponovo da se prijavite?", "Prekid veze", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.NO_OPTION) {
            showLoginDialog();

            System.exit(0);
        }
    }

    public static void showUserInfo() {
        JOptionPane.showMessageDialog(mainForm.getContentPane(), "Korisnik: " + user.getUserName() + '\n' + "Pol: " + user.getGender(),
                "Informacije o korisniku", JOptionPane.INFORMATION_MESSAGE);
    }

    public static String returnActiveUserName() {
        return user.getUserName();
    }

    public static void connectAudioStream(List<String> selectedUserNames, boolean upnp) {
        List<User> selectedUsers = new ArrayList<>();
        DatagramSocket socket;
        InetSocketAddress adress = null;
        for (String userName : selectedUserNames) {
            selectedUsers.add(new User(userName, "", "", null, user.getGender()));
        }

        try {
            socket = new DatagramSocket();
            adress = new InetSocketAddress(InetAddress.getLocalHost(), socket.getLocalPort());
            System.out.println("Audio host on " + InetAddress.getLocalHost());
   
            
            new Thread() { //start server in new thread

                @Override
                public void run() {
                    try {
                        as = new AudioServer(socket.getLocalPort(), upnp);
                        
                        System.out.println("Otvorio sound server");
                    } catch (Exception ex) {
                        System.out.println("Nije otvorio server audio");
                        ex.printStackTrace();
                        System.exit(0);
                    }
                }
            }.start();
            
            ac = new AudioClient(InetAddress.getLocalHost(), socket.getLocalPort());
            ac.start();
            
            System.out.println("Konektovao klijenta");
            
            System.out.println("Trying to connect audio stream");
            JSONAudioRequestFromClient jSONAudioRequestFromClient = new JSONAudioRequestFromClient(new JSONAudioClientUserData(user, adress), selectedUsers);
            client.sendAudioRequestFromClient(jSONAudioRequestFromClient);

            System.out.println("Poslat connect za audio");

        } catch (IOException ex) {
//                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    public static void connectVideoStream(List<String> selectedUserNames) {
        List<User> selectedUsers = new ArrayList<>();
        DatagramSocket socket;
        InetSocketAddress address = null;
        for (String userName : selectedUserNames) {
            for (User user : client.getAvailableUsers()) {
                if (user.getUserName().equals(userName)) {
                    selectedUsers.add(new User(userName, "", "", user.getAddress(), user.getGender()));
                }
            }
        }
        //server
        try {
            socket = new DatagramSocket(5555);
            address = new InetSocketAddress(InetAddress.getLocalHost(), socket.getLocalPort());
            System.out.println("Streaming server port: " + address.getPort());
            clients = new ArrayList<>();

            //run server
            ss = new StreamServer(address);
            ss.run();

            //run client
            for (User userFrom : selectedUsers) {
                StreamClient newClient = null;
                InetSocketAddress addressFrom = userFrom.getAddress();
                System.out.println("Prikazivanje kamere korisnika na: " + address);
                System.out.println("Prikazivanje kamere korisnika na FROM : " + addressFrom);
                newClient = new StreamClient(new Dimension(320, 240), 
                        new SingleVideoDisplayWindow(user.getUserName() + " => " + 
                                userFrom.getUserName(), new Dimension(320, 240)), addressFrom);
                clients.add(newClient);

            }
            for (StreamClient client : clients) {
                client.run();
            }
            JSONStreamRequestFromClient jSONStreamRequestFromClient = new JSONStreamRequestFromClient(new JSONStreamClientUserData(user, address), selectedUsers);
            client.sendStreamRequestFromClient(jSONStreamRequestFromClient);

        } catch (IOException ex) {
//                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    public static void stopStream() {
        ss.getWebcam().close();
        if (clients != null) {
            for (StreamClient client : clients) {
                client.close();
                client.getDisplayWindow().close();
            }
        }
        if (sc != null) {
            sc.close();
            sc.getDisplayWindow().close();
        }
        ss = null;
        sc = null;
        clients = null;
        if (as != null) {
            as.setExit(true);
        }
        as = null;
        if (ac != null) {
            ac.getSt().getMic().close();
        }
        ac =  null;
    }

    public static void processRequest(JSONAudioRequestFromServer data) {
        User from = data.getJsonAudioRequestFromClient().getUserData().getUser();

        JSONAudioResponseFromClient JSONAudioResponseFromClient = new JSONAudioResponseFromClient(false);

        int option = JOptionPane.showConfirmDialog(mainForm.getContentPane(), "Potvrda audio/video poziva od korisnika: " + from.getUserName(), "A/V poziv", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {

            JSONAudioResponseFromClient = new JSONAudioResponseFromClient(true);

            try {
                new AudioClient(data.getJsonAudioRequestFromClient().
                        getUserData().getAdress().getAddress(),
                        data.getJsonAudioRequestFromClient().
                        getUserData().getAdress().getPort()).start();
                System.out.println("Otvorio novog klijenta na " + data.getJsonAudioRequestFromClient().
                        getUserData().getAdress().getAddress());
            } catch (IOException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            }

                   
                }
            //client
//            System.out.println("Audio client port: " + data.getJsonAudioRequestFromClient().getUserData().getAdress().getPort());
//          new StreamClient(new Dimension(320, 240), new SingleVideoDisplayWindow(user.getUserName() + " => " + from.getUserName(), new Dimension(320, 240)), data.getJsonStreamRequestFromClient().getUserData().getAdress()).run();

        
        
        client.sendAudioResponseFromClient(JSONAudioResponseFromClient);
        System.out.println("Poslao response serveru " + JSONAudioResponseFromClient.isConfirmation());

    }

    public static void processRequest(JSONStreamRequestFromServer data) {

        JSONStreamResponseFromClient JSONStreamResponseFromClient = new JSONStreamResponseFromClient(false);
        User from = data.getJsonStreamRequestFromClient().getUserData().getUser();
//        int option = JOptionPane.showConfirmDialog(mainForm.getContentPane(), "Potvrda video poziva od korisnika: " + from.getUserName(), "Video poziv", JOptionPane.YES_NO_OPTION);
//        if (option == JOptionPane.YES_OPTION) {

        try {
            DatagramSocket socket = new DatagramSocket(5555);

            InetSocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), socket.getLocalPort());
            JSONStreamResponseFromClient = new JSONStreamResponseFromClient(true);

            //client
            System.out.println("Streaming client port: " + data.getJsonStreamRequestFromClient().getUserData().getAdress().getPort());
            ss = new StreamServer(serverAddress);
                   ss.run();
            System.out.println("Konektovanje na server " + data.getJsonStreamRequestFromClient().getUserData().getAdress());
            System.out.println("Konektovanje na server Userdata " + data.getJsonStreamRequestFromClient().getUserData().getUser().getAddress());
            sc = new StreamClient(new Dimension(320, 240), new SingleVideoDisplayWindow
                (user.getUserName() + " => " + from.getUserName(), new Dimension(320, 240)),
                    data.getJsonStreamRequestFromClient().getUserData().getUser().getAddress());
            sc.run();
            mainForm.getjPanelChat().getjButtonAudioVideo().setText("Prekini konekciju");
        } catch (Exception ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        }

        client.sendStreamResponseFromClient(JSONStreamResponseFromClient);

    }

    public static void processResponse(JSONStreamResponseFromServer jsonStreamResponseFromServer) {
        for (JSONStreamClientUserData clientUserData : jsonStreamResponseFromServer.getConfirmedClients()) {
            System.out.println(clientUserData.getUser().getUserName() + " je potvrdio zahtev za video konekcijom.");
        }
    }

    public static void processResponseAudio(JSONAudioResponseFromServer jsonAudioResponseFromServer) {
        for (JSONAudioClientUserData clientUserData : jsonAudioResponseFromServer.getConfirmedClients()) {
            System.out.println(clientUserData.getUser().getUserName() + " je potvrdio zahtev za audio konekcijom.");
        } 
    }
        

//    public static int getFreePort() {
//        try {
//            socketUDP = new DatagramSocket();
//        } catch (SocketException ex) {
//            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return socketUDP.getLocalPort();
//    }
}
