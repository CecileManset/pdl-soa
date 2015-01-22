/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.provider.ws;

import com.insa.swim.provider.ws.p1.P1WebService;
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
public class ProviderWSTest {

    public ProviderWSTest() {
    }

    /**
     * Test of pingpong method, of class ProviderWS.
     */
    @Test
    public void testPingpong() {
        ProviderWS instance = new ProviderWS();
        assertEquals("pong", instance.pingpong("ping"));
        assertEquals("error", instance.pingpong("aa"));
        assertEquals("error", instance.pingpong(""));
        assertEquals("error", instance.pingpong(null));
    }


     /**
     * Test badly formatted requests (null, without pipes or with too many fields)
     * Use a particular provider due to code specifities. All providers work the same for now
     */
    @Test
    public void testProcessRequestBadEntry() {
        P1WebService instance = new P1WebService();
        assertEquals("REQUEST", instance.processRequest(null));
        assertEquals("REQUEST", instance.processRequest(""));
        assertEquals("REQUEST", instance.processRequest("coucou"));
        assertEquals("REQUEST", instance.processRequest("0|1|4|6|10|1111111|xxxxxx|coucou"));
    }

     /**
     * Test provider ID initialization with consumer request
     * Use a particular provider due to code specifities. All providers work the same for now
     */
    @Test
    public void testProcessRequestInitializeProviderId() {
        P1WebService instance = new P1WebService();
        String requestToP1 = "0|1|4|6|10|1111111|xxxxxx";
        String requestToP3 = "0|3|4|6|10|1111111|xxxxxx";
        int goodId = 1;
        int badId = -1;

        // Send a message to bad provider
        instance.processRequest(requestToP3);
        assertEquals(instance.getProviderNumber(), badId);

        // Send a message to good provider for first time => initialization
        instance.processRequest(requestToP1);
        assertEquals(instance.getProviderNumber(), goodId);

        // Send a message to bad provider => don't try to initialize again
        instance.processRequest(requestToP1);
        assertEquals(instance.getProviderNumber(), goodId);
    }

    // TODO verify receivingDate
    // TODO verify that response = request + date + payload
    @Test
    public void testProcessRequestGoodResponse() {
//        String receivingDate;
//
//        ProviderWS instance = mock(ProviderWS.class);
//        when()
//
//        assertEquals("1|1|6000|4|SendingTimeConsumer|payload|" + receivingDate + "|", instance.processRequest("1|1|6000|4|SendingTimeConsumer|payload"));

//        ProviderWS instance = new ProviderWS();
//        String request = "1|1|3|4|6000|1111111|payloadConsumer";
        // need to mock class name retrieved with this.getClass
//        String response = instance.processRequest(request);
//
//        assertNotNull(response.split("\\|")[6]); // To change to a real assertion - reception date
//        assertNotNull(response.split("\\|")[7]); // To change to a real assertion - sending date
//        assertEquals("----", response.split("\\|")[8]);

    }

    // TODO verify that Thread.sleep(6000) was called and executed
//    @Test
//    public void testProcessRequestGoodProcessingTime() {
//        ProviderWS instance = spy(new ProviderWS());
//
//        instance.processRequest("1|1|6000|4|SendingTimeConsumer|payload");
//        verify(Thread, times(1)).sleep(6000);
//    }

    // Test processRequest : bad provider

    // Test parseRequest : request with more than 7 info

}