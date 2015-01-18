//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2015.01.12 � 05:10:35 PM CET 
//


package com.insa.swim.orchestrator.xml;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the main.java.com.insa.swim.orchestrator.xml package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: main.java.com.insa.swim.orchestrator.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Scenario }
     * 
     */
    public Scenario createScenario() {
        return new Scenario();
    }

    /**
     * Create an instance of {@link Scenario.Consumers }
     * 
     */
    public Scenario.Consumers createScenarioConsumers() {
        return new Scenario.Consumers();
    }

    /**
     * Create an instance of {@link Scenario.Consumers.Consumer }
     * 
     */
    public Scenario.Consumers.Consumer createScenarioConsumersConsumer() {
        return new Scenario.Consumers.Consumer();
    }

    /**
     * Create an instance of {@link Scenario.Consumers.Consumer.Requests }
     * 
     */
    public Scenario.Consumers.Consumer.Requests createScenarioConsumersConsumerRequests() {
        return new Scenario.Consumers.Consumer.Requests();
    }

    /**
     * Create an instance of {@link Scenario.Consumers.Consumer.Requests.Request }
     * 
     */
    public Scenario.Consumers.Consumer.Requests.Request createScenarioConsumersConsumerRequestsRequest() {
        return new Scenario.Consumers.Consumer.Requests.Request();
    }

    /**
     * Create an instance of {@link Scenario.Information }
     * 
     */
    public Scenario.Information createScenarioInformation() {
        return new Scenario.Information();
    }

    /**
     * Create an instance of {@link Scenario.Consumers.Consumer.Requests.Request.ProviderSettings }
     * 
     */
    public Scenario.Consumers.Consumer.Requests.Request.ProviderSettings createScenarioConsumersConsumerRequestsRequestProviderSettings() {
        return new Scenario.Consumers.Consumer.Requests.Request.ProviderSettings();
    }

    /**
     * Create an instance of {@link Scenario.Information.Esb }
     * 
     */
    public Scenario.Information.Esb createScenarioInformationEsb() {
        return new Scenario.Information.Esb();
    }

}
