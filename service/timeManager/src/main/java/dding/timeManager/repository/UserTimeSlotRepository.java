package dding.timeManager.repository;


import dding.timeManager.entity.UserTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserTimeSlotRepository extends JpaRepository<UserTimeSlot, Long> {

    List<UserTimeSlot> findByUserId(String userId);

    void deleteByUserId(String userId);

    // 특정 요일+시간 탐색 (정확 조회)
    @Query("SELECT s FROM UserTimeSlot s WHERE s.userId = :userId AND s.dayOfWeek = :day AND s.hour = :hour")
    Optional<UserTimeSlot> findSlotByUserIdAndDayAndHour(
            @Param("userId") String userId,
            @Param("day") DayOfWeek day,
            @Param("hour") int hour
    );

    // ✅ [통계 분석용] 요일+시간별 빈도 조회
    @Query("SELECT s.dayOfWeek, s.hour, COUNT(s.id) " +
            "FROM UserTimeSlot s WHERE s.available = true " +
            "GROUP BY s.dayOfWeek, s.hour ORDER BY COUNT(s.id) DESC")
    List<Object[]> countActiveSlotsGroupByDayAndHour();

}


