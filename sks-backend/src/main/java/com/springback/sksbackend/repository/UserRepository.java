package com.springback.sksbackend.repository;

import com.springback.sksbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);
}
