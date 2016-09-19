package com.lukong.model;

import javax.persistence.*;

/**
 * Created by lukong on 16/9/17.
 */
@Entity
@Table(name = "sensor", schema = "test", catalog = "")
public class SnEntity {
    private String sensor;
    private String protocol;
    private String communication;
    private String ip;
    private String port;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SnEntity snEntity = (SnEntity) o;

        if (sensor != null ? !sensor.equals(snEntity.sensor) : snEntity.sensor != null) return false;
        if (protocol != null ? !protocol.equals(snEntity.protocol) : snEntity.protocol != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sensor != null ? sensor.hashCode() : 0;
        result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
        return result;
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
}
