package com.insa.swim.orchestrator;

import com.insa.swim.orchestrator.amqp.AMQPHandler;
import com.insa.swim.orchestrator.configuration.WebServicesConfiguration;
import com.insa.swim.orchestrator.xml.IXmlReader;
import com.insa.swim.orchestrator.xml.Result;
import com.insa.swim.orchestrator.xml.Scenario;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import com.insa.swim.orchestrator.xml.XmlParser;
import java.io.IOException;
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

//            LOGGER.debug("Received : " + amqp.receiveResultMessage());
//            LOGGER.debug("Received : " + amqp.receiveResultMessage());

            // creating the httpClient which will be used by the threads
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
            connectionManager.setMaxTotal(100);

            CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();

            try {

                boolean stopCondition = false;
                while (!stopCondition) {
                    PostToElasticSearchThread[] threads = new PostToElasticSearchThread[10];
                    for (int i = 0; i < threads.length; i++) {
                        LOGGER.error("Waiting for results...");
                        Result result = new Result(amqp.receiveResultMessage());
                        LOGGER.error("Received a result: " + result.toString());
                        threads[i] = new PostToElasticSearchThread(httpClient, result);
                        threads[i].start();
                        LOGGER.error("Result sent to ElasticSearch");
//                Result result = new Result();
//                result.setConsumer("consumer" + i);
//                result.setProvider("provider" + i);
//                result.setC2PTime(i*i*12);
//                result.setP2CTime(15478);
//                threads[i] = new PostToElasticSearchThread(httpClient, result);
                    }

                    // start the threads
//            for (int j = 0; j < threads.length; j++) {
//                threads[j].start();
//            }

                    // join the threads
                    for (int j = 0; j < threads.length; j++) {
                        try {
                            threads[j].join();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

            } finally {
                try {
                    httpClient.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            amqp.closeConnection();

        } catch (Exception ex) {
            LOGGER.error(Controller.class.getName() + " " + ex.getMessage());
        }

        Listener listener = startListener();
        launchEsbTest();
        listener.stop();

    }
}
