/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.orchestrator.xml;

import com.insa.swim.orchestrator.xml.Scenario.Consumers.Consumer;
import com.insa.swim.orchestrator.xml.Scenario.Information;
import java.util.List;

/**
 *
 * @author Christelle
 */
public interface IXmlReader {
	/**
	 * compare the xsd to the xml to check if it is well-formated
	 * @param path
	 * 		path to the xml file
	 * @return
	 * 		true if well formated
	 */
	public boolean validateXML(String path);
	public Information parseInformation();
	public  List<Consumer> parseConsumer();
	//public List<Producer> parseProvider();
	public Scenario parseXml();
   
}
