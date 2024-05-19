package com.eunsil.guestbook.repository;

import com.eunsil.guestbook.domain.entity.Card;
import com.eunsil.guestbook.domain.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Transactional
    void deleteById(Long commentId);

    @Transactional
    void deleteAllByCardId(Long cardId);

    List<Comment> findByCardOrderByIdDesc(Card card, Pageable pageable);

    long countByCardId(Long cardId);
}