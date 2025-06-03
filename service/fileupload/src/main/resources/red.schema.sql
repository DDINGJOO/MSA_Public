CREATE TABLE image_file (
                            id VARCHAR(255) PRIMARY KEY,
                            original_name VARCHAR(255),
                            saved_name VARCHAR(255),
                            path VARCHAR(500),
                            category VARCHAR(50),
                            status ENUM('TEMP','CONFIRMED','DELETED') NOT NULL DEFAULT 'TEMP',
                            created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE profile_image (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               user_id VARCHAR(255) NOT NULL,
                               image_id VARCHAR(255) NOT NULL,
                               is_current BOOLEAN DEFAULT TRUE,
                               created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (image_id) REFERENCES image_file(id)
) ENGINE=InnoDB;

CREATE TABLE post_image (
                            id VARCHAR(255) PRIMARY KEY,
                            post_id VARCHAR(255) NOT NULL,
                            image_id VARCHAR(255),
                            sequence BIGINT,
                            created_at TIMESTAMP,
                            CONSTRAINT fk_post_image_image_file
                                FOREIGN KEY (image_id) REFERENCES image_file(id)
) ENGINE=InnoDB;
