package com.lukong.repository;

import com.lukong.model.SensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lukong on 16/9/17.
 */
public interface SNRepository extends JpaRepository<SensorEntity,Integer> {
    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    // 定义查询
    // @Param注解用于提取参数
    @Query("update SensorEntity sn set sn.sensor=:qsensor,sn.protocol=:qprotocol,sn.communication=:qcomm,sn.ip=:qip,sn.port=:qport,sn.topic=:qtopic" +
            " where sn.sensor=:qsensor")
    void updateSn(@Param("qsensor") String sensor, @Param("qprotocol") String protocol
    ,@Param("qcomm")String comm,@Param("qip") String ip,@Param("qport")String port,@Param("qtopic") String topic);
}
