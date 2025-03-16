package dding.timeManager.repository;


import dding.timeManager.entity.BandRoomSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BandRoomScheduleRepository extends JpaRepository<BandRoomSchedule, Long> {


    List<BandRoomSchedule> findByBandRoomIdAndDateBetween(Long bandRoomId, LocalDate from, LocalDate to);

    void deleteByBandRoomIdAndDate(Long bandRoomId, LocalDate parse);
}
