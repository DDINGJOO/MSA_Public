CREATE TABLE bandroom_time_slot (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    band_room_id VARCHAR(255) NOT NULL,
                                    day_of_week INT NOT NULL,
                                    hour INT NOT NULL,
                                    start_time TIME NOT NULL,
                                    end_time TIME NOT NULL,
                                    recurrence_pattern VARCHAR(50) NOT NULL, -- 예: WEEKLY, BIWEEKLY, MONTHLY
                                    is_closed BOOLEAN NOT NULL,
                                    is_odd_week BOOLEAN
);


CREATE TABLE special_closed_day (
                                    id VARCHAR(255) PRIMARY KEY,
                                    band_room_id VARCHAR(255) NOT NULL,
                                    date DATE NOT NULL,
                                    reason VARCHAR(255)
);


CREATE TABLE studio_time_slot (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  studio_id VARCHAR(255) NOT NULL,
                                  date DATE,
                                  day_of_week INT,
                                  hour INT NOT NULL,
                                  is_closed BOOLEAN NOT NULL,
                                  price INT,
                                  is_odd_week BOOLEAN,
                                  recurrence_pattern VARCHAR(50)
);

