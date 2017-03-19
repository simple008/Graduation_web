package com.lukong.services.data2db;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionFactory {
    private static DruidDataSource dataSource=null;
    public static Logger LOG= LoggerFactory.getLogger(ConnectionFactory.class);
    static {
        try{
            String driver="com.mysql.jdbc.Driver";
            String url="jdbc:mysql://10.109.253.172:3306/test";
            String user="lukong";
            String password="123456";

            dataSource=new DruidDataSource();
            dataSource.setDriverClassName(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(user);
            dataSource.setPassword(password);
            dataSource.setMinIdle(1);
            dataSource.setInitialSize(20);
            dataSource.setMaxActive(150);
            dataSource.setPoolPreparedStatements(false);
        }catch (Exception e){
            LOG.error(e.getMessage(),e);
        }
    }

    public static synchronized Connection getConnection(){
        Connection conn=null;
        try {
            conn=dataSource.getConnection();
        } catch (SQLException e) {
            LOG.error(e.getMessage(),e);
        }
        return conn;
    }
}
