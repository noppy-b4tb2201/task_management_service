package com.example.auth_service.repository;

import com.example.auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

//DB検索
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    //ログイン処理の際、emailでユーザーを検索する
    Optional<User> findByEmail(String email);
    //ユーザー検索の際、emailがすでに存在している値か調べる
    boolean existsByEmail(String email);
}