/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.consumer;

import compositeapp1.CompositeApp1Service1;
import com.insa.swim.consumer.scenario.Scenario;
import com.insa.swim.consumer.scenario.Scenario.Request;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import compositeapp1.CompositeApp1Service2;
import compositeapp1.CompositeApp1Service3;
import compositeapp1.CompositeApp1Service4;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.xml.ws.WebServiceRef;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pdlsoa
 */
@WebService()
public class ConsumerWS {

    protected static ConsumerAMQPHandler amqp;
    private static final Logger LOGGER = LogManager.getLogger("Consumer");
//    private Scenario scenario = null;
    private Scenario scenario = new Scenario("INFO|0|name|0|10|CONSUMER|2|C2" + "|REQUEST|1|0021|4|0|0|0|2000|5" + "|REQUEST|3|0023|2|true|1000|8|500|10" + "|REQUEST|4|0024|10|0|100|5|5500|2");
    private static final int THREAD_TIMEOUT = 5; // in seconds
    /*
     * These are the referenes of the services provided by the bus to join the controller
     * */
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_9080/CompositeApp1Service1/casaPort1.wsdl")
    private CompositeApp1Service4 service4;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_9080/CompositeApp1Service1/casaPort1.wsdl")
    private CompositeApp1Service3 service3;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_9080/CompositeApp1Service1/casaPort1.wsdl")
    private CompositeApp1Service2 service2;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_9080/CompositeApp1Service1/casaPort1.wsdl")
    private CompositeApp1Service1 service1;

    /**
     * This method tests the communication with a specifit provider through the bus
     * @param txt : ping
     * @param provider : number of the provider
     * @return pong if param = ping, else erro, Gros fail if exception
     */
    public String sendPing(String txt, int provider) {
        java.lang.String result = "BigFail";
        switch (provider) {
            case 1:
                compositeapp1.P1WebService port1 = service1.getCasaPort1();
                result = port1.pingpong("ping");
                break;
            case 2:
                compositeapp1.P2WebService port2 = service2.getCasaPort2();
                result = port2.pingpong("ping");
                break;
            case 3:
                compositeapp1.P3WebService port3 = service3.getCasaPort3();
                result = port3.pingpong("ping");
                break;
            case 4:
                compositeapp1.P4WebService port4 = service4.getCasaPort4();
                result = port4.pingpong("ping");
                break;
            default:
                ;
        }
        LOGGER.debug("message received : " + result);
        return result;
    }

