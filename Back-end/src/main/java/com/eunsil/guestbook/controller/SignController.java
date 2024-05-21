package com.eunsil.guestbook.controller;

import com.eunsil.guestbook.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class SignController {
    private SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }

    /**
     * 로그인
     * @param param 사용자 이름, 비밀번호
     * @return 로그인 성공 여부
     */
    @PostMapping("/sign_in")
    public boolean signIn(@RequestBody HashMap<String, String> param) {
        return signService.signIn(param.get("username"), param.get("password"));
    }

    /**
     * 회원 가입
     * @param param 사용자 이름, 비밀번호, 연락처
     * @return 이름과 연락처 중복 값 체크 or 회원 가입 성공 여부
     */
    @PostMapping("/sign_up")
    public String signUp(@RequestBody HashMap<String, String> param) {
        return signService.signUp(param.get("username"), param.get("password"), param.get("telephone"));
    }

    /**
     * ID 찾기
     * @param tel 연락처
     * @return 찾은 ID or ID 찾기 성공 여부
     */
    @GetMapping("/id")
    public String findId(@RequestParam String tel) {
        return signService.findId(tel);
    }

    /**
     * PW 찾기
     * @param param 사용자 이름, 연락처
     * @return 찾은 PW or PW 찾기 성공 여부
     */
    @PostMapping("/pw")
    public String findPw(@RequestBody HashMap<String, String> param) {
        return signService.findPw(param.get("username"), param.get("telephone"));
    }

    /**
     * 사용자 권한 체크
     * @param username 사용자 이름
     * @return 관리자 권한 소유 여부
     */
    @GetMapping("/check")
    public boolean checkUser(@RequestParam String username) {
        return signService.isAdmin(username);
    }
}
