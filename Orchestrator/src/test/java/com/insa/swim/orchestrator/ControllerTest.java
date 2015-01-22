/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.orchestrator;

import com.insa.swim.orchestrator.xml.Result;
import com.insa.swim.orchestrator.xml.Results;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alex
 */
public class ControllerTest {

    private Controller controller;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        controller = new Controller();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testStart(){
        
    }
    
    @Test
    public void computeResultsKpiTest() {
        Results results = new Results();
        results.getResults().add(new Result("1|1|100|100|0|1421678400|1421678460|1421678600|1421678700"));
        results.getResults().add(new Result("1|1|0|50|1000|1421678400|1421678600|1421678600|1421678680"));
        results.getResults().add(new Result("LOST|1|1|50|100|1000|1421678470"));
        results.getResults().add(new Result("PROVIDER|1|1|50|100|1000|1421678470"));
        results.getResults().add(new Result("1|1|50|0|1000|1421678400|1421678500|1421678600|1421678720"));
        results.getResults().add(new Result("FORMAT|1|1|50|100|1000|1421678470"));

        results = controller.computeResultsKpi(results);
        
        assertEquals(120, (int) results.getKPI().getMeanTimeConsumerProvider());
        assertEquals(100, (int) results.getKPI().getMeanTimeProviderConsumer());
    }

}
