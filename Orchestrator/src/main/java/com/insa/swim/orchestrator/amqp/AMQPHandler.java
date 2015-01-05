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

/**
 *
 * @author Alex
 */
public class AMQPHandler {

    // Variables
    private static final String host = "localhost";
    private Channel channel;
    private String[] consumers = {"C1", "C2"};
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
        Connection connection = factory.newConnection();
        this.channel = connection.createChannel();
        this.configureChannel();
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
        System.out.println("Message sent : " + message);
    }

    /**
     * This function waits (blocking way) a message on the result channel
     *
     * @return
     * @throws ShutdownSignalException
     * @throws ConsumerCancelledException
     * @throws InterruptedException
     */
    private String receiveResultMessage() throws ShutdownSignalException, ConsumerCancelledException, InterruptedException {
        QueueingConsumer.Delivery delivery = this.resultsConsumer.nextDelivery();
        String message = new String(delivery.getBody());
        System.out.println("Message received : " + message);
        return message;
    }

    public void sendStart() throws IOException {
        String startMsg = ("this is the start message");
        this.sendMessage(this.START_EXCHANGE_NAME, "", startMsg);
    }

    public void sendConf(String consumer, String msg) throws IOException {
        this.sendMessage(this.CONFIG_EXCHANGE_NAME, consumer, msg);
    }

}
