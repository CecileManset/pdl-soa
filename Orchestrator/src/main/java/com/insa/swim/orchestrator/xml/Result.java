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
@XmlRootElement(name = "result")
public class Result {

    @XmlAttribute(name = "consumer", required = true)
    private String consumer;

    @XmlAttribute(name = "provider", required = true)
    private String provider;

    @XmlAttribute(name = "requestSize", required = true)
    private int requestSize;

    @XmlAttribute(name = "responseSize", required = true)
    private int responseSize;

    @XmlAttribute(name = "processingTime", required = true)
    private int processingTime;

    @XmlAttribute(name = "sendingDateConsumer", required = true)
    private long sendingDateConsumer;

    @XmlAttribute(name = "receptionDateProvider", required = true)
    private long receptionDateProvider;

    @XmlAttribute(name = "sendingDateProvider", required = true)
    private long sendingDateProvider;

    @XmlAttribute(name = "receptionDateConsumer", required = true)
    private long receptionDateConsumer;

    @XmlAttribute(name = "C2PTime", required = true)
    private long C2PTime;

    @XmlAttribute(name = "P2CTime", required = true)
    private long P2CTime;
    
    @XmlAttribute(name = "error", required = true)
    private String error;

    public Result() {
    }

    public Result(String resultString) {

        if (resultString.contains("REQUEST")) {
            this.error = "REQUEST";
        }
        else if (resultString.contains("LOST|") || resultString.contains("FORMAT|") || resultString.contains("PROVIDER|")) {
            String[] resultParsed = resultString.split("\\|");
            this.error = resultParsed[0];
            this.consumer = resultParsed[1];
            this.provider = resultParsed[2];
            this.requestSize = Integer.parseInt(resultParsed[3]);
            this.responseSize = Integer.parseInt(resultParsed[4]);
            this.processingTime = Integer.parseInt(resultParsed[5]);
            this.sendingDateConsumer = Long.parseLong(resultParsed[6]);
        }
        else {
            String[] resultParsed = resultString.split("\\|");
            this.consumer = resultParsed[0];
            this.provider = resultParsed[1];
            this.requestSize = Integer.parseInt(resultParsed[2]);
            this.responseSize = Integer.parseInt(resultParsed[3]);
            this.processingTime = Integer.parseInt(resultParsed[4]);
            this.sendingDateConsumer = Long.parseLong(resultParsed[5]);
            this.receptionDateProvider = Long.parseLong(resultParsed[6]);
            this.sendingDateProvider = Long.parseLong(resultParsed[7]);
            this.receptionDateConsumer = Long.parseLong(resultParsed[8]);
            this.C2PTime = this.receptionDateProvider - this.sendingDateConsumer;
            this.P2CTime = this.receptionDateConsumer - this.sendingDateProvider;
            this.error = "false";
        }
    }

    public long getC2PTime() {
        return C2PTime;
    }

    public long getP2CTime() {
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

    public long getSendingDateConsumer() {
        return sendingDateConsumer;
    }

    public long getReceptionDateProvider() {
        return receptionDateProvider;
    }

    public long getSendingDateProvider() {
        return sendingDateProvider;
    }

    public long getReceptionDateConsumer() {
        return receptionDateConsumer;
    }

    public String getError() {
        return error;
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
        resultJSON.put("error", error);
        return resultJSON;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }

}
