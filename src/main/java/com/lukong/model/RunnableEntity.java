package com.lukong.model;

import javax.persistence.*;

/**
 * Created by lukong on 16/9/26.
 */
@Entity
@Table(name = "runnable", schema = "test", catalog = "")
public class RunnableEntity {
    private int id;
    private String sensor;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sensor", nullable = false, length = 50)
    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RunnableEntity that = (RunnableEntity) o;

        if (id != that.id) return false;
        if (sensor != null ? !sensor.equals(that.sensor) : that.sensor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (sensor != null ? sensor.hashCode() : 0);
        return result;
    }
}
