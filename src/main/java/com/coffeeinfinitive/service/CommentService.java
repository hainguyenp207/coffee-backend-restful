package com.coffeeinfinitive.service;

import com.coffeeinfinitive.dao.entity.Activity;
import com.coffeeinfinitive.dao.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */

public interface CommentService {
    List<Comment> getAllComment();
    Page<Comment> getCommentsByPage(Pageable pageable);
    long count();
    Comment findCommentById(String id);
    Comment save(Comment comment);
    Comment update(Comment comment);
    void delete(String id);
}
