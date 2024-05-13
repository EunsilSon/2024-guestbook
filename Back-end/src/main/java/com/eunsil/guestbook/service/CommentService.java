package com.eunsil.guestbook.service;

import com.eunsil.guestbook.domain.dto.CommentDTO;
import com.eunsil.guestbook.domain.entity.Card;
import com.eunsil.guestbook.domain.entity.Comment;
import com.eunsil.guestbook.domain.entity.User;
import com.eunsil.guestbook.repository.CardRepository;
import com.eunsil.guestbook.repository.CommentRepository;
import com.eunsil.guestbook.repository.UserRepository;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public String insert(String card_id, String name, String content) {
        Card card = cardRepository.findById(card_id); // 댓글 작성한 카드
        User user = userRepository.findUserByName(name);

        Comment comment = Comment.builder()
                .card(card)
                .user(user)
                .content(content)
                .postDate(LocalDate.now())
                .build();
        commentRepository.saveAndFlush(comment);
        return "ok";
    }

    @Transactional
    public String delete(String comment_id) {
        commentRepository.deleteById(comment_id);
        return "ok";
    }

    public List<CommentDTO> get(String cardId, Integer page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "id");

        Card card = cardRepository.findById(cardId);
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

    public int getCommentTotal(String cardId) {
        List<Comment> comments = commentRepository.findAllByCardId(cardId);
        return comments.size();
    }
}
