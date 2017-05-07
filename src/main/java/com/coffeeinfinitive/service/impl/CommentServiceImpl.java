package com.coffeeinfinitive.service.impl;

import com.coffeeinfinitive.dao.entity.Activity;
import com.coffeeinfinitive.dao.entity.Comment;
import com.coffeeinfinitive.dao.reponsitories.ActivityRepository;
import com.coffeeinfinitive.dao.reponsitories.CommentRepository;
import com.coffeeinfinitive.service.ActivityService;
import com.coffeeinfinitive.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */
@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;


    @Override
    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findCommentById(String id) {
        return commentRepository.findOne(id);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(String id) {
        commentRepository.delete(id);
    }
}