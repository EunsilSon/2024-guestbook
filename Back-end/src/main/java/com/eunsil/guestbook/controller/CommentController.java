package com.eunsil.guestbook.controller;

import com.eunsil.guestbook.domain.dto.CommentDTO;
import com.eunsil.guestbook.service.CommentService;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 새 댓글 생성
     * @param param 카드 ID, 사용자 ID, 댓글 내용
     * @return 생성 성공 여부
     */
    @PostMapping("/comment")
    @ResponseBody
    public String insert(@RequestBody HashMap<String, String> param) {
        return commentService.insert(param.get("card_id"), param.get("name"), param.get("content"));
    }

    /**
     * 댓글 삭제
     * @param param 댓글 ID
     * @return 삭제 성공 여부
     */
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
