DELIMITER $$

-- 新增喜好商品記錄
CREATE PROCEDURE AddLikeItem(
    IN in_user_id VARCHAR(20),
    IN in_product_no INT,
    IN in_account VARCHAR(20),
    IN in_order_num INT
)
BEGIN
    DECLARE v_price DECIMAL(10,2);
    DECLARE v_fee_rate DECIMAL(5,4);
    DECLARE v_total_fee DECIMAL(10,2);
    DECLARE v_total_amount DECIMAL(10,2);
    DECLARE v_existing_sn INT;
    DECLARE v_new_order_num INT;

    -- 檢查商品是否存在並取得價格與費率
    SELECT Price, FeeRate INTO v_price, v_fee_rate
    FROM Product WHERE No = in_product_no;

    IF v_price IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '找不到商品';
    END IF;

    -- 檢查是否已存在相同使用者帳戶 + 商品的 LikeList
    SELECT SN, OrderName INTO v_existing_sn, v_new_order_num
    FROM LikeList
    WHERE Account = in_account AND ProductNo = in_product_no
    LIMIT 1;

    IF v_existing_sn IS NOT NULL THEN
        -- 如果已有紀錄，更新數量與金額
        SET v_new_order_num = v_new_order_num + in_order_num;
        SET v_total_fee = v_price * v_fee_rate * v_new_order_num;
        SET v_total_amount = v_price * v_new_order_num + v_total_fee;

        UPDATE LikeList
        SET OrderName = v_new_order_num,
            TotalFee = v_total_fee,
            TotalAmount = v_total_amount
        WHERE SN = v_existing_sn;
    ELSE
        -- 沒有紀錄就新增一筆
        SET v_total_fee = v_price * v_fee_rate * in_order_num;
        SET v_total_amount = v_price * in_order_num + v_total_fee;

        INSERT INTO LikeList (OrderName, Account, TotalFee, TotalAmount, ProductNo)
        VALUES (in_order_num, in_account, v_total_fee, v_total_amount, in_product_no);
    END IF;
END$$

-- 查詢使用者喜好清單
create
    definer = `esun-demo`@`%` procedure GetLikeListByUserId(IN in_user_id varchar(20))
BEGIN
    DECLARE v_account VARCHAR(20);

    -- 取得對應帳號（Account）
    SELECT Account INTO v_account
    FROM User
    WHERE UserID = in_user_id;

    -- 若無對應帳號，則結束
    IF v_account IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '無此使用者';
    END IF;

    -- 查詢 LikeList + 商品資訊（加入 p.No）
    SELECT
        ll.SN,
        p.No AS ProductNo,
        p.Price AS ProductPrice,
        p.FeeRate AS ProductFeeRate,
        p.ProductName,
        ll.Account,
        ll.OrderName,
        ll.TotalFee,
        ll.TotalAmount,
        u.Email
    FROM LikeList ll
             JOIN Product p ON ll.ProductNo = p.No
             JOIN User u ON u.Account = ll.Account
    WHERE ll.Account = v_account;
END$$

-- 更新喜好商品
CREATE PROCEDURE UpdateLikeItem(
    IN in_sn INT,
    IN in_product_no INT,
    IN in_account VARCHAR(20),
    IN in_order_num INT
)
BEGIN
    DECLARE v_price DECIMAL(10,2);
    DECLARE v_fee_rate DECIMAL(5,4);
    DECLARE v_total_fee DECIMAL(10,2);
    DECLARE v_total_amount DECIMAL(10,2);

    -- 查出該商品的價格與手續費率
    SELECT Price, FeeRate INTO v_price, v_fee_rate
    FROM Product
    WHERE No = in_product_no;

    -- 若查無該商品，拋出錯誤
    IF v_price IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '找不到該商品';
    END IF;

    -- 計算金額
    SET v_total_fee = v_price * v_fee_rate * in_order_num;
    SET v_total_amount = v_price * in_order_num + v_total_fee;

    -- 更新喜好清單
    UPDATE LikeList
    SET Account = in_account,
        OrderName = in_order_num,
        ProductNo = in_product_no,
        TotalFee = v_total_fee,
        TotalAmount = v_total_amount
    WHERE SN = in_sn;
END;


-- 刪除喜好商品
CREATE PROCEDURE DeleteLikeItem(
    IN in_sn INT,
    OUT affected_rows INT
)
BEGIN
    DELETE FROM LikeList WHERE SN = in_sn;
    SET affected_rows = ROW_COUNT();
END;
-- 取得所有金融商品
CREATE PROCEDURE GetAllProducts()
BEGIN
    SELECT
        No AS ProductNo,
        ProductName,
        Price AS ProductPrice,
        FeeRate AS ProductFeeRate
    FROM Product
    ORDER BY No DESC;
END $$


DELIMITER ;
