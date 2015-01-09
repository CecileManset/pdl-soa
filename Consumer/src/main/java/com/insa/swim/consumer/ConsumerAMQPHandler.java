/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.consumer;

import java.io.IOException;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pdlsoa
 */
public class ConsumerAMQPHandler {

    public static final Logger logger = LogManager.getLogger("ConsumerAMQP");

    private Connection connection;
    protected final String consumerName;
    private Channel channel;
    private String host;
    // Configuration settings
    private final String CONFIG_EXCHANGE_NAME = "confExchange";
    private final String CONFIG_EXCHANGE_TYPE = "direct";
    private String CONFIG_QUEUE = "configuration";
    private QueueingConsumer configurationConsumer;
    // Start settings
    private final String START_EXCHANGE_NAME = "startExchange";
    private final String START_EXCHANGE_TYPE = "fanout";
    private String START_QUEUE;
    private QueueingConsumer startConsumer;
    // Results settings
    private final String RESULT_EXCHANGE_NAME = "resExchange";
    private final String RESULT_EXCHANGE_TYPE = "direct";

    public ConsumerAMQPHandler(String consumerName) throws IOException {
        logger.debug("configuration AMQP of " +consumerName +  "....");

        host = "localhost";
        this.consumerName = consumerName;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        connection = factory.newConnection();
        this.channel = connection.createChannel();
        this.configureChannel();

        logger.debug("configuration AMQP  " +consumerName + " done");


    }

    public void closeConnection() throws IOException {
            channel.close();
            connection.close();
    }

    /**
     * Configure the channel for receiving and sending messages
     * @throws IOException
     */
    public void configureChannel() throws IOException {
        // Configure channel for receiving configuration messages
        this.channel.exchangeDeclare(this.CONFIG_EXCHANGE_NAME, this.CONFIG_EXCHANGE_TYPE);
        this.channel.queueDeclare(this.CONFIG_QUEUE, false, false, false, null); //channel.queueDeclare().getQueue();
        this.channel.queueBind(this.CONFIG_QUEUE, this.CONFIG_EXCHANGE_NAME, this.consumerName);
        this.configurationConsumer = new QueueingConsumer(this.channel);
        this.channel.basicConsume(this.CONFIG_QUEUE, true, this.configurationConsumer);

        // Configure channel for receiving start messages
        this.channel.exchangeDeclare(this.START_EXCHANGE_NAME, this.START_EXCHANGE_TYPE);
        this.START_QUEUE = this.channel.queueDeclare().getQueue();
        this.channel.queueBind(this.START_QUEUE, this.START_EXCHANGE_NAME, "");
        this.startConsumer = new QueueingConsumer(this.channel);
        this.channel.basicConsume(this.START_QUEUE, true, this.startConsumer);

        // Configure channel for sending result messages
        this.channel.exchangeDeclare(this.RESULT_EXCHANGE_NAME, this.RESULT_EXCHANGE_TYPE);
    }

    /**
     * This function publishes a message in a
     * @param exchange
     * @param message
     * @throws IOException
     */
    public void sendMessage(String exchange, String message) throws IOException {
        // By default there is no routing key or property
        this.channel.basicPublish(exchange, "", null, message.getBytes());
        System.out.println("Message sent : " + message);
    }

    /**
     * This function blocks until a message arrives on the start channel
     * @return received message
     * @throws ShutdownSignalException
     * @throws ConsumerCancelledException
     * @throws InterruptedException
     */
    public String receiveStartMessage() throws ShutdownSignalException, ConsumerCancelledException, InterruptedException {
        QueueingConsumer.Delivery delivery = this.startConsumer.nextDelivery();
        String message = new String(delivery.getBody());
        System.out.println("Message received : " + message);
        return message;
    }

    /**
     * This function blocks until a message arrives on the configuration channel
     * @return received message
     * @throws ShutdownSignalException
     * @throws ConsumerCancelledException
     * @throws InterruptedException
     */
    public String receiveConfigurationMessage() throws ShutdownSignalException, ConsumerCancelledException, InterruptedException {
        QueueingConsumer.Delivery delivery = this.configurationConsumer.nextDelivery();
        String message = new String(delivery.getBody());
        return message;
    }

        /**
     * This function send the result to the ochestrator
     * @param message
     * @throws IOException
     */
    public void sendResult(String message) throws IOException {
        // By default there is no routing key or property
        this.channel.basicPublish(this.RESULT_EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println("Message sent : " + message);
    }
}
