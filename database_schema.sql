-- Green Ledger Database Schema
-- Subsidy Management System for Rural Afforestation

CREATE DATABASE IF NOT EXISTS greenledger;
USE greenledger;

-- Users Table (for authentication)
CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username)
);

-- Farmer Table
CREATE TABLE IF NOT EXISTS Farmer (
    farmer_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    village VARCHAR(100) NOT NULL,
    aadhaar_no VARCHAR(12) NOT NULL UNIQUE,
    phone VARCHAR(10) NOT NULL,
    email VARCHAR(100),
    land_area_hectares DECIMAL(10, 2),
    bank_account VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_village (village),
    INDEX idx_phone (phone)
);

-- Tree Table
CREATE TABLE IF NOT EXISTS Tree (
    tree_id INT AUTO_INCREMENT PRIMARY KEY,
    farmer_id INT NOT NULL,
    tree_type VARCHAR(50) NOT NULL,
    plant_date DATE NOT NULL,
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (farmer_id) REFERENCES Farmer(farmer_id) ON DELETE CASCADE,
    INDEX idx_farmer_date (farmer_id, plant_date),
    INDEX idx_plant_date (plant_date)
);

-- Subsidy Table
CREATE TABLE IF NOT EXISTS Subsidy (
    subsidy_id INT AUTO_INCREMENT PRIMARY KEY,
    farmer_id INT NOT NULL,
    tree_count INT NOT NULL,
    eligible_amount DECIMAL(15, 2) NOT NULL,
    disbursed_amount DECIMAL(15, 2) DEFAULT 0,
    status VARCHAR(20) DEFAULT 'PENDING',
    approval_date DATE,
    disbursement_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (farmer_id) REFERENCES Farmer(farmer_id) ON DELETE CASCADE,
    INDEX idx_status (status)
);

-- Audit Log Table
CREATE TABLE IF NOT EXISTS AuditLog (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    action VARCHAR(100) NOT NULL,
    table_name VARCHAR(50),
    record_id INT,
    old_value LONGTEXT,
    new_value LONGTEXT,
    action_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_action_timestamp (action_timestamp),
    INDEX idx_table_record (table_name, record_id)
);

-- Sample Data Insertion
-- Insert default admin user
INSERT INTO Users (username, password, full_name, role) VALUES 
('admin', 'admin123', 'System Administrator', 'ADMIN'),
('officer1', 'officer123', 'Rajesh Kumar', 'OFFICER'),
('staff1', 'staff123', 'Priya Singh', 'STAFF');

