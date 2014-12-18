/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.consumer.ws;

import compositeapp1.CompositeApp1Service1;
import cons.Scenario;
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
    private static final Logger logger = LogManager.getLogger("Consumer");

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_9080/CompositeApp1Service1/casaPort1.wsdl")
    private CompositeApp1Service1 service;

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
            Scenario.getInstance().init(conf);
            return "done";
    }
}

