package com.lukong.services.dao;

/**
 * Created by lukong on 2016/11/1.
 */
public interface SensorDao extends JdbcDao {
    String getSensor(String jid);
}
