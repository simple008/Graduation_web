package com.lukong.repository;

import com.lukong.model.RunnableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by lukong on 16/9/26.
 */
public interface RunRepository extends JpaRepository<RunnableEntity,Integer> {

    @Query("select re.sensor from RunnableEntity re ")
    String select();
}
