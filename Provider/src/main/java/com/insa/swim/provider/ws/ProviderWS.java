/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.provider.ws;

import java.util.Date;
import java.util.regex.PatternSyntaxException;
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
    private static final int PROVIDER_ID_INDEX = 1;
    private static final int REQUEST_FIELDS_NB = 7;

     /**
     * Receives "ping" and send back "pong", else return "error"
     * Used to test consumer-provider communication
     * @param txt : request from consumer
     * @return "pong" if txt="ping", else "error"
     */
    @WebMethod(operationName = "pingpong")
    public String pingpong(@WebParam(name = "ping") String txt) {
        logger.debug("message received by " + this.getClass() + ": " + txt);
        if (txt != null && txt.equals("ping")) {
            return "pong";
        }
        else return "error";
    }

     /**
     * Parse incoming request from consumer to retrieve the different parameters
     * Verify that request contains 7 fields
     * @param request : request String received from consumer
     * format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons(ms)|PayloadCons
     * @return parsedRequest : array with each field of the request
     */
    private String[] parseRequest(String request) {
        String[] parsedRequest = null;
        try {
            parsedRequest = request.split("\\|", REQUEST_FIELDS_NB);
            if (parsedRequest[parsedRequest.length-1].contains("|")) {
                    logger.debug("Provider " + this.getClass().toString() + " received a badly formatted request");
                    parsedRequest = null; // TODO throw an exception
            }
        }
        catch (PatternSyntaxException e) {
            logger.error(e.getMessage());
            logger.debug(e.getStackTrace());
        }
        return parsedRequest;
    }

     /**
     * Process request according to the parameters sent and respond in consequence
     * @param request : request String received from consumer
     * format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons(ms)|PayloadCons
     * @return response : send request's response to the consumer
     * format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons(ms)|ReceptionDateProv(ms)|SendingDateProv(ms)|PaylaodProv
     */
    @WebMethod(operationName = "processRequest")
    public String processRequest(@WebParam(name = "request") String request) {
        String response = new String();
        String[] parsedRequest;
        char[] payloadProvider;
        Date receptionDate;
        Date sendingDate;

        // if request = null, badly formatted request or pb with consumer-provider communication
        if (request != null) {
            receptionDate = new Date();

            logger.debug("message received by " + this.getClass() + ": " + request.replace("|", ";"));

            parsedRequest = parseRequest(request);
            // TODO attribute provider id !
            if (!parsedRequest[PROVIDER_ID_INDEX].equals(this.getClass().toString().split(".")[6].split("|")[1])) {
                return "Bad provider received request";
            }
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