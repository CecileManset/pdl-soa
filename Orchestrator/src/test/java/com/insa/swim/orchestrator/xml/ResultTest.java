/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.orchestrator.xml;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nicolas
 */
public class ResultTest {
    
    public ResultTest() {
    }

    @Test
    public void constructResultFromString() {
        Result result = new Result("3|2|60|120|1500|1421678400|1421678650|1421679650|1421679770");
        assertEquals("false", result.getError());
        assertEquals("3", result.getConsumer());
        assertEquals("2", result.getProvider());
        assertEquals(60, result.getRequestSize());
        assertEquals(120, result.getResponseSize());
        assertEquals(1500, result.getProcessingTime());
        assertEquals(1421678400, result.getSendingDateConsumer());
        assertEquals(1421678650, result.getReceptionDateProvider());
        assertEquals(1421679650, result.getSendingDateProvider());
        assertEquals(1421679770, result.getReceptionDateConsumer());
    }
    
    @Test
    public void constructErrorResultFromString() {
        Result result = new Result("LOST|1|1|50|100|1000|1421678470");
        assertEquals("LOST", result.getError());
        assertEquals("1", result.getConsumer());
        assertEquals("1", result.getProvider());
        assertEquals(50, result.getRequestSize());
        assertEquals(100, result.getResponseSize());
        assertEquals(1000, result.getProcessingTime());
        assertEquals(1421678470, result.getSendingDateConsumer());
        
        result = new Result("FORMAT|2|3|500|1000|200|1421678570");
        assertEquals("FORMAT", result.getError());
        assertEquals("2", result.getConsumer());
        assertEquals("3", result.getProvider());
        assertEquals(500, result.getRequestSize());
        assertEquals(1000, result.getResponseSize());
        assertEquals(200, result.getProcessingTime());
        assertEquals(1421678570, result.getSendingDateConsumer());
        
        result = new Result("PROVIDER|1|5|80|300|1700|1421678487");
        assertEquals("PROVIDER", result.getError());
        assertEquals("1", result.getConsumer());
        assertEquals("5", result.getProvider());
        assertEquals(80, result.getRequestSize());
        assertEquals(300, result.getResponseSize());
        assertEquals(1700, result.getProcessingTime());
        assertEquals(1421678487, result.getSendingDateConsumer());
    }
}
