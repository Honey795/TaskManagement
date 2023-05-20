package com.springboot.spring_task.repository;

import com.springboot.spring_task.entity.Kinds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KindRepository extends JpaRepository<Kinds,Long> {

}
