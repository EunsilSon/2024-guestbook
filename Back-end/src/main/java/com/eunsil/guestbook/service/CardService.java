package com.eunsil.guestbook.service;

import com.eunsil.guestbook.domain.dto.CardDTO;
import com.eunsil.guestbook.domain.entity.Card;
import com.eunsil.guestbook.domain.entity.User;
import com.eunsil.guestbook.repository.CardRepository;
import com.eunsil.guestbook.repository.CommentRepository;
import com.eunsil.guestbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CardService {
    private CardRepository cardRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    @Autowired
    public CardService(CardRepository cardRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public String insert(String name, String content) {
        User user = userRepository.findUserByName(name);

        Card card = Card.builder()
                .user(user)
                .content(content)
                .postDate(LocalDate.now())
                .build();
        cardRepository.saveAndFlush(card);
        return "ok";
    }

    @Transactional
    public String update(String card_id, String content) {
        Card card = cardRepository.findById(card_id);
        if (card != null) {
            card.setContent(content);
            cardRepository.saveAndFlush(card);
            return "ok";
        } else {
            return "Not Existed Card";
        }
    }

    @Transactional
    public String delete(String cardId) {
        commentRepository.deleteAllByCardId(Long.parseLong(cardId)); // 카드에 달린 댓글 삭제
        Card card = cardRepository.findById(cardId); // 카드 삭제
        if (card != null) {
            cardRepository.deleteById(Long.parseLong(cardId));
            return "ok";
        } else {
            return "Not Existed Card";
        }
    }

    public List<CardDTO> search(Integer page, String location, String option, String name, String content) {
        Pageable pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "id");
        List<Card> cardList;

        if (location.equals("all")) { // 모든 카드 페이지
            if (option.equals("username")) { // 사용자 명으로 찾기
                User user = userRepository.findUserByName(content);
                cardList = cardRepository.findAllByUserOrderByIdDesc(user, pageable);
            } else { // 내용으로 찾기
                cardList = cardRepository.findAllByContent(content, pageable);
            }
        } else { // 내 카드 페이지 - 내용으로 찾기
            User user = userRepository.findUserByName(name);
            cardList = cardRepository.findAllByUserByContentOrderByIdDesc(content, user, pageable);
        }

        List<CardDTO> cardDTOList = new ArrayList<>();

        for (Card cards : cardList) {
            CardDTO card = CardDTO.builder()
                    .cardId(cards.getId())
                    .name(cards.getUser().getName())
                    .content(cards.content)
                    .postDate(cards.postDate)
                    .build();
            cardDTOList.add(card);
        }
        return cardDTOList;
    }

    public List<CardDTO> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "id");
        List<Card> cardList = cardRepository.findAllByOrderByIdDesc(pageable);
        List<CardDTO> cardDtoList = new ArrayList<>();

        for (Card cards : cardList) {
            CardDTO cardDto = CardDTO.builder()
                    .cardId(cards.getId())
                    .name(cards.getUser().getName())
                    .content(cards.getContent())
                    .postDate(cards.getPostDate())
                    .build();
            cardDtoList.add(cardDto);
        }
        return cardDtoList;
    }

    public List<CardDTO> getMy(Integer page, String username){
        Pageable pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "id");
        User user = userRepository.findUserByName(username);
        List<Card> cardList = cardRepository.findAllByUserOrderByIdDesc(user, pageable);

        List<CardDTO> cardDTOList = new ArrayList<>();

        for (Card cards : cardList) {
            CardDTO card = CardDTO.builder()
                    .cardId(cards.getId())
                    .name(cards.getUser().getName())
                    .content(cards.content)
                    .postDate(cards.postDate)
                    .build();
            cardDTOList.add(card);
        }
        return cardDTOList;
    }

    public CardDTO getDetail(String cardId) {
        Card card = cardRepository.findById(cardId);

        CardDTO cardDTO = CardDTO.builder()
                .cardId(card.getId())
                .name(card.user.getName())
                .content(card.getContent())
                .postDate(card.getPostDate())
                .build();
        return cardDTO;
    }

    public int getAllTotal() {
        return cardRepository.findAll().size();
    }

    public int getMyTotal(String username) {
        User user = userRepository.findUserByName(username);
        return cardRepository.findAllByUserOrderByIdDesc(user).size();
    }

    public String updateStatus(String cardId) {
        if (cardRepository.updateCardStatusByCardId(cardId) == 1) {
            return "ok";
        } else {
            return "failed";
        }
    }

    public HashMap<String, Long> getCardCount() {
        HashMap<String, Long> countList = new HashMap<>();
        countList.put("total", (long)cardRepository.findAll().size());
        countList.put("true", cardRepository.countByStatusTrue());
        countList.put("false", cardRepository.countByStatusFalse());

        return countList;
    }

}
