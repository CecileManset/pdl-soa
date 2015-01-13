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

/**
 *
 * @author pdlsoa
 */
public class ProviderWSTest {

    public ProviderWSTest() {
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

}