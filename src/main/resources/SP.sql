DELIMITER $$

-- 新增喜好商品記錄
CREATE PROCEDURE AddLikeItem(
    IN in_user_id VARCHAR(20),
    IN in_product_name VARCHAR(100),
    IN in_price DECIMAL(10,2),
    IN in_fee_rate DECIMAL(5,4),
    IN in_account VARCHAR(20),
    IN in_order_num INT
)
BEGIN
    DECLARE v_product_id INT;
    DECLARE v_total_fee DECIMAL(10,2);
    DECLARE v_total_amount DECIMAL(10,2);

    -- 計算金額
    SET v_total_fee = in_price * in_fee_rate * in_order_num;
    SET v_total_amount = in_price * in_order_num + v_total_fee;

    SELECT No INTO v_product_id
    FROM Product WHERE ProductName = in_product_name
    LIMIT 1;

    IF v_product_id IS NULL THEN
        INSERT INTO Product (ProductName, Price, FeeRate)
        VALUES (in_product_name, in_price, in_fee_rate);
        SET v_product_id = LAST_INSERT_ID();
    END IF;

    INSERT INTO LikeList (OrderName, Account, ProductNo, TotalFee, TotalAmount)
    VALUES (in_order_num, in_account, v_product_id, v_total_fee, v_total_amount);
END$$


-- 查詢使用者喜好清單
CREATE PROCEDURE GetLikeListByUserId(IN in_user_id VARCHAR(20))
BEGIN
    SELECT
        ll.SN,
        p.ProductName,
        ll.Account,
        ll.OrderName,
        ll.TotalFee,
        ll.TotalAmount,
        u.Email
    FROM LikeList ll
             JOIN User u ON ll.Account = u.Account
             JOIN Product p ON p.No IN (
        SELECT No FROM Product
    )
    WHERE u.UserID = in_user_id;
END$$

-- 更新喜好商品
CREATE PROCEDURE UpdateLikeItem(
    IN in_sn INT,
    IN in_product_name VARCHAR(100),
    IN in_price DECIMAL(10,2),
    IN in_fee_rate DECIMAL(5,4),
    IN in_account VARCHAR(20),
    IN in_order_num INT
)
BEGIN
    DECLARE v_total_fee DECIMAL(10,2);
    DECLARE v_total_amount DECIMAL(10,2);

    SET v_total_fee = in_price * in_fee_rate * in_order_num;
    SET v_total_amount = in_price * in_order_num + v_total_fee;

    UPDATE LikeList
    SET Account = in_account,
        OrderName = in_order_num,
        TotalFee = v_total_fee,
        TotalAmount = v_total_amount
    WHERE SN = in_sn;
END$$

-- 刪除喜好商品
CREATE PROCEDURE DeleteLikeItem(
    IN in_sn INT,
    OUT affected_rows INT
)
BEGIN
    DELETE FROM LikeList WHERE SN = in_sn;
    SET affected_rows = ROW_COUNT();
END;


DELIMITER ;
