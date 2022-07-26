/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;



/**
 *
 * @author hybof
 */
@Parameters(separators = "=")

public class CommandArgs {
    @Parameter(names={"--messageBroker", "-mb"}, description="Message Broker type", required = true)
    private String messageBroker ="hornetq";
    @Parameter(names={"--host", "-h"}, description="Message Broker Host", required = true)
    private String host ="localhost";
    @Parameter(names={"--port", "-p"}, description="Message Broker port", required = true)
    private String port ="4447";
    @Parameter(names={"--filepath", "-f"}, description="Filepath", required = true)
    private String filepath;


    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }


    
    static CommandArgs instance = new CommandArgs();
    public static CommandArgs fetchInstance() {
		return instance;
	}
	
	private CommandArgs() {
		
	}
	

    public String getMessageBroker() {
        return messageBroker;
    }

    public void setMessageBroker(String messageBroker) {
        this.messageBroker = messageBroker;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }



}
