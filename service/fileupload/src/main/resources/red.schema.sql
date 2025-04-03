CREATE TABLE image_file (
                            id VARCHAR(255) PRIMARY KEY,               -- 파일 ID (UUID)
                            original_name VARCHAR(255),                -- 업로드 당시 파일명
                            saved_name VARCHAR(255),                   -- 저장된 UUID_파일명
                            path VARCHAR(500),                         -- 저장 경로 (디스크상 풀 경로)
                            category VARCHAR(50),                      -- 카테고리 (profile, board, concert 등)
                            status ENUM('TEMP', 'CONFIRMED', 'DELETED') NOT NULL DEFAULT 'TEMP',  -- 이미지 상태
                            created_at DATETIME DEFAULT CURRENT_TIMESTAMP  -- 업로드 시각
);


CREATE TABLE profile_image (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               user_id VARCHAR(255) NOT NULL,
                               image_id VARCHAR(255) NOT NULL,
                               is_current BOOLEAN DEFAULT TRUE,
                               created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (image_id) REFERENCES images(id)
);


CREATE TABLE post_image (
                            id VARCHAR(255) PRIMARY KEY,
                            post_id VARCHAR(255) NOT NULL,
                            image_id VARCHAR(255),
                            sequence BIGINT,
                            created_at TIMESTAMP,

                            CONSTRAINT fk_post_image_image_file
                                FOREIGN KEY (image_id) REFERENCES images(id)
);
