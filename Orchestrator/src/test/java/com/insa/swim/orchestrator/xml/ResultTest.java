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
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void constructResultFromString() {
        Result result = new Result("1|1|50|100|1000|1421678470|1421678570|1421678670|1421678770");
        assertEquals("false", result.getError());
        assertEquals("1", result.getConsumer());
        assertEquals("1", result.getProvider());
        assertEquals(50, result.getRequestSize());
        assertEquals(100, result.getResponseSize());
        assertEquals(1000, result.getProcessingTime());
        assertEquals(1421678470, result.getSendingDateConsumer());
        assertEquals(1421678570, result.getReceptionDateProvider());
        assertEquals(1421678670, result.getSendingDateProvider());
        assertEquals(1421678770, result.getReceptionDateConsumer());
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
        
        result = new Result("FORMAT|1|1|50|100|1000|1421678470");
        assertEquals("FORMAT", result.getError());
        assertEquals("1", result.getConsumer());
        assertEquals("1", result.getProvider());
        assertEquals(50, result.getRequestSize());
        assertEquals(100, result.getResponseSize());
        assertEquals(1000, result.getProcessingTime());
        assertEquals(1421678470, result.getSendingDateConsumer());
        
        result = new Result("PROVIDER|1|1|50|100|1000|1421678470");
        assertEquals("PROVIDER", result.getError());
        assertEquals("1", result.getConsumer());
        assertEquals("1", result.getProvider());
        assertEquals(50, result.getRequestSize());
        assertEquals(100, result.getResponseSize());
        assertEquals(1000, result.getProcessingTime());
        assertEquals(1421678470, result.getSendingDateConsumer());
    }
}
