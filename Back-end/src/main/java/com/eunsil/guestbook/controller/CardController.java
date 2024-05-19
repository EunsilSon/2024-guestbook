package com.eunsil.guestbook.controller;

import com.eunsil.guestbook.domain.dto.CardDTO;
import com.eunsil.guestbook.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @PatchMapping("/card")
    @ResponseBody
    public String update(@RequestBody HashMap<String, String> param) {
        return cardService.update(param.get("card_id"), param.get("content"));
    }

    @DeleteMapping("/card")
    @ResponseBody
    public String delete(@RequestBody HashMap<String, String> param) {
        return cardService.delete(param.get("card_id"));
    }

    @GetMapping("/card/search")
    @ResponseBody
    public List<CardDTO> search(@RequestParam Integer page, String location, String option, String username, String content) {
        return cardService.search(page, location, option, username, content);
    }

    @GetMapping("/card/my")
    public List<CardDTO> getMy(@RequestParam Integer page, String username) {
        return cardService.getMy(page, username);
    }

    @GetMapping("/card/all")
    public List<CardDTO> getAll(@RequestParam("page") Integer page) {
        return cardService.getAll(page);
    }

    @GetMapping("/card")
    public CardDTO getDetail(@RequestParam("id") String cardId) {
        return cardService.getDetail(cardId);
    }

    @GetMapping("/card/all_total")
    public int getAllTotal() {
        return cardService.getAllTotal();
    }

    @GetMapping("/card/my_total")
    public int getAllTotal(@RequestParam String username) {
        return cardService.getMyTotal(username);
    }


    @PatchMapping("/card/status")
    public String updateStatus(@RequestParam("card_id") String cardId) { return cardService.updateStatus(cardId); }

    @GetMapping("/card/count")
    public HashMap<String, Long> getCardCount() {
        return cardService.getCardCount();
    }

}
