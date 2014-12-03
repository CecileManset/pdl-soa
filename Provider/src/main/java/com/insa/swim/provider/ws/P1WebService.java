/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.provider.ws;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author tp1_g2
 */
@WebService(serviceName = "P1WebService")
public class P1WebService {

    /**
     * This is a sample web service operation
     * @param ping
     * @return pong
     */
    @WebMethod(operationName = "pingpong")
    public String pingpong(@WebParam(name = "ping") String txt) {
        if (txt.equals("ping")) {
            return "pong";
        }
        else return "error";
    }
}
