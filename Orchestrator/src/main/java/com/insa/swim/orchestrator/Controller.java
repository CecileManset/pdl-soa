package com.insa.swim.orchestrator;

import com.insa.swim.orchestrator.xml.IXmlReader;
import com.insa.swim.orchestrator.xml.Result;
import com.insa.swim.orchestrator.xml.Scenario;
//import com.insa.swim.orchestrator.xml.XmlParser;
import java.io.IOException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Controller {

    public static final Logger LOGGER = LogManager.getLogger(Controller.class);

//	private Scenario parseXml() {
//		IXmlReader xmlReader = new XmlParser();
//		Scenario scenario = xmlReader.parseXml();
//		return scenario;
//	}
    private void configureWebServices(Scenario scenario) {
//		WebServicesConfiguration config = new WebServicesConfiguration();
//		config.configure(scenario);
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

        //Scenario scenario = parseXml();

        //configureWebServices(scenario);

        Listener listener = startListener();
        launchEsbTest();
        listener.stop();

        // creating the httpClient which will be used by the threads
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();

        try {
            
            // create a thread for each URI
            PostToElasticSearchThread[] threads = new PostToElasticSearchThread[10];
            for (int i = 0; i < threads.length; i++) {
                Result result = new Result();
                result.setConsumer("consumer" + i);
                result.setProvider("provider" + i);
                result.setC2PTime(i*i*12);
                result.setP2CTime(15478);
                threads[i] = new PostToElasticSearchThread(httpClient, result);
            }

            // start the threads
            for (int j = 0; j < threads.length; j++) {
                threads[j].start();
            }

            // join the threads
            for (int j = 0; j < threads.length; j++) {
                try {
                    threads[j].join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        } finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


//        ResultHandler resultHandler = new ResultHandler();
//        Result result = new Result();
//        result.setConsumer("consumer2");
//        result.setProvider("provider3");
//        result.setC2PTime(251);
//        result.setP2CTime(324);
//        resultHandler.sendResultsToElasticsearch(result);

    }
}
