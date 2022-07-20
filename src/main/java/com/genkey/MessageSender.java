/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey;

import com.beust.jcommander.JCommander;

/**
 *
 * @author hybof
 */
public class MessageSender {
    
    public static void main(String args[]){
       CommandArgs argSpec = CommandArgs.fetchInstance();
		new JCommander(argSpec, args);
        System.out.println("Connecting to message broker");
        String messageBroker = argSpec.getMessageBroker();
        System.out.println("message Broker is "+messageBroker);
        if("hornetq".equals(messageBroker)){
            MessageDispatcher.sendHornetqMessage(argSpec.getHost(), argSpec.getPort()); 
        }
        if("artemis".equals(messageBroker)){
            MessageDispatcher.sendArtemisMessage();
        }
        if("activeMQ".equals(messageBroker)){
            MessageDispatcher.sendActiveMQMessage();
        }
        
    }
    
}
