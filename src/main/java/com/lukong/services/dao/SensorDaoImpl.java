package com.lukong.services.dao;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lukong on 2016/11/1.
 */
public class SensorDaoImpl extends BaseJdbcDao implements SensorDao {

    private Logger LOG= LoggerFactory.getLogger(SensorDaoImpl.class);

    @Override
    public String getSensor(String jid) {
        String SQL="SELECT sensor,topic_up FROM test.sensor WHERE job_up= "+"'"+jid+"'";
        Connection connection=null;
        PreparedStatement pst=null;
        ResultSet resultSet=null;
        String sensor=null;
        String topic=null;
        JSONObject jsonObject=new JSONObject();

        try {
            connection=getConnection();
            pst=connection.prepareStatement(SQL);
            resultSet=pst.executeQuery();

            if(resultSet.next()){
                sensor= resultSet.getString("sensor");
                topic=resultSet.getString("topic_up");
                jsonObject.put("sensor",sensor);
                jsonObject.put("topic_up",topic);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(),e);
        }

        return jsonObject.toJSONString();
    }

    @Override
    public void update(String jid, String sensor) {
        String SQL="update test.sensor set job_up= "+"'"+jid+"'" +" where sensor= "+"'"+sensor+"'";
        Connection connection=null;
        PreparedStatement pst=null;


        try {
            connection=getConnection();
            pst=connection.prepareStatement(SQL);
            pst.executeUpdate();

        } catch (SQLException e) {
            LOG.error(e.getMessage(),e);
        }
    }

    @Test
    public void test(){
        SensorDaoImpl sensorDao=new SensorDaoImpl();
        sensorDao.update("test","ais");
    }
}