-- Insert Sample Farmers (Demo Data - 20 farmers from different villages)
INSERT INTO Farmer (farmer_id, name, village, aadhaar_no, phone, email, land_area_hectares, bank_account) VALUES
(101, 'Rajesh Kumar Singh', 'Panchpur', '123456789012', '9876543210', 'rajesh@example.com', 2.5, '1234567890'),
(102, 'Priya Sharma', 'Nandpur', '123456789013', '9876543211', 'priya@example.com', 3.0, '1234567891'),
(103, 'Mohan Lal Yadav', 'Panchpur', '123456789014', '9876543212', 'mohan@example.com', 1.5, '1234567892'),
(104, 'Sunita Devi', 'Greenville', '123456789015', '9876543213', 'sunita@example.com', 4.0, '1234567893'),
(105, 'Vikram Singh', 'Nandpur', '123456789016', '9876543214', 'vikram@example.com', 2.0, '1234567894'),
(106, 'Anjali Patel', 'Panchpur', '123456789017', '9876543215', 'anjali@example.com', 3.5, '1234567895'),
(107, 'Rohit Kumar', 'Woodland', '123456789018', '9876543216', 'rohit@example.com', 2.8, '1234567896'),
(108, 'Meera Singh', 'Greenville', '123456789019', '9876543217', 'meera@example.com', 1.8, '1234567897'),
(109, 'Deepak Verma', 'Nandpur', '123456789020', '9876543218', 'deepak@example.com', 3.2, '1234567898'),
(110, 'Seema Gupta', 'Panchpur', '123456789021', '9876543219', 'seema@example.com', 2.2, '1234567899'),
(111, 'Arun Mehta', 'Forestville', '123456789022', '9876543220', 'arun@example.com', 4.5, '1234567900'),
(112, 'Kavya Nair', 'Greenville', '123456789023', '9876543221', 'kavya@example.com', 2.0, '1234567901'),
(113, 'Suresh Rao', 'Woodland', '123456789024', '9876543222', 'suresh@example.com', 3.8, '1234567902'),
(114, 'Leela Bhat', 'Nandpur', '123456789025', '9876543223', 'leela@example.com', 2.5, '1234567903'),
(115, 'Naveen Joshi', 'Panchpur', '123456789026', '9876543224', 'naveen@example.com', 1.9, '1234567904'),
(116, 'Ravi Kumar', 'Greenville', '123456789027', '9876543225', 'ravi@example.com', 3.3, '1234567905'),
(117, 'Pooja Malhotra', 'Forestville', '123456789028', '9876543226', 'pooja@example.com', 2.7, '1234567906'),
(118, 'Arjun Singh', 'Woodland', '123456789029', '9876543227', 'arjun@example.com', 4.1, '1234567907'),
(119, 'Divya Nath', 'Panchpur', '123456789030', '9876543228', 'divya@example.com', 2.3, '1234567908'),
(120, 'Karan Desai', 'Nandpur', '123456789031', '9876543229', 'karan@example.com', 3.6, '1234567909');

-- Insert Sample Trees (Multiple trees per farmer to demonstrate the system)
INSERT INTO Tree (farmer_id, tree_type, plant_date, latitude, longitude, status) VALUES
-- Rajesh Kumar Singh (101) - 5 trees
(101, 'Neem', '2024-01-15', 25.3245, 82.9876, 'ACTIVE'),
(101, 'Mango', '2024-02-10', 25.3246, 82.9877, 'ACTIVE'),
(101, 'Sal', '2024-03-05', 25.3247, 82.9878, 'ACTIVE'),
(101, 'Teak', '2024-04-12', 25.3248, 82.9879, 'ACTIVE'),
(101, 'Eucalyptus', '2024-05-18', 25.3249, 82.9880, 'ACTIVE'),

-- Priya Sharma (102) - 6 trees
(102, 'Ashoka', '2024-01-20', 25.4145, 82.8765, 'ACTIVE'),
(102, 'Peepul', '2024-02-15', 25.4146, 82.8766, 'ACTIVE'),
(102, 'Guava', '2024-03-10', 25.4147, 82.8767, 'ACTIVE'),
(102, 'Neem', '2024-04-08', 25.4148, 82.8768, 'ACTIVE'),
(102, 'Ber', '2024-05-22', 25.4149, 82.8769, 'ACTIVE'),
(102, 'Tamarind', '2024-06-14', 25.4150, 82.8770, 'ACTIVE'),

-- Mohan Lal Yadav (103) - 3 trees
(103, 'Mango', '2024-02-01', 25.3101, 82.9901, 'ACTIVE'),
(103, 'Jamun', '2024-03-20', 25.3102, 82.9902, 'ACTIVE'),
(103, 'Neem', '2024-05-10', 25.3103, 82.9903, 'ACTIVE'),

-- Sunita Devi (104) - 7 trees
(104, 'Sheesham', '2024-01-10', 25.5222, 82.7654, 'ACTIVE'),
(104, 'Sal', '2024-02-18', 25.5223, 82.7655, 'ACTIVE'),
(104, 'Teak', '2024-03-15', 25.5224, 82.7656, 'ACTIVE'),
(104, 'Neem', '2024-04-05', 25.5225, 82.7657, 'ACTIVE'),
(104, 'Eucalyptus', '2024-05-12', 25.5226, 82.7658, 'ACTIVE'),
(104, 'Ber', '2024-06-01', 25.5227, 82.7659, 'ACTIVE'),
(104, 'Jamun', '2024-06-20', 25.5228, 82.7660, 'ACTIVE'),

