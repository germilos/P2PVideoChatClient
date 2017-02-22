/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.client.audio.c;

import chat.client.audio.c.AudioChannel;
import datamodel.SoundMessage;
import datamodel.pojo.Utils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author Milos
 */
public class AudioClient extends Thread {

    private Socket s;
    private ArrayList<AudioChannel> chs = new ArrayList<AudioChannel>();
    private MicThread st;

    public AudioClient(InetAddress serverIp, int serverPort) throws UnknownHostException, IOException {
        s = new Socket(serverIp, serverPort);
    }

    @Override
    public void run() {
        try {
            ObjectInputStream fromServer = new ObjectInputStream(s.getInputStream());  //create object streams with the server
            ObjectOutputStream toServer = new ObjectOutputStream(s.getOutputStream());
            try {
                Utils.sleep(100); //wait for the GUI microphone test to release the microphone
                setSt(new MicThread(toServer)); //creates a MicThread that sends microphone data to the server
                getSt().start(); //starts the MicThread
            } catch (Exception e) { //error acquiring microphone. causes: no microphone or microphone busy
                System.out.println("mic unavailable " + e);
            }
            for (;;) { //this infinite cycle checks for new data from the server, then sends it to the correct AudioChannel. if needed, a new AudioChannel is created
                
                if (s.getInputStream().available() > 0) { //we got something from the server (workaround: used available method from InputStream instead of the one from ObjetInputStream because of a bug in the JRE)
                    SoundMessage in = (SoundMessage) (fromServer.readObject()); //read message
                    //decide which audio channel should get this message
                    AudioChannel sendTo = null; 
                    for (AudioChannel ch : getChs()) {
                        if (ch.getChId() == in.getChId()) {
                            sendTo = ch;
                        }
                    }
                    if (sendTo != null) {
                        sendTo.addToQueue(in);
                    } else { //new AudioChannel is needed
                        AudioChannel ch = new AudioChannel(in.getChId());
                        ch.addToQueue(in);
                        ch.start();
                        getChs().add(ch);
                    }
                }else{ //see if some channels need to be killed and kill them
                    ArrayList<AudioChannel> killMe=new ArrayList<AudioChannel>();
                    for(AudioChannel c:getChs()) if(c.canKill()) killMe.add(c);
                    for(AudioChannel c:killMe){c.closeAndKill(); getChs().remove(c);}
                    Utils.sleep(1); //avoid busy wait
                }
            }
        } catch (Exception e) { //connection error
            System.out.println("client err " + e.toString());
        }
    }

    /**
     * @return the chs
     */
    public ArrayList<AudioChannel> getChs() {
        return chs;
    }

    /**
     * @param chs the chs to set
     */
    public void setChs(ArrayList<AudioChannel> chs) {
        this.chs = chs;
    }

    /**
     * @return the st
     */
    public MicThread getSt() {
        return st;
    }

    /**
     * @param st the st to set
     */
    public void setSt(MicThread st) {
        this.st = st;
    }

   
}
 
