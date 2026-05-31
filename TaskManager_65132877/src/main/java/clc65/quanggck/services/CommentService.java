package clc65.quanggck.services;

import java.util.List;

import org.springframework.stereotype.Service;

import clc65.quanggck.models.Comment;
import clc65.quanggck.models.Task;
import clc65.quanggck.repos.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getByTask(Task task) {
        return commentRepository.findByTask(task);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }
}