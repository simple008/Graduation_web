package com.lukong.services.model;

/**
 * Created by lukong on 2016/11/1.
 */
public class Sensor {
    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTopic_up() {
        return topic_up;
    }

    public void setTopic_up(String topic_up) {
        this.topic_up = topic_up;
    }

    public String getTopic_down() {
        return topic_down;
    }

    public void setTopic_down(String topic_down) {
        this.topic_down = topic_down;
    }

    public String getJob_up() {
        return job_up;
    }

    public void setJob_up(String job_up) {
        this.job_up = job_up;
    }

    public String getJob_down() {
        return job_down;
    }

    public void setJob_down(String job_down) {
        this.job_down = job_down;
    }

    private String sensor;
    private String protocol;
    private String communication;
    private String ip;
    private String port;
    private String topic_up;
    private String topic_down;
    private String job_up;
    private String job_down;

}
