//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2015.01.12 � 05:10:35 PM CET 
//
package com.insa.swim.orchestrator.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;

/**
 * <p>
 * Classe Java pour anonymous complex type.
 *
 * <p>
 * Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette
 * classe.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="information">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                   &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}duration"/>
 *                   &lt;element name="esb">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="consumers">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="consumer" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                             &lt;element name="requests">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="request" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="providerSettings">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                                                           &lt;element name="responseSize" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                                                           &lt;element name="processingTime" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                                                 &lt;element name="sendingTime" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                                                 &lt;element name="periodic" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="period" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *                                                 &lt;element name="numberRequests" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "information",
    "consumers"
})
@XmlRootElement(name = "scenario")
public class Scenario {

    @XmlElement(required = true)
    protected Scenario.Information information;
    @XmlElement(required = true)
    protected Scenario.Consumers consumers;

    /**
     * Obtient la valeur de la propri�t� information.
     *
     * @return possible object is {@link Scenario.Information }
     *
     */
    public Scenario.Information getInformation() {
        return information;
    }

    /**
     * D�finit la valeur de la propri�t� information.
     *
     * @param value allowed object is {@link Scenario.Information }
     *
     */
    public void setInformation(Scenario.Information value) {
        this.information = value;
    }

    /**
     * Obtient la valeur de la propri�t� consumers.
     *
     * @return possible object is {@link Scenario.Consumers }
     *
     */
    public Scenario.Consumers getConsumers() {
        return consumers;
    }

    /**
     * D�finit la valeur de la propri�t� consumers.
     *
     * @param value allowed object is {@link Scenario.Consumers }
     *
     */
    public void setConsumers(Scenario.Consumers value) {
        this.consumers = value;
    }

    public int getTotalOfRequest() {
        int total = 0;
        for (Consumers.Consumer c : consumers.getConsumer()) {
            for (Consumers.Consumer.Requests.Request r : c.getRequests().getRequest()) {
                if (r.periodic.equals("false")) {
                    total++;
                }
                else {
                    total += r.getNumberRequests();
                }
            }
        }
        return total;
    }

    /**
     * <p>
     * Classe Java pour anonymous complex type.
     *
     * <p>
     * Le fragment de sch�ma suivant indique le contenu attendu figurant dans
     * cette classe.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="consumer" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                   &lt;element name="requests">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="request" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="providerSettings">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                                                 &lt;element name="responseSize" type="{http://www.w3.org/2001/XMLSchema}short"/>
     *                                                 &lt;element name="processingTime" type="{http://www.w3.org/2001/XMLSchema}short"/>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}short"/>
     *                                       &lt;element name="sendingTime" type="{http://www.w3.org/2001/XMLSchema}short"/>
     *                                       &lt;element name="periodic" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="period" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
     *                                       &lt;element name="numberRequests" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "consumer"
    })
    public static class Consumers {

        protected List<Scenario.Consumers.Consumer> consumer;

        /**
         * Gets the value of the consumer property.
         *
         * <p>
         * This accessor method returns a reference to the live list, not a
         * snapshot. Therefore any modification you make to the returned list
         * will be present inside the JAXB object. This is why there is not a
         * <CODE>set</CODE> method for the consumer property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getConsumer().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Scenario.Consumers.Consumer }
         *
         *
         */
        public List<Scenario.Consumers.Consumer> getConsumer() {
            if (consumer == null) {
                consumer = new ArrayList<Scenario.Consumers.Consumer>();
            }
            return this.consumer;
        }

        public void setConsumer(List<Scenario.Consumers.Consumer> consumer) {
            this.consumer = consumer;
        }

        /**
         * <p>
         * Classe Java pour anonymous complex type.
         *
         * <p>
         * Le fragment de sch�ma suivant indique le contenu attendu figurant
         * dans cette classe.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}byte"/>
         *         &lt;element name="requests">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="request" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="providerSettings">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}byte"/>
         *                                       &lt;element name="responseSize" type="{http://www.w3.org/2001/XMLSchema}short"/>
         *                                       &lt;element name="processingTime" type="{http://www.w3.org/2001/XMLSchema}short"/>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}short"/>
         *                             &lt;element name="sendingTime" type="{http://www.w3.org/2001/XMLSchema}short"/>
         *                             &lt;element name="periodic" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="period" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
         *                             &lt;element name="numberRequests" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "name",
            "id",
            "requests"
        })
        public static class Consumer {

            @XmlElement(required = true)
            protected String name;
            protected byte id;
            @XmlElement(required = true)
            protected Scenario.Consumers.Consumer.Requests requests;

            /**
             * Obtient la valeur de la propri�t� name.
             *
             * @return possible object is {@link String }
             *
             */
            public String getName() {
                return name;
            }

            /**
             * D�finit la valeur de la propri�t� name.
             *
             * @param value allowed object is {@link String }
             *
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Obtient la valeur de la propri�t� id.
             *
             */
            public byte getId() {
                return id;
            }

            /**
             * D�finit la valeur de la propri�t� id.
             *
             */
            public void setId(byte value) {
                this.id = value;
            }

            /**
             * Obtient la valeur de la propri�t� requests.
             *
             * @return possible object is
             *     {@link Scenario.Consumers.Consumer.Requests }
             *
             */
            public Scenario.Consumers.Consumer.Requests getRequests() {
                return requests;
            }

            /**
             * D�finit la valeur de la propri�t� requests.
             *
             * @param value allowed object is
             *     {@link Scenario.Consumers.Consumer.Requests }
             *
             */
            public void setRequests(Scenario.Consumers.Consumer.Requests value) {
                this.requests = value;
            }

            /**
             * <p>
             * Classe Java pour anonymous complex type.
             *
             * <p>
             * Le fragment de sch�ma suivant indique le contenu attendu figurant
             * dans cette classe.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="request" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="providerSettings">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}byte"/>
             *                             &lt;element name="responseSize" type="{http://www.w3.org/2001/XMLSchema}short"/>
             *                             &lt;element name="processingTime" type="{http://www.w3.org/2001/XMLSchema}short"/>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}short"/>
             *                   &lt;element name="sendingTime" type="{http://www.w3.org/2001/XMLSchema}short"/>
             *                   &lt;element name="periodic" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="period" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
             *                   &lt;element name="numberRequests" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "request"
            })
            public static class Requests {

                protected List<Scenario.Consumers.Consumer.Requests.Request> request;

                /**
                 * Gets the value of the request property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object. This is
                 * why there is not a <CODE>set</CODE> method for the request
                 * property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getRequest().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Scenario.Consumers.Consumer.Requests.Request }
                 *
                 *
                 */
                public List<Scenario.Consumers.Consumer.Requests.Request> getRequest() {
                    if (request == null) {
                        request = new ArrayList<Scenario.Consumers.Consumer.Requests.Request>();
                    }
                    return this.request;
                }

                /**
                 * <p>
                 * Classe Java pour anonymous complex type.
                 *
                 * <p>
                 * Le fragment de sch�ma suivant indique le contenu attendu
                 * figurant dans cette classe.
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="providerSettings">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}byte"/>
                 *                   &lt;element name="responseSize" type="{http://www.w3.org/2001/XMLSchema}short"/>
                 *                   &lt;element name="processingTime" type="{http://www.w3.org/2001/XMLSchema}short"/>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}short"/>
                 *         &lt;element name="sendingTime" type="{http://www.w3.org/2001/XMLSchema}short"/>
                 *         &lt;element name="periodic" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="period" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
                 *         &lt;element name="numberRequests" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "providerSettings",
                    "size",
                    "sendingTime",
                    "periodic",
                    "period",
                    "numberRequests"
                })
                public static class Request {

                    @XmlElement(required = true)
                    protected Scenario.Consumers.Consumer.Requests.Request.ProviderSettings providerSettings;
                    protected short size;
                    protected short sendingTime;
                    @XmlElement(required = true)
                    protected String periodic;
                    protected Short period;
                    protected Byte numberRequests;

                    /**
                     * Obtient la valeur de la propri�t� providerSettings.
                     *
                     * @return possible object is
                     *     {@link Scenario.Consumers.Consumer.Requests.Request.ProviderSettings }
                     *
                     */
                    public Scenario.Consumers.Consumer.Requests.Request.ProviderSettings getProviderSettings() {
                        return providerSettings;
                    }

                    /**
                     * D�finit la valeur de la propri�t� providerSettings.
                     *
                     * @param value allowed object is
                     *     {@link Scenario.Consumers.Consumer.Requests.Request.ProviderSettings }
                     *
                     */
                    public void setProviderSettings(Scenario.Consumers.Consumer.Requests.Request.ProviderSettings value) {
                        this.providerSettings = value;
                    }

                    /**
                     * Obtient la valeur de la propri�t� size.
                     *
                     */
                    public short getSize() {
                        return size;
                    }

                    /**
                     * D�finit la valeur de la propri�t� size.
                     *
                     */
                    public void setSize(short value) {
                        this.size = value;
                    }

                    /**
                     * Obtient la valeur de la propri�t� sendingTime.
                     *
                     */
                    public short getSendingTime() {
                        return sendingTime;
                    }

                    /**
                     * D�finit la valeur de la propri�t� sendingTime.
                     *
                     */
                    public void setSendingTime(short value) {
                        this.sendingTime = value;
                    }

                    /**
                     * Obtient la valeur de la propri�t� periodic.
                     *
                     * @return possible object is {@link String }
                     *
                     */
                    public String getPeriodic() {
                        return periodic;
                    }

                    /**
                     * D�finit la valeur de la propri�t� periodic.
                     *
                     * @param value allowed object is {@link String }
                     *
                     */
                    public void setPeriodic(String value) {
                        this.periodic = value;
                    }

                    /**
                     * Obtient la valeur de la propri�t� period.
                     *
                     * @return possible object is {@link Short }
                     *
                     */
                    public Short getPeriod() {
                        return period;
                    }

                    /**
                     * D�finit la valeur de la propri�t� period.
                     *
                     * @param value allowed object is {@link Short }
                     *
                     */
                    public void setPeriod(Short value) {
                        this.period = value;
                    }

                    /**
                     * Obtient la valeur de la propri�t� numberRequests.
                     *
                     * @return possible object is {@link Byte }
                     *
                     */
                    public Byte getNumberRequests() {
                        return numberRequests;
                    }

                    /**
                     * D�finit la valeur de la propri�t� numberRequests.
                     *
                     * @param value allowed object is {@link Byte }
                     *
                     */
                    public void setNumberRequests(Byte value) {
                        this.numberRequests = value;
                    }

                    /**
                     * <p>
                     * Classe Java pour anonymous complex type.
                     *
                     * <p>
                     * Le fragment de sch�ma suivant indique le contenu attendu
                     * figurant dans cette classe.
                     *
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}byte"/>
                     *         &lt;element name="responseSize" type="{http://www.w3.org/2001/XMLSchema}short"/>
                     *         &lt;element name="processingTime" type="{http://www.w3.org/2001/XMLSchema}short"/>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     *
                     *
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "providerId",
                        "responseSize",
                        "processingTime"
                    })
                    public static class ProviderSettings {

                        protected byte providerId;
                        protected short responseSize;
                        protected short processingTime;

                        /**
                         * Obtient la valeur de la propri�t� providerId.
                         *
                         */
                        public byte getProviderId() {
                            return providerId;
                        }

                        /**
                         * D�finit la valeur de la propri�t� providerId.
                         *
                         */
                        public void setProviderId(byte value) {
                            this.providerId = value;
                        }

                        /**
                         * Obtient la valeur de la propri�t� responseSize.
                         *
                         */
                        public short getResponseSize() {
                            return responseSize;
                        }

                        /**
                         * D�finit la valeur de la propri�t� responseSize.
                         *
                         */
                        public void setResponseSize(short value) {
                            this.responseSize = value;
                        }

                        /**
                         * Obtient la valeur de la propri�t� processingTime.
                         *
                         */
                        public short getProcessingTime() {
                            return processingTime;
                        }

                        /**
                         * D�finit la valeur de la propri�t� processingTime.
                         *
                         */
                        public void setProcessingTime(short value) {
                            this.processingTime = value;
                        }

                    }

                }

            }

        }

    }

    /**
     * <p>
     * Classe Java pour anonymous complex type.
     *
     * <p>
     * Le fragment de sch�ma suivant indique le contenu attendu figurant dans
     * cette classe.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}duration"/>
     *         &lt;element name="esb">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "name",
        "id",
        "duration",
        "esb",
        "date"
    })
    public static class Information {

        @XmlElement(required = true)
        protected String name;
        protected byte id;
        @XmlElement(required = true)
        protected Duration duration;
        @XmlElement(required = true)
        protected Scenario.Information.Esb esb;
        @XmlElement(required = true)
        protected String date;

        /**
         * Obtient la valeur de la propri�t� name.
         *
         * @return possible object is {@link String }
         *
         */
        public String getName() {
            return name;
        }

        /**
         * D�finit la valeur de la propri�t� name.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Obtient la valeur de la propri�t� id.
         *
         */
        public byte getId() {
            return id;
        }

        /**
         * D�finit la valeur de la propri�t� id.
         *
         */
        public void setId(byte value) {
            this.id = value;
        }

        /**
         * Obtient la valeur de la propri�t� duration.
         *
         * @return possible object is {@link Duration }
         *
         */
        public Duration getDuration() {
            return duration;
        }

        /**
         * D�finit la valeur de la propri�t� duration.
         *
         * @param value allowed object is {@link Duration }
         *
         */
        public void setDuration(Duration value) {
            this.duration = value;
        }

        /**
         * Obtient la valeur de la propri�t� esb.
         *
         * @return possible object is {@link Scenario.Information.Esb }
         *
         */
        public Scenario.Information.Esb getEsb() {
            return esb;
        }

        /**
         * D�finit la valeur de la propri�t� esb.
         *
         * @param value allowed object is {@link Scenario.Information.Esb }
         *
         */
        public void setEsb(Scenario.Information.Esb value) {
            this.esb = value;
        }

        /**
         * Obtient la valeur de la propri�t� date.
         *
         * @return possible object is {@link String }
         *
         */
        public String getDate() {
            return date;
        }

        /**
         * D�finit la valeur de la propri�t� date.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setDate(String value) {
            this.date = value;
        }

        /**
         * <p>
         * Classe Java pour anonymous complex type.
         *
         * <p>
         * Le fragment de sch�ma suivant indique le contenu attendu figurant
         * dans cette classe.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "name",
            "version"
        })
        public static class Esb {

            @XmlElement(required = true)
            protected String name;
            @XmlElement(required = true)
            protected String version;

            /**
             * Obtient la valeur de la propri�t� name.
             *
             * @return possible object is {@link String }
             *
             */
            public String getName() {
                return name;
            }

            /**
             * D�finit la valeur de la propri�t� name.
             *
             * @param value allowed object is {@link String }
             *
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Obtient la valeur de la propri�t� version.
             *
             * @return possible object is {@link String }
             *
             */
            public String getVersion() {
                return version;
            }

            /**
             * D�finit la valeur de la propri�t� version.
             *
             * @param value allowed object is {@link String }
             *
             */
            public void setVersion(String value) {
                this.version = value;
            }

        }

    }

}
