/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.orchestrator.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Alex
 */
public class AMQPHandler {
    
    private static final Logger logger = LogManager.getLogger(AMQPHandler.class);

    // Variables
    private static final long RECEPTION_TIMEOUT = 10000;
    private static final String host = "localhost"; //replace by vm address
    /*private static final String username = "test";
    private static final String password = "test";*/
    private final Channel channel;
    private Connection connection = null;
    // Configuration settings
    private final String CONFIG_EXCHANGE_NAME = "confExchange";
    private final String CONFIG_EXCHANGE_TYPE = "direct";

    // Start settings
    private final String START_EXCHANGE_NAME = "startExchange";
    private final String START_EXCHANGE_TYPE = "fanout";

    // Results settings
    private final String RESULT_EXCHANGE_NAME = "resExchange";
    private final String RESULT_EXCHANGE_TYPE = "direct";
    private String RESULT_QUEUE;
    private QueueingConsumer resultsConsumer;

    /**
     * Constructs an AMQPHandler
     * @throws IOException 
     */
    public AMQPHandler() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        /**factory.setUsername(username);
        factory.setPassword(password);
        logger.debug("host " + host + "username " + username + "password " + password);*/
        //factory.setHost(host);
        this.connection = factory.newConnection();
        this.channel = this.connection.createChannel();
        this.configureChannel();
    }
    
    /**
     * Closes the channel and the connection
     */
    public void closeConnection() {
        try {
            this.channel.close();
            this.connection.close();
        } catch (IOException e) {
            logger.error("[close connection] Unable to close AMQP connection " + e.getMessage());
        }
    }
    
    /**
     * Configures the channel to send and receive the different
     * types of messages
     *
     * @param channel
     * @throws IOException
     */
    private void configureChannel() throws IOException {
        this.channel.exchangeDeclare(this.CONFIG_EXCHANGE_NAME, this.CONFIG_EXCHANGE_TYPE);
        this.channel.exchangeDeclare(this.START_EXCHANGE_NAME, this.START_EXCHANGE_TYPE);
        this.channel.exchangeDeclare(this.RESULT_EXCHANGE_NAME, this.RESULT_EXCHANGE_TYPE);
        this.RESULT_QUEUE = this.channel.queueDeclare().getQueue();
        this.channel.queueBind(this.RESULT_QUEUE, this.RESULT_EXCHANGE_NAME, "");

        this.resultsConsumer = new QueueingConsumer(this.channel);
        this.channel.basicConsume(this.RESULT_QUEUE, true, resultsConsumer);
    }
    
    /**
     * 
     * Publishes a message in a channel
     *
     * @param exchange exchange name
     * @param message message to send
     * @throws IOException
     */
    private void sendMessage(String exchange, String routingKey, String message) throws IOException {
        // By default there is no routing key or property
        this.channel.basicPublish(exchange, routingKey, null, message.getBytes());
        /*
        logger.trace("Message sent : " + message);
        for (byte m : message.getBytes()) {
            System.out.print(Byte.valueOf(m) + " ");
        }
        System.out.println();
        logger.debug("Message bytes : " );
        */
    }

    /**
     * Waits (blocking way) that a message arrives in the result channel
     *
     * @return received message
     * @throws ShutdownSignalException
     * @throws ConsumerCancelledException
     * @throws InterruptedException
     */
    public String receiveResultMessage() throws ShutdownSignalException, ConsumerCancelledException, InterruptedException {
        QueueingConsumer.Delivery delivery = this.resultsConsumer.nextDelivery(RECEPTION_TIMEOUT);
        if (delivery == null) {
            throw new ConsumerCancelledException();
        }
        String message = new String(delivery.getBody());
        logger.debug("[result channel] Message received : " + message);
        return message;
    }

    /**
     * Sends a start message
     * @throws IOException 
     */
    public void sendStart() throws IOException {
        String startMsg = ("start");
        this.sendMessage(this.START_EXCHANGE_NAME, "", startMsg);
        logger.debug("[start channel] Message sent : " + startMsg);
    }

    /**
     * Sends a configuration message to a specific consumer
     * @param consumer destination
     * @param msg message to send
     * @throws IOException 
     */
    public void sendConf(String consumer, String msg) throws IOException {
        msg = msg.replace('|', '+');
        this.sendMessage(this.CONFIG_EXCHANGE_NAME, consumer, msg);
        logger.debug("[configuration channel] Message sent to " + consumer + " : " + msg);
    }

}
