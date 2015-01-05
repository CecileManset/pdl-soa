/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.consumer;

import compositeapp1.CompositeApp1Service1;
import com.insa.swim.consumer.scenario.Scenario;
import compositeapp1.CompositeApp1Service2;
import compositeapp1.CompositeApp1Service3;
import compositeapp1.CompositeApp1Service4;
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
    public static final int NB_PROVIDERS = 4;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_9080/CompositeApp1Service1/casaPort1.wsdl")
    private CompositeApp1Service4 service4;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_9080/CompositeApp1Service1/casaPort1.wsdl")
    private CompositeApp1Service3 service3;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_9080/CompositeApp1Service1/casaPort1.wsdl")
    private CompositeApp1Service2 service2;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_9080/CompositeApp1Service1/casaPort1.wsdl")
    private CompositeApp1Service1 service1;
    private static final Logger logger = LogManager.getLogger("Consumer");
    private Scenario scenario = null;


    @WebMethod(operationName = "sendPing")
    public String sendPing(@WebParam(name = "start") String txt) {
        try { // Call Web Service Operation
            compositeapp1.P1WebService port = service1.getCasaPort1();
            // TODO initialize WS operation arguments here
            java.lang.String ping = txt;
            // TODO process result here
            java.lang.String result = port.pingpong(ping);
            logger.debug("message received : " + result);
            return result;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            ex.printStackTrace();
            return "Gros fail!";
        }
    }

    public String sendPing(String txt, int provider) {

        java.lang.String result = "BigFail";
        try {
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
        } catch (Exception ex) {
            logger.error("message received : " + result);
            ex.printStackTrace();
        }
        logger.debug("message received : " + result);

        return result;
    }

    @WebMethod(operationName = "configConsumer")
    public String configConsumer(
            @WebParam(name = "conf") String conf) {
        logger.debug("message received : " + conf);
        scenario = new Scenario(conf);
        return "done";
    }

    @WebMethod(operationName = "startSendingRequests")
    public String startSendingRequests() {
        String startMsg = "ping";
        String startResponse = "";
        String pingResponse;

        logger.debug("Consumer " + this.getClass() + " starts sending requests");
        for (int i = 1; i <= NB_PROVIDERS; i++) {
            //startResponse = sendPing(startMsg);
            pingResponse = sendPing(startMsg, i);
            startResponse += pingResponse + " ";
            logger.debug("Response from P" + i + " to " + this.getClass() + " : " + pingResponse);
        }
        return startResponse;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
}
