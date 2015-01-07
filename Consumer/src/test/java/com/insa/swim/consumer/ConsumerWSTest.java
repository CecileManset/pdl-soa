/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.consumer;

import com.insa.swim.consumer.scenario.Scenario;
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
        when(instance.sendPing("ping")).thenReturn("pong");

        String result = instance.sendPing(txt);
        assertEquals(expResult, result);
    }

    @Test
    public void testConfigConsumer() {
        System.out.println("configConsumer");
        String conf = "INFORMATION|1|scenario1|15/11/14|PT0.500S|CONSUMER|1|consumer1|REQUEST|2|400|256|true|200|10|REQUEST|1|100|50|false|null|null|REQUEST|3|0|25|true|100|10|";
        String expResult = "done";

        ConsumerWS instance = mock(ConsumerWS.class);
        when(instance.configConsumer("INFORMATION|1|scenario1|15/11/14|PT0.500S|CONSUMER|1|consumer1|REQUEST|2|400|256|true|200|10|REQUEST|1|100|50|false|null|null|REQUEST|3|0|25|true|100|10|")).thenReturn("done");

        String result = instance.configConsumer(conf);
        assertEquals(expResult, result);
    }

    // Deprecated : startSendingRequests doesn't return anything
//    @Test
//    public void testStartSendingRequests() {
//        System.out.println("startSendingRequests");
//        String expResult = "pong";
//
//        ConsumerWS instance = mock(ConsumerWS.class);
//        when(instance.startSendingRequests()).thenReturn("pong");
//
//        String result = instance.startSendingRequests();
//        assertEquals(expResult, result);
//    }
}