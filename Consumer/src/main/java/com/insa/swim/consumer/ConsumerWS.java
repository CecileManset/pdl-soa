/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.consumer;

import compositeapp1.CompositeApp1Service1;
import com.insa.swim.consumer.scenario.Scenario;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceRef;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author pdlsoa
 */
@WebService()
public class ConsumerWS {

    private static final Logger logger = LogManager.getLogger("Consumer");
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_9080/CompositeApp1Service1/casaPort1.wsdl")
    private CompositeApp1Service1 service;

    private Scenario scenario = null;

    @WebMethod(operationName = "sendPing")
    public String sendPing(@WebParam(name = "start") String txt) {
        try { // Call Web Service Operation
            compositeapp1.P1WebService port = service.getCasaPort1();
            // TODO initialize WS operation arguments here
            java.lang.String ping = txt;
            // TODO process result here
            java.lang.String result = port.pingpong(ping);
            logger.debug("message received : " + ping);
            return result;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            ex.printStackTrace();
            return "Gros fail!";
        }
    }

    @WebMethod(operationName = "configConsumer")
    public String configConsumer(@WebParam(name = "conf") String conf) {
        logger.debug("message received : " + conf);
        scenario = new Scenario(conf);
        return "done";
    }

    @WebMethod(operationName = "startSendingRequests")
    public String startSendingRequests() {
        String startMsg = "ping";
        String startResponse;

        logger.debug("Consumer 1 starts sending requests");
        startResponse = sendPing(startMsg);
        logger.debug("Response from consumer 1 : " + startResponse);
        return startResponse;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
}
