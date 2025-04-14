package dding.timeManager.repository;

import dding.timeManager.entity.user.BandRoomSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BandRoomScheduleRepository extends JpaRepository<BandRoomSchedule, Long> {
    List<BandRoomSchedule> findByRoomId(String roomId);
    List<BandRoomSchedule> findByRoomIdAndDate(String roomId, LocalDate date);

    List<BandRoomSchedule> findByRoomIdAndDateAndAvailableTrue(String roomId, LocalDate date);

    List<BandRoomSchedule> findByRoomIdAndAvailableTrue(String roomId);

}
