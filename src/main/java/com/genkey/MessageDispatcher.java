///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.genkey;
//
//import java.io.File;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.Properties;
//import javax.jms.*;
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import org.apache.activemq.artemis.jms.client.ActiveMQDestination;
//import static org.apache.activemq.artemis.jms.client.ActiveMQDestination.QUEUE_QUALIFIED_PREFIX;
//import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
//
///**
// *
// * @author hybof
// */
//public class MessageDispatcher {
//
//    public static void sendHornetqMessage(String host, String port) {
//
//
//        RequestProcessor listFiles = new RequestProcessor();
//        System.out.println("-------------------------------------------------");
//        System.out.println("Reading Files");
//        listFiles.listAllFiles("C:\\Users\\sowah\\Documents\\testfolder");
//
//
//        try {
//            Properties prop = new Properties();
//            prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
//            prop.put(Context.PROVIDER_URL, "remote://" + host + ":" + port);
//            prop.put(Context.SECURITY_PRINCIPAL, "hornetq");
//            prop.put(Context.SECURITY_CREDENTIALS, "hornetqadmin");
//            prop.put("jboss.naming.client.ejb.context", true);
//            Context context = new InitialContext(prop);
//            System.out.println("Context is " + context);
//            ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
//            Connection connection = connectionFactory.createConnection("hornetq", "hornetqadmin");
//            Destination destination = (Destination) context.lookup("jms/queue/clientResponse");
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            MessageProducer sender = session.createProducer(destination);
//            TextMessage jmsMessage = session.createTextMessage();
////            Path path = Paths.get("C:\\Users\\sowah\\Documents\\testfolder");
////            Path filePath = path.resolve(path);
////            List<String> message = listFiles.readContent(filePath);
////            for (String msg: message
////            ) {
////                System.out.println("Sending message to the Hornet queue...");
////                jmsMessage.setText(msg);
////                sender.send(jmsMessage);
////            }
////            jmsMessage.setText(RequestProcessor.requestProcessor("\"C:\\\\Users\\\\sowah\\\\Documents\\\\file1.txt"));
////            sender.send(jmsMessage);
//            System.out.println("Sending Successful, Exiting.....");
//            connection.close();
//            System.exit(0);
//        } catch (NamingException ex) {
//            System.out.println("Failure resolving JNDI resources: " + ex.getLocalizedMessage());
//            throw new RuntimeException(ex);
//        } catch (Exception ex) {
//            System.out.println("Failure sending JMS message: " + ex.getLocalizedMessage());
//            throw new RuntimeException(ex);
//        }
//    }
//
//
//
//
//
//    }
