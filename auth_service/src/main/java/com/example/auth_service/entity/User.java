package com.example.auth_service.entity;

import com.example.auth_service.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

//Javaクラスをデータベースのテーブルと対応づける
@Data
@Entity
@Builder
@NoArgsConstructor // ← これを追加（引数なしコンストラクタ）
@AllArgsConstructor // ← @Builderと@NoArgsConstructorを併用する際に必要
//データベーステーブル名を決定する
@Table(name="users")
public class User {

    //primarykeyの設定
    @Id
    @GeneratedValue
    private UUID id;

    @Column (nullable = false)
    private String username;

    //emailは一意
    @Column (nullable = false, unique = true)
    private String email;

    @Column (nullable = false)
    private String passwordHash;

    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column (nullable = false)
    private boolean enabled;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void create() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void update() {
        updatedAt = LocalDateTime.now();
    }
}
