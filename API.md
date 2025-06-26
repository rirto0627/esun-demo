# 📘 喜好金融商品 API 文件

本 API 提供使用者管理喜好金融商品的功能，所有成功回應皆使用統一格式 `ApiResponse`。

---

## 📦 統一回應格式

```json
{
  "status": "success | error",
  "message": "描述訊息",
  "code": 200,
  "data": { ... } | null
}
```

---

## ❗ 錯誤狀態碼

| 狀態碼 | 說明          |
| --- | ----------- |
| 200 | 成功          |
| 400 | 請求資料缺漏或格式錯誤 |
| 404 | 找不到指定帳號或使用者 |
| 500 | 伺服器錯誤       |

---

## 📥 新增喜好商品

* **描述**：新增喜好金融商品（產品名稱、價格、手續費率、帳號、數量）
* **方法**：POST
* **URL**：`/api/like-list`
* **Request Body**：

```json
{
  "userId": "A1236456789",
  "productName": "基金ABC",
  "price": 1010.0,
  "feeRate": 0.01,
  "account": "1111999666",
  "orderNum": 3
}
```

* **成功回應**：

```json
{
  "status": "success",
  "message": "新增成功",
  "code": 200,
  "data": null
}
```

* **錯誤回應（缺參數）**：

```json
{
  "status": "error",
  "message": "請求資料缺漏或格式錯誤",
  "code": 400,
  "data": null
}
```

---

## 📄 查詢喜好金融商品清單

* **描述**：根據使用者 ID 查詢所有收藏商品資訊。

* **方法**：GET

* **URL**：`/api/like-list?userId=A1236456789`

* **成功回應**：

```json
[
  {
    "sn": 1,
    "productName": "基金ABC",
    "account": "1111999666",
    "orderNum": 3,
    "totalFee": 30.30,
    "totalAmount": 3060.30,
    "email": "test@example.com"
  }
]
```

* **錯誤回應（找不到使用者）**：

```json
{
  "status": "error",
  "message": "查無使用者",
  "code": 404,
  "data": null
}
```

---

## 🔁 修改喜好商品

* **描述**：修改收藏的金融商品資訊

* **方法**：PUT

* **URL**：`/api/like-list/{sn}`

* **Request Body**：

```json
{
  "userId": "A1236456789",
  "productName": "基金ABC",
  "price": 1100.0,
  "feeRate": 0.012,
  "account": "1111999666",
  "orderNum": 4
}
```

* **成功回應**：

```json
{
  "status": "success",
  "message": "更新成功",
  "code": 200,
  "data": null
}
```

* **錯誤回應（SN 不存在）**：

```json
{
  "status": "error",
  "message": "找不到指定帳號或使用者",
  "code": 404,
  "data": null
}
```

---

## 🗑️ 刪除喜好商品

* **描述**：刪除喜好商品

* **方法**：DELETE

* **URL**：`/api/like-list/{sn}`

* **成功回應**：

```json
{
  "status": "success",
  "message": "刪除成功",
  "code": 200,
  "data": null
}
```

* **錯誤回應（SN 不存在）**：

```json
{
  "status": "error",
  "message": "SN 不存在",
  "code": 404,
  "data": null
}
```
