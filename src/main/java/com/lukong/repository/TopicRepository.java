package com.lukong.repository;

import com.lukong.model.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by lukong on 2016/10/26.
 */
public interface TopicRepository extends JpaRepository<TopicEntity,Integer> {

    @Query("SELECT topic FROM TopicEntity")
    String select();
}
