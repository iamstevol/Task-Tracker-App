package com.iamstevol.Activity_Tracker.service;

import com.iamstevol.Activity_Tracker.dto.TaskRequestDto;
import com.iamstevol.Activity_Tracker.enums.Status;
import com.iamstevol.Activity_Tracker.exception.CustomException;
import com.iamstevol.Activity_Tracker.model.Task;
import com.iamstevol.Activity_Tracker.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    Task createTask(User user, TaskRequestDto taskRequestDto);

    void deleteTask(Long taskId);

    void updateTask(String title, String description, Long taskId) throws CustomException;

    List<Task> findAllTask(Long userId);

    void moveTaskForward(Long taskId);

    void moveTaskBackward(Long taskId) throws CustomException;

    void moveTaskToDone(Long taskId);

    List<Task> getTasksByStatus(Status status) throws CustomException;






}
