/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.provider.ws;

import java.util.Date;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pdlsoa
 */
@WebService()
public class ProviderWS {

    private static final Logger logger = LogManager.getLogger("Provider");
    private static final int PROCESSING_TIME_INDEX = 4;
    private static final int RESPONSE_SIZE_INDEX = 3;

    @WebMethod(operationName = "pingpong")
    public String pingpong(@WebParam(name = "ping") String txt) {
        logger.debug("message received by " + this.getClass() + ": " + txt);
        if (txt != null && txt.equals("ping")) {
            return "pong";
        }
        else return "error";
    }

    // TODO verify that the request is well constructed (6 info separated by |). Hard-coded ?

    // Parse incoming request from consumer to retrieve the different parameters
    // req format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons|PayloadCons
    private String[] parseRequest(String request) {
        return request.split("\\|");
    }

    // Process request according to the parameters sent and respond in consequence
    @WebMethod(operationName = "processRequest")
    public String processRequest(@WebParam(name = "request") String request) {
        String response = new String();
        String[] parsedRequest;
        char[] payloadProvider;
        Date receptionDate;
        Date sendingDate;

        if (request != null) {
            receptionDate = new Date();

            logger.debug("message received by " + this.getClass() + ": " + request.replace("|", ";"));

            parsedRequest = parseRequest(request);
            // TODO verify that the provider that receives the request is the intended one (attribut id?)
            // Sleep to fake the request processing
            try {
                Thread.sleep(Integer.parseInt(parsedRequest[PROCESSING_TIME_INDEX]));
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            payloadProvider = new char[Integer.parseInt(parsedRequest[RESPONSE_SIZE_INDEX])];
            for (int i = 0 ; i < payloadProvider.length ; i++) {
                payloadProvider[i] = '-';
            }

            // response = request - consumer payload
            for (int i = 0 ; i < parsedRequest.length-1 ; i++) {
                response += parsedRequest[i] + "|";
            }
            sendingDate = new Date();
            // + provider info
            response += Long.toString(receptionDate.getTime()) + "|" + Long.toString(sendingDate.getTime()) + "|" + new String(payloadProvider);
        }
        else
            return "null request";

        logger.debug("message sent from " + this.getClass() + " to Consumer " + parsedRequest[0] + ": " + response);

        return response;
    }
}
