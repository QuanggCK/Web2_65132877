package clc65.quanggck.services;

import java.util.List;

import org.springframework.stereotype.Service;

import clc65.quanggck.models.Project;
import clc65.quanggck.models.ProjectMember;
import clc65.quanggck.models.User;
import clc65.quanggck.repos.ProjectMemberRepository;

@Service
public class ProjectMemberService {

    private final ProjectMemberRepository repository;

    public ProjectMemberService(ProjectMemberRepository repository) {
        this.repository = repository;
    }

    public List<ProjectMember> getAll() {
        return repository.findAll();
    }

    public List<ProjectMember> getByProject(Project project) {
        return repository.findByProject(project);
    }

    public List<ProjectMember> getByUser(User user) {
        return repository.findByUser(user);
    }

    public ProjectMember save(ProjectMember member) {
        return repository.save(member);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}