package com.insa.swim.orchestrator.configuration;

import com.insa.swim.orchestrator.Controller;
import static com.insa.swim.orchestrator.Controller.LOGGER;
import com.insa.swim.orchestrator.amqp.AMQPHandler;
import com.insa.swim.orchestrator.xml.Scenario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Alex
 */
public class WebServicesConfiguration {

    private static final Logger LOGGER = LogManager.getLogger(WebServicesConfiguration.class);
    private static final int BUFF_SIZE = 300;
    private AMQPHandler amqp;

    public WebServicesConfiguration(AMQPHandler amqp) {
        this.amqp = amqp;
    }

    public void configure(Scenario scenario) {
        List<String> list = scenarioToString(scenario);
        LOGGER.info("Printing a scenario...");
        for (String s : list) {
            LOGGER.info(s);
        }
        try {
            for (int i = 0; i < list.size(); i++) {
                amqp.sendConf("C" + (i+1), list.get(i));
                LOGGER.debug("Configuration message sent to C" + (i+1) );
            }
        } catch (IOException ex) {
            LOGGER.error("Error while sending the configuration: " + ex.getMessage());
        }
    }

    private List<String> scenarioToString(Scenario scenario) {
        List<String> result = new ArrayList<String>();

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
                buff.append(r.getProviderSettings().getProviderId()).append("|");
                buff.append(r.getSendingTime()).append("|");
                buff.append(r.getSize()).append("|");
                buff.append(r.getPeriodic()).append("|");
                buff.append(r.getPeriod()).append("|");
                buff.append(r.getNumberRequests()).append("|");
            }
            result.add(c.getId() - 1, buffinf.toString() + buff.toString());
        }

        return result;
    }
}
