/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.consumer.scenario;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pdlsoa
 */
public class ScenarioTest {

    public ScenarioTest() {
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
     * Test of init method, of class Scenario.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        String conf = "INFORMATION|1|scenario1|15/11/14|PT0.500S|CONSUMER|1|consumer1|REQUEST|2|400|256|true|200|10|REQUEST|1|100|50|false|null|null|REQUEST|3|0|25|true|100|10|";
        Scenario instance = new Scenario();
        instance.init(conf);

        assertEquals(1, instance.getId());
        assertEquals("scenario1", instance.getName());
        assertEquals("15/11/14", instance.getDate());
        assertEquals("PT0.500S", instance.getDuration());
        assertEquals(1, instance.getConsumerId());
        assertEquals("consumer1", instance.getConsumerName());

        assertEquals(3, instance.getRequestList().size());
        assertEquals(2, instance.getRequestList().get(0).getProviderId());
        assertEquals(0, instance.getRequestList().get(2).getSendingTime());
        assertEquals(false, instance.getRequestList().get(1).isPeriodic());

        assertEquals(true, instance.getRequestList().get(0).isPeriodic());
        assertEquals(200, instance.getRequestList().get(0).getPeriod());
        assertEquals(10, instance.getRequestList().get(0).getNumberRequest());
    }

}