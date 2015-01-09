/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.orchestrator;

import com.insa.swim.orchestrator.xml.Result;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pdlsoa
 */
public class PostToElasticSearchThread extends Thread {

    private final static Logger LOGGER = LogManager.getLogger(PostToElasticSearchThread.class);

    private final static String ELASTICSEARCH_URL = "http://localhost:9200/";
    private final static String RESULT_INDEX = "open_esb/";
    private final static String RESULT_TYPE = "results/";

    private HttpClient httpClient;
    private Result result;

    public PostToElasticSearchThread(HttpClient httpClient, Result result) {
        this.httpClient = httpClient;
        this.result = result;
    }

    public void run() {
        try {
            HttpPost postRequest = new HttpPost(ELASTICSEARCH_URL + RESULT_INDEX + RESULT_TYPE);
            StringEntity input = new StringEntity(result.toString());
            input.setContentType("application/json");
            postRequest.setEntity(input);
            LOGGER.trace("Sending result: [" + result.toString());
            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != 201) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            LOGGER.debug("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                LOGGER.debug(output);
            }

        } catch (IOException ex) {
            LOGGER.error("An error occured while sending result to ElasticSearch");
            LOGGER.debug(ex.getStackTrace());
        }
    }
}
