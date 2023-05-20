package com.springboot.spring_task.repository;

import com.springboot.spring_task.entity.Tasks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {
    @Query("Select a from Tasks as a where " +
            "(a.planDate between :dateStart and :dateFinish)")
    public Page<Tasks> findTasksByPlanDate(
            @Param("dateStart") Date dateStart,
            @Param("dateFinish") Date dateFinish,
            Pageable pageable);
}
