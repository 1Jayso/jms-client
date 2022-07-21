/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey;

import com.beust.jcommander.JCommander;

import static com.genkey.MessageDispatcher.sendHornetqMessage;

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
        System.out.println("message Broker host is " +argSpec.getHost());

//        String hornetq = "hornetq";
        switch (messageBroker) {
            case "activeMQ":
                MessageDispatcher.sendActiveMQMessage();
                break;
            case "artemis":
                MessageDispatcher.sendArtemisMessage();
                break;
            default:
                sendHornetqMessage(argSpec.getHost(), argSpec.getPort());
        }



    }
    
}
