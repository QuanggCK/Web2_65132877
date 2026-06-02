package clc65.quanggck.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import clc65.quanggck.models.Task;
import clc65.quanggck.models.User;
import clc65.quanggck.models.Comment;
import clc65.quanggck.services.TaskService;
import clc65.quanggck.services.ProjectService;
import clc65.quanggck.services.UserService;
import clc65.quanggck.services.TaskStatusService;
import clc65.quanggck.services.CommentService;

import java.security.Principal;
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

    // 1. Danh sách công việc
    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "task/list";
    }

    // 2. Form thêm mới công việc 
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("task", new Task());
        
        // Cần thiết cho thẻ <select> trong add.html
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("users", userService.getAllUsers());
        
        return "task/add";
    }

 // 3. Lưu công việc mới
    @PostMapping("/save") 
    public String saveTask(@ModelAttribute Task task, Principal principal) {
        
        // 1. Kiểm tra và gán người tạo công việc (Đã xử lý ở bước trước)
        if (principal != null) {
            String username = principal.getName();
            User currentUser = userService.getByUsername(username);
            task.setCreatedBy(currentUser);
        } else {
            return "redirect:/login";
        }
        
        if (task.getStatus() == null) {
            java.util.List<clc65.quanggck.models.TaskStatus> statuses = taskStatusService.getAll();
            if (statuses != null && !statuses.isEmpty()) {
    
                task.setStatus(statuses.get(0)); 
            }

        }
        
        // 3. Tiến hành lưu công việc hợp lệ xuống Database
        taskService.save(task);
        
        // 4. Điều hướng về danh sách công việc
        return "redirect:/tasks";
    }

    // 4. Form sửa công việc (ĐÃ SỬA: Bổ sung định danh "id" vào @PathVariable)
    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable("id") Integer id, Model model) {
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

    // 6. Xóa công việc (ĐÃ SỬA: Bổ sung định danh "id" vào @PathVariable)
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Integer id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }

    // 7. Chi tiết công việc (ĐÃ SỬA: Bổ sung định danh "id" vào @PathVariable)
    @GetMapping("/{id}")
    public String detailTask(@PathVariable("id") Integer id, Model model) {
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
 // Thêm hàm này vào trong TaskController.java
    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable("id") Integer id, 
                             @RequestParam("content") String content,
                             Principal principal) { // <-- Thêm Principal để lấy user đang đăng nhập
        
        // 1. Kiểm tra công việc có tồn tại không
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return "redirect:/tasks";
        }

        // 2. Lấy thông tin tài khoản đang đăng nhập
        if (principal == null) {
            // Nếu chưa đăng nhập mà bằng cách nào đó vào đây thì đá về trang login
            return "redirect:/login"; 
        }
        String username = principal.getName(); // Lấy tên tài khoản/email đăng nhập
        
        // Tìm đối tượng User tương ứng trong Database
        // Giả sử trong UserService của bạn có hàm findByUsername hoặc findByEmail
        User currentUser = userService.getByUsername(username); 
        
        if (currentUser == null) {
            return "redirect:/tasks/" + id + "?error=user_not_found";
        }

        // 3. Tạo đối tượng Comment mới và gán đầy đủ thông tin
        Comment comment = new Comment();
        comment.setTask(task);
        comment.setContent(content);
        comment.setUser(currentUser); // <-- QUAN TRỌNG: Gán user vào đây để hết lỗi NOT NULL
        
        // Nếu trong Entity Comment chưa tự động sinh thời gian, bạn có thể gán thủ công:
        // comment.setCreatedAt(new java.util.Date()); 

        // 4. Lưu vào Database
        commentService.save(comment);

        // 5. Quay lại trang chi tiết công việc
        return "redirect:/tasks/" + id;
    }
}