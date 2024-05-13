package com.eunsil.guestbook.repository;

import com.eunsil.guestbook.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUserByNameAndPassword(String name, String pw);

    User findUserByTelephone(String tel);

    User findPasswordByNameAndTelephone(String name, String pw);

    User findUserByName(String name);
}
