package dding.timeManager.repository;


import dding.timeManager.entity.HolidatSlot.HolidaySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HolidaySlotRepository extends JpaRepository<HolidaySlot, Long> {

    HolidaySlot findByStudioIdAndDateAndHour(String studioId, LocalDate date, int hour);

    boolean existsByStudioIdAndDateAndHour(String studioId, LocalDate date, int hour);

}
