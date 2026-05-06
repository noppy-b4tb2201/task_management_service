# Task Management Microservices

## 概要
Spring Bootを用いたタスク管理システム。
認証サービスとタスク管理サービスを分離したマイクロサービス構成。

## アーキテクチャ
- auth_service（認証）
- task_service（タスク管理）

## 技術スタック
- Java
- Spring Boot
- Spring Security
- JWT
- Oracle Database
- Docker

## 機能
- ユーザー登録・ログイン
- JWT認証
- タスクCRUD
- ユーザー単位データ分離

## 工夫した点
- マイクロサービス構成
- Stateless認証設計
- userIdベースのデータ管理
