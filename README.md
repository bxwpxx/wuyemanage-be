\SERVER\SRC\MAIN\JAVA\COM  
├─config  
│      DatabaseConnectionPool.java  # 管理数据库连接池的配置和生命周期，提供高效的数据库连接复用。  
│      PasswordUtil.java            # 提供密码加密、解密或验证的工具方法（如哈希加密、盐值生成等）。  
│  
├─controller  
│      AuthController.java          # 处理用户认证相关的请求（如登录、登出、权限校验），通常调用 service 层的方法。  
│  
├─dao  
│      OwnerDAO.java                # 操作与“业主”（Owner）相关的数据库表（如 CRUD 操作）。  
│      PropertyDAO.java             # 操作与“房产”（Property）相关的数据库表。  
│      UserDAO.java                 # 操作与“用户”（User）相关的数据库表（如用户信息查询、更新）。  
│  
├─domain  
│  ├─entity  
│  │      Owner.java               # 表示“业主”实体，包含业主的属性和方法（如姓名、联系方式等）。  
│  │      Property.java            # 表示“房产”实体，包含房产的属性和方法（如地址、面积等）。  
│  │      User.java                # 表示“用户”实体，包含用户的属性和方法（如用户名、密码、角色等）。  
│  │  
│  └─vo  
│          LoginRequest.java       # 封装用户登录请求的数据（如用户名、密码）。  
│          LoginResponse.java      # 封装用户登录响应的数据（如令牌、用户信息）。  
│  
└─service  
        AuthService.java           # 定义用户认证相关的业务接口（如登录、权限校验）。  
        AuthServiceImpl.java       # 实现 AuthService 接口的具体逻辑，通常调用 dao 层的方法完成业务操作。  
总结  
分层架构：项目采用典型的 MVC 分层设计：  
controller：处理请求和响应。  
service：执行业务逻辑。  
dao：操作数据库。  
domain：定义数据模型。  
配置与工具：config 包提供全局配置和工具类支持。  
扩展性：vo 包用于数据传输，便于前后端交互或微服务间通信。  
