/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.consumer;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pdlsoa
 */
public class SuperConsumer {

    protected static final Logger LOGGER = LogManager.getLogger("Consumer");

    static protected ConsumerAMQPHandler amqp;

    protected void sendRequests() {
        try {
            // STEP 3 : Send requests to providers and results to application
            amqp.sendResult("this is a result from " + this.getClass());
            LOGGER.info("Finished sending requests");
        } catch (IOException ex) {
            LOGGER.error("exception sendRequest: " + ex.getMessage());
        }
    }

    protected void start() {
        try {
            LOGGER.debug("wait configuration...");
            String received = amqp.receiveConfigurationMessage();
            LOGGER.debug("message config : " + received);
            LOGGER.debug("wait start message...");
            String start = amqp.receiveStartMessage();

            LOGGER.debug("message start : " + start);
            sendRequests();

            amqp.closeConnection();

        } catch (Exception ex) {
            LOGGER.error("error starting: " + ex.getMessage());
        }
    }

    public SuperConsumer(String name) {
        try {
            amqp = new ConsumerAMQPHandler(name);
        } catch (IOException ex) {
            LOGGER.error("error constructor: " + ex.getMessage());
        }
    }

    public String sendPing() {

        try { // Call Web Service Operation
            compositeapp1.CompositeApp1Service2 service = new compositeapp1.CompositeApp1Service2();
            compositeapp1.P2WebService port = service.getCasaPort2();
            // TODO initialize WS operation arguments here
            java.lang.String ping = "ping";
            // TODO process result here
            java.lang.String result = port.pingpong(ping);
            LOGGER.info("Received result = "+result+ " from casa");
            return result;
        } catch (Exception ex) {
            LOGGER.error("exception raised while sending a ping: " + ex.getMessage());
            return "fail";
            // TODO handle custom exceptions here
        }

    }

    /*
    try {
    c1 = new SuperConsumer(host);
    // STEP 1 : Wait the configuration message and configure the requestQueue
    System.out.println("Starting " + this.get);
    String conf = c1.receiveConfigurationMessage();
    System.out.println(conf);
    // STEP 2 : Wait the start message
    String start = c1.receiveStartMessage();
    System.out.println(start);
    // STEP 3 : Send requests to providers and results to application
    c1.sendMessage(c1.RESULT_EXCHANGE_NAME, "this is a result from C1");
    System.out.println("end");
    } catch (IOException e1) {
    // TODO Auto-generated catch block
    e1.printStackTrace();
    } catch (ShutdownSignalException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    } catch (ConsumerCancelledException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    } catch (InterruptedException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    }
     */
}
