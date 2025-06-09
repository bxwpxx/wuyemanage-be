USE PropertyManagement;
CREATE TABLE UtilityBills (
    bill_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    BuildingNumber INT NOT NULL COMMENT '楼号(数字)',
    DoorNumber INT NOT NULL COMMENT '门牌号(数字)',
    bill_month DATE NOT NULL,
    water_fee DECIMAL(10, 2) NOT NULL,
    electricity_fee DECIMAL(10, 2) NOT NULL,
    total_fee DECIMAL(10, 2) GENERATED ALWAYS AS (water_fee + electricity_fee) STORED,
    is_paid BOOLEAN DEFAULT FALSE,
    paid_date DATE NULL,
    CONSTRAINT chk_payment_status CHECK (
        (is_paid = TRUE AND paid_date IS NOT NULL) OR 
        (is_paid = FALSE AND paid_date IS NULL)
    )
);

-- 插入未支付的水电费账单
INSERT INTO UtilityBills (BuildingNumber, DoorNumber, bill_month, water_fee, electricity_fee, is_paid)
VALUES
(1, 101, '2023-10-01', 85.50, 120.75, FALSE),
(1, 102, '2023-10-01', 78.25, 135.60, FALSE),
(2, 201, '2023-10-01', 92.30, 110.40, FALSE);

-- 插入已支付的水电费账单
INSERT INTO UtilityBills (BuildingNumber, DoorNumber, bill_month, water_fee, electricity_fee, is_paid, paid_date)
VALUES
(1, 101, '2023-09-01', 80.00, 115.50, TRUE, '2023-09-10'),
(2, 202, '2023-09-01', 88.75, 125.30, TRUE, '2023-09-15'),
(3, 301, '2023-09-01', 75.60, 105.80, TRUE, '2023-09-12');

-- 插入不同月份的账单数据
INSERT INTO UtilityBills (BuildingNumber, DoorNumber, bill_month, water_fee, electricity_fee, is_paid, paid_date)
VALUES
(1, 103, '2023-08-01', 82.40, 118.90, TRUE, '2023-08-11'),
(2, 203, '2023-08-01', 90.15, 130.25, TRUE, '2023-08-14');

-- 单独插入未支付的记录
INSERT INTO UtilityBills (BuildingNumber, DoorNumber, bill_month, water_fee, electricity_fee, is_paid)
VALUES
(3, 302, '2023-08-01', 77.80, 112.60, FALSE);

-- 查询所有账单数据
SELECT * FROM UtilityBills;