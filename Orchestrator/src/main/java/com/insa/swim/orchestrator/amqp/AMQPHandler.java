/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.orchestrator.amqp;

import com.insa.swim.orchestrator.Application;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Alex
 */
public class AMQPHandler {
    
    private static final Logger logger = LogManager.getLogger(AMQPHandler.class);

    // Variables
    private static final String host = "localhost";
    private Channel channel;
    private String[] consumers = {"C1", "C2"};
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

    public AMQPHandler() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        this.connection = factory.newConnection();
        this.channel = this.connection.createChannel();
        this.configureChannel();
    }
    
    public void closeConnection() {
        try {
            this.channel.close();
            this.connection.close();
        } catch (IOException ex) {
            logger.error("[close connection] Unable to close AMQP connection \n" + ex.getMessage());
        }
    }
    
    /**
     * This function configures the channel to send and receive the different
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
     * This function publishes a message in a
     *
     * @param exchange
     * @param message
     * @throws IOException
     */
    private void sendMessage(String exchange, String routingKey, String message) throws IOException {
        // By default there is no routing key or property
        this.channel.basicPublish(exchange, routingKey, null, message.getBytes());
    }

    /**
     * Waits (blocking way) a message on the result channel
     *
     * @return received message
     * @throws ShutdownSignalException
     * @throws ConsumerCancelledException
     * @throws InterruptedException
     */
    public String receiveResultMessage() throws ShutdownSignalException, ConsumerCancelledException, InterruptedException {
        QueueingConsumer.Delivery delivery = this.resultsConsumer.nextDelivery();
        String message = new String(delivery.getBody());
        logger.debug("[result channel] Message received : " + message);
        return message;
    }

    /**
     * Sends a start message to all the consumers
     * @throws IOException 
     */
    public void sendStart() throws IOException {
        String startMsg = ("start");
        this.sendMessage(this.START_EXCHANGE_NAME, "", startMsg);
        logger.debug("[start channel] Message sent : " + startMsg);
    }

    /**
     * Sends a configuration message for a consumer
     * @param consumer consumer name
     * @param msg message to send
     * @throws IOException 
     */
    public void sendConf(String consumer, String msg) throws IOException {
        this.sendMessage(this.CONFIG_EXCHANGE_NAME, consumer, msg);
        logger.debug("[configuration channel] Message sent : " + msg);
    }

}
