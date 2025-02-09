### プロジェクトの概要
Javaの応用学習の一環として、Spring Boot を使用した掲示板アプリを作成しました。
ユーザーが投稿・編集・削除できるシンプルな掲示板機能を実装しています。

#### 使用技術
- **バックエンド**：Spring Boot, Spring Security, Lombok  
- **フロントエンド**：Thymeleaf, HTML, CSS  
- **データベース**：H2 Database  
- **開発環境**：Spring DevTools  
- **セキュリティ**：CSRF対策, 認証機能  


### 実装機能
- **ログイン機能**
- **投稿・編集・削除**
- **エラーページの表示**

### 工夫したポイント（技術的にこだわった点、苦労した点など）

1. **Spring Security を使った認証機能の実装**  
   - ログインしないと書き込みや編集ができないようにし、セキュリティを強化  

2. **CSRF対策を適用**  
   - フォームに CSRF トークンを埋め込むことで、不正なリクエストを防止  

3. **エラーハンドリングの改善**  
   - ログインしていない状態で特定のページにアクセスすると `403 Forbidden` になったが、CSRFトークンの設定を適用することで解決  

4. **H2 Database を使用した手軽な動作確認**  
   - ローカル環境でデータベースを簡単にセットアップできるようにし、開発しやすくしました。
     
5. **Thymeleaf の構文エラーによる Whitelabel Error Page の対策**  
   - Thymeleaf のテンプレートで構文エラーがあると `Whitelabel Error Page` が表示され、原因が分かりにくかった  
   - `Spring Boot DevTools` を活用し、テンプレートを修正しながら即座に確認できる環境を整えた  
   - エラーログを詳しく見ることで、どの部分が原因か特定しやすくした  