-- Vikram Singh (105) - 4 trees
(105, 'Mango', '2024-01-25', 25.4333, 82.8543, 'ACTIVE'),
(105, 'Guava', '2024-02-28', 25.4334, 82.8544, 'ACTIVE'),
(105, 'Neem', '2024-04-15', 25.4335, 82.8545, 'ACTIVE'),
(105, 'Ashoka', '2024-05-30', 25.4336, 82.8546, 'ACTIVE'),

-- Anjali Patel (106) - 5 trees
(106, 'Sal', '2024-01-30', 25.3334, 82.9832, 'ACTIVE'),
(106, 'Teak', '2024-03-12', 25.3335, 82.9833, 'ACTIVE'),
(106, 'Sheesham', '2024-04-18', 25.3336, 82.9834, 'ACTIVE'),
(106, 'Neem', '2024-05-25', 25.3337, 82.9835, 'ACTIVE'),
(106, 'Mango', '2024-06-10', 25.3338, 82.9836, 'ACTIVE'),

-- Rohit Kumar (107) - 6 trees
(107, 'Eucalyptus', '2024-01-05', 25.6445, 82.6432, 'ACTIVE'),
(107, 'Neem', '2024-02-12', 25.6446, 82.6433, 'ACTIVE'),
(107, 'Mango', '2024-03-25', 25.6447, 82.6434, 'ACTIVE'),
(107, 'Jamun', '2024-04-30', 25.6448, 82.6435, 'ACTIVE'),
(107, 'Ber', '2024-05-15', 25.6449, 82.6436, 'ACTIVE'),
(107, 'Guava', '2024-06-18', 25.6450, 82.6437, 'ACTIVE'),

-- Meera Singh (108) - 4 trees
(108, 'Peepul', '2024-01-22', 25.5556, 82.7321, 'ACTIVE'),
(108, 'Ashoka', '2024-03-08', 25.5557, 82.7322, 'ACTIVE'),
(108, 'Neem', '2024-04-22', 25.5558, 82.7323, 'ACTIVE'),
(108, 'Mango', '2024-05-28', 25.5559, 82.7324, 'ACTIVE'),

-- Deepak Verma (109) - 5 trees
(109, 'Teak', '2024-02-05', 25.4667, 82.8210, 'ACTIVE'),
(109, 'Sal', '2024-03-18', 25.4668, 82.8211, 'ACTIVE'),
(109, 'Neem', '2024-04-25', 25.4669, 82.8212, 'ACTIVE'),
(109, 'Eucalyptus', '2024-05-20', 25.4670, 82.8213, 'ACTIVE'),
(109, 'Sheesham', '2024-06-15', 25.4671, 82.8214, 'ACTIVE'),

-- Seema Gupta (110) - 3 trees
(110, 'Mango', '2024-01-18', 25.3778, 82.9765, 'ACTIVE'),
(110, 'Guava', '2024-03-12', 25.3779, 82.9766, 'ACTIVE'),
(110, 'Neem', '2024-05-05', 25.3780, 82.9767, 'ACTIVE'),

-- Arun Mehta (111) - 7 trees
(111, 'Sheesham', '2024-01-08', 25.6889, 82.5210, 'ACTIVE'),
(111, 'Teak', '2024-02-22', 25.6890, 82.5211, 'ACTIVE'),
(111, 'Sal', '2024-03-28', 25.6891, 82.5212, 'ACTIVE'),
(111, 'Neem', '2024-04-12', 25.6892, 82.5213, 'ACTIVE'),
(111, 'Eucalyptus', '2024-05-10', 25.6893, 82.5214, 'ACTIVE'),
(111, 'Mango', '2024-06-05', 25.6894, 82.5215, 'ACTIVE'),
(111, 'Ber', '2024-06-25', 25.6895, 82.5216, 'ACTIVE'),

