/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.consumer;

import com.insa.swim.consumer.scenario.Scenario;
import com.insa.swim.consumer.scenario.Scenario.Request;
import java.lang.reflect.Method;
import java.util.Date;
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
        when(instance.sendPing("ping", 1)).thenReturn("pong");

        String result = instance.sendPing(txt, 1);
        assertEquals(expResult, result);
    }

    // pb : teter constructRequest car met timestamp __

    // Tests periodic request and constructRequest
//    @Test
//    public void testStartSendingRequestsPeriodic() {
//        System.out.println("startSendingRequestsPeriodic");
//        String constructedRequest;
//
//        ConsumerWS instance = spy(new ConsumerWS());
//        instance.setScenario(new Scenario("INFO|0|name|0|10|CONSUMER|2|C2"
//                                                            + "|REQUEST|4|0024|10|true|100|5|10000|2"));
//        instance.startSendingRequests();
//        verify(instance, times(5)).sendRequest(constructedRequest, 4);
//    }
//    
//    // Tests non periodic request and constructRequest
//    @Test
//    public void testStartSendingRequestsNonPeriodic() {
//        System.out.println("startSendingRequestsNonPeriodic");
//
//        ConsumerWS instance = spy(new ConsumerWS());
//        instance.setScenario(new Scenario("INFO|0|name|0|10|CONSUMER|2|C2"
//                                                            + "|REQUEST|1|0021|4|0|0|0|2000|5"
//                                                            + "|REQUEST|3|0023|2|0|0|0|5000|10"));
//        instance.startSendingRequests(); 
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

    //dirty test
    @Test
    public void testConstructResponse() {
        String providerResponse1 = "2|2|25|0|300|1421707165479|1421707165515|1421707165816|xxxxx";
        String providerResponse2 = "2|2|25|0|300|1421707165479|1421707165515|1421707165816|";
        String expResponse = "2|2|25|0|300|1421707165479|1421707165515|1421707165816|1421707165823";

        String[] responseParts1 = providerResponse1.split("\\|",-1);
        String result1 = "";
        int i;
        for (i = 0; i < responseParts1.length - 1; i++) {
            result1 += responseParts1[i] + "|";
        }
        // resp format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons|ReceptionDateProv|SendingDateProv|ReceptionDateCons
        result1 += "1421707165823";

        String[] responseParts2 = providerResponse2.split("\\|",-1);
        String result2 = "";
        for (i = 0; i < responseParts2.length - 1; i++) {
            result2 += responseParts2[i] + "|";
        }
        // resp format : ConsID|ProvID|ReqSize|RespSize|ProcessingTime|SendingDateCons|ReceptionDateProv|SendingDateProv|ReceptionDateCons
        result2 += "1421707165823";

        assertEquals(9, responseParts1.length);
        assertEquals(expResponse, result1);
        assertEquals(9, responseParts2.length);
        assertEquals(expResponse, result2);
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
