/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.consumer.ws;

import compositeapp1.CompositeApp1Service1;
import com.insa.swim.consumer.scenario.Scenario;
import compositeapp1.CompositeApp1Service2;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceRef;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author tp1_g2
 */
@WebService(serviceName = "C1WebService")
public class C1WebService {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_9080/CompositeApp1Service2/casaPort2.wsdl")
    private CompositeApp1Service2 service_1;

    private static final Logger logger = LogManager.getLogger("Consumer");

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_9080/CompositeApp1Service1/casaPort1.wsdl")
    private CompositeApp1Service1 service;

    Scenario scenario = null;
    
    @WebMethod(operationName = "sendPing")
    public String sendPing(@WebParam(name = "start") String txt) {


        // Envoi de ttt au casa2 (qui va ensuite au provider 2)
        try { // Call Web Service Operation
            compositeapp1.P2WebService port = service_1.getCasaPort2();
            // TODO initialize WS operation arguments here
            java.lang.String ping = "ttt";
            // TODO process result here
            java.lang.String result = port.pingpong(ping);
            logger.debug("message received : " + result);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }

        // Envoi du message au casa1
        try { // Call Web Service Operation
            compositeapp1.P1WebService port = service.getCasaPort1();
            // TODO initialize WS operation arguments here
            java.lang.String ping = txt;
            // TODO process result here
            java.lang.String result = port.pingpong(ping);
            logger.debug("message received : " + result);
            return result;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            logger.error("Failed to call casa: " + ex.getMessage());
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
}

