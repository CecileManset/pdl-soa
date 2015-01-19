/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.provider.ws;

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

    @Test
    public void testProcessRequestNullEntry() {
        ProviderWS instance = new ProviderWS();
        assertEquals("null request", instance.processRequest(null));
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

        ProviderWS instance = new ProviderWS();
        String request = "1|1|3|4|6000|1111111|payloadConsumer";
        String response = instance.processRequest(request);

        assertNotNull(response.split("\\|")[6]); // To change to a real assertion - reception date
        assertNotNull(response.split("\\|")[7]); // To change to a real assertion - sending date
        assertEquals("----", response.split("\\|")[8]);

    }

    // TODO verify that Thread.sleep(6000) was called and executed
//    @Test
//    public void testProcessRequestGoodProcessingTime() {
//        ProviderWS instance = spy(new ProviderWS());
//
//        instance.processRequest("1|1|6000|4|SendingTimeConsumer|payload");
//        verify(Thread, times(1)).sleep(6000);
//    }

}