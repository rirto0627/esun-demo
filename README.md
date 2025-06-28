# 💹 E.SUN DEMO 金融商品系統

這是一個以 Vue.js + Spring Boot 為基礎所打造的金融商品管理系統，支援商品列表瀏覽、加入喜好清單、扣款帳戶設定等功能。前端採 SPA 架構，後端提供 RESTful API，適用於現代金融應用場景。

---

## 🚀 技術棧 Technology Stack

### 🧩 前端 Frontend
- **Vue 3** - Composition API + `<script setup>`
- **TypeScript** - 強型別前端開發
- **Vite** - 快速開發工具
- **Tailwind CSS** - 實用為主的 CSS 框架
- **Lucide Icons** - icon 套件（例如：Menu、Heart、Chevron）
- **Shadcn UI** - 組件樣式系統
- **Axios** - 串接後端 API

### 🔧 後端 Backend
- **Spring Boot 3**
- **Spring MVC**
- **Spring Data JPA** - ORM 管理
- **Lombok** - 減少樣板程式碼
- **MariaDB** / **MySQL** - 資料庫支援
- **JDBC CallableStatement** - 呼叫資料庫儲存程序
- **API 回傳格式：ApiResponse<T>** - 統一封裝成功/錯誤回應格式

---

## 🧾 專案架構簡介

```aiignore
esun-demo/
├── backend/
│ ├── controller/ # REST API 控制器
│ ├── model/ # 資料模型 (e.g. ApiResponse)
│ ├── repository/ # DAO 層，使用 JDBC + CallableStatement
│ └── resources/
│ ├── application.properties
│ ├── schema.sql # DDL 建表語句
│ └── data.sql # 初始資料
└── frontend/
│ ├── pages/ # Vue 頁面 LikeList, ProductList
│ ├── components/ # 可重用元件 (Dialog, Filter, Card 等)
│ ├── router/ # Vue Router 設定
│ ├── lib/api.ts # axios 設定與封裝
│ └── main.ts # Vue app 初始化

```

---
```aiignore
## ⚙️ 環境變數設定 `.properties`

請參考 `src/main/resources/application.properties`：

```properties
spring.application.name=esun-demo
spring.profiles.active=dev
spring.web.resources.static-locations=classpath:/static/
spring.mvc.pathmatch.matching-strategy=ant_path_matcher

# === 資料庫連線設定 ===
spring.datasource.url=jdbc:mysql://localhost:3306/esun_demo?useSSL=false&serverTimezone=Asia/Taipei&characterEncoding=utf8mb4
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

# === DDL / SQL 初始化 ===
spring.sql.init.mode=always
spring.jpa.hibernate.ddl-auto=none
spring.sql.init.schema-locations=classpath:schema.sql
spring.sql.init.data-locations=classpath:data.sql

# === SQL 顯示設定 ===
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
---

## 📦 DDL

### 位置：`schema.sql,/DB/DDL.sql`

---

## ✏️DML

### 位置：`data.sql,/DB/DML.sql`
