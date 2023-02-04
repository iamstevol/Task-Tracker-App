package com.iamstevol.Activity_Tracker.controller;

import com.iamstevol.Activity_Tracker.dto.TaskRequestDto;
import com.iamstevol.Activity_Tracker.exception.CustomException;
import com.iamstevol.Activity_Tracker.model.Task;
import com.iamstevol.Activity_Tracker.model.User;
import com.iamstevol.Activity_Tracker.service.impl.TaskServiceImpl;
import com.iamstevol.Activity_Tracker.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private TaskServiceImpl taskServiceImpl;

    @Autowired
    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @GetMapping("/add_Task")
    public String taskForm(Model model) {
        model.addAttribute("task", new TaskRequestDto());
        return "/addTask";
    }


    @PostMapping("/addTask")
    public String addTask(@ModelAttribute("task") TaskRequestDto taskRequestDto, HttpSession httpSession) {

        User user = (User) httpSession.getAttribute("user");  //What I need in the backend
        taskServiceImpl.createTask(user, taskRequestDto);

            return "redirect:/home";
    }

    @GetMapping("/edit_task/{taskId}")
    public String editForm(Model model, @PathVariable Long taskId) {
        model.addAttribute("task", new TaskRequestDto());
        model.addAttribute("taskId", taskId);
        return "edit";
    }

    @PostMapping("/editTask/{taskId}")
    public String editTask(@ModelAttribute("task") TaskRequestDto requestDto, @PathVariable Long taskId) throws CustomException {
        taskServiceImpl.updateTask(requestDto.getTitle(), requestDto.getDescription(), taskId);
        return "redirect:/home";
    }

    @GetMapping("/delete_task/{taskId}")
    public String deleteTask(@PathVariable Long taskId) {
        taskServiceImpl.deleteTask(taskId);
        return "redirect:/home";
    }

//    @GetMapping("/reverse_Task/{reverseTask}")
//    public String reverseTask(@PathVariable Long reverseTask) throws CustomException {
//        taskServiceImpl.moveTaskBackward(reverseTask);
//        return "redirect:/home";
//    }

    @GetMapping("/forward_Task/{forwardTask}")
    public String forwardTask(@PathVariable Long forwardTask) {
        taskServiceImpl.moveTaskForward(forwardTask);
        return "redirect:/home";
    }

    @GetMapping("/reverse_Task/{reverseTask}")
    public String reverseStatus(@PathVariable Long reverseTask) {
        taskServiceImpl.moveTaskBackward(reverseTask);
        return "redirect:/home";
    }

    @GetMapping("/complete_Task/{completedTask}")
    public String completedStatus(@PathVariable Long completedTask) {
        taskServiceImpl.moveTaskToDone(completedTask);
        return "redirect:/home";
    }




}
