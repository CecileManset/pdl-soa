/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.orchestrator.xml;

import com.insa.swim.orchestrator.xml.Scenario.Consumers.Consumer;
import com.insa.swim.orchestrator.xml.Scenario.Consumers.Consumer.Requests.Request;
import com.insa.swim.orchestrator.xml.Scenario.Information;
//import com.insa.swim.orchestrator.xml.Scenario.Producers.Producer;
import java.io.File;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

/**
 *
 * @author Christelle
 */
public class XmlParser implements IXmlReader, IXmlWriter {

    private Scenario scenario;
    private static final Logger LOGGER = LogManager.getLogger(XmlParser.class);

    @Override
    public Scenario parseXml() {
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance("com.insa.swim.orchestrator.xml");

            Unmarshaller unmarshaller = jc.createUnmarshaller();

            // parsing the object xml created
            scenario = (Scenario) unmarshaller.unmarshal(new File("res/Scenario.xml"));
        } catch (JAXBException e) {
            LOGGER.error("An error occured while parsing the XML file describing the scenario");
            LOGGER.debug(e);
        }
        return scenario;
    }

    /**
     *
     * @param path
     * @return
     */
    @Override
    public boolean validateXML(String path) {
        JAXBContext jc;
        boolean isValid = true;
        try {
            jc = JAXBContext.newInstance("com.insa.swim.orchestrator.xml");
            Unmarshaller unmarshaller = jc.createUnmarshaller();

			// verifying that the XML file respects the XSD schema
            // creation of a schema factory
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);

            // creating the schema corresponding to the unmarshaller
            Schema schema = sf.newSchema(new File(path));
            unmarshaller.setSchema(schema);
        } catch (SAXException ex) {
            isValid = false;
            LOGGER.error("An error occured while validating the XML file describing the scenario");
            LOGGER.debug(ex);
        } catch (JAXBException ex) {
            isValid = false;
            LOGGER.error("An error occured while validating the XML file describing the scenario");
            LOGGER.debug(ex);
        }
        return true;
    }

    @Override
    public List<Consumer> parseConsumer() {
        List<Consumer> consumers = scenario.consumers.getConsumer();
        return consumers;

    }
    /*
     @Override
     public List<Producer> parseProvider() {
     List<Producer> producers = scenario.producers.getProducer();
     return producers;
     }
     */

    /**
     *
     * @param result
     * @deprecated use XMLWriter class instead
     */
    @Deprecated
    @Override
    public void write(Result result) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Result.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //marshall the root object
            marshaller.marshal(result, new File("res/out.xml"));
        } catch (JAXBException ex) {
            LOGGER.error("An error occured while writing a result : [" + result + "]");
            LOGGER.debug(ex);
        }
    }

    public Information parseInformation() {
        Information information = scenario.getInformation();
        return information;

    }

    // juste pour tester
    public static void printScenario(Scenario scenario) {

        List<Consumer> consumers = scenario.consumers.getConsumer();
		//List <Producer> producers = scenario.producers.getProducer();

		//List<Producer> producers = scenario.producers.getProducer();
        for (Consumer consumer1 : consumers) {
            Consumer consumer = (Consumer) consumer1;
            List<Request> req = consumer.requests.getRequest();
            System.out.print("consumer name : " + consumer.getName());
            for (Request r : req) {
                //System.out.print("\t nb req  : " + r.getProviderId());
            }
            System.out.print("\n");
        }

    }

    public static void main(String[] args) {
        XmlParser x = new XmlParser();
        System.out.println("bool = " + x.validateXML("res/scenario.xml"));
    }
}
