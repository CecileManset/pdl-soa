package com.insa.swim.orchestrator.configuration;

import com.insa.swim.orchestrator.xml.Scenario;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class WebServicesConfiguration {
    private final int BUFF_SIZE = 300;
    
    public WebServicesConfiguration() {
        
    }
    
    public void configure(Scenario scenario) {
        ArrayList<String> list = scenarioToString(scenario);
        for (String s : list) {
            System.out.println(s);
        }
    }
    
    private ArrayList<String> scenarioToString(Scenario scenario) {
        ArrayList<String> result = new ArrayList<String>();
        
        StringBuilder buffinf = new StringBuilder(BUFF_SIZE);
        Scenario.Information inf = scenario.getInformation();
        
        buffinf.append("INFORMATION|");
        buffinf.append(inf.getId()).append("|");
        buffinf.append(inf.getName()).append("|");
        buffinf.append(inf.getDate()).append("|");
        buffinf.append(inf.getDuration()).append("|");
        
        for (Scenario.Consumers.Consumer c : scenario.getConsumers().getConsumer()) {
            StringBuilder buff = new StringBuilder(BUFF_SIZE);
            buff.append("CONSUMER|");
            buff.append(c.getId()).append("|");
            buff.append(c.getName()).append("|");
            for (Scenario.Consumers.Consumer.Requests.Request r : c.getRequests().getRequest()) {
                buff.append("REQUEST|");
                buff.append(r.getProviderId()).append("|");
                buff.append(r.getSendingTime()).append("|");
                buff.append(r.getSize()).append("|");
                buff.append(r.getPeriodic()).append("|");
                buff.append(r.getPeriod()).append("|");
                buff.append(r.getNumberRequests()).append("|");
            }
            
            result.add(buffinf.toString() + buff.toString());
        }
        
        return result;
    }
}