package com.coffeeinfinitive.api;

import com.coffeeinfinitive.Utils.Handler;
import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.Activity;
import com.coffeeinfinitive.dao.entity.Comment;
import com.coffeeinfinitive.dao.entity.User;
import com.coffeeinfinitive.exception.CoffeeSystemErrorException;
import com.coffeeinfinitive.model.CommentForm;
import com.coffeeinfinitive.service.*;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jinz on 4/15/17.
 * Controller quản lý toàn bộ hoạt động
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/comments")
public class CommentController {

    @Autowired
    ActivityService activityService;
    @Autowired
    UserService userService;
    @Autowired
    RegisterService registerService;
    @Autowired
    ValidatorService validatorService;
    @Autowired
    CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getActivities() {
		  List<Comment> comments = commentService.getAllComment();
		  return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }
    /*
    Lay danh sach dang ky theo hoat dong
     */
//    @GetMapping(path = "/{id}/registers")
//    public ResponseEntity<?> getRegisters(@PathVariable("id") String id) {
//		  Comment activity = commentService.findCommentbyId(id);
//		  if(activity==null){
//              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//          }
//          Set<Register> registers = activity.getRegisters();
//		  return new ResponseEntity<>(registers, HttpStatus.OK);
//    }
//
//    @GetMapping(path = "/{id}/registers/{idRegister}")
//    public ResponseEntity<?> getRegister(@PathVariable("id") String id,
//                                         @PathVariable("idRegister") String idRegister) {
//		  Comment activity = commentService.findCommentById(id);
//
//          Register register = registerService.findRegister(idRegister);
//        if(register==null){
//            JsonObject result = new JsonObject();
//            result.addProperty("code", ResultCode.REGISTER_NOT_FOUND.getCode());
//            result.addProperty("message", ResultCode.REGISTER_NOT_FOUND.getMessageVn());
//            return new ResponseEntity<>(result.toString(),HttpStatus.NOT_FOUND);
//        }
//		  return new ResponseEntity<>(register, HttpStatus.OK);
//    }
    @GetMapping(path = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getComment(@PathVariable("id") String id) {
		  Comment comment = commentService.findCommentById(id);
        if(comment==null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.COMMENT_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.COMMENT_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(result.toString(),HttpStatus.NOT_FOUND);
        }
		  return new ResponseEntity<>(comment, HttpStatus.OK);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addComment(Authentication auth,@RequestBody CommentForm commentForm) {

        Activity currentActivity = activityService.findActivityById(commentForm.getActivityId());
        if(currentActivity == null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.ACTIVITY_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.ACTIVITY_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(result.toString(),HttpStatus.NOT_FOUND);
        }
        Handler<CommentForm,Comment> convert = new Handler<>(Comment.class);

        User createdBy = userService.findUserById(auth.getPrincipal().toString());
        System.out.println("Create by: "+ createdBy.getUsername());

        Comment newComment = convert.ConvertObject(commentForm);
        // Init data;
        newComment.setCreatedDate();
        newComment.setActivity(currentActivity);
        newComment.setLastUpdatedDate();
        newComment.setOwner(createdBy);
        newComment.setLastUpdatedBy(createdBy);
        Comment savedComment = commentService.save(newComment);
        return new ResponseEntity<Comment>(savedComment, HttpStatus.CREATED);
    }
    @PutMapping(path = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateComment(Authentication auth,@PathVariable("id") String id, @RequestBody CommentForm commentForm) {
        Comment currentComment = commentService.findCommentById(id);
        if(currentComment==null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.COMMENT_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.COMMENT_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(result.toString(),HttpStatus.NOT_FOUND);
        }
        Handler<CommentForm,Comment> convert = new Handler<>(Comment.class);
        User updatedBy = userService.findUserById(auth.getPrincipal().toString());
        Comment newComment = convert.ConvertObject(commentForm);
        // Init data;

        currentComment = newComment;
        currentComment.setId(id);
        currentComment.setLastUpdatedDate();
        currentComment.setLastUpdatedBy(updatedBy);
        try {
            Comment savedComment = commentService.update(currentComment);
            return new ResponseEntity<Comment>(savedComment, HttpStatus.OK);
        }catch (Exception e){
            throw new CoffeeSystemErrorException("",e);
        }
    }
    @DeleteMapping(path = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteComment(Authentication auth,@PathVariable("id") String id) {
        Comment currentComment = commentService.findCommentById(id);
        if(currentComment==null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.COMMENT_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.COMMENT_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(result.toString(),HttpStatus.NOT_FOUND);
        }

        try{
            commentService.delete(id);
            return new ResponseEntity<Comment>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new CoffeeSystemErrorException("",e);
        }
    }

}
