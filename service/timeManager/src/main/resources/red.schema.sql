CREATE TABLE user_time_schedule (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                user_id VARCHAR(64) NOT NULL,
                                day_of_week ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') NOT NULL,
                                hour INT NOT NULL CHECK (hour >= 0 AND hour <= 23),
                                available BOOLEAN NOT NULL
);

CREATE TABLE bandroom_schedule (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   band_room_id BIGINT NOT NULL,
                                   date DATE NOT NULL,
                                   hour INT NOT NULL CHECK (hour >= 0 AND hour <= 23),
                                   available BOOLEAN NOT NULL,
                                   memo VARCHAR(255)
);

-- 유저 시간 조회 성능 향상용
CREATE INDEX idx_user_time_slot_user ON user_time_schedule(user_id, day_of_week, hour);

-- 합주실 주간 스케줄 탐색 최적화
CREATE INDEX idx_bandroom_schedule_band ON bandroom_schedule(band_room_id, date, hour);
