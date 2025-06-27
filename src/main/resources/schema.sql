DROP TABLE IF EXISTS LikeList;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS User;

CREATE TABLE User (
                      UserID VARCHAR(20) PRIMARY KEY,
                      UserName VARCHAR(50) NOT NULL,
                      Email VARCHAR(100) UNIQUE NOT NULL,
                      Account VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE Product (
                         No INT PRIMARY KEY AUTO_INCREMENT,
                         ProductName VARCHAR(100) NOT NULL,
                         Price DECIMAL(10,2) NOT NULL,
                         FeeRate DECIMAL(5,4) NOT NULL
);

CREATE TABLE LikeList (
                          SN INT PRIMARY KEY AUTO_INCREMENT,
                          OrderName INT NOT NULL,
                          Account VARCHAR(20) NOT NULL,
                          TotalFee DECIMAL(10,2) NOT NULL,
                          TotalAmount DECIMAL(10,2) NOT NULL,
                          ProductNo INT NOT NULL,
                          CONSTRAINT fk_account FOREIGN KEY (Account) REFERENCES User(Account),
                          CONSTRAINT fk_product FOREIGN KEY (ProductNo) REFERENCES Product(No)
);
