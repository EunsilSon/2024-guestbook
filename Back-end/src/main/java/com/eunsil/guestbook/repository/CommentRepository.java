package com.eunsil.guestbook.repository;

import com.eunsil.guestbook.domain.entity.Card;
import com.eunsil.guestbook.domain.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM comment WHERE id = :commentId", nativeQuery = true)
    void deleteById(String commentId);

    @Transactional
    void deleteAllByCardId(Long cardId);

    List<Comment> findByCardOrderByIdDesc(Card card, Pageable pageable);

    @Query(value = "SELECT * FROM comment WHERE card_id = :cardId", nativeQuery = true)
    List<Comment> findAllByCardId(String cardId);
}