package dding.timeManager.repository;

import dding.timeManager.entity.BandRoom.BandRoomTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BandRoomTimeSlotRepository extends JpaRepository<BandRoomTimeSlot, Long> {

    List<BandRoomTimeSlot> findByBandRoomId(String bandRoomId);

    void deleteAllByBandRoomId(String bandRoomId);
}
