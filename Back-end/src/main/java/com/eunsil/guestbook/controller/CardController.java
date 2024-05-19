package com.eunsil.guestbook.controller;

import com.eunsil.guestbook.domain.dto.CardDTO;
import com.eunsil.guestbook.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class CardController {
    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    /**
     * 새 카드 생성
     * @param param 사용자 ID, 카드 내용
     * @return 삽입 성공 여부
     */
    @PostMapping("/card")
    @ResponseBody
    public boolean insert(@RequestBody HashMap<String, String> param) {
        return cardService.insert(param.get("username"), param.get("content"));
    }

    /**
     * 카드 내용 수정
     * @param param 카드 ID, 수정할 카드 내용
     * @return 수정 성공 여부
     */
    @PatchMapping("/card")
    @ResponseBody
    public String update(@RequestBody HashMap<String, String> param) {
        return cardService.update(param.get("card_id"), param.get("content"));
    }

    /**
     * 카드 삭제
     * @param param 카드 ID
     * @return 삭제 성공 여부
     */
    @DeleteMapping("/card")
    @ResponseBody
    public String delete(@RequestBody HashMap<String, String> param) {
        return cardService.delete(param.get("card_id"));
    }

    /**
     * 카드 검색 (모든 카드 페이지)
     * @param page 가져 올 카드 페이지 (5개로 제한)
     * @param option 검색 옵션 (사용자 이름 또는 카드 내용)
     * @param content 검색할 내용 (사용자 이름 또는 카드의 일부 내용)
     * @return CardDTO 리스트
     */
    @GetMapping("/cards/search")
    public List<CardDTO> search(@RequestParam Integer page, int option, String content) {
        return cardService.search(page, option, content);
    }

    /**
     * 카드 검색 (내가 쓴 카드 페이지)
     * @param page 가져 올 카드 페이지 (5개로 제한)
     * @param username 사용자 이름
     * @param content 검색할 내용 (카드의 일부 내용)
     * @return CardDTO 리스트
     */
    @GetMapping("/mycards/search")
    public List<CardDTO> search(@RequestParam Integer page, String username, String content) {
        return cardService.search(page, username, content);
    }

    /**
     * 내가 쓴 카드 조회
     * @param page 가져 올 카드 페이지 (5개로 제한)
     * @param username 사용자 이름
     * @return CardDTO 리스트
     */
    @GetMapping("/mycards")
    public List<CardDTO> getCardListByUser(@RequestParam Integer page, String username) {
        return cardService.getCardListByUser(page, username);
    }

    /**
     * 모든 카드 조회
     * @param page 가져 올 카드 페이지 (5개로 제한)
     * @return CardDTO 리스트
     */
    @GetMapping("/card/all")
    public List<CardDTO> getAllCards(@RequestParam("page") Integer page) {
        return cardService.getAllCards(page);
    }

    /**
     * 카드 상세 조회
     * @param cardId 카드 ID
     * @return 특정 카드의 모든 정보
     */
    @GetMapping("/card")
    public CardDTO getCardDetail(@RequestParam("id") String cardId) {
        return cardService.getCardDetail(cardId);
    }

    /**
     * 모든 카드의 개수 조회
     * @return 모든 카드의 개수
     */
    @GetMapping("/card/all_total")
    public long getTotal() {
        return cardService.getTotal();
    }

    /**
     * 내가 쓴 카드의 개수 조회
     * @param username 사용자 이름
     * @return 내가 쓴 카드의 개수
     */
    @GetMapping("/card/my_total")
    public long getTotal(@RequestParam String username) {
        return cardService.getTotal(username);
    }

    /**
     * 카드 현황 표시를 위한 개수 조회
     * @return 카드 총합, 확인 전 카드, 확인 후 카드 개수를 담은 Map
     */
    @GetMapping("/card/count")
    public HashMap<String, Long> getCardCount() {
        return cardService.getCardCount();
    }

    /**
     * 카드 확인 표시를 위한 카드 상태 변경
     * @param cardId 카드 ID
     * @return 상태 변경 성공 여부
     */
    @PatchMapping("/card/status")
    public boolean update(@RequestParam("card_id") String cardId) { return cardService.update(cardId); }

}
