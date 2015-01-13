/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.provider.ws;

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

    @WebMethod(operationName = "pingpong")
    public String pingpong(@WebParam(name = "ping") String txt) {
        logger.debug("message received by " + this.getClass() + ": " + txt);
                try {
            Thread.sleep(120000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (txt != null && txt.equals("ping")) {
            return "pong";
        }
        else return "error";
    }

}
