package com.insa.swim.orchestrator;

import com.insa.swim.orchestrator.amqp.AMQPHandler;
import com.insa.swim.orchestrator.configuration.WebServicesConfiguration;
import com.insa.swim.orchestrator.xml.IXmlReader;
import com.insa.swim.orchestrator.xml.Scenario;
import com.insa.swim.orchestrator.xml.XmlParser;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Controller {

    public static final Logger LOGGER = LogManager.getLogger(Controller.class);

    private Scenario parseXml() {
        IXmlReader xmlReader = new XmlParser();
        Scenario scenario = xmlReader.parseXml();
        return scenario;
    }

    private void configureWebServices(Scenario scenario, AMQPHandler amqp) {
        WebServicesConfiguration config = new WebServicesConfiguration(amqp);
        config.configure(scenario);
    }

    private void launchEsbTest() {
        // TODO Auto-generated method stub

    }

    private Listener startListener() {
        Listener listener = new Listener();
        listener.start();
        return listener;
    }

    public void start() {
        LOGGER.trace("Controller starts");

        try {
            Scenario scenario = parseXml();
            AMQPHandler amqp = new AMQPHandler();

            configureWebServices(scenario, amqp);
            
            amqp.sendStart();
            
            LOGGER.debug("Received : " + amqp.receiveResultMessage());
            //LOGGER.debug("Received : " + amqp.receiveResultMessage());
            /*Listener listener = startListener();
            launchEsbTest();
            listener.stop();*/
            
            amqp.closeConnection();

        } catch (IOException ex) {
            LOGGER.error(this.getClass() + " " + ex.getMessage());
        } catch (ShutdownSignalException ex) {
            LOGGER.error(this.getClass() + " " + ex.getMessage());
        } catch (ConsumerCancelledException ex) {
            LOGGER.error(this.getClass() + " " + ex.getMessage());
        } catch (InterruptedException ex) {
            LOGGER.error(this.getClass() + " " + ex.getMessage());
        }

    }
}
