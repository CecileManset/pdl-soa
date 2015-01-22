/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.orchestrator.amqp;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import java.io.IOException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyInt;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author joanna
 */
@RunWith(MockitoJUnitRunner.class)
public class AMQPHandlerTest {
    
    @Mock
    private QueueingConsumer resultsConsumer;
    @InjectMocks
    private AMQPHandler amqp;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
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

    @Test
    public void testReceiveResultMessage() throws InterruptedException{
        //GIVEN
        byte[] body = "success".getBytes();
        QueueingConsumer.Delivery del = new QueueingConsumer.Delivery(null, null, body);
        
        //WHEN
        Mockito.when(resultsConsumer.nextDelivery(anyInt())).thenReturn(del);
        
        //THEN
        assertEquals("success", amqp.receiveResultMessage());
        
    }
    
    
    /**
     * Test of sendStart method, of class AMQPHandler.
     * @throws java.io.IOException
     */
    @Test
    public void testSendStart() throws IOException {
        System.out.println("sendStart");
        AMQPHandler instance = Mockito.mock(AMQPHandler.class);
        instance.sendStart();
        Mockito.verify(instance).sendStart();
    }

    /**
     * Test of sendConf method, of class AMQPHandler.
     * @throws java.io.IOException
     */
    @Test
    public void testSendConf() throws IOException{
        System.out.println("sendConf");        
        String consumer = "C1";
        String msg = "confC1";
        AMQPHandler instance = Mockito.mock(AMQPHandler.class);
        instance.sendConf(consumer, msg);
        Mockito.verify(instance).sendConf(consumer, msg);
    }
    
}
