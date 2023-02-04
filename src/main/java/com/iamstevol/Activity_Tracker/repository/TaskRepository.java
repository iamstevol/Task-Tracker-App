package com.iamstevol.Activity_Tracker.repository;

import com.iamstevol.Activity_Tracker.enums.Status;
import com.iamstevol.Activity_Tracker.model.Task;
import com.iamstevol.Activity_Tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Transactional
//    @Query(value = "update task t set t.status = 1 where t.task_id = ?1", nativeQuery = true)
//    void moveToProgress (Long taskId);
    @Query(value = "update task t set t.status = ?1 where t.task_id = ?2", nativeQuery = true)
    void moveToProgress (String status, Long taskId);

    @Transactional
    @Modifying
    @Query(value ="update task t set t.status = ?1 where t.task_id = ?2", nativeQuery = true)
    void moveToComplete(String status, Long taskId);

    @Transactional
    @Modifying
    @Query(value ="update task t set t.status = ?1  where t.task_id = ?2", nativeQuery = true)
    void moveBackward(String status, Long taskId);

    @Modifying
    @Transactional
    @Query(value = "update task t set t.time_updated = ?1 where t.task_id = ?2", nativeQuery = true)
    void updateTime(LocalDate time, Long taskId);

    @Modifying
    @Transactional
    @Query(value = "update task t set t.time_completed = ?1 where t.task_id = ?2", nativeQuery = true)
    void completedTime(LocalDate time, Long taskId);

    @Query(value = "select * from task t where t.user_id = ?1 ", nativeQuery = true)
    List<Task> findAllTaskByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "update task t set t.title = ?1, t.description = ?2 where t.task_id = ?3",
            nativeQuery = true)
    void updateTask(String title, String description, Long taskId);


    @Modifying
    @Transactional
    @Query(value = "select * from task t where t.status = ?1 and t.user_id = ?2", nativeQuery = true)
    List<Task> findAllTaskByPending(String status, Long userId);

    @Modifying
    @Transactional
    @Query(value = "select * from task t where t.status = ?1 and t.user_id = ?2", nativeQuery = true)
    List<Task> findAllTaskByInProgress(String status, Long userId);

    @Modifying
    @Transactional
    @Query(value = "select * from task t where t.status = ?1 and t.user_id = ?2", nativeQuery = true)
    List<Task> findAllTaskByCompleted(String status, Long userId);
}