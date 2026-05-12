# Task Management Microservices

## ■ 概要
本プロジェクトは、Spring Bootを用いたタスク管理システムです。  
認証機能と業務機能を分離したマイクロサービス構成で設計しています。

ユーザー認証（Auth Service）とタスク管理（Task Service）を独立させることで、  
スケーラビリティと保守性を意識した設計としています。

---

## ■ アーキテクチャ構成

- auth_service（認証サービス）
  - ユーザー登録
  - ログイン
  - JWT発行・検証

- task_service（タスク管理サービス）
  - タスクCRUD機能
  - ユーザー単位のデータ管理

---

## ■ 使用技術

- Java
- Spring Boot
- Spring Security
- JWT
- PostgreSQL（Docker環境）
- Docker

---

## ■ 主な機能

### 認証機能（Auth Service）
- ユーザー登録
- ログイン
- JWTトークン発行
- JWTトークン検証

### タスク管理機能（Task Service）
- タスク作成
- タスク取得（一覧・詳細）
- タスク更新
- タスク削除

---

## ■ 設計の工夫点

- マイクロサービス構成による責務分離
- JWTを用いたStateless認証設計
- userIdによるデータアクセス制御
- サービス間依存を持たない構成

---

## ■ DB構成

- PostgreSQLをDocker環境上で構築
- 各サービスは独立してデータ管理

---

## ■ 工夫・学習した点

- 認証と業務ロジックを分離したマイクロサービス構成
- REST APIの設計思想の理解
- Spring Securityを用いた認証処理の実装

---

## ■ 今後の改善予定

バッチ処理の実装
