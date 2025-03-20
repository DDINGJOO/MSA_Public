package dding.timeManager.repository;


import dding.timeManager.entity.BandTimeSLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BandTimeSlotRepository  extends JpaRepository<BandTimeSLot ,Long> {


    boolean exisistByBandId(String bandId);

    List<BandTimeSLot> findAllByBandId(String bandId);

    List<BandTimeSLot>findByBandId(String bandId);


}
