/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.orchestrator.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Alex
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "results")
@XmlType(name = "", propOrder = {
    "kpi",
    "result"
})
public class Results {

    protected KPI kpi;
    protected List<Result> result;
    
    public Results() {
        result = new ArrayList<Result>();
    }

    public List<Result> getResults() {
        return this.result;
    }
    
    public List<Result> getResultsNoError() {
        List<Result> results = new ArrayList<Result>();
        if (this.result.size() > 0) {
            for (Result r : this.result) {
                if (r.getError().equalsIgnoreCase("false")) {
                    results.add(r);
                }
            }
        }
        return results;
    }

    public void setResults(List<Result> results) {
        this.result = results;
    }
    
    public KPI getKPI() {
        if (kpi == null) {
            kpi = new KPI();
        }
        return kpi;
    }

    @Override
    public String toString() {
        if (result == null) {
            return "";
        }
        String str = "";
        for (Result r : result) {
            str += "\n" + (r.toString());
        }
        return str;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "meanTimeConsumerProvider",
        "meanTimeProviderConsumer",
        "numberLostMessages",
        "requestsNumber"
    })
    public static class KPI {

        protected double meanTimeConsumerProvider;
        protected double meanTimeProviderConsumer;
        protected int numberLostMessages;
        protected int requestsNumber;

        public double getMeanTimeConsumerProvider() {
            return meanTimeConsumerProvider;
        }

        public void setMeanTimeConsumerProvider(double meanTimeConsumerProvider) {
            this.meanTimeConsumerProvider = meanTimeConsumerProvider;
        }

        public double getMeanTimeProviderConsumer() {
            return meanTimeProviderConsumer;
        }

        public void setMeanTimeProviderConsumer(double meanTimeProviderConsumer) {
            this.meanTimeProviderConsumer = meanTimeProviderConsumer;
        }

        public double getNumberLostMessages() {
            return numberLostMessages;
        }

        public void setNumberLostMessages(int numberLostMessages) {
            this.numberLostMessages = numberLostMessages;
        }

        public double getRequestsNumber() {
            return requestsNumber;
        }

        public void setRequestsNumber(int requestsNumber) {
            this.requestsNumber = requestsNumber;
        }

    }

}
