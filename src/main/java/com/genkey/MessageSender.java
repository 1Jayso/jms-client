/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey;

import com.beust.jcommander.JCommander;
import java.io.IOException;
import java.nio.file.Path;



/**
 *
 * @author hybof
 */
public class MessageSender {
    
    public static void main(String args[]) throws IOException {
//        requestProcessor("C:\\Users\\sowah\\Documents\\file1.txt");
       CommandArgs argSpec = CommandArgs.fetchInstance();
		new JCommander(argSpec, args);
        System.out.println("Connecting to message broker");
        String messageBroker = argSpec.getMessageBroker();
        System.out.println("message Broker is "+messageBroker);
        System.out.println("message Broker host is " +argSpec.getHost());


        RequestProcessor listFiles = new RequestProcessor();
        System.out.println("-------------------------------------------------");
        System.out.println("Reading Files...");
        listFiles.listAllFiles(argSpec.getFilepath());

        switch (messageBroker) {
            case "artemis":
                RequestProcessor.sendArtemisMessage(argSpec.getHost(), argSpec.getPort(), Path.of(argSpec.getFilepath()));
                break;
            default:
                RequestProcessor.sendHornetqMessage(argSpec.getHost(), argSpec.getPort(), Path.of(argSpec.getFilepath()));
        }



    }
    
}
