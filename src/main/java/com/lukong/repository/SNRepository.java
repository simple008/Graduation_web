package com.lukong.repository;

import com.lukong.model.SnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lukong on 16/9/17.
 */
public interface SNRepository extends JpaRepository<SnEntity,Integer> {
}
