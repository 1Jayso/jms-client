/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey;

import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author hybof
 */
public class MessageDispatcher {

   public static void sendHornetqMessage(String host, String port) {
        Session session = null;
        try {
            Properties prop = new Properties();
            prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            prop.put(Context.PROVIDER_URL, "remote://" + host + ":" + port);
            prop.put(Context.SECURITY_PRINCIPAL, "hornetq");
            prop.put(Context.SECURITY_CREDENTIALS, "hornetqadmin");
            prop.put("jboss.naming.client.ejb.context", true);

            Context context = new InitialContext(prop);
            System.out.println("Context is " + context);
            ConnectionFactory cf = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
            Connection connection = cf.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) context.lookup("/queue/clientResponseQueue");
            MessageProducer sender = session.createProducer(queue);
            ObjectMessage jmsMessage = session.createObjectMessage();
            jmsMessage.setObject("Hi there");
            sender.send(jmsMessage);
            System.out.println("Sent message: " + jmsMessage.toString());
        } catch (NamingException ex) {
            System.out.println("Failure resolving JNDI resources: " + ex.getLocalizedMessage());
            throw new RuntimeException(ex);

        } catch (JMSException ex) {
            System.out.println("Failure sending JMS message: " + ex.getLocalizedMessage());
            throw new RuntimeException(ex);
        }

    }
    
    public static void sendArtemisMessage(){
        System.out.println("Artemis");
    }
    
    public static void sendActiveMQMessage(){
        System.out.println("ActiveMQ");
    }
    

}
