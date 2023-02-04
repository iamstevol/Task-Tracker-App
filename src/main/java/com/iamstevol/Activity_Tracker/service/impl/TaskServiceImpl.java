package com.iamstevol.Activity_Tracker.service.impl;

import com.iamstevol.Activity_Tracker.dto.TaskRequestDto;
import com.iamstevol.Activity_Tracker.enums.Status;
import com.iamstevol.Activity_Tracker.exception.CustomException;
import com.iamstevol.Activity_Tracker.model.Task;
import com.iamstevol.Activity_Tracker.model.User;
import com.iamstevol.Activity_Tracker.repository.TaskRepository;
import com.iamstevol.Activity_Tracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(User user, TaskRequestDto taskRequestDto) {
        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setTimeCreated(LocalDate.now());
        task.setStatus(Status.PENDING);
        task.setUser(user);
        taskRepository.save(task);
        return task;
    }

    @Transactional
    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public void updateTask(String title, String description, Long taskId) throws CustomException {
        taskRepository.updateTask(title, description, taskId);
        taskRepository.updateTime(LocalDate.now(), taskId);
    }



    @Override
    public List<Task> findAllTask(Long userId) {
        return taskRepository.findAllTaskByUserId(userId);
    }

//    @Override
//    public void moveTaskForward(Long taskId) {
//        Optional<Task> found = taskRepository.findById(taskId);
//        TaskRequestDto taskRequestDto = new TaskRequestDto();
//        Task task = null;
//        if (found.isPresent()) {
//            task = found.get();
//        }
//        assert task != null;
//        if (task.getStatus().equals(Status.PENDING)) {
//            task.setStatus(Status.IN_PROGRESS);
//            task.setTimeUpdated(LocalDate.now());
//        } else if (task.getStatus().equals(Status.IN_PROGRESS)) {
//        task.setStatus(Status.COMPLETED);
//        task.setTimeUpdated(taskRequestDto.getFormattedUpdatedTime());
//        task.setTimeCompleted(taskRequestDto.getFormattedCompletedTime());
//        }
//        taskRepository.moveToProgress(taskId);
//        taskRepository.updateTime(LocalDate.now(), taskId);
//    }

    @Override
    public void moveTaskForward(Long taskId) {
        Optional<Task> found = taskRepository.findById(taskId);
        if (found.isPresent()) {
            taskRepository.moveToProgress("IN_PROGRESS", taskId);
            taskRepository.updateTime(LocalDate.now(), taskId);
        }
    }

    @Override
    public void moveTaskBackward(Long taskId) {
        Optional<Task> found = taskRepository.findById(taskId);
        if (found.isPresent()) {
            taskRepository.moveBackward("PENDING", taskId);
            taskRepository.updateTime(LocalDate.now(), taskId);
        }
    }


    @Override
    public void moveTaskToDone(Long taskId) {
        Optional<Task> found = taskRepository.findById(taskId);
        if (found.isPresent()) {
            taskRepository.moveToComplete("COMPLETED", taskId);
            taskRepository.completedTime(LocalDate.now(),taskId);
        }
    }


//    @Override
//    public void moveTaskBackward(Long taskId) throws CustomException {
//        Optional<Task> found = taskRepository.findById(taskId);
//        TaskRequestDto taskRequestDto = new TaskRequestDto();
//        Task task = null;
//        if(found.isPresent()) {
//            task = found.get();
//        }
//        assert task != null;
//        if(task.getStatus().equals(Status.IN_PROGRESS)) {
//            task.setStatus(Status.PENDING);
//            task.setTimeUpdated(taskRequestDto.getFormattedUpdatedTime());
//        } else if(task.getStatus().equals(Status.PENDING)) {
//            throw new CustomException("Task is currently in Pending, try moving task forward");
//        }
//        taskRepository.moveBackward(taskId);
//    }

//    @Override
//    public void moveTaskToDone(Long taskId) {
//        Optional<Task> found = taskRepository.findById(taskId);
//        TaskRequestDto taskRequestDto = new TaskRequestDto();
//        Task task = null;
//        if(found.isPresent()) {
//            task = found.get();
//        }
//        assert task != null;
//        if(task.getStatus().equals(Status.IN_PROGRESS) || task.getStatus().equals(Status.PENDING)) {
//            task.setStatus(Status.COMPLETED);
////            task.setTimeUpdated(taskRequestDto.getFormattedUpdatedTime());
////            task.setTimeCompleted(taskRequestDto.getFormattedCompletedTime());
//        }
//        taskRepository.moveToComplete(taskId);
//    }

    @Override
    public List<Task> getTasksByStatus(Status status) throws CustomException {
        User user = new User();
        List<Task> tasks = taskRepository.findAllTaskByUserId(user.getUserId());
        List<Task> pendingList = new ArrayList<>();
        List<Task> completedList = new ArrayList<>();
        List<Task> progressList = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus().equals(Status.PENDING)) {
                pendingList.add(task);
            } else if (task.getStatus().equals(Status.COMPLETED)) {
                completedList.add(task);
            } else if (task.getStatus().equals(Status.IN_PROGRESS)) {
                progressList.add(task);
            }
        }
        if (status == Status.PENDING) {
            return pendingList;
        } else if (status == Status.COMPLETED) {
            return completedList;
        } else if (status == Status.IN_PROGRESS) {
            return progressList;
        } else {
            throw new CustomException("The requested task is not available");
        }
    }

    public List<Task> findByPending(Long userId) {
        return taskRepository.findAllTaskByPending("PENDING", userId);
    }

    public List<Task> findByInProgress(Long userId) {
        return taskRepository.findAllTaskByInProgress("IN_PROGRESS", userId);
    }

    public List<Task> findByCompleted(Long userId) {
        return taskRepository.findAllTaskByCompleted("COMPLETED", userId);
    }


}
