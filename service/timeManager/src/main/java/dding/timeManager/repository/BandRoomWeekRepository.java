package dding.timeManager.repository;


import dding.timeManager.entity.BandRoom.BandRoomWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BandRoomWeekRepository extends JpaRepository<BandRoomWeek, Long> {

    BandRoomWeek findByBandRoomIdAndDayOfWeekAndHour(String studioId, int dayOfWeek, int hour);

    void deleteByBandRoomId(String bandRoomId);


    List<BandRoomWeek> findByBandRoomIdAndDayOfWeek(String bandRoomId, int dayOfWeek);


    void deleteByBandRoomIdAndDayOfWeek(String bandRoomId, int dayOfWeek);

    void deleteAllByBandRoomIdAndDayOfWeek(String bandRoomId, int dayOfWeek);


    List<BandRoomWeek> findAllByBandRoomId(String bandRoomId);


}
