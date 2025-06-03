CREATE TABLE `address` (
                           `id` BIGINT NOT NULL AUTO_INCREMENT,
                           `address_type` VARCHAR(50) NOT NULL,
                           `reference_id` VARCHAR(255) NOT NULL,
                           `city` VARCHAR(100),
                           `district` VARCHAR(100),
                           `neighborhood` VARCHAR(100),
                           `road_address` VARCHAR(255),
                           `legal_dong_code` VARCHAR(50),
                           `latitude` DOUBLE,
                           `longitude` DOUBLE,
                           PRIMARY KEY (`id`),
                           KEY `idx_address_type` (`address_type`),
                           KEY `idx_reference_id` (`reference_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
