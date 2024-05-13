package com.eunsil.guestbook.controller;

import com.eunsil.guestbook.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin(origins = "http://172.20.10.2:8080")
@RestController
public class SignController {
    private SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }

    @PostMapping("/sign_in")
    @ResponseBody
    public String signIn(@RequestBody HashMap<String, String> param) {
        return signService.signIn(param.get("username"), param.get("password"));
    }

    @PostMapping("/sign_up")
    @ResponseBody
    public String signUp(@RequestBody HashMap<String, String> param) {
        return signService.signUp(param.get("username"), param.get("password"), param.get("telephone"));
    }

    @GetMapping("/id")
    @ResponseBody
    public String findId(@RequestParam String tel) {
        System.out.println(tel);
        return signService.findId(tel);
    }

    @GetMapping("/pw")
    @ResponseBody
    public String findPw(@RequestParam String name, String tel) {
        return signService.findPw(name,tel);
    }

    @GetMapping("/check")
    public String check(@RequestParam String username) {
        return signService.checkUser(username);
    }
}
