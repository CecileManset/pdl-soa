package com.insa.swim.orchestrator;

import com.insa.swim.orchestrator.amqp.AMQPHandler;
import com.insa.swim.orchestrator.configuration.WebServicesConfiguration;
import com.insa.swim.orchestrator.xml.IXmlReader;
import com.insa.swim.orchestrator.xml.Result;
import com.insa.swim.orchestrator.xml.Results;
import com.insa.swim.orchestrator.xml.Scenario;
import com.insa.swim.orchestrator.xml.XMLWriter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import com.insa.swim.orchestrator.xml.XmlParser;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Controller {

    public static final Logger LOGGER = LogManager.getLogger(Controller.class);
    
    private Scenario scenario;

    private Scenario parseXml() {
        IXmlReader xmlReader = new XmlParser();
        Scenario sc = xmlReader.parseXml();
        return sc;
    }

    private void createXML(Results results) {
        double meanTimeConsumerProvider = 0;
        double meanTimeProviderConsumer = 0;
        int requestNumber = scenario.getTotalOfRequest();
        int lostMessages = requestNumber - results.getResultsNoError().size();
        
        results.getKPI().setRequestsNumber(requestNumber);
        results.getKPI().setNumberLostMessages(lostMessages);
        
        List<Result> resultsNoError = results.getResultsNoError();
        for (Result result : resultsNoError) {
            meanTimeConsumerProvider += result.getC2PTime();
            meanTimeProviderConsumer += result.getP2CTime();
        }
        meanTimeConsumerProvider /= resultsNoError.size();
        meanTimeProviderConsumer /= resultsNoError.size();
        
        results.getKPI().setMeanTimeConsumerProvider(meanTimeConsumerProvider);
        results.getKPI().setMeanTimeProviderConsumer(meanTimeProviderConsumer);

        XMLWriter xmlWriter = new XMLWriter();
        xmlWriter.write(results);
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
        Results results = new Results();

        try {
            scenario = parseXml();
            AMQPHandler amqp = new AMQPHandler();

            configureWebServices(scenario, amqp);

            amqp.sendStart();

            //LOGGER.debug("Received : " + amqp.receiveResultMessage());
            // creating the httpClient which will be used by the threads
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
            connectionManager.setMaxTotal(100);

            CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();

            try {

                boolean stopCondition = false;
                while (!stopCondition) {
                    PostToElasticSearchThread[] threads = new PostToElasticSearchThread[10];
                    for (int i = 0; i < threads.length; i++) {
                        LOGGER.debug("Waiting for results...");
                        Result result = new Result(amqp.receiveResultMessage());
                        LOGGER.debug("Received a result: " + result.toString());
                        results.getResults().add(result);
                        // TODO : move to avoid useless writing
                        threads[i] = new PostToElasticSearchThread(httpClient, result);
                        threads[i].start();
                        LOGGER.debug("Result sent to ElasticSearch");
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
                    LOGGER.debug("HTTP Client closed");
                    amqp.closeConnection();
                    LOGGER.debug("AMQP connection closed");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (IOException ex) {
            LOGGER.error(this.getClass() + " " + ex.getMessage());
        } catch (ShutdownSignalException ex) {
            LOGGER.error(this.getClass() + " " + ex.getMessage());
        } catch (ConsumerCancelledException ex) {
            LOGGER.error(this.getClass() + " " + ex.getMessage());
        } catch (InterruptedException ex) {
            LOGGER.error(this.getClass() + " " + ex.getMessage());
        }

        createXML(results);
        LOGGER.debug("results published");

        /*
         Listener listener = startListener();
         launchEsbTest();
         listener.stop();
         */
    }
}
