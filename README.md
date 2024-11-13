# Java_wx
微信商城项目

## 项目结构
```
WEXIN/
├── src/
│   ├── cn_gd_gdgc/                # 基础包
│   │   └── SHA1/
│   │       ├── SHA1.java           # SHA1加密工具类
│   │       ├── Test.java           # 测试类
│   │       └── Weixin.java         # 微信相关工具类
│   │
│   ├── gd/cn/message/              # 消息处理模块
│   │   ├── PicList.java           # 图片列表处理
│   │   ├── ReceiveMessage.java    # 消息接收处理
│   │   ├── ScanCodeInfo.java      # 扫码信息处理
│   │   ├── SendLocationInfo.java  # 位置信息发送
│   │   └── SendPicInfo.java       # 图片信息发送
│   │
│   └── java222/                    # 核心业务逻辑
│       ├── addCart.java           # 添加购物车处理
│       ├── addProduct.java        # 添加商品处理
│       ├── Cart.java              # 购物车实体类
│       ├── Customer.java          # 客户实体类
│       ├── DBTools.java           # 数据库工具类
│       ├── del.java               # 删除操作处理
│       ├── getProduct.java        # 获取商品信息
│       ├── modifyCart.java        # 修改购物车
│       ├── MyListener.java        # 监听器
│       ├── MySerie12.java         # 序列化处理
│       ├── pay.java               # 支付处理
│       ├── Product.java           # 商品实体类
│       └── SessionListener.java    # 会话监听器
│
├── WebRoot/                        # Web根目录
│   ├── img/                       # 图片资源目录
│   │   └── shoppingcar.jpg        # 购物车图片
│   │
│   ├── META-INF/                  # 项目配置
│   ├── WEB-INF/                   # Web配置
│   ├── addProduct.jsp             # 添加商品页面
│   ├── cart.jsp                   # 购物车页面
│   ├── H5.html                    # H5页面
│   ├── index.jsp                  # 首页
│   ├── jquery-2.7.1.min.js        # jQuery库
│   ├── p.jsp                      # 商品页面
│   ├── pay.html                   # 支付页面
│   ├── statused.jsp              # 状态页面
│   └── Weixin.jsp                # 微信相关页面
│
├── .classpath                     # 类路径配置
├── .gitattributes                # Git属性配置
├── .project                      # 项目配置文件
├── README.md                     # 项目说明文档
└── Weixin.iml                    # IDEA项目配置文件
```

## 项目功能
# WEXIN 购物车系统 - 模块详解

## 1. 用户认证模块 (cn/gd/gdgc/)
### 登录注册系统
- `login.java`: 
  - 用户登录验证
  - Session管理
  - 登录状态维护
  
- `register.java`:
  - 新用户注册
  - 用户信息验证
  - 密码加密存储

- `SHA1.java`:
  - 密码加密
  - 数据安全处理

## 2. 购物车核心模块 (java222/)
### 购物车管理
- `Cart.java`: 购物车实体类
  - 购物车信息
  - 商品列表
  - 总价计算

- `addCart.java`:
  - 添加商品到购物车
  - 数量验证
  - 库存检查

- `modifyCart.java`:
  - 修改商品数量
  - 删除购物车商品
  - 购物车更新

### 商品管理
- `Product.java`: 商品实体类
  - 商品基本信息
  - 价格管理
  - 库存管理

- `addProduct.java`:
  - 新商品添加
  - 商品信息验证
  - 图片上传处理

- `getProduct.java`:
  - 商品信息获取
  - 商品列表展示
  - 商品搜索

## 3. 支付模块
- `pay.java`:
  - 订单生成
  - 支付接口对接
  - 支付状态处理

## 4. 消息处理模块 (gd/cn/message/)
### 消息管理
- `ReceiveMessage.java`:
  - 接收用户消息
  - 消息类型判断
  - 消息内容解析

- `SendLocationInfo.java`:
  - 位置信息处理
  - 地理位置发送
  - 位置数据格式化

- `PicList.java`:
  - 图片管理
  - 图片上传
  - 图片列表维护

## 5. 工具类模块
### 数据库工具
- `DBTools.java`:
  - 数据库连接管理
  - SQL查询执行
  - 连接池管理

### 百度API工具
- `BaiduTools.java`:
  - 百度API接口调用
  - 数据处理
  - 结果格式化

### 会话管理
- `SessionListener.java`:
  - 会话创建
  - 会话销毁
  - 会话属性管理


## 前端视图 (WebRoot/)
### 主要页面
- `index.jsp`: 首页
  - 商品展示
  - 用户导航
  - 搜索功能
cart.jsp: 购物车页面
  - 购物车商品列表
  - 数量修改
  - 结算功能

- `pay.html`: 支付页面
订单确认
支付方式选择
支付状态显示

# 数据库表结构设计

## 1. 用户表 (customer)
- `id`: 用户ID，主键
- `username`: 用户名
- `password`: 密码
- `email`: 邮箱
- `phone`: 手机号
- `address`: 地址


CREATE TABLE customer (
id INT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(64) NOT NULL, -- SHA1加密存储
email VARCHAR(100),
phone VARCHAR(20),
create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
last_login DATETIME,
status TINYINT DEFAULT 1 ,
UNIQUE KEY idx_username (username)
);

## 2. 商品表 (product)
```sql
CREATE TABLE product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    description TEXT,
    image_url VARCHAR(255),
    category_id INT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
    status TINYINT DEFAULT 1 COMMENT '0-下架 1-上架',
    INDEX `idx_category` (`category_id`)
);
```

## 3. 购物车表 (cart)
```sql
CREATE TABLE cart (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
    selected TINYINT DEFAULT 1 COMMENT '0-未选中 1-已选中',
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    UNIQUE KEY `idx_customer_product` (`customer_id`, `product_id`)
);
```

## 4. 订单表 (orders)
```sql
CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(32) NOT NULL UNIQUE,
    customer_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status TINYINT DEFAULT 0 COMMENT '0-待支付 1-已支付 2-已取消',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    pay_time DATETIME,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    INDEX `idx_order_no` (`order_no`)
);
```

## 5. 订单详情表 (order_detail)
```sql
CREATE TABLE order_detail (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);
```

## 6. 商品分类表 (category)
```sql
CREATE TABLE category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    parent_id INT DEFAULT 0,
    sort_order INT DEFAULT 0,
    status TINYINT DEFAULT 1 COMMENT '0-禁用 1-正常'
);
```

## 7. 支付记录表 (payment)
```sql
CREATE TABLE payment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    payment_no VARCHAR(64) NOT NULL,
    payment_type TINYINT COMMENT '1-微信 2-支付宝',
    amount DECIMAL(10,2) NOT NULL,
    status TINYINT DEFAULT 0 COMMENT '0-待支付 1-支付成功 2-支付失败',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    UNIQUE KEY `idx_payment_no` (`payment_no`)
);
```

## 关键字段说明

### 1. 状态字段说明
- 用户状态(customer.status)
  - 0: 禁用
  - 1: 正常

- 商品状态(product.status)
  - 0: 下架
  - 1: 上架

- 订单状态(orders.status)
  - 0: 待支付
  - 1: 已支付
  - 2: 已取消

- 支付状态(payment.status)
  - 0: 待支付
  - 1: 支付成功
  - 2: 支付失败

### 2. 索引设计
- 主键索引：所有表都使用自增ID作为主键
- 唯一索引：用户名、订单号、支付单号等
- 外键索引：保证数据完整性
- 普通索引：优化查询性能
 