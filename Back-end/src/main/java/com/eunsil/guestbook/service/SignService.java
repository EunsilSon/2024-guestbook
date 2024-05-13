package com.eunsil.guestbook.service;

import com.eunsil.guestbook.domain.entity.User;
import com.eunsil.guestbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SignService {

    private UserRepository userRepository;

    @Autowired
    public SignService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String signIn(String name, String pw) {
        if (userRepository.findUserByNameAndPassword(name, pw).isEmpty()) {
            return "fail";
        } else {
            return "ok";
        }
    }

    @Transactional
    public String signUp(String name, String pw, String tel) {
        User user;
        User username = userRepository.findUserByName(name);
        User telephone = userRepository.findUserByTelephone(tel);

        if (username != null) {
            return "Existed Username";
        } else if (telephone != null) {
            return "Existed telephone";
        } else {
            user = User.builder()
                    .name(name)
                    .password(pw)
                    .telephone(tel)
                    .build();
            userRepository.saveAndFlush(user);
            return "ok";
        }
    }

    public String findId(String tel) {
        User user = userRepository.findUserByTelephone(tel);
        if (user == null) {
            return "fail";
        } else {
            return user.getName();
        }
    }

    public String findPw(String name, String tel) {
        User user = userRepository.findPasswordByNameAndTelephone(name, tel);
        if (user == null) {
            return "fail";
        } else {
            return user.getPassword();
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
