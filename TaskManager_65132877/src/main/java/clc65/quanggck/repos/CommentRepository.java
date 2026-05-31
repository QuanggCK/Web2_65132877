package clc65.quanggck.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import clc65.quanggck.models.Comment;
import clc65.quanggck.models.Task;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByTask(Task task);

}