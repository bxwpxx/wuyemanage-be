CREATE TABLE announcement (
    announcement_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '公告标题',
    content TEXT NOT NULL COMMENT '公告内容，可包含文字和图片链接或HTML',
    uploader_id INT NOT NULL COMMENT '上传人ID（关联物业表）',
    upload_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    INDEX idx_upload_time (upload_time) COMMENT '上传时间索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统公告表';
-- 插入测试数据
INSERT INTO announcement (title, content, uploader_id) VALUES
('系统维护通知', '本周六凌晨2:00-4:00进行系统升级，届时将暂停服务', 1),
('节假日安排', '春节假期期间客服服务时间调整为9:00-18:00', 1),
('新功能上线', '本次更新增加了文件上传功能，欢迎体验反馈', 1),
('安全提醒', '请及时修改默认密码，确保账户安全', 1),
('社区活动', '下周将举办开发者交流会，欢迎报名参加', 1);

-- 查询验证
SELECT * FROM announcement ORDER BY upload_time DESC;