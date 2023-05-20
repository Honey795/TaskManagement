package com.springboot.spring_task.repository;

import com.springboot.spring_task.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tags,Long> {
    @Query("Select a from Tags as a where a.code = :code")
    public Tags findByName(@Param("code") String code);
    public List<Tags> findAllByTasksIsNotNull();
}
