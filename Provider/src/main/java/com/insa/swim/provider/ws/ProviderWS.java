/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.provider.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author pdlsoa
 */
@WebService()
public class ProviderWS {

        @WebMethod(operationName = "pingpong")
    public String pingpong(@WebParam(name = "ping") String txt) {
        if (txt != null && txt.equals("ping")) {
            return "pong";
        }
        else return "error";
    }

}
