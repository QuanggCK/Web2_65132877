package clc65.quanggck.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import clc65.quanggck.models.Task;
import clc65.quanggck.services.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Danh sách task
    @GetMapping
    public String listTasks(Model model) {

        model.addAttribute("tasks", taskService.getAllTasks());

        return "task/list";
    }

    // Form thêm task
    @GetMapping("/add")
    public String showAddForm(Model model) {

        model.addAttribute("task", new Task());

        return "task/add";
    }

    // Lưu task
    @PostMapping("/save")
    public String saveTask(@ModelAttribute Task task) {

        taskService.save(task);

        return "redirect:/tasks";
    }

    // Form sửa
    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Integer id, Model model) {

        Task task = taskService.getTaskById(id);

        if (task == null) {
            return "redirect:/tasks";
        }

        model.addAttribute("task", task);

        return "task/edit";
    }

    // Cập nhật
    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task) {

        taskService.save(task);

        return "redirect:/tasks";
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Integer id) {

        taskService.delete(id);

        return "redirect:/tasks";
    }

    // Chi tiết task
    @GetMapping("/{id}")
    public String detailTask(@PathVariable Integer id,
                             Model model) {

        Task task = taskService.getTaskById(id);

        if (task == null) {
            return "redirect:/tasks";
        }

        model.addAttribute("task", task);

        return "task/detail";
    }
}