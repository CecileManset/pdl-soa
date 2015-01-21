/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.orchestrator.xml;

import com.insa.swim.orchestrator.xml.Scenario.Consumers;
import com.insa.swim.orchestrator.xml.Scenario.Consumers.Consumer;
import com.insa.swim.orchestrator.xml.Scenario.Consumers.Consumer.Requests;
import com.insa.swim.orchestrator.xml.Scenario.Consumers.Consumer.Requests.Request;
import com.insa.swim.orchestrator.xml.Scenario.Consumers.Consumer.Requests.Request.ProviderSettings;
import com.insa.swim.orchestrator.xml.Scenario.Information.Esb;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

// JUnit addon 1.4
/**
 *
 * @author Christelle
 */
public class XmlParserTest {

    private static File file;

    @BeforeClass
    public static void setUpBeforeClass() {
        file = new File("res/Scenario.xml");
    }

    public XmlParserTest() {
        super();
    }

    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
    /**
     * Test of parseXml method, of class XmlParser.
     *
     * @throws java.lang.Exception
     */
 
    /**
     * Test of parseXml method, of class XmlParser.
     * @param result
     * @throws java.lang.Exception
     */
    

     @Test
    public void testParseXml() throws Exception {
        System.out.println("parseXml");
        XmlParser instance = new XmlParser();
        Scenario expResult = new Scenario();

        Scenario.Information info = new Scenario.Information();
        info.setDate("15/11/14");
        DatatypeFactory factory = DatatypeFactory.newInstance();
        Duration duration = factory.newDuration("PT0.500S");
        System.out.println("duraiton in seconds : " + duration.getSeconds());
        info.setDuration(duration);

        Esb esb = new Esb();
        esb.setName("openESB");
        esb.setVersion("1.0.0");
        info.setEsb(esb);

        info.setId((byte) 1);

        info.setName("scenario1");

        expResult.setInformation(info);
        Scenario result = instance.parseXml();
        assertEquals(expResult.getInformation().getDate(),
                result.getInformation().getDate());
        assertEquals(expResult.getInformation().getDuration(), result.getInformation().getDuration());
        assertEquals(expResult.getInformation().getEsb().getName(), result.getInformation().getEsb().getName());
        assertEquals(expResult.getInformation().getEsb().getVersion(), result.getInformation().getEsb().getVersion());
        assertEquals(expResult.getInformation().getName(), result.getInformation().getName());
    }
     @Test
    public void testParseConsumer() {
        XmlParser instance = new XmlParser();
        Scenario expResult = new Scenario();

        Scenario result = instance.parseXml();

        for (int i = 0; i < result.getConsumers().getConsumer().size(); i++) {
            Consumer consumer1 = result.getConsumers().getConsumer().get(0);
            assertThat("consumer1", is(consumer1.getName()));
            assertThat((byte) 1, is(consumer1.getId()));

            for (int j = 0; j < consumer1.getRequests().getRequest().size(); j++) {
                Request req1 = consumer1.getRequests().getRequest().get(0);
                Request req2 = consumer1.getRequests().getRequest().get(1);
                Request req3 = consumer1.getRequests().getRequest().get(2);

                testRequest(req1, ((short) 256), ((short) 400), "true", (short) 200, (byte) 10, (byte) 2, (short) 500, (short) 200);
                testRequest(req2, ((short) 50), ((short) 100), "false", (short) 0.1, (byte) 0.1, (byte) (1), (short) 1000, (short) 20);
                testRequest(req3, ((short) 25), ((short) 0), "true", (short) 100, (byte) 10, (byte) 3, (short) 500, (short) 60);

            }
            Consumer consumer2 = result.getConsumers().getConsumer().get(1);
            assertThat("consumer2", is(consumer2.getName()));
            assertThat((byte) 2, is(consumer2.getId()));

            for (int j = 0; j < consumer2.getRequests().getRequest().size(); j++) {
                Request req1 = consumer2.getRequests().getRequest().get(0);
                Request req2 = consumer2.getRequests().getRequest().get(1);

                testRequest(req1, ((short) 50), ((short) 0), "true", (short) 200, (byte) 10, (byte) 1, (short) 100, (short) 200);
                testRequest(req2, ((short) 25), ((short) 300), "false", (short) 0.1, (byte) 0.1, (byte) (2), (short) 300, (short) 0);

            }
        }

    }

    public void testRequest(Request req, short size, short sendingTime, String periodic,
            short period, byte numberRequest, byte provId, short processingTime, short responseSize) {
        assertThat(size, is(req.getSize()));
        assertThat(sendingTime, is(req.getSendingTime()));
        assertThat(periodic, is(req.getPeriodic()));

        if (period != (short) 0.1) {
            assertThat(period, is(req.getPeriod()));
        }

        if (numberRequest != (byte) 0.1) {
            assertThat(numberRequest, is(req.getNumberRequests()));
        }

        assertThat(provId, is(req.getProviderSettings().getProviderId()));
        assertThat(processingTime, is(req.getProviderSettings().getProcessingTime()));
        assertThat(responseSize, is(req.getProviderSettings().getResponseSize()));
    }
    
    @Test
    public void testWrite() throws FileNotFoundException, IOException, Exception {
        XmlParser instance = new XmlParser();
        Scenario expResult = new Scenario();
        Result result = new Result();
        result.setName("custumer1");
        result.setTime("20");
        instance.write(result);

       // File testFile = new File("res/testFile.xml");
        File testFile = new File("res/testFile.xml");
        FileReader fr = new FileReader(testFile);
        FileReader fr2 = new FileReader("res/out.xml");
        BufferedReader br = new BufferedReader(fr);
        BufferedReader br2 = new BufferedReader(fr2);

        ArrayList<String> contenuFichier = new ArrayList();

        String line = br.readLine();
        String lines = br2.readLine();
        System.out.println("line 0 : "+ line);
        int i = 0;
        int j = 0;   

        while (line != null) {
            contenuFichier.add(i,line);
            line = br.readLine();            
            System.out.println("liste ["+i+"]= "+ contenuFichier.get(i));
            i++;
        }
        br.close();
        fr.close();
      
       
         while (lines != null) {
                System.out.println("expected ["+j+"]= "+ lines);
                 assertThat(contenuFichier.get(j), is(lines));
                 lines = br2.readLine(); 
            j++;
        }
         br2.close();
        fr2.close();
    }
}
