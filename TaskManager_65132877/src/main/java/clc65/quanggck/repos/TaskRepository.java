package clc65.quanggck.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import clc65.quanggck.models.Project;
import clc65.quanggck.models.Task;
import clc65.quanggck.models.TaskStatus;
import clc65.quanggck.models.User;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByProject(Project project);

    List<Task> findByAssignedTo(User user);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByCreatedBy(User user);

    List<Task> findByTitleContainingIgnoreCase(String keyword);

}