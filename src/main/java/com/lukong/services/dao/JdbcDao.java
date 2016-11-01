package com.lukong.services.dao;

import java.sql.Connection;

/**
 * Created by lukong on 16/9/17.
 */
public interface JdbcDao {
    Connection getConnection();
}
