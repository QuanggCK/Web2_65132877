package clc65.quanggck.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import clc65.quanggck.models.TaskStatus;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {

    TaskStatus findByStatusName(String statusName);

}