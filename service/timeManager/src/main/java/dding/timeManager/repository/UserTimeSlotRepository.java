package dding.timeManager.repository;

import dding.timeManager.entity.UserTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserTimeSlotRepository extends JpaRepository<UserTimeSlot, Long> {
    List<UserTimeSlot> findByUserId(String userId);
    boolean existsByUserIdAndDayOfWeekAndHour(String userId, int dayOfWeek, int hour);
}