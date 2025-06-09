USE PropertyManagement;
CREATE TABLE repair (
    id INT PRIMARY KEY AUTO_INCREMENT,
    description TEXT NOT NULL,
    image_path VARCHAR(255),
    location_type ENUM('indoor', 'public_area') NOT NULL,
    specific_location VARCHAR(255) NOT NULL,
    creator_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'processed', 'rated') NOT NULL DEFAULT 'pending',
    handler_id INT,
    handled_at DATETIME,
    rating TINYINT CHECK (rating BETWEEN 1 AND 5),
    rated_at DATETIME,
    
    -- 索引
    INDEX idx_status (is_processed),
    INDEX idx_creator (creator_id),
    INDEX idx_handler (handler_id)
);
USE PropertyManagement;

INSERT INTO repair (
    description, 
    image_path, 
    location_type, 
    specific_location, 
    creator_id, 
    created_at, 
    status, 
    handler_id, 
    handled_at, 
    rating, 
    rated_at
) VALUES
('卧室灯不亮了', NULL, 'indoor', '3-502', 1001, '2023-05-10 09:15:00', 'rated', 1, '2025-06-07 15:20:12', 5, '2025-06-07 15:29:49'),
('儿童游乐区秋千损坏', '/images/repairs/swing_damage.jpg', 'public_area', '小区中央花园东侧', 1002, '2023-05-11 14:30:00', 'pending', NULL, NULL, NULL, NULL),
('厨房下水道堵塞', NULL, 'indoor', '8-306', 1003, '2023-05-09 16:45:00', 'pending', 2001, '2023-05-10 10:30:00', NULL, NULL),
('空调不制冷', NULL, 'indoor', '12-1503', 1004, '2023-05-08 13:20:00', 'pending', 2002, '2023-05-09 09:15:00', 5, '2023-05-09 18:30:00'),
('小区路灯不亮', '/images/repairs/street_light.jpg', 'public_area', '小区南门主干道', 1005, '2023-05-12 19:05:00', 'pending', 2003, '2023-05-13 10:00:00', 3, '2023-05-13 20:15:00'),
('电梯按钮失灵', NULL, 'indoor', '5-电梯间', 1006, '2023-05-14 08:30:00', 'pending', 2004, '2023-05-15 16:45:00', 2, '2023-05-15 19:20:00'),
('厨房水龙头漏水', '/images/leaking_faucet.jpg', 'indoor', '5-101', 1, '2025-06-07 18:21:50', 'pending', NULL, NULL, NULL, NULL),
('厨房水龙头漏水', '/images/leaking_faucet.jpg', 'indoor', '5-102', 1, '2025-06-07 21:17:46', 'pending', NULL, NULL, NULL, NULL);  
SELECT * FROM repair;
