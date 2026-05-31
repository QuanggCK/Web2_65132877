package clc65.quanggck.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import clc65.quanggck.models.Task;
import clc65.quanggck.models.Comment;
import clc65.quanggck.services.TaskService;
import clc65.quanggck.services.ProjectService;
import clc65.quanggck.services.UserService;
import clc65.quanggck.services.TaskStatusService;
import clc65.quanggck.services.CommentService;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;
    private final TaskStatusService taskStatusService;
    private final CommentService commentService;

    // Inject đầy đủ các Service bổ trợ để phục vụ việc nạp dữ liệu lên View Thymeleaf
    public TaskController(TaskService taskService, 
                          ProjectService projectService, 
                          UserService userService, 
                          TaskStatusService taskStatusService,
                          CommentService commentService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
        this.taskStatusService = taskStatusService;
        this.commentService = commentService;
    }

    // 1. Danh sách công việc (Giữ nguyên)
    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "task/list";
    }

    // 2. Form thêm mới công việc (Đã sửa - Nạp thêm danh sách Project và User)
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("task", new Task());
        
        // Cần thiết cho thẻ <select> trong add.html
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("users", userService.getAllUsers());
        
        return "task/add";
    }

    // 3. Lưu công việc mới (Giữ nguyên)
    @PostMapping("/save")
    public String saveTask(@ModelAttribute Task task) {
        taskService.save(task);
        return "redirect:/tasks";
    }

    // 4. Form sửa công việc (Đã sửa - Nạp thêm Project, User và Status)
    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Integer id, Model model) {
        Task task = taskService.getTaskById(id);

        if (task == null) {
            return "redirect:/tasks";
        }

        model.addAttribute("task", task);
        
        // Cần thiết cho các thẻ <select> lựa chọn cập nhật trong edit.html
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("statuses", taskStatusService.getAll()); // Lấy danh sách trạng thái tiến độ

        return "task/edit";
    }

    // 5. Cập nhật dữ liệu công việc (Đã tối ưu hóa tránh mất dữ liệu ẩn)
    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task) {
        Task existingTask = taskService.getTaskById(task.getTaskId());
        
        if (existingTask == null) {
            return "redirect:/tasks";
        }

        // Cập nhật các trường thông tin được phép sửa đổi từ form
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setPriority(task.getPriority());
        existingTask.setStartDate(task.getStartDate());
        existingTask.setDeadline(task.getDeadline());
        existingTask.setProject(task.getProject());
        existingTask.setAssignedTo(task.getAssignedTo());
        existingTask.setStatus(task.getStatus());

        // Thực hiện lưu trữ đối tượng đã cập nhật
        taskService.save(existingTask);

        return "redirect:/tasks";
    }

    // 6. Xóa công việc (Giữ nguyên)
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Integer id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }

    // 7. Chi tiết công việc (Đã sửa - Nạp thêm danh sách Comment)
    @GetMapping("/{id}")
    public String detailTask(@PathVariable Integer id, Model model) {
        Task task = taskService.getTaskById(id);

        if (task == null) {
            return "redirect:/tasks";
        }

        model.addAttribute("task", task);
        
        // Tìm và nạp danh sách bình luận của công việc này để truyền sang detail.html hiển thị
        List<Comment> comments = commentService.getByTask(task);
        model.addAttribute("comments", comments);

        return "task/detail";
    }
}