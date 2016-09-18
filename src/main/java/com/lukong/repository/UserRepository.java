package com.lukong.repository;

import com.lukong.model.UserEntity;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by lukong on 16/9/18.
 */
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    @Transient
    @Query("select us.name from UserEntity us where us.name=:qname and us.password=:qpassword")
    String select(@Param("qname")String name,@Param("qpassword") String password);
}
