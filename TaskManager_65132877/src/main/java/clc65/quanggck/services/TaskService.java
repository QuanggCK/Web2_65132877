package clc65.quanggck.services;

import java.util.List;

import org.springframework.stereotype.Service;

import clc65.quanggck.models.Project;
import clc65.quanggck.models.Task;
import clc65.quanggck.models.TaskStatus;
import clc65.quanggck.models.User;
import clc65.quanggck.repos.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Integer id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> getByProject(Project project) {
        return taskRepository.findByProject(project);
    }

    public List<Task> getByAssignedUser(User user) {
        return taskRepository.findByAssignedTo(user);
    }

    public List<Task> getByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> search(String keyword) {
        return taskRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Integer id) {
        taskRepository.deleteById(id);
    }
}