/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.consumer.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author pdlsoa
 */
@WebService()
public class C1WebService {
    @WebMethod(operationName = "useless")
    public String useless(@WebParam(name = "start") String txt) {
            return "o";
        }

}
