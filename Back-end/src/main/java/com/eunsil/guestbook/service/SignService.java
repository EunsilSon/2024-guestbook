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

    /**
     * 로그인
     * @param name 사용자 이름
     * @param pw 비밀번호
     * @return 로그인 성공 여부
     */
    public boolean signIn(String name, String pw) {
        return userRepository.existsByNameAndPassword(name, pw);
    }

    /**
     * 회원 가입
     * @param name 사용자 이름
     * @param pw 비밀번호
     * @param tel 연락처
     * @return 이름과 비밀번호 중복 값 체크 or 성공 여부
     */
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

    /**
     * ID 찾기
     * @param tel 연락처
     * @return 찾은 ID or 성공 여부
     */
    public String findId(String tel) {
        if (userRepository.existsByTelephone(tel)) {
            User user = userRepository.findUserByTelephone(tel);
            return user.getName();
        } else {
            return "fail";
        }
    }

    /**
     * PW 찾기
     * @param name 사용자 이름
     * @param tel 연락처
     * @return 찾은 PW or 성공 여부
     */
    public String findPw(String name, String tel) {
        if (userRepository.existsByNameAndTelephone(name, tel)) {
            User user = userRepository.findPasswordByNameAndTelephone(name, tel);
            return user.getPassword();
        } else {
            return "fail";
        }
    }

    /**
     * 사용자 권한 체크
     * @param username 사용자 이름
     * @return 관리자 권한 소유 여부
     */
    public boolean isAdmin(String username) {
        User user = userRepository.findUserByName(username);
        return user.isAdmin();
    }
}
