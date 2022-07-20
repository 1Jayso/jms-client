/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey;

/**
 *
 * @author hybof
 */
public class MessageSender {
    
    public static void main(String args[]){
        CommandArgs commandArgs = new CommandArgs();
        System.out.println("Connecting to message broker");
        String messageBroker = commandArgs.getMessageBroker();
        if("hornetq".equals(messageBroker)){
            MessageDispatcher.sendHornetqMessage(commandArgs.getHost(), commandArgs.getPort()); 
        }
        
    }
    
}
