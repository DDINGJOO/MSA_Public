package dding.timeManager.repository;

import dding.timeManager.entity.Studio.StudioTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudioTimeSlotRepository  extends JpaRepository<StudioTimeSlot,String> {
    List<StudioTimeSlot> findByStudioIdAndDayOfWeekAndIsHolidayAndAvailableTrue(String studioId, int dayOfWeek, boolean isHoliday);
}
