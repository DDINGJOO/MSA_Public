package dding.timeManager.repository;

import dding.timeManager.entity.Studio.StudioReservationTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudioReservationTimeSlotRepository extends JpaRepository<StudioReservationTimeSlot, Long> {

    List<StudioReservationTimeSlot> findByStudioIdAndDate(String studioId, LocalDate date);
}
