package dding.timeManager.repository;

import dding.timeManager.entity.BandRoom.SpecialClosedDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialClosedDayRepository extends JpaRepository<SpecialClosedDay, String> {

    List<SpecialClosedDay> findByBandRoomId(String bandRoomId);

    void deleteAllByBandRoomId(String bandRoomId);
}
