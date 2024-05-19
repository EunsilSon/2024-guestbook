package com.eunsil.guestbook.repository;

import com.eunsil.guestbook.domain.entity.Card;
import com.eunsil.guestbook.domain.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Card findAllById(Long cardId);

    List<Card> findAllByContentContaining(String content, Pageable pageable);

    List<Card> findAllByUserAndContentContainingOrderByIdDesc(User user, String content, Pageable pageable);

    List<Card> findAllByUserOrderByIdDesc(User user, Pageable pageable);

    List<Card> findAllByOrderByIdDesc(Pageable pageable);

    Long countByUser(User user);

    Long countByStatusTrue();

    Long countByStatusFalse();

}