-- Kavya Nair (112) - 4 trees
(112, 'Neem', '2024-01-28', 25.5100, 82.7098, 'ACTIVE'),
(112, 'Ashoka', '2024-03-05', 25.5101, 82.7099, 'ACTIVE'),
(112, 'Guava', '2024-04-20', 25.5102, 82.7100, 'ACTIVE'),
(112, 'Jamun', '2024-06-08', 25.5103, 82.7101, 'ACTIVE'),

-- Suresh Rao (113) - 6 trees
(113, 'Mango', '2024-01-12', 25.6111, 82.6087, 'ACTIVE'),
(113, 'Teak', '2024-02-20', 25.6112, 82.6088, 'ACTIVE'),
(113, 'Sal', '2024-03-30', 25.6113, 82.6089, 'ACTIVE'),
(113, 'Neem', '2024-04-18', 25.6114, 82.6090, 'ACTIVE'),
(113, 'Eucalyptus', '2024-05-25', 25.6115, 82.6091, 'ACTIVE'),
(113, 'Sheesham', '2024-06-12', 25.6116, 82.6092, 'ACTIVE'),

-- Leela Bhat (114) - 5 trees
(114, 'Peepul', '2024-01-35', 25.4222, 82.8976, 'ACTIVE'),
(114, 'Neem', '2024-02-28', 25.4223, 82.8977, 'ACTIVE'),
(114, 'Mango', '2024-04-10', 25.4224, 82.8978, 'ACTIVE'),
(114, 'Ber', '2024-05-18', 25.4225, 82.8979, 'ACTIVE'),
(114, 'Guava', '2024-06-22', 25.4226, 82.8980, 'ACTIVE'),

-- Naveen Joshi (115) - 3 trees
(115, 'Neem', '2024-02-08', 25.3333, 82.9854, 'ACTIVE'),
(115, 'Mango', '2024-03-22', 25.3334, 82.9855, 'ACTIVE'),
(115, 'Ashoka', '2024-05-15', 25.3335, 82.9856, 'ACTIVE'),

-- Ravi Kumar (116) - 5 trees
(116, 'Sal', '2024-01-18', 25.5211, 82.7176, 'ACTIVE'),
(116, 'Teak', '2024-02-25', 25.5212, 82.7177, 'ACTIVE'),
(116, 'Neem', '2024-04-08', 25.5213, 82.7178, 'ACTIVE'),
(116, 'Eucalyptus', '2024-05-20', 25.5214, 82.7179, 'ACTIVE'),
(116, 'Sheesham', '2024-06-18', 25.5215, 82.7180, 'ACTIVE'),

-- Pooja Malhotra (117) - 6 trees
(117, 'Mango', '2024-01-20', 25.6322, 82.5265, 'ACTIVE'),
(117, 'Guava', '2024-02-28', 25.6323, 82.5266, 'ACTIVE'),
(117, 'Neem', '2024-03-15', 25.6324, 82.5267, 'ACTIVE'),
(117, 'Jamun', '2024-04-25', 25.6325, 82.5268, 'ACTIVE'),
(117, 'Ber', '2024-05-30', 25.6326, 82.5269, 'ACTIVE'),
(117, 'Peepul', '2024-06-20', 25.6327, 82.5270, 'ACTIVE'),

-- Arjun Singh (118) - 7 trees
(118, 'Teak', '2024-01-10', 25.6433, 82.6154, 'ACTIVE'),
(118, 'Sal', '2024-02-18', 25.6434, 82.6155, 'ACTIVE'),
(118, 'Neem', '2024-03-28', 25.6435, 82.6156, 'ACTIVE'),
(118, 'Sheesham', '2024-04-20', 25.6436, 82.6157, 'ACTIVE'),
(118, 'Eucalyptus', '2024-05-15', 25.6437, 82.6158, 'ACTIVE'),
(118, 'Mango', '2024-06-10', 25.6438, 82.6159, 'ACTIVE'),
(118, 'Ashoka', '2024-06-28', 25.6439, 82.6160, 'ACTIVE'),

