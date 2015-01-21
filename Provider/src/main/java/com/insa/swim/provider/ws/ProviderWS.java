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

    private static final Logger LOGGER = LogManager.getLogger("Provider");
    private static final int PROCESSING_TIME_INDEX = 4;
    private static final int RESPONSE_SIZE_INDEX = 3;
    private static final int PROVIDER_ID_INDEX = 1;
    private static final int REQUEST_FIELDS_NB = 7;
    private int providerNumber = -1;

    /**
     * Method used to test consumer-provider communication
     * @param txt : payload of consumer request
     * @return "pong" if txt="ping", else "error"
     */
    @WebMethod(operationName = "pingpong")
    public String pingpong(@WebParam(name = "ping") String txt) {
        LOGGER.debug("Message received by " + this.getClass() + ": " + txt);

        if (txt != null && txt.equals("ping")) {
            return "pong";
        } else {
            return "error";
        }
    }

    /**
     * Parse incoming request from consumer to retrieve the different parameters
     * Verify that request contains 7 fields
     * @param request : request String received from consumer
     * format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons(ms)|PayloadCons
     * @return parsedRequest : array containing the different fields of the request
     */
    private String[] parseRequest(String request) {
        String[] parsedRequest = null;

        if (request.contains("|")) {
            parsedRequest = request.split("\\|", REQUEST_FIELDS_NB);

            if (parsedRequest[parsedRequest.length - 1].contains("|")) {
                LOGGER.debug("Provider " + this.getClass().toString() + " received a badly formatted request : " + request);
                parsedRequest = null;
            }
        } else {
            LOGGER.debug("Provider " + this.getClass().toString() + " received a badly formatted request : " + request);
            parsedRequest = null;
        }
        return parsedRequest;
    }

    /**
     * Process request according to the parameters sent and respond in consequence, adding reception and sending timestamps
     * @param request : received from consumer
     *  req format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons(ms)|PayloadCons
     * @return response to the consumer
     * result = request - PayloadCons + ReceptionDateProv|RendingDateProv|PayloadProv
     */
    @WebMethod(operationName = "processRequest")
    public String processRequest(@WebParam(name = "request") String request) {
        String response = new String();
        String[] parsedRequest;
        char[] payloadProvider;
        Date receptionDate;
        Date sendingDate;
        int providerNumberFromName = Integer.parseInt(this.getClass().toString().split("\\.")[6].split("|")[2]);

        // if request = null, badly formatted request or pb with consumer-provider communication
        if (request != null) {
            receptionDate = new Date();
            boolean badProvider = false;

            LOGGER.debug("Message received by " + this.getClass() + ": " + request.replace("|", ";"));

            parsedRequest = parseRequest(request);

            if (parsedRequest != null) {

                // initialise provider ID if first request received
                if (providerNumber == -1) {
                    LOGGER.debug("Provider P" + providerNumberFromName + " initializing its ID");
                    providerNumber = Integer.parseInt(parsedRequest[PROVIDER_ID_INDEX]);

                    // check that the provider was initialized with the right number
                    if (providerNumber != providerNumberFromName) {
                        LOGGER.debug("Provider P" + providerNumberFromName + "initialization failed");
                        providerNumber = -1;
                        // else, it means that the message what not intended for this provider
                        badProvider = true;
                    } else {
                        LOGGER.debug("Provider P" + providerNumberFromName + "initialization succeeded");
                    }
                }

                // Verify that the right provider received the request
                if (!badProvider && (providerNumber == Integer.parseInt(parsedRequest[PROVIDER_ID_INDEX]))) {

                    // Sleep to fake the request processing
                    try {
                        Thread.sleep(Integer.parseInt(parsedRequest[PROCESSING_TIME_INDEX]));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    payloadProvider = new char[Integer.parseInt(parsedRequest[RESPONSE_SIZE_INDEX])];
                    for (int i = 0; i < payloadProvider.length; i++) {
                        payloadProvider[i] = '-';
                    }

                    // response = request - consumer payload
                    for (int i = 0; i < parsedRequest.length - 1; i++) {
                        response += parsedRequest[i] + "|";
                    }
                    sendingDate = new Date();
                    // + provider info
                    response += Long.toString(receptionDate.getTime()) + "|" + Long.toString(sendingDate.getTime()) + "|" + new String(payloadProvider);
                } else {
                    response = "PROVIDER|" + request;
                    LOGGER.debug("Bad provider (P" + providerNumberFromName + ") received request : " + request);
                    return response;
                }
            } else {
                LOGGER.debug("Provider P" + providerNumberFromName + " couldn't parse request : " + request);
                return "REQUEST";
            }
        } else {
            LOGGER.debug("Provider P" + providerNumberFromName + " returned null request");
            return "REQUEST";
        }

        LOGGER.debug("Message sent from provider P" + providerNumberFromName + " to Consumer " + parsedRequest[0] + ": " + response);

        return response;
    }
}
