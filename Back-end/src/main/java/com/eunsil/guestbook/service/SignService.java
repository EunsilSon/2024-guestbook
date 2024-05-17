package com.eunsil.guestbook.service;

import com.eunsil.guestbook.domain.entity.User;
import com.eunsil.guestbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SignService {

    private UserRepository userRepository;

    @Autowired
    public SignService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String signIn(String name, String pw) {
        if (userRepository.existsByNameAndPassword(name, pw)) {
            return "ok";
        } else {
            return "fail";
        }
    }

    @Transactional
    public String signUp(String name, String pw, String tel) {

        if (userRepository.existsByName(name)) {
            return "Existed Username";
        }

        if (userRepository.existsByTelephone(tel)) {
            return "Existed telephone";
        }

        User user = User.builder()
                .name(name)
                .password(pw)
                .telephone(tel)
                .build();
        userRepository.saveAndFlush(user);
        return "ok";
    }

    public String findId(String tel) {
        if (userRepository.existsByTelephone(tel)) {
            User user = userRepository.findUserByTelephone(tel);
            return user.getName();
        } else {
            return "fail";
        }
    }

    public String findPw(String name, String tel) {
        if (userRepository.existsByNameAndTelephone(name, tel)) {
            User user = userRepository.findPasswordByNameAndTelephone(name, tel);
            return user.getPassword();
        } else {
            return "fail";
        }
    }

    public String checkUser(String username) {
        User user = userRepository.findUserByName(username);
        if (user.isAdmin()) {
            return "true";
        } else {
            return "false";
        }
    }
}
