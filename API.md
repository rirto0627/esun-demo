# API 文件

## 錯誤代碼定義

| 錯誤碼 | 說明                             |
| --- | ------------------------------ |
| 404 | 找不到資源，例如使用者、商品或 LikeList 資料不存在 |
| 500 | 伺服器內部錯誤，如資料庫失敗或程序錯誤            |

---

## LikeList API

### 取得使用者收藏清單

**GET** `/like-list/{userId}`

* 參數：

    * `userId`：使用者 ID
* 成功回傳：

```json
[
  {
    "sn": 1,
    "productNo": 101,
    "productName": "債券高股息A",
    "productPrice": "3910.48",
    "productFeeRate": "0.05",
    "account": "1111999666",
    "orderNum": 3,
    "totalFee": 586.57,
    "totalAmount": 11731.44,
    "email": "test@example.com"
  }
]
```

* 錯誤：

    * 使用者 ID 不存在 → 404

### 新增收藏

**POST** `/like-list`

* Body：

```json
{
  "userId": "A1236456789",
  "productNo": 101,
  "account": "1111999666",
  "orderNum": 3
}
```

* 成功回傳：

```json
{
  "code": 200,
  "message": "新增成功"
}
```

* 錯誤：

    * Account 或商品不存在 → 404
    * DB 呼叫失敗 → 500

### 修改收藏

**PUT** `/like-list/{sn}`

* Body：

```json
{
  "productNo": 101,
  "account": "1111999666",
  "orderNum": 2
}
```

* 成功回傳：

```json
{
  "code": 200,
  "message": "更新成功"
}
```

* 錯誤：

    * SN 不存在或其他錯誤 → 404 / 500

### 刪除收藏

**DELETE** `/like-list/{sn}`

* 成功回傳：

```json
{
  "code": 200,
  "message": "刪除成功"
}
```

* 錯誤：

    * SN 不存在 → 404

---

## 商品 API

### 查詢所有商品

**GET** `/product-list`

* 成功回傳：

```json
[
  {
    "productNo": 101,
    "productName": "債券高股息A",
    "productPrice": 3910.48,
    "productFeeRate": 0.05
  }
]
```
