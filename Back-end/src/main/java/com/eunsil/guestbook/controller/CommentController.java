package com.eunsil.guestbook.controller;

import com.eunsil.guestbook.domain.dto.CommentDTO;
import com.eunsil.guestbook.service.CommentService;
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
    @PostMapping
    public String insert(@RequestBody HashMap<String, String> param) {
        return commentService.insert(param.get("card_id"), param.get("name"), param.get("content"));
    }

    /**
     * 댓글 삭제
     * @param param 댓글 ID
     * @return 삭제 성공 여부
     */
    @DeleteMapping("/comment")
    public String delete(@RequestBody HashMap<String, String> param) {
        return commentService.delete(param.get("comment_id"));
    }

    /**
     * 댓글 조회
     * @param page 가져 올 페이지 (5개로 제한)
     * @param cardId 가져올 댓글의 카드 ID
     * @return 댓글 리스트
     */
    @GetMapping("/comment")
    public List<CommentDTO> get(@RequestParam Integer page, @RequestParam("card_id") String cardId) {
        return commentService.get(page, cardId);
    }

    /**
     * 댓글 개수 조회
     * @param cardId 가져올 댓글의 카드 ID
     * @return 댓글 개수
     */
    @GetMapping("/comment/total")
    public long get(@RequestParam String cardId) {
        return commentService.get(cardId);
    }
}