-- Divya Nath (119) - 4 trees
(119, 'Neem', '2024-01-25', 25.3444, 82.9743, 'ACTIVE'),
(119, 'Guava', '2024-03-10', 25.3445, 82.9744, 'ACTIVE'),
(119, 'Mango', '2024-04-30', 25.3446, 82.9745, 'ACTIVE'),
(119, 'Ber', '2024-06-12', 25.3447, 82.9746, 'ACTIVE'),

-- Karan Desai (120) - 5 trees
(120, 'Peepul', '2024-02-02', 25.4555, 82.8865, 'ACTIVE'),
(120, 'Sal', '2024-03-15', 25.4556, 82.8866, 'ACTIVE'),
(120, 'Teak', '2024-04-22', 25.4557, 82.8867, 'ACTIVE'),
(120, 'Neem', '2024-05-18', 25.4558, 82.8868, 'ACTIVE'),
(120, 'Eucalyptus', '2024-06-25', 25.4559, 82.8869, 'ACTIVE');

-- Insert Subsidy Records
INSERT INTO Subsidy (farmer_id, tree_count, eligible_amount, disbursed_amount, status, approval_date) VALUES
(101, 5, 50000, 50000, 'DISBURSED', '2024-06-15'),
(102, 6, 60000, 30000, 'PARTIALLY_DISBURSED', '2024-06-10'),
(103, 3, 30000, 0, 'PENDING', NULL),
(104, 7, 70000, 70000, 'DISBURSED', '2024-06-20'),
(105, 4, 40000, 0, 'APPROVED', '2024-06-18'),
(106, 5, 50000, 50000, 'DISBURSED', '2024-06-15'),
(107, 6, 60000, 60000, 'DISBURSED', '2024-06-12'),
(108, 4, 40000, 20000, 'PARTIALLY_DISBURSED', '2024-06-14'),
(109, 5, 50000, 0, 'PENDING', NULL),
(110, 3, 30000, 30000, 'DISBURSED', '2024-06-22'),
(111, 7, 70000, 70000, 'DISBURSED', '2024-06-08'),
(112, 4, 40000, 0, 'APPROVED', '2024-06-19'),
(113, 6, 60000, 60000, 'DISBURSED', '2024-06-11'),
(114, 5, 50000, 25000, 'PARTIALLY_DISBURSED', '2024-06-16'),
(115, 3, 30000, 0, 'PENDING', NULL),
(116, 5, 50000, 50000, 'DISBURSED', '2024-06-17'),
(117, 6, 60000, 60000, 'DISBURSED', '2024-06-13'),
(118, 7, 70000, 35000, 'PARTIALLY_DISBURSED', '2024-06-09'),
(119, 4, 40000, 0, 'APPROVED', '2024-06-21'),
(120, 5, 50000, 50000, 'DISBURSED', '2024-06-14');

-- Insert Audit Log Entries (Sample)
INSERT INTO AuditLog (user_id, action, table_name, record_id, old_value, new_value) VALUES
(2, 'INSERT', 'Farmer', 101, NULL, 'Rajesh Kumar Singh - Panchpur'),
(2, 'INSERT', 'Farmer', 102, NULL, 'Priya Sharma - Nandpur'),
(2, 'INSERT', 'Tree', 1, NULL, 'Farmer 101 - Neem - 2024-01-15'),
(3, 'UPDATE', 'Subsidy', 101, 'PENDING', 'DISBURSED'),
(2, 'INSERT', 'Tree', 2, NULL, 'Farmer 101 - Mango - 2024-02-10');

-- Fraud Detection Table
CREATE TABLE IF NOT EXISTS FraudDetection (
    fraud_id INT AUTO_INCREMENT PRIMARY KEY,
    farmer_id INT NOT NULL,
    tree_id INT,
    fraud_type VARCHAR(50),
    description VARCHAR(255),
    severity VARCHAR(20),
    status VARCHAR(20) DEFAULT 'FLAGGED',
    flagged_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP NULL,
    FOREIGN KEY (farmer_id) REFERENCES Farmer(farmer_id) ON DELETE CASCADE,
    INDEX idx_farmer_fraud (farmer_id),
    INDEX idx_severity (severity),
    INDEX idx_status (status)
);

