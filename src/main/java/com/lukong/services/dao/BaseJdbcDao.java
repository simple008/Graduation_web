package com.lukong.services.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by lukong on 16/9/17.
 */
public class BaseJdbcDao implements JdbcDao {
    public static Logger LOG= LoggerFactory.getLogger(BaseJdbcDao.class);
    private String driver="com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://10.109.253.172:3306/test";
    private String usr="lukong";
    private String pwd="123456";

    @Override
    public Connection getConnection() {
        Connection connection=null;
        try {
            Class.forName(driver);
            connection=DriverManager.getConnection(url,usr,pwd);
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage(),e);
        } catch (SQLException e) {
            LOG.error(e.getMessage(),e);
        }
        return connection;
    }
    public void close(ResultSet rs, Statement stmt, Connection conn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(),e);
            }
        }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(),e);
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(),e);
            }
        }
    }
}
