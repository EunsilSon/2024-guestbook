package com.eunsil.guestbook.repository;

import com.eunsil.guestbook.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNameAndPassword(String name, String pw);

    boolean existsByNameAndTelephone(String name, String telephone);

    User findPasswordByNameAndTelephone(String name, String pw);

    User findUserByName(String name);

    User findUserByTelephone(String tel);

    boolean existsByName(String name);

    boolean existsByTelephone(String tel);
}
