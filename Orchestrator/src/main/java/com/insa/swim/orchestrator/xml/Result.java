/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.orchestrator.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Christelle
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Result")
public class Result {
    @XmlAttribute(name ="consumer", required = true)
     private String consumer;

    @XmlAttribute(name ="provider", required = true)
    private String provider;
    
    @XmlAttribute(name ="requestSize", required = true)
    private int requestSize;
    
    @XmlAttribute(name ="responseSize", required = true)
    private int responseSize;
    
    @XmlAttribute(name ="processingTime", required = true)
    private int processingTime;

    @XmlAttribute(name ="sendingDateConsumer", required = true)
    private int sendingDateConsumer;
    
    @XmlAttribute(name ="receptionDateProvider", required = true)
    private int receptionDateProvider;
    
    @XmlAttribute(name ="sendingDateProvider", required = true)
    private int sendingDateProvider;
    
    @XmlAttribute(name ="receptionDateConsumer", required = true)
    private int receptionDateConsumer;

    @XmlAttribute(name ="C2PTime", required = true)
    private int C2PTime;

    @XmlAttribute(name ="P2CTime", required = true)
    private int P2CTime;

    public Result(String resultString) {
        resultString = "1|1|50|100|1000|1421678470|1421678570|1421678670|1421678770";
//        int min = 1;
//        int max = 5;
//        int consumerId = min + (int) (Math.random() * (max - min) + 1);
//        this.consumer = "consumer" + consumerId;
//        int providerId = min + (int) (Math.random() * (max - min) + 1);
//        this.provider = "provider" + providerId;
//        this.C2PTime = providerId * consumerId * 12;
//        this.P2CTime = consumerId * 27;
        
        String[] resultParsed = resultString.split("\\|");
        this.consumer = resultParsed[0];
        this.provider = resultParsed[1];
        this.requestSize = Integer.parseInt(resultParsed[2]);
        this.responseSize = Integer.parseInt(resultParsed[3]);
        this.processingTime = Integer.parseInt(resultParsed[4]);
        this.sendingDateConsumer = Integer.parseInt(resultParsed[5]);
        this.receptionDateProvider = Integer.parseInt(resultParsed[6]);
        this.sendingDateProvider = Integer.parseInt(resultParsed[7]);
        this.receptionDateConsumer = Integer.parseInt(resultParsed[8]);
        this.C2PTime = this.receptionDateProvider - this.sendingDateConsumer;
        this.P2CTime = this.receptionDateConsumer - this.sendingDateProvider;
    }

    public int getC2PTime() {
        return C2PTime;
    }

    public int getP2CTime() {
        return P2CTime;
    }

    public String getConsumer() {
        return consumer;
    }

    public String getProvider() {
        return provider;
    }

    public int getRequestSize() {
        return requestSize;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public int getSendingDateConsumer() {
        return sendingDateConsumer;
    }

    public int getReceptionDateProvider() {
        return receptionDateProvider;
    }

    public int getSendingDateProvider() {
        return sendingDateProvider;
    }

    public int getReceptionDateConsumer() {
        return receptionDateConsumer;
    }

    public void setC2PTime(int C2PTime) {
        this.C2PTime = C2PTime;
    }

    public void setP2CTime(int P2CTime) {
        this.P2CTime = P2CTime;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public org.json.JSONObject toJSON() {
        org.json.JSONObject resultJSON = new org.json.JSONObject();
        resultJSON.put("consumer", consumer);
        resultJSON.put("provider", provider);
        resultJSON.put("C2PTime", C2PTime);
        resultJSON.put("P2CTime", P2CTime);
        resultJSON.put("sendingDateConsumer", sendingDateConsumer);
        resultJSON.put("receptionDateProvider", receptionDateProvider);
        resultJSON.put("sendingDateProvider", sendingDateProvider);
        resultJSON.put("receptionDateConsumer", receptionDateConsumer);        
        return resultJSON;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }

}
