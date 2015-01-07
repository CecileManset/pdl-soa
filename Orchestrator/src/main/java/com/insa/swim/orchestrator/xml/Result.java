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

    @XmlAttribute(name ="timestamp", required = true)
     private int timestamp;

    @XmlAttribute(name ="C2PTime", required = true)
     private int C2PTime;

    @XmlAttribute(name ="P2CTime", required = true)
    private int P2CTime;

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

    public int getTimestamp() {
        return timestamp;
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

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public org.json.JSONObject toJSON() {
        org.json.JSONObject resultJSON = new org.json.JSONObject();
        resultJSON.put("consumer", consumer);
        resultJSON.put("provider", provider);
        resultJSON.put("C2PTime", C2PTime);
        resultJSON.put("P2CTime", P2CTime);
        resultJSON.put("timestamp", timestamp);
        return resultJSON;
    }

    public String toString() {
        return toJSON().toString();
    }

}
