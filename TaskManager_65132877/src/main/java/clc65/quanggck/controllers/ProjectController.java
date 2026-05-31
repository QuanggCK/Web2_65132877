package clc65.quanggck.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import clc65.quanggck.models.Project;
import clc65.quanggck.services.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Danh sách project
    @GetMapping
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "project/list";
    }

    // Form thêm project
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("project", new Project());
        return "project/add";
    }

    // Lưu project
    @PostMapping("/save")
    public String saveProject(@ModelAttribute Project project) {
        projectService.save(project);
        return "redirect:/projects";
    }

    // Form sửa
    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable Integer id, Model model) {

        Project project = projectService.getProjectById(id);

        if (project == null) {
            return "redirect:/projects";
        }

        model.addAttribute("project", project);

        return "project/edit";
    }

    @PostMapping("/update")
    public String updateProject(@ModelAttribute Project project) {

        Project oldProject =
                projectService.getProjectById(project.getProjectId());

        if (oldProject == null) {
            return "redirect:/projects";
        }

        oldProject.setProjectName(project.getProjectName());
        oldProject.setDescription(project.getDescription());

        projectService.save(oldProject);

        return "redirect:/projects";
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Integer id) {

        projectService.delete(id);

        return "redirect:/projects";
    }
}