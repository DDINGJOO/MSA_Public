package dding.timeManager.repository;


import dding.timeManager.entity.Studio.StudioTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.net.Socket;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudioTimeSlotRepository  extends JpaRepository<StudioTimeSlot,Long> {
    StudioTimeSlot findByStudioIdAndDateAndHour(String studioId, LocalDate date, int hour);

    boolean existsByStudioIdAndDateAndHour(String studioId, LocalDate date, int hour);


}

