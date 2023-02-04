package com.iamstevol.Activity_Tracker.mapper;

import com.iamstevol.Activity_Tracker.dto.TaskRequestDto;
import com.iamstevol.Activity_Tracker.model.Task;

import java.util.function.Function;

public class TaskRequestDtoMapper implements Function<Task, TaskRequestDto> {
    @Override
    public TaskRequestDto apply(Task task) {
        return new TaskRequestDto(
                task.getTitle(),
                task.getDescription(),
                task.getTimeCreated(),
                task.getTimeUpdated(),
                task.getTimeCompleted()
        );
    }
}
