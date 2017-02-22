/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.pojo;

import javax.swing.JOptionPane;

/**
 *
 * @author Milos
 */
public class ErrorMessage {
    public static void main(String args[]){
        JOptionPane.showMessageDialog(new JOptionPane(),"This is a library, not a program.\nPlease run VoiceChat_Server or VoiceChat_Client");
    }
}
