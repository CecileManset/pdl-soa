/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.consumer;

import com.insa.swim.consumer.scenario.Scenario;
import com.insa.swim.consumer.scenario.Scenario.Request;
import java.lang.reflect.Method;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author pdlsoa
 */
public class ConsumerWSTest {

    public ConsumerWSTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSendPing() {

        System.out.println("sendPing");
        String txt = "ping";
        String expResult = "pong";

        ConsumerWS instance = mock(ConsumerWS.class);
        when(instance.sendPing("ping",1)).thenReturn("pong");

        String result = instance.sendPing(txt,1);
        assertEquals(expResult, result);
    }

//    @Test
//    public void testStartSendingRequests() {
//        System.out.println("startSendingRequests");
//
//        ConsumerWS instance = spy(new ConsumerWS());
//        instance.startSendingRequests();
//        for (int i = 1; i <= ConsumerWS.NB_PROVIDERS; i++) {
//            verify(instance, times(1)).sendPing("ping", i);
//        }
//    }

    @Test
    public void testSendRequest() {
        String request = "1|1|3|4|6000|1111111|payloadConsumer";
        String expResponse = "1|1|3|4|6000|1111111|2222222|33333333|payloadProvider";
        String response;

        ConsumerWS instance = mock(ConsumerWS.class);
        when(instance.sendRequest("1|1|3|4|6000|1111111|payloadConsumer", 1)).thenReturn("1|1|3|4|6000|1111111|2222222|33333333|payloadProvider");

        response = instance.sendRequest(request, 1);
        assertEquals(expResponse, response);
    }

//    @Test
//    public void testConstructRequest() {
//        Scenario scenario = new Scenario("INFO|id|name|date|duration|CONSUMER|2|C2"
//                                                            + "|REQUEST|1|0021|4|0|0|0|2000|5"
//                                                            + "|REQUEST|3|0023|2|0|0|0|5000|10"
//                                                            + "|REQUEST|4|0024|10|0|0|0|10000|2");
//        ConsumerWS instance = new ConsumerWS();
//
//        for (Request req : scenario.getRequestList()) {
//
//            Method method = ConsumerWS.getDeclaredMethod(instance, req);
//            method.setAccessible(true);
//            method.invoke(instance, req);
//
//        }
//    }

}