CREATE TABLE band_room_week (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                band_room_id VARCHAR(255) NOT NULL,
                                day_of_week INT NOT NULL,
                                hour INT NOT NULL,
                                recurrence_pattern VARCHAR(20),
                                is_odd_week BOOLEAN,
                                is_closed BOOLEAN NOT NULL,
                                special_price INT
);

CREATE TABLE studio_week (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             studio_id VARCHAR(255) NOT NULL,
                             day_of_week INT NOT NULL,
                             hour INT NOT NULL,
                             recurrence_pattern VARCHAR(255),
                             is_odd_week BOOLEAN,
                             is_closed BOOLEAN NOT NULL,
                             special_price INT,
                             overridden BOOLEAN NOT NULL
);

CREATE TABLE studio_time_slot (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  studio_id VARCHAR(255) NOT NULL,
                                  date DATE NOT NULL,
                                  hour INT NOT NULL,
                                  is_closed BOOLEAN NOT NULL,
                                  special_price INT
);
CREATE TABLE holiday_slot (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              date DATE NOT NULL,
                              studio_id VARCHAR(255) NOT NULL,
                              hour INT NOT NULL,
                              is_closed BOOLEAN NOT NULL,
                              special_price INT
);
