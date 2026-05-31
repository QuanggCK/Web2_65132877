package clc65.quanggck.services;

import java.util.List;

import org.springframework.stereotype.Service;

import clc65.quanggck.models.TaskStatus;
import clc65.quanggck.repos.TaskStatusRepository;

@Service
public class TaskStatusService {

    private final TaskStatusRepository repository;

    public TaskStatusService(TaskStatusRepository repository) {
        this.repository = repository;
    }

    public List<TaskStatus> getAll() {
        return repository.findAll();
    }

    public TaskStatus getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public TaskStatus getByName(String name) {
        return repository.findByStatusName(name);
    }

    public TaskStatus save(TaskStatus status) {
        return repository.save(status);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}