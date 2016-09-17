package com.lukong.model;

import javax.persistence.*;

/**
 * Created by lukong on 16/9/17.
 */
@Entity
@Table(name = "ads_id", schema = "test", catalog = "")
public class AdsIdEntity {
    private int id;
    private String targetId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "target_id", nullable = false, length = 155)
    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdsIdEntity that = (AdsIdEntity) o;

        if (id != that.id) return false;
        if (targetId != null ? !targetId.equals(that.targetId) : that.targetId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (targetId != null ? targetId.hashCode() : 0);
        return result;
    }
}
