/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.pojo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 *
 * @author Milos
 */
public class Utils {
    public static void sleep(int ms){
        try {Thread.sleep(ms);} catch (InterruptedException ex) {}
    }
    public static String getExternalIP(){
        try {
            URL myIp=new URL("http://checkip.dyndns.org/");
            BufferedReader in=new BufferedReader(new InputStreamReader(myIp.openStream()));
            String s=in.readLine();
            return s.substring(s.lastIndexOf(":")+2,s.lastIndexOf("</body>"));
        } catch (Exception ex) {
            return "error "+ex;
        }     
    }
    public static String getInternalIP(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            return "error";
        }
    }
}