-- Create Indexes for better performance
CREATE INDEX idx_farmer_id ON Tree(farmer_id);
CREATE INDEX idx_plant_date ON Tree(plant_date);
CREATE INDEX idx_subsidy_status ON Subsidy(status);
CREATE INDEX idx_audit_timestamp ON AuditLog(action_timestamp);

-- ========== DATABASE TRIGGERS ==========

-- TRIGGER 1: Audit log for Tree INSERT
DELIMITER //
CREATE TRIGGER tree_insert_audit AFTER INSERT ON Tree
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (user_id, action, table_name, record_id, new_value, action_timestamp)
    VALUES (IFNULL((SELECT user_id FROM Users LIMIT 1), 1), 
            'INSERT',
            'Tree',
            NEW.tree_id,
            CONCAT('Farmer:', NEW.farmer_id, ' Type:', NEW.tree_type, ' Date:', NEW.plant_date),
            NOW());
END //
DELIMITER ;

-- TRIGGER 2: Audit log for Tree UPDATE
DELIMITER //
CREATE TRIGGER tree_update_audit AFTER UPDATE ON Tree
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (user_id, action, table_name, record_id, old_value, new_value, action_timestamp)
    VALUES (IFNULL((SELECT user_id FROM Users LIMIT 1), 1),
            'UPDATE',
            'Tree',
            NEW.tree_id,
            CONCAT('Status:', OLD.status),
            CONCAT('Status:', NEW.status),
            NOW());
END //
DELIMITER ;

-- TRIGGER 3: Audit log for Tree DELETE
DELIMITER //
CREATE TRIGGER tree_delete_audit AFTER DELETE ON Tree
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (user_id, action, table_name, record_id, old_value, action_timestamp)
    VALUES (IFNULL((SELECT user_id FROM Users LIMIT 1), 1),
            'DELETE',
            'Tree',
            OLD.tree_id,
            CONCAT('Farmer:', OLD.farmer_id, ' Type:', OLD.tree_type),
            NOW());
END //
DELIMITER ;

-- TRIGGER 4: Audit log for Farmer INSERT
DELIMITER //
CREATE TRIGGER farmer_insert_audit AFTER INSERT ON Farmer
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (user_id, action, table_name, record_id, new_value, action_timestamp)
    VALUES (IFNULL((SELECT user_id FROM Users LIMIT 1), 1),
            'INSERT',
            'Farmer',
            NEW.farmer_id,
            CONCAT('Name:', NEW.name, ' Village:', NEW.village),
            NOW());
END //
DELIMITER ;

-- TRIGGER 5: Audit log for Farmer UPDATE
DELIMITER //
CREATE TRIGGER farmer_update_audit AFTER UPDATE ON Farmer
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (user_id, action, table_name, record_id, old_value, new_value, action_timestamp)
    VALUES (IFNULL((SELECT user_id FROM Users LIMIT 1), 1),
            'UPDATE',
            'Farmer',
            NEW.farmer_id,
            CONCAT('Name:', OLD.name),
            CONCAT('Name:', NEW.name),
            NOW());
END //
DELIMITER ;

-- TRIGGER 6: Audit log for Subsidy INSERT
DELIMITER //
CREATE TRIGGER subsidy_insert_audit AFTER INSERT ON Subsidy
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (user_id, action, table_name, record_id, new_value, action_timestamp)
    VALUES (IFNULL((SELECT user_id FROM Users LIMIT 1), 1),
            'INSERT',
            'Subsidy',
            NEW.subsidy_id,
            CONCAT('Farmer:', NEW.farmer_id, ' Amount:', NEW.eligible_amount, ' Status:', NEW.status),
            NOW());
END //
DELIMITER ;

