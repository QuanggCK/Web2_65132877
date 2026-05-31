package clc65.quanggck.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import clc65.quanggck.models.Project;
import clc65.quanggck.models.ProjectMember;
import clc65.quanggck.models.User;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Integer> {

    List<ProjectMember> findByProject(Project project);

    List<ProjectMember> findByUser(User user);

}