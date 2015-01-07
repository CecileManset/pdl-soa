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

}