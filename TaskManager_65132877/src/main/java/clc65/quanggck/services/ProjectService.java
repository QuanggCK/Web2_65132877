package clc65.quanggck.services;

import java.util.List;

import org.springframework.stereotype.Service;

import clc65.quanggck.models.Project;
import clc65.quanggck.models.User;
import clc65.quanggck.repos.ProjectRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Integer id) {
        return projectRepository.findById(id).orElse(null);
    }

    public List<Project> getProjectsByUser(User user) {
        return projectRepository.findByCreatedBy(user);
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public void delete(Integer id) {
        projectRepository.deleteById(id);
    }
}