    /**
     * This method tests the communication with a specifit provider throurgh the bus
     * @param request format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons|PayloadCons
     * @param provider : id of the provider
     * @return response format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons|ReceptionDateProv|PayloadProv
     */
    @WebMethod(operationName = "sendRequest")
    public String sendRequest(String request, int provider) {
        String response = "No response from provider " + provider;

        try {
            switch (provider) {
                case 1:
                    compositeapp1.P1WebService port1 = service1.getCasaPort1();
                    response = port1.processRequest(request);
                    break;
                case 2:
                    compositeapp1.P2WebService port2 = service2.getCasaPort2();
                    response = port2.processRequest(request);
                    break;
                case 3:
                    compositeapp1.P3WebService port3 = service3.getCasaPort3();
                    response = port3.processRequest(request);
                    break;
                case 4:
                    compositeapp1.P4WebService port4 = service4.getCasaPort4();
                    response = port4.processRequest(request);
                    break;
                default:
                    ;
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            LOGGER.debug(ex.getStackTrace());
        }

        return response;
    }

    /**
     * This method formats a request from the scenario to be sent to the provider later
     * @param request from scenario
     * @return format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons(ms)|PayloadCons
     */
    private String constructRequest(Request req) {
        String consumerID = Integer.toString(scenario.getConsumerId());
        String providerID = Integer.toString(req.getProviderId());
        String requestSize = Integer.toString(req.getSize());
        String responseSize = Integer.toString(req.getResponseSize());
        String processingTime = Integer.toString(req.getProcessingTime());
        String sendingDate = Integer.toString(req.getSendingTime()); // can be used as a starting point for the request
        char[] payloadConsumer = new char[req.getSize()];
        String request;
        Date now;

        // construct payload
        for (int i = 0; i < payloadConsumer.length; i++) {
            payloadConsumer[i] = 'x';
        }

        // timestamp to know when the consumer sends the request in milliseconds since January, 1st 1970
        now = new Date();
        sendingDate = Long.toString(now.getTime());

        request = consumerID + "|" + providerID + "|" + requestSize + "|" + responseSize + "|" + processingTime + "|" + sendingDate + "|" + new String(payloadConsumer);
        LOGGER.debug("Constructed request : " + request.replace("|", ";"));

        // req format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons|PayloadCons
        return request;
    }

    /**
     * Starts one thread per request to handle the sending and receiving
     */
    @WebMethod(operationName = "startSendingRequests")
    public void startSendingRequests() {
        int providerNumber;
        int nbRequests = 1;
        int period = 0;

        for (Request req : scenario.getRequestList()) {
            providerNumber = req.getProviderId();

            LOGGER.debug("Consumer " + this.getClass() + " starts sending requests");


            // Send back too many timeouts
            if (req.isPeriodic()) {
                nbRequests = req.getNumberRequest();
                period = req.getPeriod();
            }

            // Create a thread that handles the request sending to provider i (send, wait for response and send it to app)
            for (int nbReq = 0; nbReq < nbRequests; nbReq++) {
                Thread thread = new Thread(new ConsumerThread(constructRequest(req), providerNumber), this.getClass().toString());
                //            Thread thread = new Thread(new ConsumerThread(req), this.getClass().toString());
                thread.start();
                try {
                    Thread.sleep(period);
                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(ConsumerWS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Class used for consumer timeout, to know the number of lost messages
     */
    public class BlockingMethodCallable {

        String req;
        int providerNumber;

        public BlockingMethodCallable(String req, int providerNumber) {
            this.req = req;
            this.providerNumber = providerNumber;
        }
        Callable<Object> task = new Callable<Object>() {

            @Override
            public Object call() {
                return sendRequest(req, providerNumber);
            }
        };
    }

    /**
     * Thread class to handle sending requests in parallel
     */
    private class ConsumerThread implements Runnable {

        String request;
        int providerNumber;

        public ConsumerThread(String request, int providerNumber) {
            this.request = request;
            this.providerNumber = providerNumber;
        }

        /**
         * Thread main : send a request to a given provider,
         * wait for response during a certain amount of time
         * and send the response to the Orchestrator
         */
        @Override
        public void run() {
            Date receptionDateConsumer;
            String result = "No response from provider " + request.split("\\|")[1] + " : " + request;
            String response = "";

            ExecutorService executor = Executors.newCachedThreadPool();
            BlockingMethodCallable blockingMethodCallable = new BlockingMethodCallable(request, providerNumber);
            Future<Object> future = executor.submit(blockingMethodCallable.task);

            try {
                response = (String) future.get(THREAD_TIMEOUT, TimeUnit.SECONDS);
            }
            catch (TimeoutException ex) {
                response = "timeout";
                result = "timeout";
            }
            catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
                LOGGER.debug(e.getStackTrace());
            }
            catch (ExecutionException e) {
                LOGGER.error(e.getMessage());
                LOGGER.debug(e.getStackTrace());
            }
            finally {
                future.cancel(true);
            }

            LOGGER.debug("Response from P" + providerNumber + " to " + Thread.currentThread().getName() + " : " + response.replace("|", ";"));

            // send results to orchestrator with AMQP
            if (response.contains("|")) {
                String[] responseParts = response.split("\\|", -1);
                int i;

                for (i = 0; i < responseParts.length - 1; i++) {
                    result += responseParts[i] + "|";
                }

                receptionDateConsumer = new Date();
                // resp format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons|ReceptionDateProv|SendingDateProv|ReceptionDateCons
                result += Long.toString(receptionDateConsumer.getTime());
            }

            try {
                LOGGER.debug("Consumer " + Thread.currentThread().getName() + " sends result to app : " + result.replace("|", ";"));
                amqp.sendResult(result);
            }
            catch (IOException ex) {
                LOGGER.error("[Consumer thread] Unable to send result to application" + ex.getMessage());
            }
        }
    }

    /**
     * Must be invoked at the begining
     * @param name used to be identified by AMQP (e.g "C2")
     * @return "done"
     */
    @WebMethod(operationName = "initialiseConsumer")
    public String initialiseConsumer(String name) {
        scenario = new Scenario();

        try {
            amqp = new ConsumerAMQPHandler(name);

            LOGGER.debug("Wait for configuration...");
            String received = amqp.receiveConfigurationMessage();
            LOGGER.debug("Message config : " + received);

            scenario.init(received);

            if (scenario.isInitialized()) {
                LOGGER.debug("Wait for start message...");
                String start = amqp.receiveStartMessage();

                LOGGER.debug("message start : " + start);
                startSendingRequests();
            }
            else {
                LOGGER.error("Impossible to create the scenario. The XML may be wrong");
                amqp.sendResult("Bad xml format");
            }
        }
        catch (IOException ex) {
            LOGGER.error("error initialisation" + this.getClass());
            ex.printStackTrace();
        }
        catch (ShutdownSignalException ex) {
            LOGGER.error("error initialisation" + this.getClass());
            ex.printStackTrace();
        }
        catch (ConsumerCancelledException ex) {
            LOGGER.error("error initialisation" + this.getClass());
            ex.printStackTrace();
        }
        catch (InterruptedException ex) {
            LOGGER.error("error initialisation" + this.getClass());
            ex.printStackTrace();
        }
        return "done";
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
}