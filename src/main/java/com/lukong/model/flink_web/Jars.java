package com.lukong.model.flink_web;

import java.util.List;
import java.util.Map;

/**
 * Created by lukong on 16/10/8.
 */
public class Jars {
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Map<String, Object>> getFiles() {
        return files;
    }

    public void setFiles(List<Map<String, Object>> files) {
        this.files = files;
    }

    private String address;
    private List<Map<String,Object>>files;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Jars jars = (Jars) o;

        if (!address.equals(jars.address)) return false;
        return files.equals(jars.files);

    }

    @Override
    public int hashCode() {
        int result = address.hashCode();
        result = 31 * result + files.hashCode();
        return result;
    }
}
