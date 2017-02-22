/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.client.audio.s;

/**
 *
 * @author Milos
 */
public class Log {
    private static String log="";
    public static void add(String s){log+=s+"\n";}
    public static String get(){return log;}
}

