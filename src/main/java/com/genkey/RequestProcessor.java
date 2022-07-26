/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.genkey;

import com.beust.jcommander.JCommander;
import org.apache.activemq.artemis.jms.client.ActiveMQDestination;
import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import static org.apache.activemq.artemis.utils.DestinationUtil.QUEUE_QUALIFIED_PREFIX;

/**
 *
 * @author hybof
 */
public class RequestProcessor {


    public static void listAllFiles(String path){
        CommandArgs argSpec = CommandArgs.fetchInstance();
        new JCommander(argSpec);
        String host =  argSpec.getHost();
        String port = argSpec.getPort();
        System.out.println("In listAllfiles(String path) method");
        try(Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        sendArtemisMessage(host, port, filePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void sendHornetqMessage(String host, String port, Path filePath) throws IOException {
        System.out.println("read file " + filePath);
        List<String> fileList = Files.readAllLines(filePath);
        for (String msg : fileList) {

            try{
                Properties prop = new Properties();
                prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
                prop.put(Context.PROVIDER_URL, "remote://" + host + ":" + port);
                prop.put(Context.SECURITY_PRINCIPAL, "hornetq");
                prop.put(Context.SECURITY_CREDENTIALS, "hornetqadmin");
                prop.put("jboss.naming.client.ejb.context", true);
                Context context = new InitialContext(prop);
                System.out.println("Context is " + context);
                ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
                Connection connection = connectionFactory.createConnection("hornetq", "hornetqadmin");
                Destination destination = (Destination) context.lookup("jms/queue/clientResponse");
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer sender = session.createProducer(destination);
                TextMessage jmsMessage = session.createTextMessage();
                System.out.println("This is the message going to the queue: "+ msg);
                jmsMessage.setText(msg);
                sender.send(jmsMessage);
                System.out.println("Sending Successful, Exiting.....");
                connection.close();
                System.exit(0);
            } catch (NamingException ex) {
                System.out.println("Failure resolving JNDI resources: " + ex.getLocalizedMessage());
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                System.out.println("Failure sending JMS message: " + ex.getLocalizedMessage());
                throw new RuntimeException(ex);
            }
        }
    }

    public static void sendArtemisMessage(String host, String port, Path filePath) throws IOException{
        System.out.println("read file " + filePath);
        List<String> fileList = Files.readAllLines(filePath);
        for (String msg: fileList
        ) {
            try {
                ConnectionFactory cf = new ActiveMQJMSConnectionFactory("tcp://" + host + ":" + port);
                String queueName = QUEUE_QUALIFIED_PREFIX + "ClientResponseQueue";
                Destination destination = ActiveMQDestination.fromPrefixedName(queueName);
                Connection connection = cf.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer sender = session.createProducer(destination);
                connection.start();
                System.out.println("This is the message going to the queue: "+ msg);
                TextMessage jmsMessage = session.createTextMessage();
                jmsMessage.setText(msg);
                System.out.println("Hi there, sending messages, be calm ");
                sender.send(jmsMessage);
                System.out.println("Sending Successful, Exiting.....");
                connection.close();
//                System.exit(0);
            } catch (Exception AccessDeniedException) {
                System.out.println("Failure sending JMS message: " + AccessDeniedException.getLocalizedMessage());
                throw new RuntimeException(AccessDeniedException);

            }

        }

    }




    }
















    



