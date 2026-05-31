package clc65.quanggck.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import clc65.quanggck.models.Project;
import clc65.quanggck.models.User;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findByCreatedBy(User user);

}