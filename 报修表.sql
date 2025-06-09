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
