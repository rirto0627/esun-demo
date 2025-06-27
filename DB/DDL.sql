-- 使用者資料表
CREATE TABLE User (
                      UserID VARCHAR(20) PRIMARY KEY,
                      UserName VARCHAR(50) NOT NULL,
                      Email VARCHAR(100) NOT NULL,
                      Account VARCHAR(20) NOT NULL
);

-- 產品資料表
CREATE TABLE Product (
                         No INT PRIMARY KEY AUTO_INCREMENT,
                         ProductName VARCHAR(100) NOT NULL,
                         Price DECIMAL(10, 2) NOT NULL,
                         FeeRate DECIMAL(5, 4) NOT NULL -- ex: 0.01 代表 1%
);

-- 喜好清單
CREATE TABLE LikeList (
                          SN INT PRIMARY KEY AUTO_INCREMENT,
                          OrderName INT NOT NULL, -- 購買數量
                          Account VARCHAR(20) NOT NULL,
                          FeeTotal DECIMAL(10, 2) NOT NULL,
                          TotalAmount DECIMAL(10, 2) NOT NULL,

    -- 外鍵（可選）
                          CONSTRAINT fk_account FOREIGN KEY (Account) REFERENCES User(Account)
);
