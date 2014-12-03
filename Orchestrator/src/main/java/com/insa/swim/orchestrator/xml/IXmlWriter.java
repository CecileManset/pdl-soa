/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.orchestrator.xml;

/**
 *
 * @author Christelle
 */
public interface IXmlWriter {
    /**
	 * Write the result in the XML document containing all the results
	 * @param result
	 */
	public void write(Result result);
    
}