-- TRIGGER 7: Fraud Detection - Duplicate tree on same date
DELIMITER //
CREATE TRIGGER detect_duplicate_trees AFTER INSERT ON Tree
FOR EACH ROW
BEGIN
    DECLARE duplicate_count INT;
    
    SELECT COUNT(*) INTO duplicate_count 
    FROM Tree 
    WHERE farmer_id = NEW.farmer_id 
    AND plant_date = NEW.plant_date 
    AND tree_id != NEW.tree_id;
    
    IF duplicate_count > 0 THEN
        INSERT INTO FraudDetection (farmer_id, tree_id, fraud_type, description, severity, status)
        VALUES (NEW.farmer_id, NEW.tree_id, 'DUPLICATE_PLANTING', 
                CONCAT('Farmer planted multiple trees on same date: ', NEW.plant_date),
                'MEDIUM', 'FLAGGED');
    END IF;
END //
DELIMITER ;

-- TRIGGER 8: Fraud Detection - Excessive trees in short time
DELIMITER //
CREATE TRIGGER detect_excessive_planting AFTER INSERT ON Tree
FOR EACH ROW
BEGIN
    DECLARE recent_count INT;
    
    SELECT COUNT(*) INTO recent_count 
    FROM Tree 
    WHERE farmer_id = NEW.farmer_id 
    AND plant_date >= DATE_SUB(NEW.plant_date, INTERVAL 7 DAY);
    
    IF recent_count > 8 THEN
        INSERT INTO FraudDetection (farmer_id, tree_id, fraud_type, description, severity, status)
        VALUES (NEW.farmer_id, NEW.tree_id, 'EXCESSIVE_PLANTING', 
                'Farmer planted excessive trees in short time period',
                'HIGH', 'FLAGGED');
    END IF;
END //
DELIMITER ;

-- TRIGGER 9: Fraud Detection - Invalid coordinates
DELIMITER //
CREATE TRIGGER detect_invalid_coordinates AFTER INSERT ON Tree
FOR EACH ROW
BEGIN
    IF (NEW.latitude < -90 OR NEW.latitude > 90 OR NEW.longitude < -180 OR NEW.longitude > 180) THEN
        INSERT INTO FraudDetection (farmer_id, tree_id, fraud_type, description, severity, status)
        VALUES (NEW.farmer_id, NEW.tree_id, 'INVALID_COORDINATES', 
                CONCAT('Invalid GPS coordinates: Lat:', NEW.latitude, ' Long:', NEW.longitude),
                'HIGH', 'FLAGGED');
    END IF;
END //
DELIMITER ;

-- TRIGGER 10: Update subsidy tree count when tree is added
DELIMITER //
CREATE TRIGGER update_subsidy_on_tree_insert AFTER INSERT ON Tree
FOR EACH ROW
BEGIN
    UPDATE Subsidy 
    SET tree_count = (SELECT COUNT(*) FROM Tree WHERE farmer_id = NEW.farmer_id)
    WHERE farmer_id = NEW.farmer_id;
    
    IF NOT EXISTS (SELECT 1 FROM Subsidy WHERE farmer_id = NEW.farmer_id) THEN
        INSERT INTO Subsidy (farmer_id, tree_count, eligible_amount, status)
        VALUES (NEW.farmer_id, 1, 10000, 'PENDING');
    END IF;
END //
DELIMITER ;

-- TRIGGER 11: Update subsidy tree count when tree is deleted
DELIMITER //
CREATE TRIGGER update_subsidy_on_tree_delete AFTER DELETE ON Tree
FOR EACH ROW
BEGIN
    UPDATE Subsidy 
    SET tree_count = (SELECT COUNT(*) FROM Tree WHERE farmer_id = OLD.farmer_id),
        eligible_amount = (SELECT COUNT(*) FROM Tree WHERE farmer_id = OLD.farmer_id) * 10000
    WHERE farmer_id = OLD.farmer_id;
END //
DELIMITER ;

COMMIT;
