package clc65.quanggck.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import clc65.quanggck.models.Project;
import clc65.quanggck.models.User;
import clc65.quanggck.services.ProjectService;
import clc65.quanggck.services.UserService;
import clc65.quanggck.services.ProjectMemberService;
import clc65.quanggck.services.TaskService;

import java.security.Principal;
import java.sql.Timestamp;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectMemberService projectMemberService;
    private final TaskService taskService;

    // Inject thêm các service bổ trợ để quản lý thành viên, công việc và user đăng nhập
    public ProjectController(ProjectService projectService, 
                             UserService userService,
                             ProjectMemberService projectMemberService,
                             TaskService taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.projectMemberService = projectMemberService;
        this.taskService = taskService;
    }

    // 1. Hiển thị danh sách dự án
    @GetMapping
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "project/list";
    }

    // 2. Form thêm mới dự án
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("project", new Project());
        return "project/add";
    }

    // 3. Lưu dự án mới (Đã tối ưu: Tự động gán Người tạo & Ngày tạo từ hệ thống)
    @PostMapping("/save")
    public String saveProject(@ModelAttribute Project project, Principal principal) {
        if (principal != null) {
            // Lấy username của người đang đăng nhập hệ thống hiện tại
            String username = principal.getName();
            User currentUser = userService.getByUsername(username);
            
            // Thiết lập thông tin ẩn trước khi lưu xuống database
            project.setCreatedBy(currentUser);
            project.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        }
        
        projectService.save(project);
        return "redirect:/projects";
    }

    // 4. Form sửa thông tin dự án
    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable Integer id, Model model) {
        Project project = projectService.getProjectById(id);

        if (project == null) {
            return "redirect:/projects";
        }

        model.addAttribute("project", project);
        return "project/edit";
    }

    // 5. Cập nhật thông tin dự án (Giữ nguyên cơ chế bảo toàn dữ liệu cũ của bạn)
    @PostMapping("/update")
    public String updateProject(@ModelAttribute Project project) {
        Project oldProject = projectService.getProjectById(project.getProjectId());

        if (oldProject == null) {
            return "redirect:/projects";
        }

        // Chỉ cập nhật những trường được thay đổi từ Form giao diện
        oldProject.setProjectName(project.getProjectName());
        oldProject.setDescription(project.getDescription());

        projectService.save(oldProject);
        return "redirect:/projects";
    }

    // 6. Xóa dự án
    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Integer id) {
        projectService.delete(id);
        return "redirect:/projects";
    }

    // 7. TÍNH NĂNG MỚI: Chi tiết dự án (Hiển thị Thành viên và các Công việc liên quan)
    @GetMapping("/{id}")
    public String detailProject(@PathVariable Integer id, Model model) {
        Project project = projectService.getProjectById(id);

        if (project == null) {
            return "redirect:/projects";
        }

        model.addAttribute("project", project);
        
        // Nạp danh sách thành viên thuộc dự án sang trang project/detail.html
        model.addAttribute("members", projectMemberService.getByProject(project));
        
        // Nạp danh sách các công việc (tasks) thuộc dự án này sang trang detail
        model.addAttribute("tasks", taskService.getByProject(project));

        return "project/detail";
    }
}