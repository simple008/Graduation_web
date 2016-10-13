package com.lukong.model;

import javax.persistence.*;

/**
 * Created by lukong on 2016/10/10.
 */
@Entity
@Table(name = "sensor", schema = "test", catalog = "")
public class SensorEntity {
    private String sensor;
    private String protocol;
    private String communication;
    private String ip;
    private String port;
    private String topic;

    @Id
    @Column(name = "sensor", nullable = false, length = 50)
    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    @Basic
    @Column(name = "protocol", nullable = false, length = 50)
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Basic
    @Column(name = "communication", nullable = false, length = 50)
    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    @Basic
    @Column(name = "ip", nullable = false, length = 50)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "port", nullable = false, length = 50)
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Basic
    @Column(name = "topic", nullable = false, length = 50)
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensorEntity that = (SensorEntity) o;

        if (sensor != null ? !sensor.equals(that.sensor) : that.sensor != null) return false;
        if (protocol != null ? !protocol.equals(that.protocol) : that.protocol != null) return false;
        if (communication != null ? !communication.equals(that.communication) : that.communication != null)
            return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;
        if (topic != null ? !topic.equals(that.topic) : that.topic != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sensor != null ? sensor.hashCode() : 0;
        result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
        result = 31 * result + (communication != null ? communication.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        return result;
    }
}
