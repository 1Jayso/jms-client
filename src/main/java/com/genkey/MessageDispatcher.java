/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey;

import java.util.Properties;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author hybof
 */
public class MessageDispatcher {

    public static void sendHornetqMessage(String host, String port) {
        try {
            Properties prop = new Properties();
            prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            prop.put(Context.PROVIDER_URL, "remote://" + host + ":" + port);
            prop.put(Context.SECURITY_PRINCIPAL, "hornetq");
            prop.put(Context.SECURITY_CREDENTIALS, "hornetqadmin");
//            prop.put("jboss.naming.client.ejb.context", true);
            Context context = new InitialContext(prop);
            System.out.println("Context is " + context);

            QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
            Queue queue = (Queue) context.lookup("jms/queue/clientResponseQueue");
            context.close();

//            creating Objects
            QueueConnection connection = factory.createQueueConnection("hornetq", "hornetqadmin");
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender sender = session.createSender(queue);

            String messageText = "Hello, it's JMS here!";
            TextMessage message = session.createTextMessage(messageText);
            sender.send(message);

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

    public static void sendArtemisMessage(String host, String port) {
        try {
            Properties prop = new Properties();
            prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory");
            prop.put(Context.PROVIDER_URL, "tcp://" + host + ":" + port);
            prop.put(Context.SECURITY_PRINCIPAL, "artemis");
            prop.put(Context.SECURITY_CREDENTIALS, "artemisadmin");
            Context context = new InitialContext(prop);
            System.out.println("Context is " + context);
            ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");
            Queue orderQueue = (Queue) context.lookup("queues/clientResponseQueue");
            Connection connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(orderQueue);
            connection.start();
            TextMessage message = session.createTextMessage("This is an order");
            producer.send(message);
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
