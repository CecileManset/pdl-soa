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

    protected ConsumerAMQPHandler amqp;
    private static final Logger LOGGER = LogManager.getLogger("Consumer");
    private Scenario scenario = null;
//    private Scenario scenario = new Scenario("INFO|0|name|0|10|CONSUMER|2|C2" + "|REQUEST|1|0021|4|0|0|0|2000|5" + "|REQUEST|3|0023|2|true|1000|8|500|10" + "|REQUEST|4|0024|10|0|100|5|5500|2");
    // thread timeout in seconds
    private static final int THREAD_TIMEOUT = 5;
    private static final int PROVIDER_ID_INDEX = 1;
    private static final int PROVIDER1_ID = 1;
    private static final int PROVIDER2_ID = 2;
    private static final int PROVIDER3_ID = 3;
    private static final int PROVIDER4_ID = 4;

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
            case PROVIDER1_ID:
                compositeapp1.P1WebService port1 = service1.getCasaPort1();
                result = port1.pingpong(txt);
                break;
            case PROVIDER2_ID:
                compositeapp1.P2WebService port2 = service2.getCasaPort2();
                result = port2.pingpong(txt);
                break;
            case PROVIDER3_ID:
                compositeapp1.P3WebService port3 = service3.getCasaPort3();
                result = port3.pingpong(txt);
                break;
            case PROVIDER4_ID:
                compositeapp1.P4WebService port4 = service4.getCasaPort4();
                result = port4.pingpong(txt);
                break;
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
                case PROVIDER1_ID:
                    compositeapp1.P1WebService port1 = service1.getCasaPort1();
                    response = port1.processRequest(request);
                    break;
                case PROVIDER2_ID:
                    compositeapp1.P2WebService port2 = service2.getCasaPort2();
                    response = port2.processRequest(request);
                    break;
                case PROVIDER3_ID:
                    compositeapp1.P3WebService port3 = service3.getCasaPort3();
                    response = port3.processRequest(request);
                    break;
                case PROVIDER4_ID:
                    compositeapp1.P4WebService port4 = service4.getCasaPort4();
                    response = port4.processRequest(request);
                    break;
            }
        }
        catch (Exception e) {
            LOGGER.error("Consumer C" + scenario.getConsumerId() + "bus problem when sending request", e);
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
        // sendingDate can be used as a starting point for the request
        String sendingDate = Integer.toString(req.getSendingTime());
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
        LOGGER.debug("Consumer C" + scenario.getConsumerId() + " constructed request : " + request.replace("|", ";"));

        // req format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons|PayloadCons
        return request;
    }

    /**
     * Starts one thread per request to handle the sending and receiving
     */
    @WebMethod(operationName = "startSendingRequests")
    public void startSendingRequests() {
        int providerNumber;
        int nbRequests;
        int period;

        for (Request req : scenario.getRequestList()) {
            nbRequests = 1;
            period = 0;
            providerNumber = req.getProviderId();

            LOGGER.debug("Consumer C" + scenario.getConsumerId() + " starts sending requests");

            if (req.isPeriodic()) {
                nbRequests = req.getNumberRequest();
                period = req.getPeriod();
            }

            // Create a thread that handles the request sending to provider i (send, wait for response and send it to app)
            for (int nbReq = 0; nbReq < nbRequests; nbReq++) {
                Thread thread = new Thread(new ConsumerThread(constructRequest(req), providerNumber), this.getClass().toString());
                thread.start();
                try {
                    Thread.sleep(period);
                } catch (InterruptedException e) {
                    LOGGER.debug("Consumer C" + scenario.getConsumerId() + "period sleep interrupted", e);
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
        Callable<Object> task = new Callable<Object>() {
            @Override
            public Object call() {
                return sendRequest(req, providerNumber);
            }
        };

        public BlockingMethodCallable(String req, int providerNumber) {
            this.req = req;
            this.providerNumber = providerNumber;
        }
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
            boolean timedOut = false;
            String result = "No response from provider " + request.split("\\|")[1] + " : " + request;
            String response = "";

            ExecutorService executor = Executors.newCachedThreadPool();
            BlockingMethodCallable blockingMethodCallable = new BlockingMethodCallable(request, providerNumber);
            Future<Object> future = executor.submit(blockingMethodCallable.task);

            try {
                response = (String) future.get(THREAD_TIMEOUT, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                result = "LOST|" + request;
                LOGGER.debug("Consumer C" + scenario.getConsumerId() + " didn't receive message from provider P" + providerNumber + " to request : " + request.replace("|", ";"), e);
                timedOut = true;
            } catch (InterruptedException e) {
                LOGGER.debug("Consumer C" + scenario.getConsumerId() + " thread timeout interrupted", e);
            } catch (ExecutionException e) {
                LOGGER.debug("Consumer C" + scenario.getConsumerId() + " thread timeout issue", e);
            } finally {
                future.cancel(true);
            }

            LOGGER.debug("Response from P" + providerNumber + " to Consumer C" + scenario.getConsumerId() + " : " + response.replace("|", ";"));

            if (!timedOut) {
                // send results to orchestrator with AMQP
                if (response != null || !response.contains("|")) {
                    String[] responseParts = response.split("\\|", -1);

                    if (Integer.parseInt(responseParts[PROVIDER_ID_INDEX]) == scenario.getConsumerId()) {

                        for (int i = 0; i < responseParts.length - 1; i++) {
                            result += responseParts[i] + "|";
                        }

                        receptionDateConsumer = new Date();
                        // resp format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons|ReceptionDateProv|SendingDateProv|ReceptionDateCons
                        result += Long.toString(receptionDateConsumer.getTime());
                    } else {
                        LOGGER.debug("Bad consumer (C" + scenario.getConsumerId() + ") received response : " + response + " to request " + request);
                        result = "CONSUMER|" + request;
                    }
                } else {
                    result = "FORMAT|" + request;
                    LOGGER.debug("Consumer C" + scenario.getConsumerId() + " received a badly formatted response : " + response + " to request " + request);

                    try {
                        LOGGER.debug("Consumer C" + scenario.getConsumerId() + " sends result to app : " + result.replace("|", ";"));
                        amqp.sendResult(result);
                    } catch (IOException e) {
                        LOGGER.error("[Consumer thread] Unable to send result to application", e);
                    }
                }
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
            } else {
                LOGGER.error("Impossible to create the scenario. The XML may be wrong");
                amqp.sendResult("Bad xml format");
            }
        } catch (IOException e) {
            LOGGER.error("error initialisation" + this.getClass(), e);
        } catch (ShutdownSignalException e) {
            LOGGER.error("error initialisation" + this.getClass(), e);
        } catch (ConsumerCancelledException e) {
            LOGGER.error("error initialisation" + this.getClass(), e);
        } catch (InterruptedException e) {
            LOGGER.error("error initialisation" + this.getClass(), e);
        }
        return "done";
    }

    /**
     * Must be invoked at the end
     * @return "done"
     */
    @WebMethod(operationName = "closeConnection")
    public String closeConnection() {
        try {
            amqp.closeConnection();
            LOGGER.debug("connection closed");
            return "done";
        } catch (IOException e) {
            LOGGER.debug("impossible to close connection", e);
            return "error";
        }
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
}