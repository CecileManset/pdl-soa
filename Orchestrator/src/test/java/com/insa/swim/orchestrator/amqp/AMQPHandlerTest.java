/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.orchestrator.amqp;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author joanna
 */
@RunWith(MockitoJUnitRunner.class)
public class AMQPHandlerTest {
    
    public AMQPHandlerTest() {
    }

    /**
     * Test of closeConnection method, of class AMQPHandler.
     */
    @Test
    public void testCloseConnection() {
        System.out.println("closeConnection");
        AMQPHandler instance = Mockito.mock(AMQPHandler.class);
        instance.closeConnection();
        Mockito.verify(instance).closeConnection();
        System.out.println("end closeConnection");
    }

    /**
     * Test of receiveResultMessage method, of class AMQPHandler.
     */
    @Test
    public void testReceiveResultMessage() throws Exception {
        System.out.println("receiveResultMessage");
        AMQPHandler instance = Mockito.mock(AMQPHandler.class);
        Mockito.when(instance.receiveResultMessage()).thenReturn("result", "not a result");
        String expResult = "result";
        System.out.println("success case");
        String result = instance.receiveResultMessage();
        Mockito.verify(instance.receiveResultMessage());
        assertEquals(expResult, result);
        
        System.out.println("failure case");
        result = instance.receiveResultMessage();
        Mockito.verify(instance.receiveResultMessage());
        assertNotEquals(expResult, result);
    }

    /**
     * Test of sendStart method, of class AMQPHandler.
     */
    @Test
    public void testSendStart() throws Exception {
        System.out.println("sendStart");
        AMQPHandler instance = Mockito.mock(AMQPHandler.class);
        instance.sendStart();
        Mockito.verify(instance).sendStart();
    }

    /**
     * Test of sendConf method, of class AMQPHandler.
     */
    @Test
    public void testSendConf() throws Exception {
        System.out.println("sendConf");        
        String msgC1 = "confC1";
        String msgC2 = "confC2";
        AMQPHandler instance = Mockito.mock(AMQPHandler.class);
        instance.sendConf("C1", msgC1);
        instance.sendConf("C2", msgC2);
        Mockito.verify(instance).sendConf("C1", msgC1);
        Mockito.verify(instance).sendConf("C2", msgC2);
    }
    
}
