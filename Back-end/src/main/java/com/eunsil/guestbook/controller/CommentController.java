package com.eunsil.guestbook.controller;

import com.eunsil.guestbook.domain.dto.CommentDTO;
import com.eunsil.guestbook.service.CommentService;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "http://172.20.10.2:8080")
@RestController
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    @ResponseBody
    public String insert(@RequestBody HashMap<String, String> param) {
        return commentService.insert(param.get("card_id"), param.get("name"), param.get("content"));
    }

    @DeleteMapping("/comment")
    @ResponseBody
    public String delete(@RequestBody HashMap<String, String> param) {
        return commentService.delete(param.get("comment_id"));
    }

    @GetMapping("/comment")
    @ResponseBody
    public List<CommentDTO> get(@RequestParam Integer page, String cardId) {
        return commentService.get(cardId, page);
    }

    @GetMapping("/comment/total")
    @ResponseBody
    public int get(@RequestParam String cardId) {
        return commentService.getCommentTotal(cardId);
    }
}
