package com.eunsil.guestbook.service;

import com.eunsil.guestbook.domain.dto.CommentDTO;
import com.eunsil.guestbook.domain.entity.Card;
import com.eunsil.guestbook.domain.entity.Comment;
import com.eunsil.guestbook.domain.entity.User;
import com.eunsil.guestbook.repository.CardRepository;
import com.eunsil.guestbook.repository.CommentRepository;
import com.eunsil.guestbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CardRepository cardRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CardRepository cardRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * 새 댓글 생성
     * @param cardId 카드 ID
     * @param name 사용자 ID
     * @param content 댓글 내용
     * @return 생성 성공 여부
     */
    @Transactional
    public String insert(String cardId, String name, String content) {
        Card card = cardRepository.findAllById(Long.valueOf(cardId));
        User user = userRepository.findUserByName(name);

        Comment comment = Comment.builder()
                .card(card)
                .user(user)
                .content(content)
                .postDate(LocalDate.now())
                .build();
        commentRepository.save(comment);
        return "ok";
    }

    /**
     * 댓글 삭제
     * @param commentId 댓글 ID
     * @return 삭제 성공 여부
     */
    @Transactional
    public String delete(String commentId) {
        commentRepository.deleteById(Long.valueOf(commentId));
        return "ok";
    }

    /**
     * 댓글 조회
     * @param page 가져 올 페이지 (5개로 제한)
     * @param cardId 가져올 댓글의 카드 ID
     * @return 댓글 리스트
     */
    public List<CommentDTO> get(Integer page, String cardId) {
        Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "id");

        Card card = cardRepository.findAllById(Long.valueOf(cardId));
        List<Comment> commentList = commentRepository.findByCardOrderByIdDesc(card, pageable);
        List<CommentDTO> commentDtoList = new ArrayList<>();

        for (Comment comments : commentList) {
            CommentDTO commentDto = CommentDTO.builder()
                    .commentId(comments.getId())
                    .name(comments.getUser().getName())
                    .content(comments.getContent())
                    .postDate(comments.getPostDate())
                    .build();
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }

    /**
     * 댓글 개수 조회
     * @param cardId 가져올 댓글의 카드 ID
     * @return 댓글 개수
     */
    public int get(String cardId) {
        List<Comment> comments = commentRepository.findAllByCardId(cardId);
        return comments.size();
    }
}
