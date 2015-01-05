package com.insa.swim.orchestrator;

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
 * Main class. Orchestrate the different components.
 */
public class Application {


    private static final Logger logger = LogManager.getLogger(Application.class);

    private Controller controller;

    public Application() {
        controller = new Controller();
    }
    
    public void start() {
        controller.start();
    }

    public static void main(String[] args) {
        logger.trace("Program starts");
        Application app = new Application();
        app.start();

        logger.error("Hello World!");
    }
}
