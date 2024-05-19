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

import jakarta.transaction.Transactional;
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

    /**
     * 새 카드 생성
     * @param name 사용자 ID
     * @param content 카드 내용
     * @return 삽입 성공 여부
     */
    @Transactional
    public boolean insert(String name, String content) {
        User user = userRepository.findUserByName(name);

        Card card = Card.builder()
                .user(user)
                .content(content)
                .postDate(LocalDate.now())
                .build();
        cardRepository.save(card);
        return true;
    }

    /**
     * 카드 내용 수정
     * @param cardId  카드 ID
     * @param content 수정할 카드 내용
     * @return 수정 성공 여부
     */
    @Transactional
    public String update(String cardId, String content) {
        Card card = cardRepository.findAllById(Long.valueOf(cardId));
        card.setContent(content);
        cardRepository.saveAndFlush(card);
        return "ok";
    }

    /**
     * 카드 삭제
     * @param cardId 카드 ID
     * @return 삭제 성공 여부
     */
    @Transactional
    public String delete(String cardId) {
        commentRepository.deleteAllByCardId(Long.valueOf(cardId)); // Card 의 참조키인 Comment 삭제 -> 외래키 제약 조건
        cardRepository.deleteById(Long.valueOf(cardId));
        return "ok";
    }

    /**
     * 모든 카드 페이지에서 카드 검색
     * @param page 가져 올 카드 페이지 (5개로 제한)
     * @param option 검색 옵션 (0: 사용자 이름, 1: 카드 내용)
     * @param content 검색할 내용 (사용자 이름 또는 카드의 일부 내용)
     * @return  CardDTO 리스트
     */
    public List<CardDTO> search(Integer page, int option, String content) {
        Pageable pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "id");
        List<Card> cardList;

        if (option == 0) { // 사용자 이름으로 찾기
            User user = userRepository.findUserByName(content);
            cardList = cardRepository.findAllByUserOrderByIdDesc(user, pageable);
        } else { // 내용으로 찾기
            cardList = cardRepository.findAllByContentContaining(content, pageable);
        }

        List<CardDTO> cardDTOList = new ArrayList<>();
        for (Card cards : cardList) {
            CardDTO card = CardDTO.builder()
                    .cardId(cards.getId())
                    .name(cards.getUser().getName())
                    .content(cards.getContent())
                    .postDate(cards.getPostDate())
                    .build();
            cardDTOList.add(card);
        }
        return cardDTOList;
    }

    /**
     * 내가 쓴 카드 페이지에서 카드 검색
     * @param page 가져 올 카드 페이지 (5개로 제한)
     * @param username 사용자 이름
     * @param content 검색할 내용 (카드의 일부 내용)
     * @return  CardDTO 리스트
     */
    public List<CardDTO> search(Integer page, String username, String content) {
        Pageable pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "id");

        User user = userRepository.findUserByName(username);
        List<Card> cardList = cardRepository.findAllByUserAndContentContainingOrderByIdDesc(user, content, pageable);

        List<CardDTO> cardDTOList = new ArrayList<>(); // 필요한 카드 정보만 담기 위해 CardDTO 사용
        for (Card cards : cardList) {
            CardDTO card = CardDTO.builder()
                    .cardId(cards.getId())
                    .name(cards.getUser().getName())
                    .content(cards.getContent())
                    .postDate(cards.getPostDate())
                    .build();
            cardDTOList.add(card);
        }
        return cardDTOList;
    }

    /**
     * 내가 쓴 카드 조회
     * @param page 가져 올 카드 페이지 (5개로 제한)
     * @param username 사용자 이름
     * @return CardDTO 리스트
     */
    public List<CardDTO> getCardListByUser(Integer page, String username) {
        Pageable pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "id");
        User user = userRepository.findUserByName(username);
        List<Card> cardList = cardRepository.findAllByUserOrderByIdDesc(user, pageable);

        List<CardDTO> cardDTOList = new ArrayList<>(); // 필요한 카드 정보만 담기 위해 CardDTO 사용
        for (Card cards : cardList) {
            CardDTO card = CardDTO.builder()
                    .cardId(cards.getId())
                    .name(cards.getUser().getName())
                    .content(cards.getContent())
                    .postDate(cards.getPostDate())
                    .build();
            cardDTOList.add(card);
        }
        return cardDTOList;
    }

    /**
     * 모든 카드 조회
     * @param page 가져 올 카드 페이지 (5개로 제한)
     * @return CardDTO 리스트
     */
    public List<CardDTO> getAllCards(Integer page) {
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

    /**
     * 카드 상세 조회
     * @param cardId 카드 ID
     * @return 특정 카드의 모든 정보
     */
    public CardDTO getCardDetail(String cardId) {
        Card card = cardRepository.findAllById(Long.valueOf(cardId));

        CardDTO cardDTO = CardDTO.builder()
                .cardId(card.getId())
                .name(card.getUser().getName())
                .content(card.getContent())
                .postDate(card.getPostDate())
                .build();
        return cardDTO;
    }

    /**
     * 모든 카드의 개수 조회
     * @return 모든 카드의 개수
     */
    public long getTotal() {
        return cardRepository.count();
    }

    /**
     * 내가 쓴 카드의 개수 조회
     * @param username 사용자 이름
     * @return 내가 쓴 카드의 개수
     */
    public long getTotal(String username) {
        User user = userRepository.findUserByName(username);
        return cardRepository.countByUser(user);
    }

    /**
     * 카드 현황 표시를 위한 개수 조회
     * @return 카드 총합, 확인 전 카드, 확인 후 카드 개수를 담은 Map
     */
    public HashMap<String, Long> getCardCount() {
        HashMap<String, Long> countList = new HashMap<>();
        countList.put("total", cardRepository.count());
        countList.put("true", cardRepository.countByStatusTrue());
        countList.put("false", cardRepository.countByStatusFalse());

        return countList;
    }

    /**
     * 카드 확인 표시를 위한 카드 상태 변경
     * @param cardId 카드 ID
     * @return 상태 변경 성공 여부
     */
    public boolean update(String cardId) {
        Card card = cardRepository.findAllById(Long.valueOf(cardId));
        card.setStatus(true);
        cardRepository.save(card);
        return true;
    }
}