/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.consumer;

import com.insa.swim.consumer.scenario.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    
    private static final Logger LOGGER = LogManager.getLogger(ConsumerWSTest.class);

    public ConsumerWSTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        /* We do not need anything to be done in here, 
           but if you need to do something, please, feel free to delete
           this comment! It's only here for Sonarqube to be happy!
        */
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        /* We do not need anything to be done in here, 
           but if you need to do something, please, feel free to delete
           this comment! It's only here for Sonarqube to be happy!
        */
    }

    @Before
    public void setUp() {
        /* We do not need anything to be done in here, 
           but if you need to do something, please, feel free to delete
           this comment! It's only here for Sonarqube to be happy!
        */
    }

    @After
    public void tearDown() {
        /* We do not need anything to be done in here, 
           but if you need to do something, please, feel free to delete
           this comment! It's only here for Sonarqube to be happy!
        */
    }

    @Test
    public void testSendPing() {

        LOGGER.info("Testing sendPing");
        String txt = "ping";
        String expResult = "pong";

        ConsumerWS instance = mock(ConsumerWS.class);
        when(instance.sendPing("ping")).thenReturn("pong");

        String result = instance.sendPing(txt);
        assertEquals(expResult, result);
    }

    @Test
    public void testConfigConsumer() {
        LOGGER.info("Testing configConsumer");
        String conf = "INFORMATION|1|scenario1|15/11/14|PT0.500S|CONSUMER|1|consumer1|REQUEST|2|400|256|true|200|10|REQUEST|1|100|50|false|null|null|REQUEST|3|0|25|true|100|10|";
        String expResult = "done";

        ConsumerWS instance = mock(ConsumerWS.class);
        when(instance.configConsumer("INFORMATION|1|scenario1|15/11/14|PT0.500S|CONSUMER|1|consumer1|REQUEST|2|400|256|true|200|10|REQUEST|1|100|50|false|null|null|REQUEST|3|0|25|true|100|10|")).thenReturn("done");

        String result = instance.configConsumer(conf);
        assertEquals(expResult, result);
    }

    // Deprecated : startSendingRequests doesn't return anything
    @Test
    public void testStartSendingRequests() {
        LOGGER.info("Testing startSendingRequests");

        ConsumerWS instance = spy(new ConsumerWS());
        instance.startSendingRequests();
        for (int i = 1; i <= ConsumerWS.NB_PROVIDERS; i++) {
            verify(instance, times(1)).sendPing("ping", i);
        }
    }
}