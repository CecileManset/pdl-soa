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

    private static final Logger LOGGER = LogManager.getLogger(Controller.class);
    private static final int NUMBER_THREADS_SENDING_RESULT = 10;
    private static final int SIZE_CONNECTION_POOL = 100;

    private Scenario scenario;

    private Scenario parseXml() {
        IXmlReader xmlReader = new XmlParser();
        return xmlReader.parseXml();
    }

    public Results computeResultsKpi(Results results) {
        double meanTimeConsumerProvider = 0;
        double meanTimeProviderConsumer = 0;

        List<Result> resultsNoError = results.getResultsNoError();
        for (Result result : resultsNoError) {
            meanTimeConsumerProvider += result.getC2PTime();
            meanTimeProviderConsumer += result.getP2CTime();
        }
        meanTimeConsumerProvider /= resultsNoError.size();
        meanTimeProviderConsumer /= resultsNoError.size();

        results.getKPI().setMeanTimeConsumerProvider(meanTimeConsumerProvider);
        results.getKPI().setMeanTimeProviderConsumer(meanTimeProviderConsumer);
        return results;
    }

    private void createXML(Results results) {
        int requestNumber = scenario.getTotalOfRequest();
        int lostMessages = requestNumber - results.getResultsNoError().size();

        results.getKPI().setRequestsNumber(requestNumber);
        results.getKPI().setNumberLostMessages(lostMessages);

        Results res = computeResultsKpi(results);
        XMLWriter xmlWriter = new XMLWriter();
        xmlWriter.write(res);
    }

    private void configureWebServices(Scenario scenario, AMQPHandler amqp) {
        WebServicesConfiguration config = new WebServicesConfiguration(amqp);
        config.configure(scenario);
    }

    private void initConsumers(Scenario scenario) {
        // not enough time to init the consumers through a webservice 
    }

    private Results collectResults(AMQPHandler amqp) throws ShutdownSignalException, ConsumerCancelledException, InterruptedException {
        Results results = new Results();
        // creating the httpClient which will be used by the threads
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(SIZE_CONNECTION_POOL);

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();

        try {

            boolean stopCondition = false;
            while (!stopCondition) {
                PostToElasticSearchThread[] threads = new PostToElasticSearchThread[NUMBER_THREADS_SENDING_RESULT];
                for (int i = 0; i < threads.length; i++) {
                    LOGGER.debug("Waiting for results...");
                    Result result = new Result(amqp.receiveResultMessage());
                    LOGGER.debug("Received a result: " + result.toString());
                    results.getResults().add(result);
                    threads[i] = new PostToElasticSearchThread(httpClient, result);
                    threads[i].start();
                    LOGGER.debug("Result sent to ElasticSearch");
                }

                // join the threads
                for (PostToElasticSearchThread thread : threads) {
                    try {
                        thread.join();
                    } catch (InterruptedException ex) {
                        LOGGER.error("Error while waiting for the threads to join " + ex);
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
                LOGGER.error("Could not close http client or AMQP " + ex);
            }
        }
        return results;
    }

    public void start() {
        LOGGER.trace("Controller starts");
        Results results = new Results();

        try {
            scenario = parseXml();
            AMQPHandler amqp = new AMQPHandler();

            initConsumers(scenario);
            configureWebServices(scenario, amqp);

            amqp.sendStart();

            results = collectResults(amqp);

        } catch (IOException ex) {
            LOGGER.error("Error while collecting results " + ex);
        } catch (ShutdownSignalException ex) {
            LOGGER.error("Error while collecting results : shutdownsignal " + ex);
        } catch (ConsumerCancelledException ex) {
            LOGGER.error("Error while collecting results : Consumer cancelled " + ex);
        } catch (InterruptedException ex) {
            LOGGER.error("Error while collecting results : interrupted " + ex.getMessage());
        }

        createXML(results);
        LOGGER.debug("results published");
    }
}
