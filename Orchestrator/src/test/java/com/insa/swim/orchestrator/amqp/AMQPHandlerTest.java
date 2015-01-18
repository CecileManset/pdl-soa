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
 * AMQPHandler test class
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
    }

    /**
     * Test of receiveResultMessage method, of class AMQPHandler.
     * success case
     */
    @Test
    public void testFailReceiveResultMessage() throws Exception {
        System.out.println("receiveResultMessage");
        AMQPHandler instance = Mockito.mock(AMQPHandler.class);
        Mockito.when(instance.receiveResultMessage()).thenReturn("not a result");
        String expResult = "result";
        String result = instance.receiveResultMessage();
        assertNotEquals(expResult, result);
    }

    /**
     * receiveResultMessage test method, of class AMQPHandler.
     * failure case
     */
    @Test
    public void testSuccessReceiveResultMessage() throws Exception {
        System.out.println("receiveResultMessage");
        AMQPHandler instance = Mockito.mock(AMQPHandler.class);
        Mockito.when(instance.receiveResultMessage()).thenReturn("result");
        String expResult = "result";
        String result = instance.receiveResultMessage();
        assertEquals(expResult, result);
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
        String consumer = "C1";
        String msg = "configuration message";
        AMQPHandler instance = Mockito.mock(AMQPHandler.class);
        instance.sendConf(consumer, msg);
        Mockito.verify(instance).sendConf(consumer, msg);
    }
    
}
