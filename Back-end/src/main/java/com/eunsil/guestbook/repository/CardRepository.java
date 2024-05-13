package com.eunsil.guestbook.repository;

import com.eunsil.guestbook.domain.entity.Card;
import com.eunsil.guestbook.domain.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query(value = "SELECT * FROM card WHERE id = :id", nativeQuery = true)
    Card findById(String id);

    @Query(value = "SELECT * FROM card WHERE content LIKE %:content%", nativeQuery = true)
    List<Card> findAllByContent(String content, Pageable pageable);

    @Query(value = "SELECT * FROM card WHERE user_id = :user AND content LIKE %:content%", nativeQuery = true)
    List<Card> findAllByUserByContentOrderByIdDesc(String content, User user, Pageable pageable);

    List<Card> findAllByUserOrderByIdDesc(User user);

    List<Card> findAllByUserOrderByIdDesc(User user, Pageable pageable);

    List<Card> findAllByOrderByIdDesc(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE card SET status='1' WHERE id = :cardId", nativeQuery = true)
    int updateCardStatusByCardId(String cardId);

    Long countByStatusTrue();

    Long countByStatusFalse();

}