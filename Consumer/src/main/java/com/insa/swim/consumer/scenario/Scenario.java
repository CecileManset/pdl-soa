package com.insa.swim.consumer.scenario;

import java.util.ArrayList;

/**
 * singleton
 * @author Alex Binguy
 */
public class Scenario {

    // TODO complete the attribute list
    private String conf = null;
    private int id = -1;
    private String name = "";
    private String date = "";
    private String duration = "";
    private int consumerId = 0;
    private String consumerName = "";
    private ArrayList<Request> requestList = null;

    public Scenario() {
        conf = "";
    }

    public Scenario(String conf) {
        init(conf);
    }

    public void init(String conf) {
        try {

            this.conf = conf;
            requestList = new ArrayList<Request>();

            String[] requests = conf.split("\\|REQUEST\\|");
            String[] infos = requests[0].split("\\|");

            id = Integer.parseInt(infos[1]);

            name = infos[2];

            date = infos[3];
            duration = infos[4];
            //infos[5] = CONSUMER
            consumerId = Integer.parseInt(infos[6]);
            consumerName = infos[7];

            for (int i = 1; i < requests.length; i++) {
                Request req = new Request();
                String[] champs = requests[i].split("\\|");

                req.providerId = Integer.parseInt(champs[0]);
                req.sendingTime = Integer.parseInt(champs[1]);
                req.size = Integer.parseInt(champs[2]);
                req.periodic = Boolean.parseBoolean(champs[3]);
                if (req.periodic) {
                    req.period = Integer.parseInt(champs[4]);
                    req.numberRequest = Integer.parseInt(champs[5]);
                }
                req.processingTime = Integer.parseInt(champs[6]);
                req.responseSize = Integer.parseInt(champs[7]);


                requestList.add(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    public int getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(int consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(ArrayList<Request> requestList) {
        this.requestList = requestList;
    }

    public static class Request {

        private int providerId;
        private int sendingTime;
        private int size;
        private boolean periodic;
        private int period;
        private int numberRequest;
        private int processingTime;
        private int responseSize;

        public Request() {
        }

        public int getProcessingTime() {
            return processingTime;
        }

        public void setProcessingTime(int processingTime) {
            this.processingTime = processingTime;
        }

        public int getResponseSize() {
            return responseSize;
        }

        public void setResponseSize(int responseSize) {
            this.responseSize = responseSize;
        }

        public int getNumberRequest() {
            return numberRequest;
        }

        public void setNumberRequest(int numberRequest) {
            this.numberRequest = numberRequest;
        }

        public int getPeriod() {
            return period;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public boolean isPeriodic() {
            return periodic;
        }

        public void setPeriodic(boolean periodic) {
            this.periodic = periodic;
        }

        public int getProviderId() {
            return providerId;
        }

        public void setProviderId(int providerId) {
            this.providerId = providerId;
        }

        public int getSendingTime() {
            return sendingTime;
        }

        public void setSendingTime(int sendingTime) {
            this.sendingTime = sendingTime;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
