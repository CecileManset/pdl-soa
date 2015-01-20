/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.orchestrator.xml;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Alex
 */
public class XMLWriter {

    private static final Logger LOGGER = LogManager.getLogger(XMLWriter.class);

    public void write(Results results) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Results.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //marshall the root object
            marshaller.marshal(results, new File("res/out.xml"));
        } catch (JAXBException ex) {
            LOGGER.error("An error occured while writing the results");
            LOGGER.debug(ex);
        }
    }
}
